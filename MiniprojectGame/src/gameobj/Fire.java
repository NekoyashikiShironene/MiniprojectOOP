package gameobj;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Player;

public class Fire extends GameObject {
    boolean playerStepped = false;
    public Fire() {
        this.frames = 6;
        this.scale = 2;
        this.framedelay = 2;
        this.xDiff = 55;
        this.yDiff = 110;
        this.BI = new BufferedImage[this.frames];
        setCollectbox(this.x, this.y, 20, 20);
        setImage();
    }

    public void setImage() {
        for (int i = 0; i < this.frames; i++) {
            try {
                BI[i] = ImageIO.read(getClass().getResourceAsStream(String.format("/image/fire/Fire+Sparks%d.png", i+1)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }
    }

    public boolean isPlayerNearObj(Player p) {
        return Math.abs(p.x - this.x) < 100;

    }

    @Override
    public void playerStep(Player p) {
        if (!isPlayerNearObj(p) || this.playerStepped)
            return;
        int stepboxX = p.stepbox[0];
        int stepboxY = p.stepbox[1];
        int stepboxWidth = p.stepbox[2];
        int stepboxHeight = p.stepbox[3];

        int collectboxX = this.collectbox[0];
        int collectboxY = this.collectbox[1];
        int collectboxWidth = this.collectbox[2];
        int collectboxHeight = this.collectbox[3];

        boolean isCollected = (stepboxX + stepboxWidth >= collectboxX && stepboxX <= collectboxX + collectboxWidth)
                        && (stepboxY + stepboxHeight >= collectboxY && stepboxY <= collectboxY + collectboxHeight); 
        

        if (isCollected) {
            p.health -= 7;
            this.playerStepped = true;
        }

    }

    

    
}
