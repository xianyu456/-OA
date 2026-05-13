package com.mwh.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Author admin
 * @Description 调用python启动人脸识别
 * @Date 2026/05/12/21:36
 * @Version 1.0
 */
@RestController
@RequestMapping("/face")
public class FaceController {

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/face-detect")
    public String faceDetect() {
        String pythonUrl = "http://localhost:5000/detect";
        return restTemplate.postForObject(pythonUrl, null, String.class);
    }
}
