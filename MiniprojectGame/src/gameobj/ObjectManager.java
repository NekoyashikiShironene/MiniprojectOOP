package gameobj;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import entity.Player;
import panel.DefaultPanel;
import panel.GamePanel;
import random.RD;
import tile.TileManager;

public class ObjectManager {
    private GamePanel gp;
    private Player player;
    private TileManager tm;
    private ArrayList<GameObject> go;

    public ObjectManager(GamePanel gp, TileManager tm, Player player) {
        this.gp = gp;
        this.tm = tm;
        this.player = player;
        this.go = new ArrayList<GameObject>();
    }

    public void update() {
        if (this.gp.gameState == 1) {
            generate(new Fire(), 0.09*(1+gp.dificultRate));
            generate(new AmmoPack(), 0.003);
            generate(new FirstAid(), 0.003);
            generate(new DecorativeObj(), 0.1);
        }

        Iterator<GameObject> it = this.go.iterator();
        while (it.hasNext()) {
            GameObject gameObject = it.next();
            gameObject.spriteCounter++;

            if (gameObject.spriteCounter >= gameObject.framedelay) {
                gameObject.spriteNum++;
                gameObject.spriteCounter = 0;
            }
            if (gameObject.spriteNum >= gameObject.frames) {
                gameObject.spriteNum = 0;
            }
            gameObject.x -= player.speed;
            gameObject.updateCollectbox(tm.temp_distanceY);
            gameObject.playerStep(player);
            if (gameObject instanceof FirstAid) {
                FirstAid fa = (FirstAid)gameObject;
                if (fa.playerStepped) {
                    it.remove();
                    continue;
                }
                    
            } else if ( gameObject instanceof AmmoPack) {
                AmmoPack ap = (AmmoPack)gameObject;
                if (ap.playerStepped) {
                    it.remove();
                    continue;
                }

            }
                

            if (gameObject.x <= -100) {
                it.remove();
            }
        }
    }

    public void generate(GameObject go, double rate) {
        boolean shouldGenerate = Math.random() < rate;
        if (shouldGenerate) {
            go.setPostion(DefaultPanel.screenWidth + 100, RD.random(-5, 18) * DefaultPanel.tileSize);
            this.go.add(go);
        }
    }

    public void draw(Graphics2D g2) {
        for (int i = 0; i < this.go.size(); i++) {
            GameObject gameobject = this.go.get(i);

            if (gameobject.y+tm.temp_distanceY < -100 || gameobject.y+tm.temp_distanceY > 900) 
                continue;

            BufferedImage image = null;
            if (gameobject.spriteNum >= gameobject.frames) {
                image = gameobject.BI[0];
            } else {
                image = gameobject.BI[gameobject.spriteNum];
            }

            

            g2.drawImage(image, gameobject.x, gameobject.y + tm.temp_distanceY, DefaultPanel.tileSize * gameobject.scale,
                    DefaultPanel.tileSize * gameobject.scale, null);

            //sg2.drawRect(gameobject.collectbox[0], gameobject.collectbox[1], gameobject.collectbox[2], gameobject.collectbox[3]);
        }
    }
}
