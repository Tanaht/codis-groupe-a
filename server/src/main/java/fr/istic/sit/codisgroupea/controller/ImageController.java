package fr.istic.sit.codisgroupea.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class ImageController {

    @GetMapping(value = "/images/{name}", produces = MediaType.IMAGE_JPEG_VALUE )
    public byte[] getImage(@PathVariable("name") String name){
        Path path = Paths.get("./images/"+name);
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
