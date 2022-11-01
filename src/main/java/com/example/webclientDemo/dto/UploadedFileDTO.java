package com.example.webclientDemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadedFileDTO {

    private String fileName;
    private String fileUrl;
    private long fileSize;
}
