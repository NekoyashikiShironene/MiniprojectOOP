package panel;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;

import tile.Tile;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class HomePanel extends JPanel {

    private Tile[] tile;
    private String[] buttonText = { "START", "EXIT" };
    private int selectedButtonIndex = 0;

    public HomePanel(JFrame window) {
        this.tile = new Tile[6];
        for (int i = 0; i < tile.length; i++)
            this.tile[i] = new Tile(i);

        setPreferredSize(new DimensionUIResource(DefaultPanel.screenWidth, DefaultPanel.screenHeight));
        setFocusable(true);
        requestFocus();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_UP) {
                    selectedButtonIndex = (selectedButtonIndex - 1 + buttonText.length) % buttonText.length;
                } else if (keyCode == KeyEvent.VK_DOWN) {
                    selectedButtonIndex = (selectedButtonIndex + 1) % buttonText.length;
                } else if (keyCode == KeyEvent.VK_SPACE) {
                    if (selectedButtonIndex == 0) {
                        window.getContentPane().removeAll();
                        GamePanel gamePanel = new GamePanel(window);
                        window.add(gamePanel);
                        gamePanel.requestFocusInWindow();
                        gamePanel.startGameThread();
                        window.setVisible(true);
                    }
                    

                    else if (selectedButtonIndex == 1)
                        System.exit(0);

                }
                repaint();
            }
        });
    }

    private Rectangle getButtonRect(int index) {
        int buttonWidth = 250;
        int buttonHeight = 60;
        int x = (getWidth() - buttonWidth) / 2 - 50;
        int y = (getHeight() / 2 - buttonText.length * buttonHeight / 2) + index * buttonHeight + 100;
        return new Rectangle(x, y, buttonWidth, buttonHeight);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        

        for (int y = 0; y < DefaultPanel.maxScreenRow + 5; y++) {
            Tile tiletemp = this.tile[0];;
            if (y == 0)
                tiletemp = this.tile[3];
            else if (y == DefaultPanel.maxScreenRow-3)
                tiletemp = this.tile[4];
            else if (y > DefaultPanel.maxScreenRow-3)
                tiletemp = this.tile[5];

            for (int x = 0; x < DefaultPanel.maxScreenCol + 5; x++) {
                g.drawImage(tiletemp.image, DefaultPanel.tileSize * x, DefaultPanel.tileSize * y, DefaultPanel.tileSize,
                        DefaultPanel.tileSize, null);
            }
        }

        Font buttonFont = new Font("Arial", Font.BOLD, 36);
        g.setFont(buttonFont);

        for (int i = 0; i < buttonText.length; i++) {
            Rectangle buttonRect = getButtonRect(i);
            Color buttonColor = (selectedButtonIndex == i) ? Color.WHITE : Color.GRAY;
            g.setColor(buttonColor);
            g.fillRect(buttonRect.x, buttonRect.y, buttonRect.width, buttonRect.height);

            g.setColor(Color.BLACK);
            g.drawRect(buttonRect.x, buttonRect.y, buttonRect.width, buttonRect.height);

            g.setColor(Color.BLACK);
            g.drawString(buttonText[i], buttonRect.x + 5, buttonRect.y + buttonRect.height / 2 + 5);

            if (selectedButtonIndex == i) {
                int arrowX = buttonRect.x - 40;
                int arrowY = buttonRect.y + buttonRect.height / 2 - 5;
                int[] xPoints = { arrowX, arrowX - 15, arrowX - 15 };
                int[] yPoints = { arrowY, arrowY - 10, arrowY + 10 };
                g.fillPolygon(xPoints, yPoints, 3);
            }
        }

        Font titleFont = new Font("Arial", Font.BOLD, 56);
        g.setFont(titleFont);
        g.drawString("Last Survivor: Nakhon Si Thammarat", 360, 130);

        BufferedImage player = null, zombie = null, fire = null;
        try {
            player = ImageIO.read(getClass().getResourceAsStream("/image/playershoot/Shot_4.png"));
            zombie = ImageIO.read(getClass().getResourceAsStream("/image/zombiemandead/Dead_1.png"));
            fire = ImageIO.read(getClass().getResourceAsStream("/image/fire/Fire+Sparks3.png"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(player, 250, (DefaultPanel.screenHeight / 2) - 450, DefaultPanel.tileSize * 8,
                DefaultPanel.tileSize * 8, null);
        g.drawImage(zombie, 1000, (DefaultPanel.screenHeight / 2) - 325, DefaultPanel.tileSize * 6,
                DefaultPanel.tileSize * 6, null);

        g.drawImage(fire, 100, (DefaultPanel.screenHeight / 2) - 325, DefaultPanel.tileSize * 3,
                DefaultPanel.tileSize * 3, null);
        
        g.drawImage(fire, 1400, (DefaultPanel.screenHeight / 2) - 200, DefaultPanel.tileSize * 3,
                DefaultPanel.tileSize * 3, null);
    }
}
