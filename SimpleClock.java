import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class SimpleClock extends JFrame {

    Calendar calendar;
    SimpleDateFormat timeFormat;
    SimpleDateFormat dayFormat;
    SimpleDateFormat dateFormat;

    JLabel timeLabel;
    JLabel dayLabel;
    JLabel dateLabel;
    JButton formatToggleButton; // Button to toggle between 12/24 hour format
    JButton timezoneToggleButton; // Button to toggle between local time and GMT
    boolean is24HourFormat = false; // Initially set to 12-hour format
    boolean isLocalTime = true; // Initially set to true to show local time
    String time;
    String day;
    String date;

    SimpleClock() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Digital Clock");
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        this.setSize(400, 250);
        this.setResizable(false);

        timeFormat = new SimpleDateFormat("hh:mm:ss a");
        dayFormat = new SimpleDateFormat("EEEE");
        dateFormat = new SimpleDateFormat("dd MMMMM, yyyy");

        timeLabel = new JLabel();
        timeLabel.setFont(new Font("Verdana", Font.PLAIN, 50));
        timeLabel.setForeground(new Color(0x00FF00));
        timeLabel.setBackground(Color.BLACK);
        timeLabel.setOpaque(true);
        timeLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        dayLabel = new JLabel();
        dayLabel.setFont(new Font("Verdana", Font.BOLD, 25));
        dayLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        dateLabel = new JLabel();
        dateLabel.setFont(new Font("Verdana", Font.BOLD, 20));
        dateLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        formatToggleButton = new JButton("Toggle Format");
        formatToggleButton.addActionListener(e -> toggleTimeFormat());

        timezoneToggleButton = new JButton("Toggle Timezone");
        timezoneToggleButton.addActionListener(e -> toggleTimezone());

        this.add(timeLabel);
        this.add(dayLabel);
        this.add(dateLabel);
        this.add(formatToggleButton);
        this.add(timezoneToggleButton);

        this.setVisible(true);

        setTimer();
    }

    public void setTimer() {
        Thread clockThread = new Thread(() -> {
            while (true) {
                if (isLocalTime) {
                    calendar = Calendar.getInstance();
                } else {
                    calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
                }

                time = timeFormat.format(calendar.getTime());
                timeLabel.setText(time);

                day = dayFormat.format(calendar.getTime());
                dayLabel.setText(day);

                date = dateFormat.format(calendar.getTime());
                dateLabel.setText(date);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        clockThread.start();
    }

    private void toggleTimeFormat() {
        is24HourFormat = !is24HourFormat;
        if (is24HourFormat) {
            timeFormat = new SimpleDateFormat("HH:mm:ss");
        } else {
            timeFormat = new SimpleDateFormat("hh:mm:ss a");
        }
        updateTimeDisplay();
    }

    private void toggleTimezone() {
        isLocalTime = !isLocalTime;
        // Optionally, adjust the SimpleDateFormat timezone setting for a consistent experience
        timeFormat.setTimeZone(isLocalTime ? TimeZone.getDefault() : TimeZone.getTimeZone("GMT"));
        updateTimeDisplay();
    }

    private void updateTimeDisplay() {
        time = timeFormat.format(calendar.getTime());
        timeLabel.setText(time);
    }

    public static void main(String[] args) {
        new SimpleClock();
    }
}