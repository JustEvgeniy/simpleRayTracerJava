import Geometry.Vec3;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Renderer {
    private BufferedImage image;
    private int width;
    private int height;
    private int fovDeg;
    private Scene currentScene;

    private static final Vec3 BACKGROUND_COLOR = new Vec3(.2, .7, .8);

    public Renderer(int width, int height, int fovDeg) {
        this.width = width;
        this.height = height;
        this.fovDeg = fovDeg;
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    public void setFov(int fovDeg) {
        this.fovDeg = fovDeg;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void render(Scene scene, Canvas canvas) {
        currentScene = scene;
        Graphics graphics = canvas.getGraphics();

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
            graphics.drawImage(image, 0, 0, canvas);
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

        for (Light light : currentScene.lights) {
            Vec3 lightDir = light.position.subtract(intersection.hit).normalize();

            diffuseLightIntensity += light.intensity * Math.max(0, lightDir.dotProduct(intersection.normal));
            specularLightIntensity += Math.pow(
                    Math.max(0, reflect(lightDir, intersection.normal).dotProduct(direction.inverse())),
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
        double objectDistance = Double.MAX_VALUE;

        Intersection result = new Intersection();

        for (Sphere sphere : currentScene.spheres) {
            Intersection intersection = sphere.rayIntersect(origin, direction);
            if (intersection.isIntersection && intersection.distance < objectDistance) {
                objectDistance = intersection.distance;
                Vec3 hit = origin.add(direction.multiply(intersection.distance));
                result = new Intersection(hit, hit.subtract(sphere.center).normalize(), sphere.material);
            }
        }

        return result;
    }

    public Vec3 reflect(Vec3 I, Vec3 N) {
        return N.multiply(2).multiply(I.dotProduct(N)).subtract(I);
    }
}
