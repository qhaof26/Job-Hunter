package vn.gqhao.jobhunter.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@Slf4j
public class FileService {
    @Value("${gqhao.upload-file.base-uri}")
    private String baseURI;

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

    public void store(MultipartFile file, String folder) throws IOException, URISyntaxException{
        //create unique filename
        String finalName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
        //save file
        URI uri = new URI(baseURI + folder + "/" + finalName);
        Path path = Paths.get(uri);
        try(InputStream inputStream = file.getInputStream()){
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
