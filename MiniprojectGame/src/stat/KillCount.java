package stat;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Player;

public class KillCount {
    private Player player;
    private BufferedImage image = null;
        
    public KillCount(Player player) {
        this.player = player;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/image/skull.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(image, 15, 170, 40, 40, null);
        g2.drawString(String.valueOf(player.killCount) + " killed", 60, 200);
 
    }
    
}
