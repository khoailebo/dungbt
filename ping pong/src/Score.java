import java.awt.*;

public class Score {
    GamePanel gp;
    int player1,player2,width,height;
    Graphics g;
    int Option_Alpha = 0;
    public Score(GamePanel gp,int GAME_WIDTH,int GAME_HEIGHT) {
        this.gp = gp;
        width = GAME_WIDTH;
        height = GAME_HEIGHT;
    }
    public void draw(Graphics g)
    {
        this.g = g;
        if(gp.Game_State == GamePanel.Play_State)
        {
            drawBgr();
        }
        else if(gp.Game_State == GamePanel.Menu_State)
        {
            drawMenu();
        }
        else if(gp.Game_State == GamePanel.Option_State){
            drawBgr();
            drawOption();
        }
        
    }
    public void drawOption(){
        if(Option_Alpha < 150)Option_Alpha += 15;
        g.setColor(new Color(255,255,255,Option_Alpha));
        g.fillRect(0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
        String Menu_Options[] = {"Back to menu","Resume"};
        
        drawCenterMenu(Menu_Options);
    }
    public void drawCenterMenu(String menus[]){

        int Menu_Y = 150;
        drawMenuBck();
        g.setColor(Color.white);
        for(int i = 0;i < menus.length;i++){
            int x = getStringCenter(menus[i]);
            g.drawString(menus[i], x, Menu_Y);
            if(i == gp.key.Option_State_Choice)g.drawString(">",x - 40,Menu_Y);
            Menu_Y += 60;
        }
    }
    public void drawMenuBck(){
        int Bck_Width = 350;
        int Bck_Height = 400;
        int x = GamePanel.GAME_WIDTH / 2 - Bck_Width / 2;
        int y = GamePanel.GAME_HEIGHT / 2 - Bck_Height / 2;
        g.setColor(new Color(0, 0, 0, 220));
        g.fillRoundRect(x, y, Bck_Width, Bck_Height, 25, 25);
    }
    public void drawMenu(){
        String menu []= {"Welcome","to ping pong game"};
        g.setFont(new Font("MV Bali",Font.BOLD,40));
        g.setColor(Color.white);
        int string_y = 80;
        for (String item : menu)
        {
            int Center_X = getStringCenter(item);
            g.drawString(item, Center_X,string_y);
            string_y += 80;
        }
        String Play_Options [] = {"2 Players","1 Player","Exit Game"};
        g.setFont(new Font("MV Bali",Font.BOLD,30));
        string_y = 400;
        for(int i = 0;i < Play_Options.length;i++)
        {
            int Center_X = getStringCenter(Play_Options[i]);
            g.drawString(Play_Options[i],Center_X,string_y);
            if(i == gp.key.choice)g.drawString(">",Center_X - 40,string_y);
            string_y += 40;
        }
    }
    public int getStringCenter(String string){
        int length =(int) g.getFontMetrics().getStringBounds(string, g).getWidth();
        return GamePanel.GAME_WIDTH / 2 - length / 2;
    }
    public void drawBgr(){
        g.setFont(new Font("MV Bali",Font.BOLD,30));
        g.setColor(Color.blue);
        g.drawString(Integer.toString(player1 / 10) + Integer.toString(player1 % 10),450, 30);
        g.setColor(Color.red);
        g.drawString(Integer.toString(player2 / 10) + Integer.toString(player2 % 10),520, 30);
        g.setColor(Color.white);
        g.drawLine(500, 0, 500, height);
        g.drawOval(width/2 - 150/2, height/2 - 150/2, 150, 150);
    }
}
