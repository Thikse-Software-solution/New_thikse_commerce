package com.example.admin.Service;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class ProfileImageService {

    public byte[] generateProfileImage(String name, String email) {
        String initials = (name.charAt(0) + "" + email.charAt(0)).toUpperCase();

        // Image dimensions
        int width = 200;
        int height = 200;

        // Create the image
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        // Set background color
        g2d.setColor(Color.decode("#99015B"));
        g2d.fillRect(0, 0, width, height);

        // Set font and color for initials
        g2d.setFont(new Font("Arial", Font.BOLD, 100));
        g2d.setColor(Color.WHITE);

        // Get font metrics for centering the initials
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int stringWidth = fontMetrics.stringWidth(initials);
        int stringHeight = fontMetrics.getAscent();

        // Draw initials in the center
        g2d.drawString(initials, (width - stringWidth) / 2, (height + stringHeight) / 2 - 10);

        g2d.dispose();

        // Convert the image to a byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "png", baos);
            return baos.toByteArray();  // Return byte array
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
