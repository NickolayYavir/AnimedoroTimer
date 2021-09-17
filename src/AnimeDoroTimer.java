import javax.swing.*;

public class AnimeDoroTimer {

    public static void main(String[] args) {

        JFrame frame = new JFrame("AnimeDoro Timer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new MyPanel());
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }


}
