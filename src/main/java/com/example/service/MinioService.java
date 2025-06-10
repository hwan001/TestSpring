package com.example.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.PropertySource;
import org.springframework.beans.factory.annotation.Value;

import com.example.config.YamlPropertySourceFactory;

import java.io.InputStream;


@Service
@PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class)
public class MinioService {

    @Value("${file.minio.bucketName}")
    private String BUCKET_NAME;

    @Autowired
    private AmazonS3 amazonS3;

    public String uploadFile(String fileName, InputStream input, long contentLength, String contentType) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(contentLength);
        metadata.setContentType(contentType);

        PutObjectRequest request = new PutObjectRequest(BUCKET_NAME, fileName, input, metadata);
        amazonS3.putObject(request);
        return amazonS3.getUrl(BUCKET_NAME, fileName).toString();
    }

    public S3Object downloadFile(String fileName) {
        return amazonS3.getObject(BUCKET_NAME, fileName);
    }
}
