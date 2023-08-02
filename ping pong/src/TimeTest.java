public class TimeTest {
    public static void main(String[] args)
    {
        long lastTime = System.nanoTime();
        double tick = 60.0;
        double ns = 1000000000/tick;
        double delta = 0;
        while(true)
        {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            if(delta >= 100)
            {
                System.out.println("Test\n");
                delta = 0;
            }
            lastTime = now;
        }
    }
}
