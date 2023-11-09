package entity;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;

import panel.DefaultPanel;
import panel.GamePanel;
import tile.TileManager;

import java.awt.image.BufferedImage;

public class ZombieSpawner {
    Player player;
    GamePanel gp;
    TileManager tm;
    ArrayList<Zombie> zombies;

    public ZombieSpawner(GamePanel gp, TileManager tm, Player player) {
        this.gp = gp;
        this.tm = tm;
        this.player = player;
        this.zombies = new ArrayList<Zombie>();
    }

    public static int random(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    public void spawn() {
        boolean shouldGenerate = Math.random() < (gp.dificultRate * 0.5); // 10% chance
        if (shouldGenerate) {
            int x = DefaultPanel.screenWidth + 100, y = random(-7, 18) * DefaultPanel.tileSize;
            double randomValue = Math.random();

            if (randomValue < 0.7) { 
                Zombie z = new ZombieMan(x, y);
                this.zombies.add(z);
            } else if (randomValue < 0.9) { 
                Zombie z = new WildZombie(x, y);
                this.zombies.add(z);
            } else { 
                Zombie z = new ZombieWoman(x, y);
                this.zombies.add(z);
            }
        }

    }

    public void update() {
        if (this.gp.gameState == 1)
            spawn();
        Iterator<Zombie> it = this.zombies.iterator();

        while (it.hasNext()) {
            Zombie z = it.next();
            z.setPostion(z.x - (z.speed + player.speed),
                    (z.hitbox[1] < (player.hitbox[1]) ? z.y + z.turnSpeed : z.y - z.turnSpeed));
            if (player.playerStateID == 5) z.setTurnSpeed(0);
            z.updateHitbox(tm.temp_distanceY);
            z.spriteCounter++;
            if (z.isZombieNearPlayer(player)) {
                if (z.isKilled(player) && z.isAlive && z.zombieStateID != 3) {
                    z.zombieDead();
                    player.killCount++;
                }

                if (z.isPlayerHitByZombie(player) && !z.attacked && z.zombieStateID == 1) {
                    z.zombieAttack();
                    z.attacked = true;
                    player.health -= z.damage;
                }
            }

            if (z.x <= -50) {
                it.remove();
            }
            if (z.spriteCounter >= z.framedelay) {
                z.spriteNum++;
                z.spriteCounter = 0;
            }
            if (z.spriteNum >= z.frames) {
                if (z.zombieStateID == 3) {
                    z.isAlive = false;
                }
                if (z.zombieStateID == 2) {
                    z.zombieRun();
                }

                z.spriteNum = 0;
            }
        }

    }

    public void draw(Graphics2D g2) {
        for (int i = 0; i < this.zombies.size(); i++) {
            Zombie z = zombies.get(i);
            if (z.y + tm.temp_distanceY < -100 || z.y + tm.temp_distanceY > 900)
                continue;
            BufferedImage image = null;

            if (z.spriteNum >= z.frames + 1)
                image = z.BI[0];
            else {
                image = z.BI[z.spriteNum];
            }
            if (z.isAlive) {
                g2.drawImage(image, z.x, z.y + tm.temp_distanceY, z.getSizeW(), z.getSizeH(), null);
                // g2.draw(new Rectangle(z.hitbox[0], z.hitbox[1], z.hitbox[2], z.hitbox[3]));
                // //draw hitbox
                // g2.draw(new Rectangle(z.attackbox[0], z.attackbox[1], z.attackbox[2],
                // z.attackbox[3]));
            }

        }

    }

}
