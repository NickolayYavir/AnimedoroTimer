import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class MyPanel extends JPanel {


    private boolean isTimerStarted = false;
    private int sessionTime = 2400000;
    private int seconds = 0;
    private int minutes = 0;

    private String secondString = String.format("%02d", seconds);
    private String minutesString = String.format("%02d", minutes);

    MyPanel() {

        JToggleButton startOrPauseButton = new JToggleButton("START", false);
        JLabel timerLabel = new JLabel("40:00");
        JLabel workOrAnimeLabel = new JLabel("Work");

        setPreferredSize(new Dimension(280, 299));
        setLayout(null);

        add(startOrPauseButton);
        add(timerLabel);
        add(workOrAnimeLabel);

        Timer timer = new Timer(1000, e1 -> {
            sessionTime -= 1000;
            minutes = (sessionTime / 60000) % 60;
            seconds = (sessionTime / 1000) % 60;

            secondString = String.format("%02d", seconds);
            minutesString = String.format("%02d", minutes);
            timerLabel.setText(minutesString + ":" + secondString);

        });

        startOrPauseButton.addActionListener(e -> {

            if (!isTimerStarted) {
                startOrPauseButton.setText("PAUSE");
                timer.start();
                isTimerStarted = true;
            } else {
                startOrPauseButton.setText("START");
                timer.stop();
                isTimerStarted = false;
            }

        });

        this.setBackground(Color.BLACK);

        startOrPauseButton.setBounds(5, 240, 270, 55);
        startOrPauseButton.setFocusable(false);
        startOrPauseButton.setBackground(Color.ORANGE);
        startOrPauseButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        startOrPauseButton.setFont(new Font("Monospaced", Font.PLAIN, 40));

        timerLabel.setBounds(5, 90, 270, 145);
        timerLabel.setOpaque(true);
        timerLabel.setBackground(Color.BLACK);
        timerLabel.setForeground(Color.GREEN);
        timerLabel.setHorizontalAlignment(JLabel.CENTER);
        timerLabel.setFont(new Font("Monospaced", Font.PLAIN, 80));
        timerLabel.setBorder(BorderFactory.createEtchedBorder(Color.WHITE, Color.LIGHT_GRAY));

        workOrAnimeLabel.setBounds(5, 10, 270, 50);
        workOrAnimeLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        workOrAnimeLabel.setFont(new Font("Monospaced", Font.PLAIN, 40));
        workOrAnimeLabel.setHorizontalAlignment(JLabel.CENTER);
        workOrAnimeLabel.setForeground(Color.WHITE);

    }
}
