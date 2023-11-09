package gameobj;
import java.awt.image.BufferedImage;

import entity.Player;

public abstract class GameObject {
    boolean playerStepped = false;
    BufferedImage[] BI;
    int x, y;
    public int scale;
    public int spriteCounter = 0;
    public int spriteNum = 0;
    public int frames;
    public int framedelay;
    public int[] collectbox = new int[4];
    public int xDiff, yDiff;

    abstract public void setImage();

    public void playerStep(Player player) {}

    public void setPostion(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void setCollectbox(int x, int y, int w, int h) {
        this.collectbox[0] = x;
        this.collectbox[1] = y;
        this.collectbox[2] = w;
        this.collectbox[3] = h;
    }

    public void updateCollectbox(int temp_distanceY) {
        setCollectbox(this.x+this.xDiff, this.y+this.yDiff+temp_distanceY, this.collectbox[2], this.collectbox[3]);
    }

}
