package gameobj;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import entity.Player;


public class AmmoPack extends GameObject {
    boolean playerStepped = false;

    public AmmoPack() {
        this.frames = 1;
        this.scale = 1;
        this.framedelay = 1;
        this.xDiff = 20;
        this.yDiff = 20;
        this.BI = new BufferedImage[this.frames];
        setCollectbox(this.x, this.y, 20, 20);
        setImage();
    }

    public void setImage() {
        try {
            BI[0] = ImageIO.read(getClass().getResourceAsStream("/image/ammo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean isPlayerNearObj(Player p) {
        return Math.abs(p.x - this.x) < 100;

    }

    @Override
    public void playerStep(Player p) {
        if (!isPlayerNearObj(p) && this.playerStepped)
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
            p.shotgunBullet = Math.min(48, p.shotgunBullet+8);
            this.playerStepped = true;
        }

    }


}
