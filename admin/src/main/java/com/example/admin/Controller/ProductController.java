package com.example.admin.Controller;

import com.example.admin.DTO.CardDTO;
import com.example.admin.DTO.ProductDTO;
import com.example.admin.Entity.Card;
import com.example.admin.Entity.Product;
import com.example.admin.Service.ProductService;
import com.example.admin.Service.ReviewService;
import jakarta.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "http://192.168.1.20:4200")
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ReviewService reviewService;

    // Get all products
    @GetMapping("/getall")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // Get product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Add product with image uploads
    @PostMapping("/add")
    public ResponseEntity<?> addProduct(
            @RequestPart("product") Product product,
            @RequestPart(value = "mainImage", required = false) MultipartFile mainImageFile,
            @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnailFile,
            @RequestPart(value = "threeDImages", required = false) List<MultipartFile> threeDImageFiles,
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            @RequestPart(value = "cardImages", required = false) List<MultipartFile> cardImageFiles) {
        try {
            handleImageUpdates(product, mainImageFile, thumbnailFile, threeDImageFiles, images);
            handleCardImageUpdates(product, cardImageFiles);

            Product savedProduct = productService.addProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while processing images: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while adding product: " + e.getMessage());
        }
    }

    // Update product with image uploads
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @RequestPart("product") Product updatedProduct,
            @RequestPart(value = "mainImage", required = false) MultipartFile mainImageFile,
            @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnailFile,
            @RequestPart(value = "threeDImages", required = false) List<MultipartFile> threeDImageFiles,
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            @RequestPart(value = "cardImages", required = false) List<MultipartFile> cardImageFiles) {
        try {
            Product existingProduct = productService.getProductById(id);
            updateProductDetails(existingProduct, updatedProduct);
            handleImageUpdates(existingProduct, mainImageFile, thumbnailFile, threeDImageFiles, images);
            handleCardImageUpdates(existingProduct, cardImageFiles);

            Product savedProduct = productService.saveProduct(existingProduct);
            return ResponseEntity.ok(savedProduct);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while processing images: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating product: " + e.getMessage());
        }
    }

    // Delete product
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok("Product deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product not found with ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting product: " + e.getMessage());
        }
    }

    @GetMapping("/json")
    public ResponseEntity<List<ProductDTO>> getProductsAsJson() {
        try {
            List<Product> products = productService.getAllProducts();
            List<ProductDTO> jsonOutput = products.stream().map(this::convertToDTO).collect(Collectors.toList());
            return ResponseEntity.ok(jsonOutput);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    // Handle image updates (convert MultipartFile to byte[])
    private void handleImageUpdates(Product product, MultipartFile mainImageFile, MultipartFile thumbnailFile,
                                    List<MultipartFile> threeDImageFiles, List<MultipartFile> images) throws IOException {
        if (mainImageFile != null && !mainImageFile.isEmpty()) {
            product.setMainimage(mainImageFile.getBytes());
        }
        if (thumbnailFile != null && !thumbnailFile.isEmpty()) {
            product.setThumbnail(thumbnailFile.getBytes());
        }
        if (threeDImageFiles != null && !threeDImageFiles.isEmpty()) {
            product.setThreeDImages(threeDImageFiles.stream()
                    .map(file -> {
                        try {
                            return file.getBytes();
                        } catch (IOException e) {
                            throw new RuntimeException("Failed to convert 3D image", e);
                        }
                    })
                    .collect(Collectors.toList()));
        }
        if (images != null && !images.isEmpty()) {
            product.setImages(images.stream()
                    .map(file -> {
                        try {
                            return file.getBytes();
                        } catch (IOException e) {
                            throw new RuntimeException("Failed to convert image", e);
                        }
                    })
                    .collect(Collectors.toList()));
        }
    }

    // Handle card image updates (convert MultipartFile to byte[])
    private void handleCardImageUpdates(Product product, List<MultipartFile> cardImageFiles) throws IOException {
        if (cardImageFiles != null && !cardImageFiles.isEmpty()) {
            List<Card> cards = product.getCards();
            if (cards != null) {
                // Ensure cardImageFiles size matches cards size
                for (int i = 0; i < Math.min(cards.size(), cardImageFiles.size()); i++) {
                    MultipartFile cardImageFile = cardImageFiles.get(i);
                    if (cardImageFile != null && !cardImageFile.isEmpty()) {
                        cards.get(i).setImage(cardImageFile.getBytes());
                    }
                }
            }
        }
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setCategory(product.getCategory());
        dto.setFavorited(product.getFavorited());
        dto.setShine(product.getShine());
        dto.setSheShine(product.getSheShine());
        dto.setSubcategory(product.getSubcategory());
        dto.setMainimage(product.getMainimage());
        dto.setCards(convertCardsToDTO(product.getCards()));
        dto.setImages(product.getImages());
        dto.setThreeDImages(product.getThreeDImages());
        dto.setThumbnail(product.getThumbnail());
        dto.setTitle(product.getTitle());
        dto.setName(product.getName());
        dto.setBenefit(product.getBenefit());
        dto.setSuitable(product.getSuitable());
        dto.setDescription(product.getDescription());
        dto.setKeybenefit(product.getKeybenefit());
        dto.setHowToUse(product.getHowToUse());
        dto.setIngredients(product.getIngredients());
        dto.setSize(product.getSize());
        dto.setMrp(product.getMrp());
        dto.setPrice(product.getPrice());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setDiscount(product.getDiscount());
        dto.setAverageRating(product.getAverageRating());
        dto.setFeature(product.getFeature());
        dto.setTrend(product.getTrend());
        dto.setSpecial(product.getSpecial());
        return dto;
    }

    private List<CardDTO> convertCardsToDTO(List<Card> cards) {
        return cards.stream().map(card -> {
            CardDTO cardDTO = new CardDTO();
            cardDTO.setId(card.getId());
           cardDTO.setText(card.getText());
           cardDTO.setTitle(card.getTitle());
            cardDTO.setImage(card.getImage());

            return cardDTO;
        }).collect(Collectors.toList());
    }

    private void updateProductDetails(Product existingProduct, Product updatedProduct) {
        existingProduct.setTitle(updatedProduct.getTitle());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setDiscount(updatedProduct.getDiscount());
        existingProduct.setStockQuantity(updatedProduct.getStockQuantity());
        existingProduct.setCategory(updatedProduct.getCategory());
        existingProduct.setSubcategory(updatedProduct.getSubcategory());
        existingProduct.setSize(updatedProduct.getSize());
        existingProduct.setFeature(updatedProduct.getFeature());
        existingProduct.setBenefit(updatedProduct.getBenefit());
        existingProduct.setSuitable(updatedProduct.getSuitable());
        existingProduct.setKeybenefit(updatedProduct.getKeybenefit());
        existingProduct.setHowToUse(updatedProduct.getHowToUse());
        existingProduct.setIngredients(updatedProduct.getIngredients());
        existingProduct.setAverageRating(updatedProduct.getAverageRating());
        existingProduct.setCards(updatedProduct.getCards());
        existingProduct.setThreeDImages(updatedProduct.getThreeDImages());
        existingProduct.setImages(updatedProduct.getImages());
        existingProduct.setThumbnail(updatedProduct.getThumbnail());
        existingProduct.setMainimage(updatedProduct.getMainimage());
        existingProduct.setTrend(updatedProduct.getTrend());
        existingProduct.setSpecial(updatedProduct.getSpecial());
    }
    // Reduce product stock by a specified amount
    @PutMapping("/update-stock/{id}")
    public ResponseEntity<?> updateStock(
            @PathVariable Long id,
            @RequestParam("reduceQuantity") int reduceQuantity) {
        try {
            Product existingProduct = productService.getProductById(id);

            // Check if stock is available
            if (existingProduct.getStockQuantity() >= reduceQuantity) {
                // Reduce the stock
                existingProduct.setStockQuantity(existingProduct.getStockQuantity() - reduceQuantity);
                Product savedProduct = productService.saveProduct(existingProduct);
                return ResponseEntity.ok(savedProduct);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Not enough stock available. Current stock: " + existingProduct.getStockQuantity());
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product not found with ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating product stock: " + e.getMessage());
        }
    }


}
