import java.awt.*;
import javax.swing.*;

public class GameFrame extends JFrame{
    GameFrame(){
        GamePanel panel = new GamePanel(this);
        

        this.add(panel);
        this.setBackground(Color.black);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Pong Game");
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
