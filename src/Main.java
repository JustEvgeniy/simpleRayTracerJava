import Geometry.Vec3;

import javax.swing.*;
import java.awt.*;

public class Main extends Canvas {
    public static final int width = 1024;
    public static final int height = 768;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Rendered image");
        Canvas canvas = new Main();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(canvas);
        frame.setSize(width, height);
        frame.setResizable(false);
        frame.setVisible(true);

        Material ivory = new Material(new double[]{.6, .3}, new Vec3(0.4, 0.4, 0.3), 50);
        Material redRubber = new Material(new double[]{.9, .1}, new Vec3(0.3, 0.1, 0.1), 10);

        Scene scene = new Scene();
        scene.addSphere(-3, 0, -16, 2, ivory);
        scene.addSphere(-1, -1.5, -12, 2, redRubber);
        scene.addSphere(1.5, -0.5, -18, 3, redRubber);
        scene.addSphere(7, 5, -18, 4, ivory);

        scene.addLight(-20, 20, 20, 1.5);
        scene.addLight(30, 50, -25, 1.8);
        scene.addLight(30, 20, 30, 1.7);

        Renderer renderer = new Renderer(width, height, 60);
        renderer.render(scene, canvas);

        ImageWriter.saveImage(renderer.getImage(), "out");
    }
}
