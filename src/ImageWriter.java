import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageWriter {
    public static void saveImage(BufferedImage image, String filename) {
        try {
            System.out.println("Saving image");

            ImageIO.write(image, "PNG", new File(filename + ".png"));

            System.out.println("Image saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
