package Tutorial9;// Student name: Wu Kwun Yu
// Student ID  : 54845500

// Submission deadline: Friday, 15 Nov, 12 noon

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Tut_09 extends JFrame {
    private final JButton set, clear, hourPlus, minPlus;
    private final JLabel alarmState;  // ON or OFF
    private final MyPanel displayPanel;

    private int hour = 0, minute = 0, second = 0, sSecond = 0;
    private int alarmHour = 0, alarmMinute = 0;
    private final MyClock clockThread;

    // Other state variables used in your design
    private boolean settingMode = false;
    private volatile boolean setOn = false;
    private volatile int sHour = 0, sMinute = 0;

    public static void main(String[] args) {
        Tut_09 alarmclock = new Tut_09();
        alarmclock.setVisible(true);
    }

    private class MyPanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) // You may modify this method
        {
            super.paintComponent(g);
            if (!settingMode) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setFont(new Font("Monspaced", Font.BOLD, 40));
                g2.setColor(Color.blue);
                g2.drawString("Time: " + hour / 10 + hour % 10 + ":" +
                        minute / 10 + minute % 10 + ":" +
                        second / 10 + second % 10, 15, 40);
            } else {
                Graphics2D g2 = (Graphics2D) g;
                g2.setFont(new Font("Monspaced", Font.BOLD, 40));
                g2.setColor(Color.blue);
                g2.drawString("Set alarm: " + alarmHour / 10 + alarmHour % 10 + ":" +
                        alarmMinute / 10 + alarmMinute % 10, 15, 40);
            }
        }
    }

    public Tut_09() {
        setTitle("My Alarm Clock");
        setSize(350, 150);

        JPanel buttonPanel = new JPanel();
        set = new JButton("Set");
        clear = new JButton("Clear");
        hourPlus = new JButton("Hour+");
        minPlus = new JButton("Min+");

        alarmState = new JLabel("OFF");
        Border b = BorderFactory.createLineBorder(Color.RED);
        alarmState.setBorder(b);

        buttonPanel.add(alarmState);
        buttonPanel.add(set);
        buttonPanel.add(hourPlus);
        buttonPanel.add(minPlus);
        buttonPanel.add(clear);
        add(buttonPanel, "South");

        displayPanel = new MyPanel();
        add(displayPanel, "Center");

        clockThread = new MyClock();
        clockThread.start();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ActionListener listener = new ButtonListener();
        set.addActionListener(listener);
        clear.addActionListener(listener);
        hourPlus.addActionListener(listener);
        minPlus.addActionListener(listener);
    }

    // other methods required in your design

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            Object source = event.getSource();

            // Your codes
            if (source == set) {
                if (!setOn) {
                    setOn = true;
                    settingMode = true;
                } else {
                    settingMode = false;
                    setStartTime();
                    alarmState.setText("ON");
                }

                displayPanel.repaint();
            } else if (source == hourPlus) {
                updateAlarmHour();
            } else if (source == minPlus) {
                updateAlarmMinute();
            } else if (source == clear) {
                reset();
            }
        }
    }


    synchronized private void updateAlarmHour() {
        if (settingMode) {
            alarmHour = (alarmHour + 1) % 24;
            displayPanel.repaint();
        }
    }

    synchronized private void updateAlarmMinute() {
        if (settingMode) {
            alarmMinute = (alarmMinute + 1) % 59;
            displayPanel.repaint();
        }
    }

    synchronized private void reset() {
        setOn = false;
        settingMode = false;
        alarmHour = 0;
        alarmMinute = 0;

        alarmState.setText("OFF");
        displayPanel.repaint();
    }


    synchronized private void setStartTime() {
        LocalDateTime curTime = LocalDateTime.now();
        sHour = curTime.getHour();
        sMinute = curTime.getMinute();
        sSecond = curTime.getSecond();
    }

    synchronized private void updateCurrentTime() {
        LocalDateTime curTime = LocalDateTime.now();
        hour = curTime.getHour();
        minute = curTime.getMinute();
        second = curTime.getSecond();

        displayPanel.repaint();
    }


    private class MyClock extends Thread {
        @Override
        public void run() {
            // Your codes
            try {
                while (true) {
                    updateCurrentTime();
                    checkAlarmOn();
                    sleep(20);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        synchronized private void checkAlarmOn() {
            if (setOn) {
                if (sHour + alarmHour == hour && sMinute + alarmMinute == minute && sSecond == second) {
                    try {
                        while (setOn) {
                            updateCurrentTime();
                            java.awt.Toolkit.getDefaultToolkit().beep();
                            System.out.println("Beeping");
                            sleep(1000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
}
		