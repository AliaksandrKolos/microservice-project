package com.kolos.resourceservice.client;

public interface StorageS3Client {


    void upload(byte[] content, String key);

    void delete(String key);

    byte[] download(String key);
}
