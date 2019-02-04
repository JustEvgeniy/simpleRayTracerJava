import Geometry.Vec3;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main extends Canvas {
    public static void main(String[] args) {
        render();
    }

    public static final int width = 1024;
    public static final int height = 768;
    private static BufferedImage image;

    private static void render() {
        Vec3[] frameBuffer = new Vec3[width * height];

        System.out.println("Created frameBuffer");

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                frameBuffer[j + i * height] = new Vec3((double) i / width, (double) j / height, 0.0);
            }
        }

        System.out.println("Filled frameBuffer");

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int color = 0;
                color += 255 * frameBuffer[j + i * height].getX();
                color <<= 8;
                color += 255 * frameBuffer[j + i * height].getY();
                color <<= 8;
                color += 255 * frameBuffer[j + i * height].getZ();
                image.setRGB(i, j, color);
            }
        }

        showImage();
        saveImage();
    }

    private static void saveImage() {
        try {
            System.out.println("Saving image");

            ImageIO.write(image, "PNG", new File("out.png"));

            System.out.println("Image saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void showImage() {
        System.out.println("Displaying image");

        javax.swing.SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Rendered image");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Main());
        frame.setSize(width, height);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, this);
    }
}
