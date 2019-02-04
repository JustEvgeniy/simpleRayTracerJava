import Geometry.Vec3;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Canvas {
    public static final int width = 1024;
    public static final int height = 768;
    private static BufferedImage image;

    public static void main(String[] args) {
        Material ivory = new Material(new Vec3(0.4, 0.4, 0.3));
        Material redRubber = new Material(new Vec3(0.3, 0.1, 0.1));

        List<SceneObject> scene = new ArrayList<>();
        scene.add(new Sphere(new Vec3(-3, 0, -16), 2, ivory));
        scene.add(new Sphere(new Vec3(-1, -1.5, -12), 2, redRubber));
        scene.add(new Sphere(new Vec3(1.5, -0.5, -18), 3, redRubber));
        scene.add(new Sphere(new Vec3(7, 5, -18), 4, ivory));

        List<Light> lights = new ArrayList<>();
        lights.add(new Light(new Vec3(-20, 20, 20), 1.5));

        Renderer renderer = new Renderer(width, height, 60);
        Vec3[] frameBuffer = renderer.render(scene, lights);

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int color = 0;
                color += 255 * Math.min(1, frameBuffer[j + i * height].getX());
                color <<= 8;
                color += 255 * Math.min(1, frameBuffer[j + i * height].getY());
                color <<= 8;
                color += 255 * Math.min(1, frameBuffer[j + i * height].getZ());
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
