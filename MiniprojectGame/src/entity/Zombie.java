package entity;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

abstract class Zombie extends Entity {
    String path;
    int zombieStateID;
    int xDiff, yDiff, sizeW, sizeH;
    int damage;
    public boolean attacked = false;
    public boolean isAlive = true;
    public int[] attackbox = new int[4];

    BufferedImage image;

    public abstract void zombieRun();
    public abstract void zombieAttack();
    public abstract void zombieDead();

    public Zombie(int x, int y) {
        super(x, y, 3, 3, 10);
        setAttackbox(x, y, x, y);
        zombieRun();
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

    public void setAttackbox(int x, int y, int w , int h) {
        this.attackbox[0] = x;
        this.attackbox[1] = y;
        this.attackbox[2] = w;
        this.attackbox[3] = h;
    }

    public void updateHitbox(int temp_distanceY) {
        setHitbox(this.x+this.xDiff, this.y+this.yDiff+temp_distanceY, this.sizeW, this.sizeH);
        setAttackbox(this.x+this.xDiff-20, this.y+temp_distanceY+100, 20, 20);
    }


    public boolean isKilled(Player p) {
        int shootboxX = p.shootbox[0];
        int shootboxY = p.shootbox[1];
        int shootboxWidth = p.shootbox[2];
        int shootboxHeight = p.shootbox[3];

        int attackboxX = p.attackbox[0];
        int attackboxY = p.attackbox[1];
        int attackboxWidth = p.attackbox[2];
        int attackboxHeight = p.attackbox[3];

        int zombieX = this.hitbox[0];
        int zombieY = this.hitbox[1];
        int zombieWidth = this.hitbox[2];
        int zombieHeight = this.hitbox[3];

        // Check if the shootbox and zombiehitbox intersect
        boolean isShot = (shootboxX + shootboxWidth >= zombieX && shootboxX <= zombieX + zombieWidth)
                        && (shootboxY + shootboxHeight >= zombieY && shootboxY <= zombieY + zombieHeight);
        boolean isAttacked = (attackboxX + attackboxWidth >= zombieX && attackboxX <= zombieX + zombieWidth)
                        && (attackboxY + attackboxHeight >= zombieY && attackboxY <= zombieY + zombieHeight); 
        

        return (isShot && p.playerStateID == 4 && p.spriteNum < 6)
                || (isAttacked && p.playerStateID == 3);
    }

    public boolean isPlayerHitByZombie(Player p) {
        int playerX = p.hitbox[0];
        int playerY = p.hitbox[1];
        int playerWidth = p.hitbox[2];
        int playerHeight = p.hitbox[3];
    
        int attackboxX = this.attackbox[0];
        int attackboxY = this.attackbox[1];
        int attackboxWidth = this.attackbox[2];
        int attackboxHeight = this.attackbox[3];
    
        int playerCenterX = playerX + playerWidth / 2;
        int playerCenterY = playerY + playerHeight / 2;
    
        int attackboxCenterX = attackboxX + attackboxWidth / 2;
        int attackboxCenterY = attackboxY + attackboxHeight / 2;
    
        int playerHalfWidth = playerWidth / 2;
        int playerHalfHeight = playerHeight / 2;
        int attackboxHalfWidth = attackboxWidth / 2;
        int attackboxHalfHeight = attackboxHeight / 2;
    
        boolean isPlayerHit = (Math.abs(playerCenterX - attackboxCenterX) < (playerHalfWidth + attackboxHalfWidth))
                && (Math.abs(playerCenterY - attackboxCenterY) < (playerHalfHeight + attackboxHalfHeight));
    
        return isPlayerHit;
    }

    public boolean isZombieNearPlayer(Player p) {
        int detectionRange = 400;
        return (Math.abs(p.x - this.x) <= detectionRange);
    }
    

}


class ZombieMan extends Zombie {
    public ZombieMan(int x, int y) {
        super(x, y);
    }

    public void zombieRun() {
        this.spriteNum = 0;
        this.zombieStateID = 1;
        this.damage = 5;
        setFrame(7);
        setSpeed(2);
        setTurnSpeed(1);
        setFrameDelay(8);
        this.xDiff = 80;
        this.yDiff = 90;
        this.sizeW = 35;
        this.sizeH = 50;
        path = "/image/zombiemanrun/Run_%d.png";
        setImage();
        
    }

    public void zombieAttack() {
        spriteNum = 0;
        this.zombieStateID = 2;
        setFrame(5);
        setSpeed(2);
        setTurnSpeed(0);
        setFrameDelay(4);
        path = "/image/zombiemanattack/Attack_%d.png";
        setImage();

    }

    public void zombieDead() {
        setHitbox(0, 0, 0, 0);
        spriteNum = 0;
        this.zombieStateID = 3;
        setFrame(5);
        setSpeed(0);
        setTurnSpeed(0);
        setFrameDelay(10);
        path = "/image/zombiemandead/Dead_%d.png";
        setImage();

    }

}

class ZombieWoman extends Zombie {
    public ZombieWoman(int x, int y) {
        super(x, y);
    }

    public void zombieRun() {
        spriteNum = 0;
        this.zombieStateID = 1;
        this.damage = 25;
        setFrame(7);
        setSpeed(10);
        setTurnSpeed(5);
        setFrameDelay(3);
        this.xDiff = 80;
        this.yDiff = 90;
        this.sizeW = 35;
        this.sizeH = 50;
        path = "/image/zombiewomanrun/Run_%d.png";
        setImage();
        

    }
    
    public void zombieAttack() {
        spriteNum = 0;
        this.zombieStateID = 2;
        setFrame(4);
        setSpeed(2);
        setTurnSpeed(0);
        setFrameDelay(4);
        path = "/image/zombiewomanattack/Attack_%d.png";
        setImage();

    }

    public void zombieDead() {
        setHitbox(0, 0, 0, 0);
        spriteNum = 0;
        this.zombieStateID = 3;
        setFrame(5);
        setSpeed(0);
        setTurnSpeed(0);
        setFrameDelay(10);
        path = "/image/zombiewomandead/Dead_%d.png";
        setImage();

    }

}


class WildZombie extends Zombie {
    public WildZombie(int x, int y) {
        super(x, y);
    }

    public void zombieRun() {
        spriteNum = 0;
        this.damage = 15;
        this.zombieStateID = 1;
        setFrame(8);
        setSpeed(4);
        setTurnSpeed(3);
        setFrameDelay(6);
        this.xDiff = 70;
        this.yDiff = 100;
        this.sizeW = 35;
        this.sizeH = 50;
        path = "/image/wildzombierun/Run_%d.png";
        setImage();

    }
    
    public void zombieAttack() {
        spriteNum = 0;
        this.zombieStateID = 2;
        setFrame(4);
        setSpeed(2);
        setTurnSpeed(0);
        setFrameDelay(4);
        path = "/image/wildzombieattack/Attack_%d.png";
        setImage();

    }

    public void zombieDead() {
        setHitbox(0, 0, 0, 0);
        spriteNum = 0;
        this.zombieStateID = 3;
        setFrame(5);
        setSpeed(0);
        setTurnSpeed(0);
        setFrameDelay(10);
        path = "/image/wildzombiedead/Dead_%d.png";
        setImage();
    }

}