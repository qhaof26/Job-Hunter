package vn.gqhao.jobhunter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vn.gqhao.jobhunter.service.FileService;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class FileController {
    private final FileService fileService;
    @Value("${gqhao.upload-file.base-uri}")
    private String baseURI;

    @PostMapping("/files")
    public String upload(
            @RequestParam("file")MultipartFile file,
            @RequestParam("folder") String folder
            ) throws URISyntaxException, IOException {
        fileService.createDirectory(baseURI + folder);
        fileService.store(file, folder);
        return file.getOriginalFilename() + folder;
    }
}
