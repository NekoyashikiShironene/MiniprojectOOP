package panel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//Used for player only

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean spacePressed;
    public boolean shootPressed;
    public boolean attackPressed;

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP) 
            upPressed = true;
        
        if (code == KeyEvent.VK_DOWN) 
            downPressed = true;

        if (code == KeyEvent.VK_RIGHT) 
            rightPressed = true; 
        
        if (code == KeyEvent.VK_SPACE) 
            spacePressed = true;
        
        if (code == KeyEvent.VK_A) 
            attackPressed = true;
        
        if (code == KeyEvent.VK_S) 
            shootPressed = true;
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP) {
            upPressed = false;
        }

        if (code == KeyEvent.VK_DOWN) {
            downPressed = false;
        } 

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }
}
