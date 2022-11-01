package com.example.azureBlob.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.example.webclientDemo.dto.UploadedFileDTO;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.SharedAccessBlobPermissions;
import com.microsoft.azure.storage.blob.SharedAccessBlobPolicy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

@Service
@Slf4j
public class AzureServiceLayer {

  @Autowired private CloudBlobClient cloudBlobClient;

  @Value("${storage.StorageEndPoint}")
  private String StorageEndPoint;

  public List<UploadedFileDTO> uploadFileToBlob(List<MultipartFile> files) throws Exception {

    log.info("Uploading MultipartFIle To Blob ");

    List<UploadedFileDTO> filedetailListtoReturn = new ArrayList<>();
    BlobServiceClient blobServiceClient = getBlobServiceClient();
    BlobContainerClient blobContainerClient =
        blobServiceClient.getBlobContainerClient("mycontainer");

    for (MultipartFile file : files) {
      BlobClient client = blobContainerClient.getBlobClient(file.getName());

      if (client.exists()) {

        log.info("file with this name Already exist in Blob");
        BlobClient blobClient = blobContainerClient.getBlobClient(file.getName());

        UploadedFileDTO uploadedFileDTO =
            new UploadedFileDTO(file.getName(), blobClient.getBlobUrl(), file.getSize());
        filedetailListtoReturn.add(uploadedFileDTO);
      } else {
        // upload the file
        client.upload(file.getInputStream(), file.getSize());
        BlobClient blobClient = blobContainerClient.getBlobClient(file.getName());

        UploadedFileDTO uploadedFileDTO =
            new UploadedFileDTO(file.getName(), blobClient.getBlobUrl(), file.getSize());
        filedetailListtoReturn.add(uploadedFileDTO);

        log.info("File Uploaded Successfully");
      }
    }
    return filedetailListtoReturn;
  }

  private BlobServiceClient getBlobServiceClient() throws Exception {
    String sasToken = generateSasToken();
    return new BlobServiceClientBuilder()
        .endpoint(StorageEndPoint)
        .sasToken(sasToken)
        .buildClient();
  }

  private String generateSasToken() throws Exception {

    CloudBlobContainer container = cloudBlobClient.getContainerReference("mycontainer");

    Date expirationTime =
        Date.from(LocalDateTime.now().plusDays(7).atZone(ZoneOffset.UTC).toInstant());
    SharedAccessBlobPolicy sharedAccessPolicy = new SharedAccessBlobPolicy();
    sharedAccessPolicy.setPermissions(
        EnumSet.of(SharedAccessBlobPermissions.READ, SharedAccessBlobPermissions.WRITE));
    sharedAccessPolicy.setSharedAccessStartTime(new Date());
    sharedAccessPolicy.setSharedAccessExpiryTime(expirationTime);

    String sasToken = container.generateSharedAccessSignature(sharedAccessPolicy, null);

    return "?" + sasToken;
  }
}
