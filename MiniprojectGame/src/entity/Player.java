package entity;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import panel.DefaultPanel;
import panel.GamePanel;
import panel.KeyHandler;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    String path;
    public boolean isAlive = true;
    public int[] shootbox = {280, (DefaultPanel.screenHeight / 2) - 130, 230, 100};
    public int[] attackbox = {310, (DefaultPanel.screenHeight / 2) - 100, 50, 50};
    public int[] stepbox = {270, (DefaultPanel.screenHeight / 2) - 20, 45, 25};
    public final int maxHealth = 100;
    public int health;
    public int shotgunBullet;
    public int runDistance;
    public int killCount;
    public int playerStateID = 0;
    boolean on_action = false;


    public Player(GamePanel gp, KeyHandler keyH) {
        super(182, (DefaultPanel.screenHeight / 2) - 250, 4, 4, 20);
        this.gp = gp;
        this.keyH = keyH;
        this.health = this.maxHealth;
        this.runDistance = 0;
        this.shotgunBullet = 8;
        this.killCount = 0;
        
        playerIdle();
        setImage();
    }

    public void playerIdle() {
        playerStateID = 0;
        setFrame(6);
        setSpeed(0);
        setTurnSpeed(0);
        setFrameDelay(8);
        path = "/image/playeridle/Idle_%d.png";
        setImage();
        setHitbox(285, (DefaultPanel.screenHeight / 2) - 90, 0, 0);
    }

    public void playerRun() {
        playerStateID = 1;
        setFrame(8);
        setSpeed((int)(10+1*gp.dificultRate));
        setTurnSpeed((int)((speed*0.8)+gp.dificultRate));
        setFrameDelay(5);
        
        path = "/image/playerrun/Run_%d.png";
        setImage();
        setHitbox(270, (DefaultPanel.screenHeight / 2) - 90, 35, 50);
    }

    public void playerJump() {
        on_action = true;
        spriteNum = 0;
        playerStateID = 2;
        setFrame(8);
        setFrameDelay(3);
        setSpeed((int)(this.speed*2));
        setTurnSpeed((int)(speed*0.5));
        path = "/image/playerjump/Jump_%d.png";
        setImage();
        setHitbox(290, (DefaultPanel.screenHeight / 2) - 90, 35, 50);
            
    }

    public void playerAttack() {
        on_action = true;
        spriteNum = 0;
        playerStateID = 3;
        setFrame(6);
        setFrameDelay(4);
        setSpeed(0);
        setTurnSpeed(0);
        path = "/image/playerattack/Attack_1_%d.png";
        setImage();
        setHitbox(285, (DefaultPanel.screenHeight / 2) - 90, 35, 50);

    }

    public void playerShoot() {
        on_action = true;
        spriteNum = 0;
        playerStateID = 4;
        setFrame(12);
        setFrameDelay(2);
        setSpeed(0);
        setTurnSpeed(0);
        path = "/image/playershoot/Shot_%d.png";
        setImage();
        setHitbox(225, (DefaultPanel.screenHeight / 2) - 90, 35, 50);

        this.shotgunBullet--;

    }

    public void playerDead() {
        gp.gameState = 2;
        on_action = true;
        spriteNum = 0;
        playerStateID = 5;
        setFrame(4);
        setFrameDelay(16);
        setSpeed(0);
        setTurnSpeed(0);
        path = "/image/playerdead/Dead_%d.png";
        setImage();
        setHitbox(0, 0, 0, 0);

    }


    
    public void setImage() {
        try {
            for (int i=0; i < frames; i++) {  
                BI[i] = ImageIO.read(getClass().getResourceAsStream(String.format(path, i+1)));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() { 

        if (!isAlive)
           return;
        
        this.runDistance += this.speed;
 
         if (keyH.spacePressed && playerStateID != 2 && !on_action) {
            playerJump();
        } else if (keyH.shootPressed && playerStateID != 4 && !on_action && shotgunBullet > 0) {
            playerShoot();
        } else if (keyH.attackPressed && playerStateID != 3 && !on_action) {
            playerAttack();
        } else if (this.health <= 0 && playerStateID != 5) {
            playerDead();
        } else if (gp.gameState == 1 && playerStateID != 1 && !on_action) {
            playerRun();
            System.out.println(1);
        }


        spriteCounter++;
        
        if (spriteCounter >= framedelay) {
            spriteNum++;
            spriteCounter = 0;
        }
        if (spriteNum >= frames) {
            on_action = false;
            keyH.spacePressed = false;
            keyH.shootPressed = false;
            keyH.attackPressed = false;
            if (playerStateID == 2 || playerStateID == 3 || playerStateID == 4) {
                playerRun();
            } else if (playerStateID == 5) {
                isAlive = false;
                
            }
            spriteNum = 0;    
            
        }
    }

    public void draw(Graphics2D g2) {
        if (!isAlive)
           return;
        BufferedImage image = null;

        if (spriteNum >= frames)
                image = BI[0];
        else {
            image = BI[spriteNum];
        } 

        if (image == null) {
            System.out.println("Player image is null!"); // check if the player image is null
        } else {
            g2.drawImage(image, x, y, getSizeH(), getSizeW(),  null);
          //  g2.draw(new Rectangle(hitbox[0], hitbox[1], hitbox[2], hitbox[3]));
         //   g2.draw(new Rectangle(shootbox[0], shootbox[1], shootbox[2], shootbox[3])); 
         //   g2.draw(new Rectangle(stepbox[0], stepbox[1], stepbox[2], stepbox[3])); 
          //  g2.draw(new Rectangle(attackbox[0], attackbox[1], attackbox[2], attackbox[3])); //draw hitbox
        }
 
    }
    
}
