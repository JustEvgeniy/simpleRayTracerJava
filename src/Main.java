import Geometry.Vec3;
import Renderer.Renderer;
import Renderer.Scene;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    // Settings
    private static final boolean withGui = false;
    private static final boolean withImage = true;
    private static final boolean withSphereWall = true;
    private static final boolean timeTests = false;
    private static final Map<String, List<Long>> times = new HashMap<>();

    public static void main(String[] args) {
        if (!withGui && !withImage) {
            throw new RuntimeException("Wrong settings");
        }

        Scene scene = new Scene();

        scene.addMaterial("ivory", 1, .6, .3, .1, 0, new Vec3(0.4, 0.4, 0.3), 50);
        scene.addMaterial("glass", 1.5, 0, .5, .1, .8, new Vec3(0.6, 0.7, 0.8), 125);
        scene.addMaterial("redRubber", 1, .9, .1, 0, 0, new Vec3(0.3, 0.1, 0.1), 10);
        scene.addMaterial("mirror", 1, 0, 10, .8, 0, new Vec3(1.0, 1.0, 1.0), 1425);
        List<String> materials = scene.getMaterialNames();

        scene.addSphere(-3, 0, -16, 2, "ivory");
        scene.addSphere(-1, -1.5, -12, 2, "glass");
        scene.addSphere(1.5, -0.5, -18, 3, "redRubber");
        scene.addSphere(7, 5, -18, 4, "mirror");

        if (withSphereWall) {
            for (int i = -10; i <= 10; i += 2) {
                for (int j = -10; j <= 10; j += 2) {
                    for (int k = 0; k < 1; k++) {
                        scene.addSphere(i, j, k * 3 - 22, 1, materials.get((int) (Math.random() * 123456) % materials.size()));
                    }
                }
            }
        }

        scene.addLight(-20, 20, 20, 1.3);
        scene.addLight(30, 50, -25, 1.5);
        scene.addLight(30, 20, 30, 1.9);

        scene.loadEnvmap("data/envmap.jpg");

        int[] fovs = new int[]{60, 90, 30};
        int runs;
        if (timeTests) {
            runs = 10;
        } else {
            runs = 1;
        }

        for (int i = 0; i < runs; i++) {
            if (timeTests) {
                renderWithResolution(1024 / 4, 768 / 4, fovs, scene);
            } else {
                renderWithResolution(1024, 768, fovs, scene);
                renderWithResolution(1920, 1080, fovs, scene);
                renderWithResolution(1920 * 2, 1080 * 2, fovs, scene);
            }
        }

        System.out.println("Average times:");
        times.forEach((key, value) -> {
            double avg = 0;
            for (Long l : value) {
                avg += l;
            }
            avg /= 1000 * value.size();
            System.out.println("\t" + key + ":\t" + avg);
        });
    }

    private static void renderWithResolution(int width, int height, int[] fovs, Scene scene) {
        Renderer renderer = new Renderer();
        renderer.setSize(width, height);

        renderer.setScene(scene);

        for (int fov : fovs) {
            if (withGui) {
                Canvas canvas = setupGui(width, height, fov + " fov");
                renderer.setGraphics(canvas.getGraphics());
            }

            renderer.setFov(fov);
            long time = renderer.render();

            String curRenderString = fov + "fov" + width + 'x' + height;
            List<Long> curTimes = times.get(curRenderString);
            if (curTimes == null) {
                curTimes = new ArrayList<>();
                curTimes.add(time);
                times.put(curRenderString, curTimes);
            } else {
                curTimes.add(time);
            }

            if (withImage) {
                ImageWriter.saveImage(renderer.getImage(), "out" + curRenderString);
            }
        }
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
