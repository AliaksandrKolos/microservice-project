package com.kolos.resourceservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;
import java.time.Duration;

@Configuration
public class ClientS3Configuration {

    @Value("${clients.S3.Url}")
    private String S3ClientUrl;

    @Value("${clients.s3.access_key}")
    private String s3AccessKey;

    @Value("${clients_s3_secret_key}")
    private String s3SecretKey;

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.AF_SOUTH_1)
                .endpointOverride(URI.create(S3ClientUrl))
                .forcePathStyle(true)
                .httpClientBuilder(
                        ApacheHttpClient.builder()
                                .maxConnections(100)
                                .connectionTimeout(Duration.ofSeconds(7))
                )
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(s3AccessKey, s3SecretKey)
                        )
                )
                .build();
    }
}
