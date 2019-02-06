import Geometry.Vec3;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Envmap {
    private BufferedImage image;

    public Envmap(String filename) {
        try {
            System.out.println("Loading envmap " + filename);

            image = ImageIO.read(new File(filename));

            System.out.println("Envmap loaded");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Vec3 get(Vec3 dir) {
        int x = (int) ((Math.atan2(dir.getZ(), dir.getX()) / (2 * Math.PI) + 0.5) * image.getWidth());
        int y = (int) (Math.acos(dir.getY()) / Math.PI * image.getHeight());

        int pixel = image.getRGB(x, y);
        pixel &= (1 << 24) - 1;
        double B = pixel % (1 << 8);
        pixel >>= 8;
        double G = pixel % (1 << 8);
        pixel >>= 8;
        double R = pixel % (1 << 8);
        return new Vec3(R / 255, G / 255, B / 255);
    }
}
