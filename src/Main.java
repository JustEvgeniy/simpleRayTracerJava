import Geometry.Vec3;

import javax.swing.*;
import java.awt.*;

public class Main {
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
                for (int k = 0; k < 1; k++) {
                    scene.addSphere(i, j, k * 3 - 22, 1, materials[(int) (Math.random() * 123456) % materials.length]);
                }
            }
        }

        scene.addLight(-20, 20, 20, 1.3);
        scene.addLight(30, 50, -25, 1.5);
        scene.addLight(30, 20, 30, 1.9);

        scene.setEnvmap("data/envmap.jpg");

        int width = 1024/2;
        int height = 768/2;

        renderWithResolution(width, height, scene);

//        width = 1920;
//        height = 1080;
//
//        renderWithResolution(width, height, scene);
//
//        width = 1920 * 2;
//        height = 1080 * 2;
//
//        renderWithResolution(width, height, scene);
    }

    private static void renderWithResolution(int width, int height, Scene scene) {
        Renderer renderer = new Renderer(width, height, 60);

        Canvas canvas1 = setupGui(width, height, "60 fov");
        renderer.render(scene, canvas1);
        ImageWriter.saveImage(renderer.getImage(), "out60fov" + width + 'x' + height);

        Canvas canvas2 = setupGui(width, height, "90 fov");
        renderer.setFov(90);
        renderer.render(scene, canvas2);
        ImageWriter.saveImage(renderer.getImage(), "out90fov" + width + 'x' + height);

        Canvas canvas3 = setupGui(width, height, "30 fov");
        renderer.setFov(30);
        renderer.render(scene, canvas3);
        ImageWriter.saveImage(renderer.getImage(), "out30fov" + width + 'x' + height);
    }

    private static Canvas setupGui(int width, int height, String title) {
        JFrame frame = new JFrame("Rendered image | " + title + " | " + width + 'x' + height);
        Canvas canvas = new Canvas();

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(canvas);
        frame.setSize(width, height);
        frame.setResizable(false);
        frame.setVisible(true);
        return canvas;
    }
}
