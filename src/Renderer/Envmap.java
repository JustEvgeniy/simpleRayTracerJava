package Renderer;

import Geometry.Vec3;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class Envmap {
    private static final Vec3 BACKGROUND_COLOR = new Vec3(.2, .7, .8);
    private Vec3[][] data;
    private int width;
    private int height;

    Envmap() {
    }

    Envmap(String filename) {
        try {
            System.out.println("Loading envmap " + filename);

            BufferedImage image = ImageIO.read(new File(filename));
            width = image.getWidth();
            height = image.getHeight();

            data = new Vec3[width][height];

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    int pixel = image.getRGB(i, j);
                    pixel &= (1 << 24) - 1;
                    double B = pixel % (1 << 8);
                    pixel >>= 8;
                    double G = pixel % (1 << 8);
                    pixel >>= 8;
                    double R = pixel % (1 << 8);
                    data[i][j] = new Vec3(R / 255, G / 255, B / 255);
                }
            }
            System.out.println("Envmap loaded [" + width + "x" + height + "]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Vec3 get(Vec3 dir) {
        if (data == null) {
            return BACKGROUND_COLOR;
        }

        int x = (int) ((Math.atan2(dir.getZ(), dir.getX()) / (2 * Math.PI) + 0.5) * width);
        int y = (int) (Math.acos(dir.getY()) / Math.PI * height);

        //TODO: optimize+
        return data[x][y];
    }
}
