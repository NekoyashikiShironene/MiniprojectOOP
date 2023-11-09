package stat;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Font;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Player;

public class Ammo {
    private Player player;
    private BufferedImage image = null;

    public Ammo(Player player) {
        this.player = player;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/image/ammoicon.png"));
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public void draw(Graphics2D g2) {      
        Font font = new Font("Arial", Font.BOLD, 36);   
        g2.drawImage(image, 15, 75, 55, 55, null);
        g2.setFont(font);
        g2.drawString(String.valueOf(player.shotgunBullet)+" / 48", 60, 115);

    }
    
}
