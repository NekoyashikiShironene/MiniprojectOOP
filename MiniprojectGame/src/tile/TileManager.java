package tile;
import java.awt.Graphics2D;

import entity.Player;
import panel.DefaultPanel;
import panel.GamePanel;
import panel.KeyHandler;

public class TileManager {
    GamePanel gp;
    KeyHandler keyH;
    Player player;
    Tile[] tile;
    public int temp_distanceX, temp_distanceY;
    
    private final int mapTileSizeW = 16;
    private final int[] mapLimit = {mapTileSizeW*-1, mapTileSizeW+DefaultPanel.maxScreenRow};

    public TileManager(GamePanel gp, KeyHandler keyH, Player player) {
        this.gp = gp;
        this.temp_distanceY = 0;
        this.keyH = keyH;
        this.player = player;
        this.tile = new Tile[10];
        setTileImage();
    }

    public void setTileImage() {
        for (int i=0; i<6; i++) {
            this.tile[i] = new Tile(i);
        }
    }


    public void update() {
        
        if (keyH.upPressed && player.playerStateID != 0 && this.temp_distanceY <= 760) {
            this.temp_distanceY += player.turnSpeed; 
        }
        if (keyH.downPressed && player.playerStateID != 0 && this.temp_distanceY >= -830) {
            this.temp_distanceY -= player.turnSpeed;
        }

        if (gp.gameState == 1) {
            if (temp_distanceX >= DefaultPanel.tileSize*2) {
                temp_distanceX = player.speed;
            }
            temp_distanceX += player.speed;

        }
        
    }

    public void draw(Graphics2D g2) {
        for (int y = mapLimit[0]; y < mapLimit[1]; y++) {
            int tileID = 0;
            if (y == mapLimit[0]+10) tileID = 3;
            else if (y == mapLimit[1]-10) tileID = 4;
            else if (y < mapLimit[0]+10 || y > mapLimit[1]-10) tileID = 5;

            if (DefaultPanel.tileSize*y+temp_distanceY < -100 || DefaultPanel.tileSize*y+temp_distanceY > 1100) 
                continue;
    
            for (int x = 0; x < DefaultPanel.maxScreenCol+6; x++) {   
                g2.drawImage(tile[tileID].image, DefaultPanel.tileSize*x-temp_distanceX, DefaultPanel.tileSize*y+temp_distanceY, DefaultPanel.tileSize, DefaultPanel.tileSize, null);
            }
        
        }
        
    }
    
}



