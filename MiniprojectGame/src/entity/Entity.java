package entity;
import java.awt.image.BufferedImage;

import panel.DefaultPanel;

public abstract class Entity {
    
    public int x, y;
    private int sizeW, sizeH;
    public int[] hitbox = new int[4];

    public int speed;
    public int turnSpeed;
    public int frames;
    public int framedelay;

    public int spriteCounter = 0;
    public int spriteNum = 0;

    BufferedImage[] BI;

    abstract void setImage();

    public Entity() {
        setPostion(0, 0);
        setSize(DefaultPanel.tileSize, DefaultPanel.tileSize);
        this.BI = new BufferedImage[20];  
    }

    public Entity(int sizeW, int sizeH, int maxFrames) {
        setPostion(0, 0);
        setSize(DefaultPanel.tileSize*sizeW, DefaultPanel.tileSize*sizeH);
        this.BI = new BufferedImage[maxFrames]; 
    }

    public Entity(int x, int y, int sizeW, int sizeH, int maxFrames) {
        setPostion(x, y);
        setSize(DefaultPanel.tileSize*sizeW, DefaultPanel.tileSize*sizeH);
        this.BI = new BufferedImage[maxFrames];  
    }
    {
        framedelay = frames;
    }

    public void setHitbox(int x, int y, int w, int h) {
        hitbox[0] = x;
        hitbox[1] = y;
        hitbox[2] = w;
        hitbox[3] = h;
    }

    public void setFrameDelay(int s) {
        this.framedelay = s;
    }

    public void setSize(int w, int h) {
        this.sizeW = w;
        this.sizeH = h;
    }

    public void setFrame(int n) {
        this.frames = n; 
    }

    public void setPostion(int x, int y) {
        this.x = x;
        this.y = y;

    }

    public void setSpeed(int s) {
        this.speed = s;
    }

    public void setTurnSpeed(int s) {
        this.turnSpeed = s;
    }

    public int getSizeW() {
        return this.sizeW;
    }

    public int getSizeH() {
        return this.sizeH;
    }

}
