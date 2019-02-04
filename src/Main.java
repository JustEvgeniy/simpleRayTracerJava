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
        javax.swing.SwingUtilities.invokeLater(Main::createAndShowGUI);
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
        Material ivory = new Material(new double[]{.6, .3}, new Vec3(0.4, 0.4, 0.3), 50);
        Material redRubber = new Material(new double[]{.9, .1}, new Vec3(0.3, 0.1, 0.1), 10);

        List<SceneObject> scene = new ArrayList<>();
        scene.add(new Sphere(new Vec3(-3, 0, -16), 2, ivory));
        scene.add(new Sphere(new Vec3(-1, -1.5, -12), 2, redRubber));
        scene.add(new Sphere(new Vec3(1.5, -0.5, -18), 3, redRubber));
        scene.add(new Sphere(new Vec3(7, 5, -18), 4, ivory));

        List<Light> lights = new ArrayList<>();
        lights.add(new Light(new Vec3(-20, 20, 20), 1.5));
        lights.add(new Light(new Vec3(30, 50, -25), 1.8));
        lights.add(new Light(new Vec3(30, 20, 30), 1.7));

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Renderer renderer = new Renderer(this, g, image, width, height, 60);
        renderer.render(scene, lights);

        g.drawImage(image, 0, 0, this);

        saveImage();
    }
}
