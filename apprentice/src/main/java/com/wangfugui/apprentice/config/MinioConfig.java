package com.wangfugui.apprentice.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.net.URL;

@Configuration
public class MinioConfig {
	
	@Value("${minio.url}")
    private String url;
    @Value("${minio.accessKey}")
    private String accessKey;
    @Value("${minio.secretKey}")
    private String secretKey;
    
    @Bean
    public MinioClient getMinioClient() {
        try {
            return MinioClient.builder().endpoint(new URL(url))
                    .credentials(accessKey, secretKey).build();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
