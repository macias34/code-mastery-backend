package com.macias34.codemastery.course.service;

import com.macias34.codemastery.exception.BadRequestException;
import org.apache.tika.Tika;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    private final Path videosRoot = Paths.get("src/main/resources/uploads");
    private final Path avatarsRoot = Paths.get("src/main/resources/avatars");

    public void init() {
        try {
            Files.createDirectories(videosRoot);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    public void save(MultipartFile file, Integer fileId, String expectedExtension) {

        try {
            String extension = getExtension(file.getOriginalFilename());

            Path root = videosRoot;

            Tika tika = new Tika();
            String mimeType = tika.detect(file.getInputStream());

            if(mimeType.startsWith("image/")){
                root = avatarsRoot;
            }else if(!extension.equalsIgnoreCase(expectedExtension)){
                throw new BadRequestException();
            }

            Path filePath = root.resolve(fileId.toString()+extension );
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            if (e instanceof BadRequestException) {
                throw new BadRequestException(String.format("File must have %s extension",expectedExtension));
            }
            if (e instanceof FileAlreadyExistsException) {
                throw new BadRequestException("A file of that name already exists.");
            }
            if(e instanceof FileSizeLimitExceededException){
                throw new BadRequestException("File size limit achieved (50MB)");
            }
            throw new RuntimeException(e.getMessage());
        }
    }

    public Resource load(String filename) {
        try {
            Path root = getRootPath(filename);

            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public void deleteByfileName(String fileName) {
        Path root = getRootPath(fileName);
        try{
            FileSystemUtils.deleteRecursively(root.resolve(fileName));
        }catch (Exception e){
            throw new RuntimeException("Cannot remove file");
        }
    }

    private Path getRootPath(String fileName){
        Path root = videosRoot;

        Tika tika = new Tika();
        String mimeType = tika.detect(fileName);

        if(mimeType.startsWith("image/")){
            root = avatarsRoot;
        }

        return root;
    }

    public String getExtension(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }

}
