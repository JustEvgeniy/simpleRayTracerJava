import Geometry.Vec3;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static final int width = 1024;
    public static final int height = 768;

    public static void main(String[] args) {
        Material ivory =
                new Material(1.0, new double[]{0.6, 0.3, 0.1, 0.0}, new Vec3(0.4, 0.4, 0.3), 50);
        Material glass =
                new Material(1.5, new double[]{0.0, 0.5, 0.1, 0.8}, new Vec3(0.6, 0.7, 0.8), 125);
        Material redRubber =
                new Material(1.0, new double[]{0.9, 0.1, 0.0, 0.0}, new Vec3(0.3, 0.1, 0.1), 10);
        Material mirror =
                new Material(1.0, new double[]{0.0, 10.0, 0.8, 0.0}, new Vec3(1.0, 1.0, 1.0), 1425);

        Scene scene = new Scene();
        scene.addSphere(-3, 0, -16, 2, ivory);
        scene.addSphere(-1, -1.5, -12, 2, glass);
        scene.addSphere(1.5, -0.5, -18, 3, redRubber);
        scene.addSphere(7, 5, -18, 4, mirror);

        Material[] materials = new Material[]{ivory, glass, redRubber, mirror};

        for (int i = -10; i <= 10; i += 2) {
            for (int j = -10; j <= 10; j += 2) {
                for (int k = 0; k < 2; k++) {
                    scene.addSphere(i, j, k * 3 - 22, 1, materials[(int) (Math.random() * 123456) % materials.length]);
                }
            }
        }

        scene.addLight(-20, 20, 20, 1.5);
        scene.addLight(30, 50, -25, 1.8);
        scene.addLight(30, 20, 30, 1.7);

        Renderer renderer = new Renderer(width, height, 60);

        Canvas canvas1 = setupGui("60 fov");
        renderer.render(scene, canvas1);
        ImageWriter.saveImage(renderer.getImage(), "out60fov");

        Canvas canvas2 = setupGui("90 fov");
        renderer.setFov(90);
        renderer.render(scene, canvas2);
        ImageWriter.saveImage(renderer.getImage(), "out90fov");

        Canvas canvas3 = setupGui("30 fov");
        renderer.setFov(30);
        renderer.render(scene, canvas3);
        ImageWriter.saveImage(renderer.getImage(), "out30fov");
    }

    private static Canvas setupGui(String title) {
        JFrame frame = new JFrame("Rendered image | " + title);
        Canvas canvas = new Canvas();

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(canvas);
        frame.setSize(width, height);
        frame.setResizable(false);
        frame.setVisible(true);
        return canvas;
    }
}
