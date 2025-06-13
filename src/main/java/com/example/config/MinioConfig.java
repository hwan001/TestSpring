package com.example.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.beans.factory.annotation.Value;


@Configuration
@PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class)
public class MinioConfig {
    
    @Value("${file.minio.server}")
    private String server;
    @Value("${file.minio.region}")
    private String region;
    @Value("${file.minio.userId}")
    private String userId;
    @Value("${file.minio.userPw}")
    private String userPw;


    @Bean
    public AmazonS3 amazonS3() {
        return AmazonS3ClientBuilder.standard()
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(server, region))
            .withCredentials(new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(userId, userPw)
            ))
            .withPathStyleAccessEnabled(true)
            .build();
    }
}
