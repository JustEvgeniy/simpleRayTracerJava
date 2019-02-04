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
        return castRay(origin, direction, 0);
    }

    private Vec3 castRay(Vec3 origin, Vec3 direction, int depth) {
        Intersection intersection = sceneIntersect(origin, direction);
        if (depth > 4 || !intersection.isIntersecting) {
            return BACKGROUND_COLOR;
        }

        Vec3 reflectDir = reflect(direction.inverse(), intersection.normal).normalize();
        Vec3 reflectOrgin = reflectDir.dotProduct(intersection.normal) < 0 ?
                intersection.hit.subtract(intersection.normal.multiply(1e-3)) :
                intersection.hit.add(intersection.normal.multiply(1e-3));
        Vec3 reflectColor = castRay(reflectOrgin, reflectDir, depth + 1);

        Vec3 refractDir = refract(direction, intersection.normal, intersection.material.refractiveIndex).normalize();
        Vec3 refractOrigin = refractDir.dotProduct(intersection.normal) < 0 ?
                intersection.hit.subtract(intersection.normal.multiply(1e-3)) :
                intersection.hit.add(intersection.normal.multiply(1e-3));
        Vec3 refractColor = castRay(refractOrigin, refractDir, depth + 1);

        double diffuseLightIntensity = 0;
        double specularLightIntensity = 0;

        for (Light light : currentScene.lights) {
            Vec3 lightDir = light.position.subtract(intersection.hit).normalize();
            double lightDistance = light.position.subtract(intersection.hit).length();

            Vec3 shadowOrigin = lightDir.dotProduct(intersection.normal) < 0 ?
                    intersection.hit.subtract(intersection.normal.multiply(1e-3)) :
                    intersection.hit.add(intersection.normal.multiply(1e-3));

            Intersection intersectionShadows = sceneIntersect(shadowOrigin, lightDir);

            if (intersectionShadows.isIntersecting && intersection.hit.subtract(shadowOrigin).length() < lightDistance)
                continue;

            diffuseLightIntensity += light.intensity * Math.max(0, lightDir.dotProduct(intersection.normal));
            specularLightIntensity += Math.pow(
                    Math.max(0, reflect(lightDir, intersection.normal).dotProduct(direction.inverse())),
                    intersection.material.specularExponent);
        }

        Vec3 diffuseComponent = intersection.material.diffuseColor
                .multiply(diffuseLightIntensity)
                .multiply(intersection.material.albedo[0]);

        Vec3 specularComponent = new Vec3(1, 1, 1)
                .multiply(specularLightIntensity)
                .multiply(intersection.material.albedo[1]);

        Vec3 reflectionComponent = reflectColor.multiply(intersection.material.albedo[2]);

        Vec3 refractionComponent = refractColor.multiply(intersection.material.albedo[3]);

        return diffuseComponent.add(specularComponent).add(reflectionComponent).add(refractionComponent);
    }

    private Intersection sceneIntersect(Vec3 origin, Vec3 direction) {
        double objectDistance = Double.MAX_VALUE;

        Intersection result = new Intersection();

        for (Sphere sphere : currentScene.spheres) {
            Intersection intersection = sphere.rayIntersect(origin, direction);
            if (intersection.isIntersecting && intersection.distance < objectDistance) {
                objectDistance = intersection.distance;
                Vec3 hit = origin.add(direction.multiply(intersection.distance));
                result = new Intersection(hit, hit.subtract(sphere.center).normalize(), sphere.material);
            }
        }

        return result;
    }

    private Vec3 reflect(Vec3 I, Vec3 N) {
        return N.multiply(2).multiply(I.dotProduct(N)).subtract(I);
    }

    private Vec3 refract(Vec3 I, Vec3 N, double refractiveIndex) {
        double cosi = -I.dotProduct(N);
        double etai = 1;
        double etat = refractiveIndex;
        Vec3 n = N;
        if (cosi < 0) {
            cosi = -cosi;
            n = N.inverse();
            double t = etai;
            etai = etat;
            etat = t;
        }
        double eta = etai / etat;
        double k = 1 - eta * eta * (1 - cosi * cosi);
        return k < 0 ? new Vec3(0, 0, 0) : I.multiply(eta).add(n.multiply(eta * cosi - Math.sqrt(k)));
    }
}
