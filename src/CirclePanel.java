import javax.swing.*;
import java.awt.*;

// TODO rename class
public class CirclePanel extends JPanel implements Runnable {

    private int startXPoint = 0;
    private int changeX = -120;

    CirclePanel() {
        this.setBackground(Color.BLACK);
    }

    private void circleAnimation(){
        while(!Thread.currentThread().isInterrupted()){
            if(startXPoint == 240 || startXPoint == 0){
                changeX *=-1;
            }
            startXPoint += changeX;
            repaint();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    public void paint(Graphics g) {
        super.paint(g); // paint background
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(Color.WHITE);
        g2D.setColor(Color.ORANGE);
        g2D.fillOval(startXPoint, 0, 30, 30);

    }

    @Override
    public void run() {
        circleAnimation();
    }
}
