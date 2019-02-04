import Geometry.Vec3;

import java.util.List;

public class Renderer {
    private int width;
    private int height;
    private int fovDeg;
    private List<SceneObject> currentScene;

    private static final Vec3 BACKGROUND_COLOR = new Vec3(.4, .9, 1);

    public Renderer(int width, int height, int fovDeg) {
        this.width = width;
        this.height = height;
        this.fovDeg = fovDeg;
    }

    public Vec3[] render(List<SceneObject> scene) {
        Vec3[] frameBuffer = new Vec3[width * height];
        currentScene = scene;

        System.out.println("Created frameBuffer\n" + width + 'x' + height + '=' + width * height + " pixels");

        Vec3 center = new Vec3(0, 0, 0);

        double fov = fovDeg * Math.PI / 180;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double dirX = (i + 0.5) - width / 2.0;
                double dirY = -(j + 0.5) + height / 2.0;
                double dirZ = -height / (2 * Math.tan(fov / 2));
                Vec3 dir = new Vec3(dirX, dirY, dirZ).normalize();
                frameBuffer[j + i * height] = castRay(center, dir);
            }
        }

        System.out.println("Filled frameBuffer");
        return frameBuffer;
    }

    private Vec3 castRay(Vec3 origin, Vec3 direction) {
        double sphereDist = Double.MAX_VALUE;
        for (SceneObject object : currentScene) {
            IntersectionResult res = object.rayIntersect(origin, direction);
            if (res.intersection)
                return new Vec3(.4, .4, .3);
        }
        return BACKGROUND_COLOR;
    }
}
