import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class ImageWriter {
    static void saveImage(BufferedImage image, String filename) {
        try {
            System.out.println("Saving image: " + filename + ".png");

            ImageIO.write(image, "PNG", new File(filename + ".png"));

            System.out.println("Image saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
