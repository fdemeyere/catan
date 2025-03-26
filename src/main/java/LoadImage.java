import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import java.util.HashMap;
import java.util.Map;

public class LoadImage {

    private static final Map<String, BufferedImage> imageCache = new HashMap<>();

    public static BufferedImage load(String imageName) {
        if (imageCache.containsKey(imageName)) {
            return imageCache.get(imageName);
        } else {
            try (InputStream input = LoadImage.class.getResourceAsStream("/images/" + imageName)) {
                if (input == null) {
                    throw new IOException("Image not found: " + imageName);
                }
                BufferedImage img = ImageIO.read(input);
                imageCache.put(imageName, img);
                return img;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

    }
}