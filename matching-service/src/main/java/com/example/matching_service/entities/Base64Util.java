package com.example.matching_service.entities;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class Base64Util {

    public static String encodeImageToBase64(String imagePath) {
        try {
            byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
