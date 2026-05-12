package com.mwh.controller;

import com.mwh.result.Result;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author admin
 * @Description 通用接口
 * @Date 2026/05/12/17:52
 * @Version 1.0
 */
@RestController
public class CommonController {
    @Autowired
    private FileStorageService fileStorageService;

    /**
     * 上传文件
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile file) {
        LocalDateTime now = LocalDateTime.now();
        String s = now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")) + "/";
        //调用注入的实例，然后setpath填自定义个上传路径，会自动将文件名改为随机名称
        //，这里的目的是上传到oss上
        FileInfo upload = fileStorageService.of(file).
                setPath(s)
                .upload();
        return Result.success(upload.getUrl());
    }
}
