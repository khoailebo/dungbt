import java.awt.*;

public class Stater {
    int y = 330;
    public void draw(Graphics g,int num,int id)
    {
        g.setColor(Color.white);
        g.setFont(new Font("Comic Sans MS",Font.BOLD,150));
        // if(num >0) g.drawString(Integer.toString(num), 470, 300);
        // if(num == 0) 
        if(num == -1) g.drawString("GO", 400, y);
        else if(num == 0)g.drawString("Ready", 300, y);
        else if(num == 1)g.drawString(Integer.toString(num), 450, y);
        else if(num == 2)g.drawString(Integer.toString(num), 450, y);
        else if(num == 3)g.drawString(Integer.toString(num), 450, y);
        else if(num == 4)
        {
            if(id == 1)
            {
                g.setColor(Color.blue);
                g.drawString("Player 1", 225, y - 80);
                g.drawString("win", 350, y + 90);
            }
            if(id == 2)
            {
                g.setColor(Color.red);
                g.drawString("Player 2", 225, y - 80);
                g.drawString("win", 350, y + 90);
            }
        }
    }
}
