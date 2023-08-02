import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {
    JFrame frame;
    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = (int) (GAME_WIDTH * (0.5555));
    int PADDLE_WIDTH = 10, PADDLE_HEIGHT = 100;
    Paddle paddle1, paddle2;
    Ball ball;
    Score score;
    Image image;
    Graphics2D graphics;
    Thread gameThread;
    boolean loose = true;
    boolean sleep = true;
    Stater stater;
    Song bck = new Song();
    Song se = new Song();
    public int Game_State = 0;
    public static final int Menu_State = 1;
    public static final int Play_State = 2;
    public static final int Option_State = 3;
    public static final int Start_State = 4;
    static final int Ball_D = 20;
    boolean ai = false;
    static final Dimension SCREEN_DIMENSION = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    int num = 0;
    int win;
    keyListener key = new keyListener();

    public GamePanel(JFrame f) {
        newPaddles();
        newBall();
        newScore();
        newStater();
        Game_State = Menu_State;
        frame = f;
        this.setFocusable(true);
        this.setPreferredSize(SCREEN_DIMENSION);
        this.addKeyListener(key);
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void playBck() {
        bck.setFile(0);
    }

    public void newPaddles() {
        paddle1 = new Paddle(this, 0, GAME_HEIGHT / 2 - PADDLE_HEIGHT / 2, GAME_WIDTH, GAME_HEIGHT, PADDLE_WIDTH,
                PADDLE_HEIGHT, 1, false);
        paddle2 = new Paddle(this, GAME_WIDTH - PADDLE_WIDTH, GAME_HEIGHT / 2 - PADDLE_HEIGHT / 2, GAME_WIDTH,
                GAME_HEIGHT, PADDLE_WIDTH, PADDLE_HEIGHT, 2, ai);
    }

    public void paint(Graphics g) {
        image = createImage(getWidth(), getHeight());
        graphics = (Graphics2D) image.getGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        draw(graphics);// ve tranh
        g.drawImage(image, 0, 0, this);// treo tranh
    }

    public void newStater() {
        stater = new Stater();
    }

    public void draw(Graphics g) {
        if (Game_State != Menu_State) {
            paddle1.draw(g);
            paddle2.draw(g);
            ball.draw(g);
        }
        score.draw(g);
        if (Game_State == Start_State)
            stater.draw(g, num, win);
        // if(loose == true)
        // {
        // stater.draw(g);
        // loose = false;
        // }
        // Toolkit.getDefaultToolkit().sync();
    }

    public void newBall() {
        ball = new Ball(this, GAME_WIDTH / 2 - Ball_D / 2, GAME_HEIGHT / 2 - Ball_D / 2, Ball_D);
    }

    public void newScore() {
        score = new Score(this, GAME_WIDTH, GAME_HEIGHT);
    }

    public void move() {
        if (Game_State == Play_State && sleep == false) {
            paddle1.move();
            paddle2.move();
            ball.move();
        }
    }

    public void playSE(int i) {
        se.setFile(i);
        se.start();
    }

    public void checkCollision() {
        // ball touch wall
        if (ball.y <= 0) {
            ball.y_Direction = -ball.y_Direction;
            playSE(1);
        }
        if (ball.y + Ball_D >= GAME_HEIGHT) {
            ball.y_Direction = -ball.y_Direction;
            playSE(1);
        }

        // paddle touch wall
        if (paddle1.y <= 0)
            paddle1.y = 0;
        if (paddle1.y + PADDLE_HEIGHT >= GAME_HEIGHT)
            paddle1.y = GAME_HEIGHT - PADDLE_HEIGHT;
        if (paddle2.y <= 0)
            paddle2.y = 0;
        if (paddle2.y + PADDLE_HEIGHT >= GAME_HEIGHT)
            paddle2.y = GAME_HEIGHT - PADDLE_HEIGHT;

        // paddle touch ball
        if (ball.intersects(paddle1)) {
            ball.x_Direction = -ball.x_Direction;
            paddle2.AI_Cal = false;
            ball.speed++;
            playSE(2);
        }
        if (ball.intersects(paddle2)) {
            ball.x_Direction = -ball.x_Direction;
            ball.speed++;
            playSE(2);
        }
        // loosing
        if (ball.x <= -3) {
            score.player2++;
            newGame();
            // newBall();
            // newPaddles();
        }
        if (ball.x + Ball_D >= GAME_WIDTH + 3) {
            score.player1++;
            // setState(Start_State);
            // newBall();
            // newPaddles();
            newGame();
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        double tick = 60.0;
        double delta = 0;
        double ns = 1000000000 / tick;
        playBck();
        while (true) {
            long now = System.nanoTime();
            delta += ((now - lastTime) / ns);
            // if(loose == true)
            // {
            // newGame();
            // }
            if (delta >= 1) {
                move();
                checkCollision();
                repaint();
                delta = 0;
            }
            // try {
            // Thread.sleep(10);
            // } catch (InterruptedException e) {
            // e.printStackTrace();
            // }
            lastTime = now;
        }
    }

    public void newGame(int id) {
        // int choice = JOptionPane.showConfirmDialog(null, "Do you want to play
        // again?", "You loosed", JOptionPane.YES_NO_OPTION);
        // if(choice == 1 || choice == -1)
        // {
        // frame.dispose();
        // }
        win = id;
        num = 5;
        newBall();
        newPaddles();
        try {
            Thread.sleep(100);
        } catch (Exception ex) {
        }
        while (true) {
            --num;
            repaint();
            try {
                if (num > -1)
                    Thread.sleep(100);
                else if (num == -1)
                    Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (num == -1)
                break;
        }
        // --num;
        // repaint();
        // try {
        // Thread.sleep(1000);
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
        // --num;
        // repaint();
        // try {
        // Thread.sleep(1000);
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
        // --num;
        // repaint();
        // try {
        // Thread.sleep(1000);
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
        // --num;
        // repaint();
        // try {
        // Thread.sleep(1000);
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
        // long lastTime = System.nanoTime();
        // double tick = 60.0;
        // double ns = 1000000000/tick;
        // double delta = 0;
        // while(true)
        // {
        // long now = System.nanoTime();
        // delta += (now - lastTime) / ns;
        // if(delta >= 100)
        // {
        // removeAll();
        // validate();
        // repaint();
        // --num;
        // }
        // lastTime = now;
        // if(num == -1)break;
        // }
        setState(Play_State);
    }

    public void newGame() {
        // int choice = JOptionPane.showConfirmDialog(null, "Do you want to play
        // again?", "You loosed", JOptionPane.YES_NO_OPTION);
        // if(choice == 1 || choice == -1)
        // {
        // frame.dispose();
        // }
        num = 4;
        win = 0;
        newBall();
        newPaddles();
        // draw(graphics);
        paint(this.getGraphics());
        revalidate();
        try {
            sleep = true;
            Thread.sleep(1000);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            sleep = false;
        }
        // --num;
        // repaint();
        // try {
        // Thread.sleep(1000);
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
        // --num;
        // repaint();
        // try {
        // Thread.sleep(1000);
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
        // --num;
        // repaint();
        // try {
        // Thread.sleep(1000);
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
        // --num;
        // repaint();
        // try {
        // Thread.sleep(1000);
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
        // long lastTime = System.nanoTime();
        // double tick = 60.0;
        // double ns = 1000000000/tick;
        // double delta = 0;
        // while(true)
        // {
        // long now = System.nanoTime();
        // delta += (now - lastTime) / ns;
        // if(delta >= 100)
        // {
        // removeAll();
        // validate();
        // repaint();
        // --num;
        // }
        // lastTime = now;
        // if(num == -1)break;
        // }
    }

    public void setState(int State) {
        Game_State = State;
    }

    class keyListener extends KeyAdapter {
        int choice = 0;
        int Option_State_Choice = 0;

        public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            if (Game_State == Play_State) {
                getStartstate(e);
            } else if (Game_State == Menu_State) {
                getMenuState(code);
            } else if (Game_State == Option_State) {
                getOptionState(code);
            }
        }

        public void getStartstate(KeyEvent e) {
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);
            if (e.getKeyCode() == KeyEvent.VK_O) {
                Game_State = Option_State;
            }
        }

        public void getOptionState(int code) {
            if (code == KeyEvent.VK_W) {
                Option_State_Choice--;
                if (Option_State_Choice < 0)
                    Option_State_Choice = 1;
            } else if (code == KeyEvent.VK_S) {
                Option_State_Choice++;
                if (Option_State_Choice > 1)
                    Option_State_Choice = 0;
            } else if (code == KeyEvent.VK_ENTER) {
                switch (Option_State_Choice) {
                    case 0:
                        setState(Menu_State);
                        break;
                    case 1:
                        setState(Play_State);
                        score.Option_Alpha = 0;
                        break;
                }
            } else if (code == KeyEvent.VK_O) {
                setState(Play_State);
                score.Option_Alpha = 0;
            }
        }

        public void getMenuState(int code) {
            if (code == KeyEvent.VK_W) {
                choice--;
                if (choice < 0)
                    choice = 2;
            } else if (code == KeyEvent.VK_S) {
                choice++;
                if (choice > 2)
                    choice = 0;
            } else if (code == KeyEvent.VK_ENTER) {
                switch (choice) {
                    case 1:
                        ai = true;
                        break;
                    case 0:
                        ai = false;
                        break;
                    case 2:
                        System.exit(0);
                }
                setState(Play_State);
                newGame();
            }

        }

        public void keyReleased(KeyEvent e) {
            if (Game_State == Play_State) {
                paddle1.keyReleased(e);
                paddle2.keyReleased(e);
            }
        }
    }
}
