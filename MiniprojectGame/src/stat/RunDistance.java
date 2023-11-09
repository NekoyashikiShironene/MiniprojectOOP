package stat;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Player;
import panel.GamePanel;

public class RunDistance {
    private Player player;
    private GamePanel gp;
    private BufferedImage image = null;

    public RunDistance(GamePanel gp, Player player) {
        this.player = player;
        this.gp = gp;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/image/run.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int calculateMeters() {
        return player.runDistance / 60;
    }

    public void draw(Graphics2D g2) {
        gp.dificultRate = (double)calculateMeters() / 2500.0;  
        g2.drawImage(image, 15, 120, 40, 40, null); 
        g2.drawString(String.valueOf(calculateMeters())+" m", 60, 155);

    }
}
