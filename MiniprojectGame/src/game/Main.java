package game;
import javax.swing.JFrame;
import panel.HomePanel;

public class Main {

    public static void main(String[] args) {
        JFrame window = new JFrame("Last Survivor: Nakhon Si Thammarat");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);

        HomePanel homePanel = new HomePanel(window);
        window.add(homePanel);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        
        
    }
}

