package com.user.management.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MinioService {

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucketName;

    public String upload(MultipartFile image) throws Exception {

        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(image.getOriginalFilename())
                        .stream(image.getInputStream(), image.getSize(), -1)
                        .contentType(image.getContentType())
                        .build()
        );

        return "http://localhost:9000/" + bucketName + "/" + image.getOriginalFilename();
    }
}
