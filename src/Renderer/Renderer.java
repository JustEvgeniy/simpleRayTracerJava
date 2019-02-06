package Renderer;

import Geometry.Vec3;
import Geometry.VectorTransform;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Renderer {
    private int width;
    private int height;
    private int fovDeg;
    private BufferedImage image;
    private Graphics graphics;
    private Scene currentScene;

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    public void setFov(int fovDeg) {
        this.fovDeg = fovDeg;
    }

    public void setGraphics(Graphics graphics) {
        this.graphics = graphics;
    }

    public void setScene(Scene scene) {
        currentScene = scene;
    }

    public BufferedImage getImage() {
        if (image == null) {
            throw new RendererException("Image is not set");
        }
        return image;
    }

    public void render() {
        if (image == null) {
            throw new RendererException("Image is not set");
        }
        if (currentScene == null) {
            throw new RendererException("Renderer.Scene is not set");
        }
        if (fovDeg == 0) {
            throw new RendererException("Fov is equals 0 or not set");
        }

        System.out.println("Rendering: " + fovDeg + " fov");
        System.out.println("\t" + width + "x" + height + "=" + width * height + " pixels");
        System.out.println("\t" + currentScene.lights.size() + " lights");
        System.out.println("\t" + currentScene.spheres.size() + " spheres");
        long startTime = System.currentTimeMillis();

        Vec3 center = new Vec3(0, 0, 0);

        double fov = fovDeg * Math.PI / 180;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double dirX = (i + 0.5) - width / 2.0;
                double dirY = -(j + 0.5) + height / 2.0;
                double dirZ = -height / (2 * Math.tan(fov / 2));

                Vec3 dir = new Vec3(dirX, dirY, dirZ).normalize();
                Vec3 vec3 = castRay(new Ray(center, dir));

                int color = 0;
                double m = vec3.getMax();
                if (m > 1)
                    vec3 = vec3.mul(1 / m);
                color += 255 * vec3.getX();
                color <<= 8;
                color += 255 * vec3.getY();
                color <<= 8;
                color += 255 * vec3.getZ();
                image.setRGB(i, j, color);
            }

            if (graphics != null) {
                graphics.drawImage(image, 0, 0, null);
            }
        }

        long endTime = System.currentTimeMillis();

        System.out.println("Finished rendering");
        System.out.println("\tTime: " + ((endTime - startTime) / 1000.0) + " s");
    }

    /*======================================================================================*/

    private Vec3 castRay(Ray ray) {
        return castRay(ray, 0);
    }

    private Vec3 castRay(Ray ray, int depth) {
        if (depth > 4) {
            return currentScene.envmap.get(ray.dir);
        }

        Intersection isn = sceneIntersect(ray);
        if (isn == null) {
            return currentScene.envmap.get(ray.dir);
        }

        //TODO: optimize this
        Vec3 pointInside = isn.hit.sub(isn.N.mul(1e-3));
        Vec3 pointOutside = isn.hit.add(isn.N.mul(1e-3));

        double diffuseLightIntensity = 0;
        double specularLightIntensity = 0;

        for (Light light : currentScene.lights) {
            //TODO: optimize this

            //light.pos = hit + lightDir ===> lightDir = light.pos - hit
            Vec3 lightDir = light.pos.sub(isn.hit).normalize();
            double lightDist = light.pos.sub(isn.hit).length();

            //TODO: optimize this
            Vec3 shadowOrigin = lightDir.dot(isn.N) < 0 ? pointInside : pointOutside;

            Intersection isnShadows = sceneIntersect(new Ray(shadowOrigin, lightDir));

            //TODO: optimize this
            if (isnShadows != null && isnShadows.hit.sub(shadowOrigin).length() < lightDist)
                continue;

            diffuseLightIntensity += light.intensity * Math.max(0, lightDir.dot(isn.N));
            specularLightIntensity += Math.pow(
                    Math.max(0, VectorTransform.reflect(lightDir, isn.N).dot(ray.dir.inverse())),
                    isn.material.specularExponent);
        }

        //TODO: optimize this
        Vec3 diffuseComponent = isn.material.diffuseColor
                .mul(diffuseLightIntensity)
                .mul(isn.material.albedo[0]);

        //TODO: optimize this
        Vec3 specularComponent = new Vec3(1, 1, 1)
                .mul(specularLightIntensity)
                .mul(isn.material.albedo[1]);

        Vec3 reflectionComponent = new Vec3(0, 0, 0);
        if (isn.material.albedo[2] != 0) {
            //TODO: optimize this
            Vec3 reflectDir = VectorTransform.reflect(ray.dir.inverse(), isn.N).normalize();
            Vec3 reflectOrigin = reflectDir.dot(isn.N) < 0 ? pointInside : pointOutside;
            reflectionComponent = castRay(new Ray(reflectOrigin, reflectDir), depth + 1).mul(isn.material.albedo[2]);
        }

        Vec3 refractionComponent = new Vec3(0, 0, 0);
        if (isn.material.albedo[3] != 0) {
            //TODO: optimize this
            Vec3 refractDir = VectorTransform.refract(ray.dir, isn.N, isn.material.refractiveIndex).normalize();
            Vec3 refractOrigin = refractDir.dot(isn.N) < 0 ? pointInside : pointOutside;
            refractionComponent = castRay(new Ray(refractOrigin, refractDir), depth + 1).mul(isn.material.albedo[3]);
        }

        return diffuseComponent.add(specularComponent).add(reflectionComponent).add(refractionComponent);
    }

    private Intersection sceneIntersect(Ray ray) {
        double objectDistance = Double.MAX_VALUE;

        Intersection result = null;

        for (Sphere sphere : currentScene.spheres) {
            Intersection isn = sphere.rayIntersect(ray);
            if (isn != null && isn.dist < objectDistance) {
                objectDistance = isn.dist;
                result = isn;
            }
        }

        if (Math.abs(ray.dir.getY()) > 1e-3) {
            double d = -(ray.origin.getY() + 4) / ray.dir.getY();
            //TODO: optimize
            Vec3 pt = ray.origin.add(ray.dir.mul(d));
            if (d > 0 && d < objectDistance &&
                    pt.getX() > -10 && pt.getX() < 10 &&
                    pt.getZ() > -30 && pt.getZ() < -10) {
                result = new Intersection(pt, new Vec3(0, 1, 0),
                        //TODO: optimize
                        new Material(
                                ((int) (.5 * pt.getX() + 1000) + (int) (.5 * pt.getZ())) % 2 == 1 ?
                                        new Vec3(.3, .3, .3) :
                                        new Vec3(.3, .2, .1)));
            }
        }

        return result;
    }
}
