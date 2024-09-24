//package com.example.admin.Security;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.Statement;
//import java.io.FileWriter;
//import java.io.IOException;
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//public class ExportProductsToJson {
//
//    public static void main(String[] args) {
//        // Database connection details
//        String url = "jdbc:mysql://localhost:3306/ecom";
//        String user = "root";
//        String password = "1234";
//
//        // JSON output file path
//        String outputFilePath = "products.json";
//
//        // SQL query to fetch the product data
//        String sql = "SELECT id, category, trend, feature, special, mainimage, " +
//                "images, thumbnail, title, name, suitable, benefit, description, " +
//                "keybenefit, specialLine, size, mrp, price, discount, rating, favorited " +
//                "FROM products";
//
//        try (Connection connection = DriverManager.getConnection(url, user, password);
//             Statement statement = connection.createStatement();
//             ResultSet resultSet = statement.executeQuery(sql)) {
//
//            // JSON array to hold all products
//            JSONArray productsArray = new JSONArray();
//
//            // Loop through each row of the result set
//            while (resultSet.next()) {
//                // Create a JSON object for each product
//                JSONObject product = new JSONObject();
//
//                // Map database fields to JSON object
//                product.put("id", resultSet.getInt("id"));
//                product.put("category", resultSet.getString("category"));
//                product.put("trend", resultSet.getBoolean("trend"));
//                product.put("feature", resultSet.getBoolean("feature"));
//                product.put("special", resultSet.getBoolean("special"));
//                product.put("mainimage", resultSet.getString("mainimage"));
//
//                // Assuming images are stored as a comma-separated string in the database
//                String imagesString = resultSet.getString("images");
//                JSONArray imagesArray = new JSONArray();
//                for (String image : imagesString.split(",")) {
//                    imagesArray.put(image.trim());
//                }
//                product.put("images", imagesArray);
//
//                product.put("thumbnail", resultSet.getString("thumbnail"));
//                product.put("title", resultSet.getString("title"));
//                product.put("name", resultSet.getString("name"));
//                product.put("suitable", resultSet.getString("suitable"));
//                product.put("benefit", resultSet.getString("benefit"));
//                product.put("description", resultSet.getString("description"));
//                product.put("keybenefit", resultSet.getString("keybenefit"));
//                product.put("specialLine", resultSet.getString("specialLine"));
//                product.put("size", resultSet.getString("size"));
//                product.put("mrp", resultSet.getDouble("mrp"));
//                product.put("price", resultSet.getDouble("price"));
//                product.put("discount", resultSet.getDouble("discount"));
//                product.put("rating", resultSet.getInt("rating"));
//                product.put("favorited", resultSet.getBoolean("favorited"));
//
//                // Add the product JSON object to the array
//                productsArray.put(product);
//            }
//
//            // Write the JSON array to the output file
//            try (FileWriter fileWriter = new FileWriter(outputFilePath)) {
//                fileWriter.write(productsArray.toString(4)); // Pretty print with 4 spaces
//                System.out.println("Data has been exported to " + outputFilePath);
//            } catch (IOException e) {
//                System.err.println("Error writing to file: " + e.getMessage());
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
