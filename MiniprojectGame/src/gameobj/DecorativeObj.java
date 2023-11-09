package gameobj;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import random.RD;

public class DecorativeObj extends GameObject {
    public int id;
    public DecorativeObj() {
        this.frames = 1;
        this.scale = 1;
        this.BI = new BufferedImage[1];
        setCollectbox(0, 0, 0, 0);
        setImage();
    }

    public void setImage() {
        try {
            this.BI[0] = ImageIO.read(getClass().getResourceAsStream(String.format("/image/decorativeobj/%d.png", RD.random(0, 11))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {}

    
}
