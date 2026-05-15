# python/config.example.py
# 复制此文件为 config.py 并填写真实配置

# 数据库配置
DB_CONFIG = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'password': 'your_password_here',
    'database': 'attendance_management',
    'charset': 'utf8mb4'
}

# MinIO配置
MINIO_CONFIG = {
    'endpoint': '127.0.0.1:9000',
    'access_key': 'your_access_key',
    'secret_key': 'your_secret_key',
    'secure': False
}