import javax.swing.*;
import java.awt.*;


class MyPanel extends JPanel {

    private JLabel timerLabel = new JLabel("40:00");
    private boolean isTimerStarted = false;
    private boolean isStudyTime = true;
    //private int sessionTime = 2400000; // 40 minutes in milliseconds
    private int sessionTime = 5000;
    private String secondString;
    private String minutesString;


    MyPanel() {

        JToggleButton startOrPauseButton = new JToggleButton("START", false);
        JLabel workOrAnimeLabel = new JLabel("Work");

        setPreferredSize(new Dimension(280, 299));
        setLayout(null);

        add(startOrPauseButton);
        add(timerLabel);
        add(workOrAnimeLabel);

        Timer timer = new Timer(1000, e1 -> {

            if (isStudyTime) {

                if (sessionTime > 0) {
                    displayRemainingTime();
                } else {
                    isStudyTime = false;
                    //sessionTime = 1440000; // 24 minutes - average anime episode length
                    sessionTime = 3000;
                    workOrAnimeLabel.setText("Anime");
                }

            } else { // if anime time

                if (sessionTime > 0) {
                    displayRemainingTime();
                } else {
                    isStudyTime = true;
                    //sessionTime = 2400000; // 40 minutes in milliseconds
                    sessionTime = 5000;
                    workOrAnimeLabel.setText("Work");
                }
            }


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

    private void displayRemainingTime() {
        sessionTime -= 1000;
        minutesString = String.format("%02d", (sessionTime / 60000) % 60);
        secondString = String.format("%02d", (sessionTime / 1000) % 60);
        timerLabel.setText(minutesString + ":" + secondString);
    }
}
