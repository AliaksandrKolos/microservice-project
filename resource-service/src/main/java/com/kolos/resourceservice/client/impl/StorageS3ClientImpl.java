package com.kolos.resourceservice.client.impl;

import com.kolos.resourceservice.client.StorageS3Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class StorageS3ClientImpl implements StorageS3Client {

    @Value("${client.s3.bucket}")
    private String bucket;

    private final S3Client s3Client;

    @Override
    public void upload(byte[] content, String key) {
        if (!bucketExists(bucket)) {
            createBucket(bucket);
        }
        PutObjectRequest request = PutObjectRequest.builder().bucket(bucket).key(key).build();
        s3Client.putObject(request, RequestBody.fromBytes(content));
        log.info("Uploaded content to s3: {}", key);
    }

    @Override
    public void delete(String key) {
        DeleteObjectRequest request = DeleteObjectRequest.builder().bucket(bucket).key(key).build();
        s3Client.deleteObject(request);
        log.info("Deleted key: {}", key);
    }

    @Override
    public byte[] download(String key) {
        GetObjectRequest request = GetObjectRequest.builder().bucket(bucket).key(key).build();
        try {
            return s3Client.getObject(request).readAllBytes();
        } catch (IOException e) {
            log.error("Failed to download key: {}. Error: {}", key, e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private void createBucket(String bucketName) {
        CreateBucketRequest bucketRequest = CreateBucketRequest.builder()
                .bucket(bucketName)
                .build();

        s3Client.createBucket(bucketRequest);

        S3Waiter s3Waiter = s3Client.waiter();
        HeadBucketRequest bucketRequestWait = HeadBucketRequest.builder()
                .bucket(bucketName)
                .build();

        WaiterResponse<HeadBucketResponse> waiterResponse = s3Waiter.waitUntilBucketExists(bucketRequestWait);
        waiterResponse.matched().response().map(Object::toString).ifPresent(log::info);
    }

    private boolean bucketExists(String bucketName) {
        HeadBucketRequest request = HeadBucketRequest.builder()
                .bucket(bucketName)
                .build();
        try {
            s3Client.headBucket(request);
            return true;
        } catch (NoSuchBucketException e) {
            return false;
        }
    }
}
