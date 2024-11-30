package vn.gqhao.jobhunter.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.gqhao.jobhunter.exception.ErrorCode;
import vn.gqhao.jobhunter.exception.FileUploadException;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class FileService {
    @Value("${gqhao.upload-file.base-uri}")
    private String baseURI;
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList(".pdf", ".png", ".jpg", ".jpeg", ".doc", ".docx");

    public void createDirectory(String folder) throws URISyntaxException {
        URI uri = new URI(folder);
        Path path = Paths.get(uri);
        File tmpDir = new File(path.toString());
        if(!tmpDir.isDirectory()){
            try{
                Files.createDirectory(tmpDir.toPath());
                log.info("Create new directory successful, path: " + tmpDir.toPath());
            } catch (IOException ex){
                ex.printStackTrace();
            }
        } else{
            log.info("Skip making directory, already exists");
        }
    }

    public void saveFile(MultipartFile file, String folder, String finalName) throws IOException, URISyntaxException{
        // Validate file
        if(file == null || file.isEmpty()){
            throw new FileUploadException(ErrorCode.FILE_UPLOAD_NOT_EXISTED);
        }
        boolean isValid = isAllowedFileType(finalName);
        if(!isValid){
            throw new FileUploadException(ErrorCode.FILE_UPLOAD_INVALID);
        }
        // Save file
        URI uri = new URI(baseURI + folder + "/" + finalName);
        Path path = Paths.get(uri);
        try(InputStream inputStream = file.getInputStream()){
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    public boolean isAllowedFileType(String fileName) {
        return ALLOWED_EXTENSIONS.stream()
                .anyMatch(extension -> fileName.toLowerCase().endsWith(extension));
    }

    public long getFileLength(String fileName, String folder) throws URISyntaxException{
        URI uri = new URI(baseURI + folder + "/" + fileName);
        Path path = Paths.get(uri);
        File tmpDir = new File(path.toString());
        if(!tmpDir.exists() || tmpDir.isDirectory()){
            return 0;
        }
        return tmpDir.length();
    }

    public InputStreamResource getResource(String fileName, String folder) throws URISyntaxException, FileNotFoundException{
        if(fileName == null || fileName.isEmpty()){
            throw new FileUploadException(ErrorCode.FILE_UPLOAD_NOT_EXISTED);
        }
        long fileLength = getFileLength(fileName, folder);
        if(fileLength == 0){
            throw new FileUploadException(ErrorCode.FILE_UPLOAD_NOT_EXISTED);
        }

        URI uri = new URI(baseURI + folder + "/" + fileName);
        Path path = Paths.get(uri);
        File file = new File(path.toString());
        return new InputStreamResource(new FileInputStream(file));

    }
}
