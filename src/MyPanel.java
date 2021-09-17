import javax.swing.*;
import java.awt.*;


class MyPanel extends JPanel {

    private JLabel timerLabel = new JLabel("40:00");
    private JLabel workOrAnimeLabel = new JLabel("Work");
    private JLabel graphicLabel = new JLabel();
    private JToggleButton startOrPauseButton = new JToggleButton("START", false);
    private JButton stopTimerButton = new JButton("STOP");
    private JButton skipPhaseButton = new JButton("SKIP");
    private JButton settingButton = new JButton("CHANGE SETTING");

    private JLabel inputWorkTimeLabel = new JLabel("Input work time");
    private JLabel inputRestTimeLabel = new JLabel("Input rest time");
    private JTextField workTimeField = new JTextField(5);
    private JTextField restTimeField = new JTextField(5);

    private JLabel errorLabel = new JLabel();

    private boolean isTimerStarted = false;
    private boolean isStudyTime = true;

    private int workTime = 2400000; // 40 minutes in milliseconds
    private int restTime = 1440000; // 24 minutes - average anime episode length

    private int sessionTime = workTime;
    private Timer timer;

    private JPanel circlePanel;
    private Thread circleThread;

    MyPanel() {

        timerSettings();
        onClickStartOrPause();
        onClickStopButton();
        onClickSkipButton();

        try {
            onClickSetting();
        } catch (Exception ignored) {
        }

        displayPanel();

    }

    public void setSessionTime(int sessionTime) {
        this.sessionTime = sessionTime;
    }

    public void setStudyTime(boolean studyTime) {
        isStudyTime = studyTime;
    }

    public void setWorkTime(int workTime) {
        this.workTime = workTime;
    }

    public void setRestTime(int restTime) {
        this.restTime = restTime;
    }

    public void setTimerStarted(boolean timerStarted) {
        isTimerStarted = timerStarted;
    }

    public int getSessionTime() {
        return sessionTime;
    }


    private void displayRemainingTime() {
        String minutesString = String.format("%02d", (sessionTime / 60000) % 60);
        String secondString = String.format("%02d", (sessionTime / 1000) % 60);
        timerLabel.setText(minutesString + ":" + secondString);
    }

    private void timerSwitchActivity(String Activity) {
        timer.stop();

        try {
            circleThread.interrupt();
        } catch (Exception ignored) {
        }

        java.awt.Toolkit.getDefaultToolkit().beep(); // BEEEEP, that use System sound // TODO change sound, beep work here badly
        setTimerStarted(false);
        startOrPauseButton.setSelected(false);
        startOrPauseButton.setText("START");
        displayRemainingTime();
        workOrAnimeLabel.setText(Activity);
    }

    private void onClickStartOrPause() {
        startOrPauseButton.addActionListener(e -> {

            errorLabel.setText("");

            if (!isTimerStarted) {

                startOrPauseButton.setText("PAUSE");
                timer.start();
                setTimerStarted(true);

                circlePanel = new CirclePanel();
                addCirclePanel();
                circleThread = new Thread((Runnable) circlePanel);
                circleThread.start();

                settingButton.setEnabled(false);

            } else {
                startOrPauseButton.setText("START");
                timer.stop();
                displayRemainingTime();
                setTimerStarted(false);

                try {
                    circleThread.interrupt();
                } catch (Exception ignored) {
                }
            }
        });
    }

    private void onClickStopButton() {
        stopTimerButton.addActionListener(e -> {
            timerSwitchActivity("Work");
            setSessionTime(workTime);
            displayRemainingTime();
            setStudyTime(true);

            try {
                circleThread.interrupt();
            } catch (Exception ignored) {
            }

            settingButton.setEnabled(true);
        });
    }

    private void onClickSkipButton() {

        skipPhaseButton.addActionListener(e -> {
            if (isStudyTime) {
                timerSwitchActivity("Anime");
                setStudyTime(false);
                setSessionTime(restTime);
            } else {
                timerSwitchActivity("Work");
                setStudyTime(true);
                setSessionTime(workTime);
            }
            displayRemainingTime();
            try {
                circleThread.interrupt();
            } catch (Exception ignored) {
            }
        });
    }

    // TODO remake input fields
    private void onClickSetting() {
        settingButton.addActionListener(e -> {

            errorLabel.setText("");

            String[] workTimeStrings = workTimeField.getText().split(":");
            String[] restTimeStrings = restTimeField.getText().split(":");

            try {
                if (getTimeFromStrings(workTimeStrings) < 3599001 && getTimeFromStrings(workTimeStrings) > 0
                        && getTimeFromStrings(restTimeStrings) < 3599001 && getTimeFromStrings(restTimeStrings) > 0) {

                    setWorkTime(getTimeFromStrings(workTimeStrings));
                    setRestTime(getTimeFromStrings(restTimeStrings));
                }
                else{
                    errorLabel.setText("Input error. Please input from 00:01 to 59:59");
                }

            } catch (Exception ignored) {
            }

            if (isStudyTime) {
                sessionTime = workTime;
                displayRemainingTime();
            } else {
                sessionTime = restTime;
                displayRemainingTime();
            }
        });
    }

    private int getTimeFromStrings(String[] strings) {
        int minutes = Integer.parseInt(strings[0]);
        int seconds = Integer.parseInt(strings[1]);
        return minutes * 60000 + seconds * 1000;
    }

    private void timerSettings() {

        timer = new Timer(1000, e1 -> {

            if (isStudyTime) {

                if (getSessionTime() > 0) {
                    displayRemainingTime();
                    setSessionTime(getSessionTime() - 1000);
                } else {
                    setStudyTime(false);
                    setSessionTime(restTime);
                    timerSwitchActivity("Anime");
                }
            } else { // if anime time

                if (getSessionTime() > 0) {
                    displayRemainingTime();
                    setSessionTime(getSessionTime() - 1000);
                } else {

                    setStudyTime(true);
                    setSessionTime(workTime);
                    timerSwitchActivity("Work");
                }
            }
        });
    }

    private void displayPanel() {
        setPreferredSize(new Dimension(280, 580));
        setLayout(null);

        add(graphicLabel);
        add(startOrPauseButton);
        add(timerLabel);
        add(workOrAnimeLabel);
        add(stopTimerButton);
        add(skipPhaseButton);
        add(settingButton);

        add(inputWorkTimeLabel);
        add(inputRestTimeLabel);
        add(workTimeField);
        add(restTimeField);
        add(errorLabel);

        this.setBackground(Color.BLACK);

        workOrAnimeLabel.setBounds(5, 10, 270, 50);
        workOrAnimeLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        workOrAnimeLabel.setFont(new Font("Monospaced", Font.PLAIN, 40));
        workOrAnimeLabel.setHorizontalAlignment(JLabel.CENTER);
        workOrAnimeLabel.setForeground(Color.WHITE);

        timerLabel.setBounds(5, 90, 270, 145);
        timerLabel.setOpaque(true);
        timerLabel.setBackground(Color.BLACK);
        timerLabel.setForeground(Color.GREEN);
        timerLabel.setHorizontalAlignment(JLabel.CENTER);
        timerLabel.setFont(new Font("Monospaced", Font.PLAIN, 80));
        timerLabel.setBorder(BorderFactory.createEtchedBorder(Color.WHITE, Color.LIGHT_GRAY));

        startOrPauseButton.setBounds(5, 240, 270, 55);
        startOrPauseButton.setFocusable(false);
        startOrPauseButton.setBackground(Color.ORANGE);
        startOrPauseButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        startOrPauseButton.setFont(new Font("Monospaced", Font.PLAIN, 40));

        stopTimerButton.setBounds(5, 300, 270, 55);
        stopTimerButton.setFocusable(false);
        stopTimerButton.setBackground(Color.ORANGE);
        stopTimerButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        stopTimerButton.setFont(new Font("Monospaced", Font.PLAIN, 40));

        skipPhaseButton.setBounds(5, 360, 270, 55);
        skipPhaseButton.setFocusable(false);
        skipPhaseButton.setBackground(Color.ORANGE);
        skipPhaseButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        skipPhaseButton.setFont(new Font("Monospaced", Font.PLAIN, 40));

        settingButton.setBounds(5, 420, 270, 55);
        settingButton.setFocusable(false);
        settingButton.setBackground(Color.ORANGE);
        settingButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        settingButton.setFont(new Font("Monospaced", Font.PLAIN, 25));

        inputWorkTimeLabel.setBounds(5, 480, 200, 30);
        inputWorkTimeLabel.setForeground(Color.ORANGE);
        inputWorkTimeLabel.setFont(new Font("Monospaced", Font.PLAIN, 20));

        workTimeField.setBounds(200, 480, 70, 30);
        workTimeField.setText("40:00");

        inputRestTimeLabel.setBounds(5, 520, 200, 30);
        inputRestTimeLabel.setForeground(Color.ORANGE);
        inputRestTimeLabel.setFont(new Font("Monospaced", Font.PLAIN, 20));

        restTimeField.setBounds(200, 520, 70, 30);
        restTimeField.setText("24:00");

        errorLabel.setBounds(5, 560, 270, 20);
        errorLabel.setForeground(Color.red);
        errorLabel.setFont(new Font("Monospaced", Font.PLAIN, 10));
    }

    private void addCirclePanel() {
        add(circlePanel);
        circlePanel.setBounds(5, 60, 270, 30);
    }

}
