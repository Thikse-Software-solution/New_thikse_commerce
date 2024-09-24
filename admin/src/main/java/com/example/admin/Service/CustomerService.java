package com.example.admin.Service;

import com.example.admin.Entity.Customer;
import com.example.admin.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // Get all customers
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Get a customer by ID
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    // Add a new customer
    public Customer addCustomer(Customer customer) {
        // If no image is uploaded, generate a default image
        if (customer.getCustomerImg() == null || customer.getCustomerImg().length == 0) {
            byte[] generatedImage = generateDefaultImage(customer.getName());
            customer.setCustomerImg(generatedImage);
        }
        return customerRepository.save(customer);
    }

    // Update a customer
    public Customer updateCustomer(Long id, Customer customerDetails) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer != null) {
            customer.setName(customerDetails.getName());
            customer.setText(customerDetails.getText());

            // If a new image is provided, update it; otherwise, keep the old image or generate a new one
            if (customerDetails.getCustomerImg() != null && customerDetails.getCustomerImg().length > 0) {
                customer.setCustomerImg(customerDetails.getCustomerImg());
            } else if (customer.getCustomerImg() == null || customer.getCustomerImg().length == 0) {
                byte[] generatedImage = generateDefaultImage(customerDetails.getName());
                customer.setCustomerImg(generatedImage);
            }

            return customerRepository.save(customer);
        }
        return null;
    }

    // Delete a customer by ID
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public byte[] generateDefaultImage(String name) {
        try {
            int width = 100;
            int height = 100;
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = bufferedImage.createGraphics();

            // Fill the background with a solid color
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fillRect(0, 0, width, height);

            // Set text properties and write the initials
            g2d.setColor(Color.decode("#99015B"));
            g2d.setFont(new Font("Arial", Font.BOLD, 40));

            // Get initials from the name
            String initials = name != null && !name.isEmpty() ? name.substring(0, 1).toUpperCase() : "C";

            // Center the text
            FontMetrics fontMetrics = g2d.getFontMetrics();
            int stringWidth = fontMetrics.stringWidth(initials);
            int stringHeight = fontMetrics.getHeight(); // Changed to getHeight()

            // Calculate the coordinates to center the text with an adjustment
            int x = (width - stringWidth) / 2;
            int y = (height + stringHeight) / 2 - 8; // Adjusted for vertical centering

            // Draw the initials
            g2d.drawString(initials, x, y);

            // Dispose the graphics context
            g2d.dispose();

            // Convert to byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
