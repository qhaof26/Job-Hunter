package vn.gqhao.jobhunter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.gqhao.jobhunter.dto.response.FileUploadResponse;
import vn.gqhao.jobhunter.exception.ErrorCode;
import vn.gqhao.jobhunter.exception.FileUploadException;
import vn.gqhao.jobhunter.service.FileService;
import vn.gqhao.jobhunter.util.annotation.ApiMessage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class FileController {
    private final FileService fileService;
    @Value("${gqhao.upload-file.base-uri}")
    private String baseURI;

    @PostMapping("/files")
    @ApiMessage("Upload single file")
    public ResponseEntity<FileUploadResponse> upload(
            @RequestParam(name = "file") MultipartFile file,
            @RequestParam("folder") String folder
            ) throws URISyntaxException, IOException, FileUploadException {

        String fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
        // Save file
        fileService.createDirectory(baseURI + folder);
        fileService.saveFile(file, folder, fileName);

        FileUploadResponse fileUploadResponse = FileUploadResponse.builder()
                .fileName(fileName)
                .uploadedAt(Instant.now())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(fileUploadResponse);
    }

    @GetMapping("files")
    @ApiMessage("Download single file")
    public ResponseEntity<Resource> download(
            @RequestParam("fileName") String fileName,
            @RequestParam("folder") String folder
    ) throws URISyntaxException, FileNotFoundException {

        InputStreamResource resource = this.fileService.getResource(fileName, folder);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentLength(fileService.getFileLength(fileName, folder))
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
