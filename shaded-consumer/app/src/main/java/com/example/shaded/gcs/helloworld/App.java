package com.example.shaded.gcs.helloworld;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import static java.nio.charset.StandardCharsets.UTF_8;

public class App {

    public static void main(String[] args) {
        String bucketName = "28248bd719526bd3";
        Storage storage = StorageOptions.getDefaultInstance().getService();
        Bucket bucket = storage.get(bucketName);
        System.out.println(bucket.toString());

        BlobId blobId = BlobId.of(bucketName, "test-object.3.txt");
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/plain").build();
        Blob blob = storage.create(blobInfo, "4 random data".getBytes(UTF_8));

        System.out.println(blob);
    }
}
