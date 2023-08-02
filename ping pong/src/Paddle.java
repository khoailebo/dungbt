import java.awt.*;
import java.awt.event.*;

public class Paddle extends Rectangle{
    static int PADDLE_WIDTH,PADDLE_HEIGHT;
    int y_Velocity,y_Direction;
    int ID;
    boolean AI = false;
    int AI_Count = 0;
    int SPEED = 10;
    boolean AI_Cal = false;
    int Center_Y;
    GamePanel gp;
    public int AI_Y = 0;
    public Paddle(GamePanel gp,int x,int y,int GAME_WIDTH,int GAME_HEIGHT,int paddle_WIDTH,int paddle_HEIGHT,int id,boolean AI){
        super(x,y,paddle_WIDTH,paddle_HEIGHT);
        this.gp = gp;
        this.ID = id;
        this.AI = AI;
        PADDLE_HEIGHT = paddle_HEIGHT;
        PADDLE_WIDTH = paddle_WIDTH;
    }
    public void setYdirention(int speed){
        y_Velocity = speed;
    }
    public int getAI_Y(){
        if(AI_Cal == false)
        {
            AI_Cal = true;
            return gp.ball.finalCollisionPoint(new Point(gp.ball.x,gp.ball.y), gp.ball.y_Direction).y;
        }
        else return AI_Y;
    }
    public void move(){
        if(AI == true )
        {
            AI_Y = getAI_Y();
            AI_Count++;
            // int Ball_Center_Y = (int)gp.ball.getCenterY();
            // int Paddle_Center_Y = (int)getCenterY();
            if(AI_Count ==5)
            {
            //     if(Paddle_Center_Y + GamePanel.Ball_D  > Ball_Center_Y )setYdirention(-10);
            //     else if(Paddle_Center_Y - GamePanel.Ball_D  < Ball_Center_Y )setYdirention(10);
                AI_Count = 0;
                int Paddle_Center_Y = (int)getCenterY();
                if(Paddle_Center_Y < AI_Y - 40)setYdirention(SPEED);
                else if(Paddle_Center_Y > AI_Y + 40)setYdirention(-SPEED);
                else setYdirention(0);
            }
        }
        y += y_Velocity;
    }
    public void draw(Graphics g){
        if(ID == 1)
        {
            g.setColor(Color.blue);
        }
        else
        {
            g.setColor(Color.red);
        }
        g.fillRect(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
    }
    public void keyPressed(KeyEvent e)
    {
        int choice = e.getKeyCode();
        switch(ID)
        {
            case 1:
            
            if(choice == KeyEvent.VK_W)
            {
                setYdirention(-SPEED);
            }
            else if(choice == KeyEvent.VK_S)
            setYdirention(SPEED);
            break;
            case 2:
            if(AI == false)
            {
                if(e.getKeyCode() == KeyEvent.VK_UP)
                {
                    setYdirention(-SPEED);
                }
                else if(choice == KeyEvent.VK_DOWN) setYdirention(SPEED);
            }
        }
    }
    public void keyReleased(KeyEvent e)
    {
        int choice = e.getKeyCode();
        switch(ID)
        {
            case 1:
            
            if(choice == KeyEvent.VK_W)
            {
                setYdirention(0);
            }
            else if(choice == KeyEvent.VK_S)
            setYdirention(0);
            break;
            case 2:
            if(e.getKeyCode() == KeyEvent.VK_UP)
            {
                setYdirention(0);
            }
            else if(choice == KeyEvent.VK_DOWN) setYdirention(0);
        }
    }
}
