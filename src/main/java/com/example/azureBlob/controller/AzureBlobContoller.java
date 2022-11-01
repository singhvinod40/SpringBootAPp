package com.example.azureBlob.controller;

import com.example.azureBlob.service.AzureServiceLayer;
import com.example.webclientDemo.dto.UploadedFileDTO;
import com.example.webclientDemo.model.Submitdocuments404ResponseDto;
import com.example.webclientDemo.model.Submitdocuments500ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.example.utill.Constant.*;

@RestController
@Slf4j
@RequestMapping("/api")
public class AzureBlobContoller {

    @Autowired
    private AzureServiceLayer azureServiceLayer;

    @Operation(summary  = "Upload the multipart file ",responses = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched document", content = {
                    @Content( mediaType = "application/json",schema = @Schema(implementation = UploadedFileDTO.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(implementation = UploadedFileDTO.class))}),
            @ApiResponse(responseCode = "404",description = "Resource not found error",content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Submitdocuments404ResponseDto.class)),
                    @Content( mediaType = "application/xml",schema = @Schema(implementation = Submitdocuments404ResponseDto.class))}),
            @ApiResponse(responseCode = "500",description = "Internal server error",content = {
                    @Content( mediaType = "application/json",schema = @Schema(implementation = Submitdocuments500ResponseDto.class)),
                    @Content(mediaType = "application/xml",schema = @Schema(implementation = Submitdocuments500ResponseDto.class))})
    })

    @Parameter(name = "fileType",description = "Specify what is the serviceType",required = true,style = ParameterStyle.FORM)
    @Parameter(name = "description",description = "describe the uploaded file usecase",required = true,allowEmptyValue = true,style = ParameterStyle.SIMPLE)
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/v1/{receiptId}/documents",
            produces = {"application/json", "application/xml"},
            consumes = {"multipart/form-data"})

    public ResponseEntity<List<UploadedFileDTO>> uploadMultipartFileToBlob(
            @RequestParam("files") List<MultipartFile> files, HttpServletRequest request)throws Exception{

        log.info("upload multipartFiles to Azure blob"+CONTROLLER+START);

        try{
            List<UploadedFileDTO> dataToReturn = azureServiceLayer.uploadFileToBlob(files);

            return ResponseEntity.ok().body(dataToReturn);

        }catch (Exception e) {
            log.error("Exception Occoured"+CONTROLLER +END);
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ArrayList<>());
        }

    }
}
