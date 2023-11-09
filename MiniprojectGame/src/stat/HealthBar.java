package stat;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Player;

public class HealthBar {
    private Player player;
    private int displayhp;
    private BufferedImage image = null;
    private final int h = 25;
    private final int w = 450;

    public HealthBar(Player player) {
        this.player = player;
        this.displayhp = player.maxHealth;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/image/heart.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (this.displayhp > player.health) 
            this.displayhp--;
        else if (this.displayhp < player.health)
            this.displayhp++;

    }

    public void draw(Graphics2D g2) {

        g2.drawImage(image, 20, 40, this.h+20, this.h+20, null);
        g2.setColor(new Color(227, 27, 35));
        g2.fillRect(70, 50, w * this.displayhp / player.maxHealth, this.h);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(70, 50, w, h);
        g2.setStroke(new BasicStroke(1));

 

    }
    

}
