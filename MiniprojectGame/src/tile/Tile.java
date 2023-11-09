package tile;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tile {
    public BufferedImage image;
    
    public Tile(int id) {
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream(String.format("/image/tiles/%d.png", id)));
        } catch (IOException e) {
            e.printStackTrace();
        }
 
    }
}
