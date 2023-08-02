import java.awt.*;

public class Ball extends Rectangle{
    
    //instance variable has default value is 0;
    int x_Direction,y_Direction,x_Velocity,y_Velocity;
    int speed = 3;
    int Center_Y;
    GamePanel gp;
    public Ball(GamePanel gp,int x,int y,int d) {
        super(x,y,d,d);
        this.gp = gp;
        setXDirection();
        setYDirection();
    }
    public void setXDirection(){
        int direction =(int) (Math.random() * 2);
        if(direction == 0)--direction;
        x_Direction = direction;
    }
    public void setYDirection(){
        int direction =(int) (Math.random() * 2);
        if(direction == 0)--direction;
        y_Direction = direction;
        
    }
    public void move(){
        if(speed >16)speed = 16;
        x_Velocity = x_Direction * speed;
        y_Velocity = y_Direction * speed;
        x += x_Velocity;
        y += y_Velocity;
    }
    public void draw(Graphics g)
    {
        g.setColor(Color.white);
        g.fillOval(x, y, width, height);
    }
    public Point nextPointCollision(Point Current_Point,int y_direct){
        int X_To_Go = GamePanel.GAME_WIDTH - Current_Point.x;
        int Y_To_Go =(int) (X_To_Go );
        int Curent_Y = Current_Point.y + y_direct * Y_To_Go;
        if(Curent_Y >= 0 && Curent_Y <= GamePanel.GAME_HEIGHT)return new Point(GamePanel.GAME_WIDTH,Curent_Y);
        else 
        {
            Y_To_Go = (y_direct == 1) ? (GamePanel.GAME_HEIGHT - Current_Point.y) : Current_Point.y;
            X_To_Go = Y_To_Go;
            int Curent_X = Current_Point.x + X_To_Go;
            Curent_Y = (y_direct == 1) ? GamePanel.GAME_HEIGHT : 0;
            return nextPointCollision(new Point(Curent_X, Curent_Y),-y_direct);
        }
    }
    public Point finalCollisionPoint(Point point,int y_direct){
        return nextPointCollision(point,y_direct);
    }
}
