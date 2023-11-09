package panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.plaf.DimensionUIResource;

import entity.Player;
import entity.ZombieSpawner;
import gameobj.ObjectManager;
import stat.Ammo;
import stat.HealthBar;
import stat.KillCount;
import stat.RunDistance;
import tile.TileManager;


class HealthBarThread extends Thread {
    private HealthBar healthBar;

    public HealthBarThread(HealthBar healthBar) {
        this.healthBar = healthBar;
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / DefaultPanel.FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (true) {
            healthBar.update();
            try {
                double remainTime = nextDrawTime - System.nanoTime();
                remainTime /= 1000000;
                if (remainTime < 0) {
                    remainTime = 0;
                }

                Thread.sleep((long) remainTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }
    }
}

class PlayerThread extends Thread {
    private Player player;

    public PlayerThread(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / DefaultPanel.FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (true) {
            player.update();
            try {
                double remainTime = nextDrawTime - System.nanoTime();
                remainTime /= 1000000;
                if (remainTime < 0) {
                    remainTime = 0;
                }

                Thread.sleep((long) remainTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }
    }
}

class ZombieThread extends Thread {
    private ZombieSpawner zombieSpawner;

    public ZombieThread(ZombieSpawner zombieSpawner) {
        this.zombieSpawner = zombieSpawner;
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / DefaultPanel.FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (true) {
            zombieSpawner.update();
            try {
                double remainTime = nextDrawTime - System.nanoTime();
                remainTime /= 1000000;
                if (remainTime < 0) {
                    remainTime = 0;
                }

                Thread.sleep((long) remainTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }
    }
}

public class GamePanel extends JPanel implements Runnable {

    public int gameState = 0;
    public double dificultRate = 1.0;

    private JFrame window;
    private Thread gameThread;
    private Thread healthBarThread, playerThread, zombieSpawnerThread;

    KeyHandler keyH;
    Player player;
    HealthBar hb;
    Ammo ammo;
    KillCount kc;
    RunDistance rd;
    TileManager tileM;
    ObjectManager objM;
    ZombieSpawner zbs;

    private String[] buttonText = { "Restart", "Exit" };
    private int selectedButtonIndex = 0;

    public GamePanel(JFrame window) {
        this.window = window;
        this.keyH = new KeyHandler();
        this.player = new Player(this, keyH);
        this.hb = new HealthBar(player);
        this.ammo = new Ammo(player);
        this.kc = new KillCount(player);
        this.rd = new RunDistance(this, player);
        this.tileM = new TileManager(this, keyH, player);
        this.objM = new ObjectManager(this, tileM, player);
        this.zbs = new ZombieSpawner(this, tileM, player);

        this.setPreferredSize(new DimensionUIResource(DefaultPanel.screenWidth, DefaultPanel.screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_RIGHT) {
                    gameState = 1;
                    removeKeyListener(this);
                    addKeyListener(keyH);
                }

                repaint();
            }
        });
        this.removeKeyListener(keyH);
    }

    private void reset() {
        this.removeKeyListener(keyH);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_UP) {
                    selectedButtonIndex = (selectedButtonIndex - 1 + buttonText.length) % buttonText.length;

                } else if (keyCode == KeyEvent.VK_DOWN) {
                    selectedButtonIndex = (selectedButtonIndex + 1) % buttonText.length;
                    
                } else if (keyCode == KeyEvent.VK_SPACE) {
                    if (selectedButtonIndex == 0) {
                        gameThread = null;
                        window.getContentPane().removeAll();
                        GamePanel gamePanel = new GamePanel(window);
                        window.add(gamePanel);
                        gamePanel.requestFocusInWindow();
                        gamePanel.startGameThread();
                        window.setVisible(true);

                    } else if (selectedButtonIndex == 1) {
                        window.getContentPane().removeAll();
                        HomePanel homePanel = new HomePanel(window);
                        window.add(homePanel);
                        homePanel.requestFocusInWindow();
                        window.setVisible(true);
                    }
                }
                repaint();
            }
        });
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        healthBarThread = new HealthBarThread(hb);
        playerThread = new PlayerThread(player);
        zombieSpawnerThread = new ZombieThread(zbs);

        playerThread.start();
        healthBarThread.start();
        zombieSpawnerThread.start();
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / DefaultPanel.FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (gameThread != null) {

            update();
            repaint();

            try {
                double remainTime = nextDrawTime - System.nanoTime();
                remainTime /= 1000000;
                if (remainTime < 0) {
                    remainTime = 0;
                }

                Thread.sleep((long) remainTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {

                e.printStackTrace();
            }

        }

    }

    public void update() {

        if (gameState == 2) {
            reset();
            gameState = 3;
        }
        objM.update();
        tileM.update();

    }

    private Rectangle getButtonRect(int index) {
        int buttonWidth = 250;
        int buttonHeight = 60;
        int x = (getWidth() - buttonWidth) / 2 - 50;
        int y = (getHeight() / 2 - buttonText.length * buttonHeight / 2) + index * buttonHeight + 100;
        return new Rectangle(x, y, buttonWidth, buttonHeight);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2); //game tile
        objM.draw(g2); //game object
        zbs.draw(g2); //zombie
        player.draw(g2); //player

        hb.draw(g2); //health bar
        ammo.draw(g2);
        rd.draw(g2); //run distance
        kc.draw(g2); //kill count

        if (gameState == 3) { //draw menu when gameover!
            for (int i = 0; i < buttonText.length; i++) {
                Rectangle buttonRect = getButtonRect(i);
                Color buttonColor = (selectedButtonIndex == i) ? Color.WHITE : Color.GRAY;
                g.setColor(buttonColor);
                g.fillRect(buttonRect.x, buttonRect.y, buttonRect.width, buttonRect.height);

                g.setColor(Color.BLACK);
                g.drawRect(buttonRect.x, buttonRect.y, buttonRect.width, buttonRect.height);

                g.setColor(Color.BLACK);
                g.drawString(buttonText[i], buttonRect.x + 10, buttonRect.y + buttonRect.height / 2 + 5);
                if (selectedButtonIndex == i) {
                    int arrowX = buttonRect.x - 40;
                    int arrowY = buttonRect.y + buttonRect.height / 2 - 5;
                    int[] xPoints = { arrowX, arrowX - 15, arrowX - 15 };
                    int[] yPoints = { arrowY, arrowY - 10, arrowY + 10 };
                    g.fillPolygon(xPoints, yPoints, 3);
                }
            }

        } else if (gameState == 0) { //Show how to play before game start
            Font titleFont = new Font("Arial", Font.BOLD, 24);
            g.setFont(titleFont);

            String[] lines = {
                    "Press 'Up' and 'Down' to move up and down.",
                    "Press 'A' to make an attack.",
                    "Press 'S' to shoot zombies.",
                    "Press the right button to start"
            };

            int lineHeight = g.getFontMetrics().getHeight();
            int y = 170;

            for (String line : lines) {
                g.drawString(line, 200, y);
                y += lineHeight;
            }

        }

        g2.dispose();
    }

}


