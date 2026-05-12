package com.mwh;

import org.dromara.x.file.storage.spring.EnableFileStorage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author admin
 * @Description 考勤系统
 * @Date 2026/05/12/08:15
 * @Version 1.0
 */
@SpringBootApplication
@EnableFileStorage
public class KaoQinApplication {
    public static void main(String[] args) {
        SpringApplication.run(KaoQinApplication.class, args);
    }
}
