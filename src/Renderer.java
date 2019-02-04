import Geometry.Vec3;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Renderer {
    private BufferedImage image;
    private int width;
    private int height;
    private int fovDeg;
    private List<SceneObject> currentScene;
    private List<Light> currentLights;
    private Graphics g;
    private Canvas c;

    private static final Vec3 BACKGROUND_COLOR = new Vec3(.4, .9, 1);

    public Renderer(Canvas c, Graphics g, BufferedImage image, int width, int height, int fovDeg) {
        this.g = g;
        this.c = c;
        this.image = image;
        this.width = width;
        this.height = height;
        this.fovDeg = fovDeg;
    }

    public void render(List<SceneObject> scene, List<Light> lights) {
        currentScene = scene;
        currentLights = lights;

        System.out.println("Rendering\n" + width + 'x' + height + '=' + width * height + " pixels");

        Vec3 center = new Vec3(0, 0, 0);

        double fov = fovDeg * Math.PI / 180;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double dirX = (i + 0.5) - width / 2.0;
                double dirY = -(j + 0.5) + height / 2.0;
                double dirZ = -height / (2 * Math.tan(fov / 2));
                Vec3 dir = new Vec3(dirX, dirY, dirZ).normalize();
                Vec3 vec3 = castRay(center, dir);
                int color = 0;
                double m = vec3.getMax();
                if (m > 1)
                    vec3 = vec3.multiply(1 / m);
                color += 255 * vec3.getX();
                color <<= 8;
                color += 255 * vec3.getY();
                color <<= 8;
                color += 255 * vec3.getZ();
                image.setRGB(i, j, color);
            }
            g.drawImage(image, 0, 0, c);
        }

        System.out.println("Finished rendering");
    }

    private Vec3 castRay(Vec3 origin, Vec3 direction) {
        Intersection intersection = sceneIntersect(origin, direction);
        if (!intersection.isIntersection) {
            return BACKGROUND_COLOR;
        }

        double diffuseLightIntensity = 0;
        double specularLightIntensity = 0;

        for (Light light : currentLights) {
            Vec3 lightDir = light.position.subtract(intersection.hit).normalize();

            diffuseLightIntensity += light.intensity * Math.max(0, lightDir.dotProduct(intersection.normal));
            specularLightIntensity += Math.pow(
                    reflect(lightDir, intersection.normal).dotProduct(direction.inverse()),
                    intersection.material.specularExponent);
        }

        return intersection.material.diffuseColor
                .multiply(diffuseLightIntensity)
                .multiply(intersection.material.albedo[0])
                .add(new Vec3(1, 1, 1).
                        multiply(specularLightIntensity).
                        multiply(intersection.material.albedo[1]));
    }

    private Intersection sceneIntersect(Vec3 origin, Vec3 direction) {
        double spheresDist = Double.MAX_VALUE;

        Intersection result = new Intersection();

        for (SceneObject object : currentScene) {
            if (object instanceof Sphere) {
                Sphere sphere = (Sphere) object;
                Intersection intersection = object.rayIntersect(origin, direction);
                if (intersection.isIntersection && intersection.distance < spheresDist) {
                    spheresDist = intersection.distance;
                    Vec3 hit = origin.add(direction.multiply(intersection.distance));
                    result = new Intersection(hit, hit.subtract(sphere.center).normalize(), sphere.material);
                }
            }
        }

        if (spheresDist < 1000)
            return result;
        else
            return new Intersection();
    }

    public Vec3 reflect(Vec3 I, Vec3 N) {
        return N.multiply(2).multiply(I.dotProduct(N)).subtract(I);
    }
}
