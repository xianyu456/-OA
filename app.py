# face_server.py
from flask import Flask, jsonify
import cv2
import numpy as np
import time
import pymysql
from minio import Minio

app = Flask(__name__)

try:
    from config import DB_CONFIG, MINIO_CONFIG
except ImportError:
    print("错误: 找不到 config.py，请复制 config.example.py 为 config.py 并填写配置")
    exit(1)


# ==================== 使用配置文件 ====================
minio_client = Minio(
    MINIO_CONFIG['endpoint'],
    access_key=MINIO_CONFIG['access_key'],
    secret_key=MINIO_CONFIG['secret_key'],
    secure=MINIO_CONFIG['secure']
)

def get_db_connection():
    return pymysql.connect(**DB_CONFIG)


# ==================== 全局变量 ====================
face_detector = cv2.CascadeClassifier(cv2.data.haarcascades + 'haarcascade_frontalface_default.xml')
recognizer = cv2.face.LBPHFaceRecognizer_create()
known_face_ids = []
known_face_names = []
known_face_images = []
recognizer_trained = False
last_load_time = None


def get_image_from_minio(url):
    """从MinIO URL下载图片"""
    try:
        path = url.replace('http://127.0.0.1:9000/', '')
        if '/' in path:
            parts = path.split('/', 1)
            bucket = parts[0]
            object_name = parts[1]
        else:
            return None

        response = minio_client.get_object(bucket, object_name)
        data = response.read()
        response.close()
        response.release_conn()

        img_array = np.frombuffer(data, np.uint8)
        img = cv2.imdecode(img_array, cv2.IMREAD_GRAYSCALE)
        return img
    except Exception as e:
        print(f"  MinIO下载失败: {e}")
        return None


def extract_face(img):
    """从图片中提取人脸"""
    if img is None:
        return None
    detected = face_detector.detectMultiScale(img, 1.1, 5)
    if len(detected) > 0:
        x, y, w, h = detected[0]
        face_roi = img[y:y + h, x:x + w]
        return cv2.resize(face_roi, (200, 200))
    return None


def full_train():
    """全量训练（首次启动时调用）"""
    global known_face_ids, known_face_names, known_face_images, recognizer_trained, last_load_time

    conn = get_db_connection()
    cursor = conn.cursor(pymysql.cursors.DictCursor)

    try:
        sql = """
            SELECT id, real_name, face_photo_url
            FROM user
            WHERE face_registered = 1
              AND face_photo_url IS NOT NULL
              AND face_photo_url != ''
              AND status = 1
        """
        cursor.execute(sql)
        users = cursor.fetchall()

        known_face_ids = []
        known_face_names = []
        known_face_images = []

        for user in users:
            print(f"加载用户人脸: {user['real_name']} (ID:{user['id']})")

            img = get_image_from_minio(user['face_photo_url'])
            face_roi = extract_face(img)

            if face_roi is not None:
                known_face_ids.append(user['id'])
                known_face_names.append(user['real_name'])
                known_face_images.append(face_roi)
                print(f"  ✓ 成功")
            else:
                print(f"  ✗ 未检测到人脸或下载失败")

        if known_face_images:
            recognizer.train(known_face_images, np.array(known_face_ids))
            recognizer_trained = True
            last_load_time = time.time()
            print(f"\n全量训练完成，共 {len(known_face_ids)} 人")
        else:
            print("\n没有可用的人脸数据")

    finally:
        cursor.close()
        conn.close()


def load_newest_face():
    """增量加载：只加载最近添加的一个用户"""
    global known_face_ids, known_face_names, known_face_images, recognizer_trained, last_load_time

    conn = get_db_connection()
    cursor = conn.cursor(pymysql.cursors.DictCursor)

    try:
        sql = """
            SELECT id, real_name, face_photo_url, updated_at
            FROM user
            WHERE face_registered = 1
              AND face_photo_url IS NOT NULL
              AND face_photo_url != ''
              AND status = 1
            ORDER BY updated_at DESC
            LIMIT 1
        """
        cursor.execute(sql)
        user = cursor.fetchone()

        if not user:
            print("没有已注册的用户")
            return

        if user['id'] in known_face_ids:
            print(f"用户 {user['real_name']} (ID:{user['id']}) 已存在，无需重新加载")
            return

        print(f"加载新用户: {user['real_name']} (ID:{user['id']})")

        img = get_image_from_minio(user['face_photo_url'])
        face_roi = extract_face(img)

        if face_roi is not None:
            known_face_ids.append(user['id'])
            known_face_names.append(user['real_name'])
            known_face_images.append(face_roi)

            recognizer.train(known_face_images, np.array(known_face_ids))
            recognizer_trained = True
            last_load_time = time.time()
            print(f"  ✓ 增量训练完成，共 {len(known_face_ids)} 人")
        else:
            print(f"  ✗ 未检测到人脸或下载失败")

    finally:
        cursor.close()
        conn.close()


@app.route('/detect', methods=['POST'])
def detect_face():
    if not recognizer_trained:
        full_train()

    if not recognizer_trained:
        return jsonify({
            "success": False, "matched": False,
            "user_id": None, "real_name": None,
            "message": "没有已注册的人脸数据"
        })

    camera = cv2.VideoCapture(0)

    try:
        for i in range(5):
            camera.read()
            time.sleep(0.1)

        start_time = time.time()
        timeout = 15

        while time.time() - start_time < timeout:
            ret, frame = camera.read()
            if not ret:
                continue

            gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
            faces = face_detector.detectMultiScale(gray, 1.1, 5)

            if len(faces) > 0:
                x, y, w, h = faces[0]
                face_roi = gray[y:y + h, x:x + w]
                face_roi = cv2.resize(face_roi, (200, 200))

                label, confidence = recognizer.predict(face_roi)

                if confidence < 100:
                    try:
                        index = known_face_ids.index(label)
                        matched_name = known_face_names[index]

                        camera.release()
                        cv2.destroyAllWindows()

                        return jsonify({
                            "success": True, "matched": True,
                            "user_id": label, "real_name": matched_name,
                            "message": f"人脸匹配成功: {matched_name}"
                        })
                    except:
                        pass

                camera.release()
                cv2.destroyAllWindows()

                return jsonify({
                    "success": True, "matched": False,
                    "user_id": None, "real_name": None,
                    "message": "检测到人脸但未匹配"
                })

            cv2.imshow('Face Recognition', frame)

            if cv2.waitKey(1) & 0xFF == ord('q'):
                break

        camera.release()
        cv2.destroyAllWindows()

        return jsonify({
            "success": False, "matched": False,
            "user_id": None, "real_name": None,
            "message": "未检测到人脸"
        })

    except Exception as e:
        return jsonify({
            "success": False, "matched": False,
            "user_id": None, "real_name": None,
            "message": f"错误: {str(e)}"
        })
    finally:
        camera.release()
        cv2.destroyAllWindows()


@app.route('/reload', methods=['POST'])
def reload_faces():
    """增量加载最新用户"""
    load_newest_face()
    return jsonify({
        "success": True,
        "count": len(known_face_ids),
        "users": known_face_names,
        "message": f"当前共 {len(known_face_ids)} 人: {known_face_names}"
    })


@app.route('/health', methods=['GET'])
def health():
    return jsonify({
        "status": "running",
        "trained": recognizer_trained,
        "count": len(known_face_ids),
        "users": known_face_names
    })


if __name__ == '__main__':
    print("=" * 50)
    print("人脸识别服务启动中...")
    full_train()
    print("\n服务地址: http://0.0.0.0:5000")
    print("检测接口: POST /detect")
    print("增量加载: POST /reload")
    print("健康检查: GET /health")
    print("=" * 50)
    app.run(host='0.0.0.0', port=5000)