//package com.example.admin.Controller;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/api/images")
//public class ImageController {
//
//    private final String uploadDir = "src/main/resources/static/images/"; // Directory to save images
//
//    @PostMapping("/upload")
//    public ResponseEntity<Map<String, String>> uploadImages(
//            @RequestParam(value = "mainImage", required = false) MultipartFile mainImage,
//            @RequestParam(value = "additionalImages", required = false) MultipartFile[] additionalImages,
//            @RequestParam(value = "threeDImages", required = false) MultipartFile[] threeDImages,
//            @RequestParam(value = "thumbnail", required = false) MultipartFile thumbnail,
//            @RequestParam Map<String, MultipartFile[]> cardImages // Handles card image files
//    ) {
//        Map<String, String> fileUrls = new HashMap<>();
//
//        try {
//            // Process main image
//            if (mainImage != null) {
//                String mainImageUrl = saveFile(mainImage, "mainImage");
//                fileUrls.put("mainImage", mainImageUrl);
//            }
//
//            // Process additional images
//            if (additionalImages != null) {
//                for (MultipartFile file : additionalImages) {
//                    String url = saveFile(file, "additionalImages");
//                    fileUrls.put(file.getOriginalFilename(), url);
//                }
//            }
//
//            // Process 3D images
//            if (threeDImages != null) {
//                for (MultipartFile file : threeDImages) {
//                    String url = saveFile(file, "threeDImages");
//                    fileUrls.put(file.getOriginalFilename(), url);
//                }
//            }
//
//            // Process thumbnail image
//            if (thumbnail != null) {
//                String thumbnailUrl = saveFile(thumbnail, "thumbnail");
//                fileUrls.put("thumbnail", thumbnailUrl);
//            }
//
//            // Process card images
//            cardImages.forEach((key, files) -> {
//                for (MultipartFile file : files) {
//                    try {
//                        String url = saveFile(file, key);
//                        fileUrls.put(file.getOriginalFilename(), url);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//
//            return ResponseEntity.ok(fileUrls);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }
//
//    // Helper method to save files
//    private String saveFile(MultipartFile file, String folder) throws IOException {
//        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
//        Path path = Paths.get(uploadDir + folder + "/" + fileName); // Save in subdirectories
//        Files.createDirectories(path.getParent()); // Ensure directories exist
//        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
//        return "/images/" + folder + "/" + fileName; // Return path to access the image
//    }
//}
