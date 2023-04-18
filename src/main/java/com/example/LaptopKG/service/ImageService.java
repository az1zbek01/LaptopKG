package com.example.LaptopKG.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.LaptopKG.exception.FileEmptyException;
import com.example.LaptopKG.model.Laptop;
import com.example.LaptopKG.repository.LaptopRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final LaptopRepository laptopRepository;

    public ResponseEntity<String> saveForLaptop(Long laptopId, MultipartFile file) throws IOException {
        if(laptopRepository.existsById(laptopId)){
            Laptop laptop = laptopRepository.findById(laptopId).get();
            laptop.setImageUrl(saveImage(file));
            laptopRepository.save(laptop);
            return ResponseEntity.ok("Image was saved");
        }else{
            return ResponseEntity.badRequest().body("Laptop with id " + laptopId + " wasn't found");
        }
    }


    public String saveImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new FileEmptyException("File is empty");
        }

        final String urlKey = "cloudinary://753949556892917:SCszCjA1duCgeAaMxDP-7Qq3dP8@dja0nqat2";

        File saveFile = Files.createTempFile(
                        System.currentTimeMillis() + "",
                        Objects.requireNonNull
                                        (file.getOriginalFilename(), "File must have an extension")
                                .substring(file.getOriginalFilename().lastIndexOf("."))
                )
                .toFile();

        file.transferTo(saveFile);

        Cloudinary cloudinary = new Cloudinary((urlKey));

        Map upload = cloudinary.uploader().upload(saveFile, ObjectUtils.emptyMap());

        return (String) upload.get("url");
    }

}
