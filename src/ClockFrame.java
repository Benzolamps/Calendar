import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * 设置闹钟界面
 *
 * @author Benzolamps
 *
 */
public class ClockFrame extends JFrame implements ActionListener {
    private static final long serialVersionUID = -2335732010000436154L;
    private JTextField hourText, minuteText;
    private CalendarFrame frame;

    public ClockFrame(CalendarFrame frame) {
        super("设置闹钟");
        this.frame = frame;
        setLayout(new BorderLayout(30, 30));
        setSize(240, 160);
        setVisible(true);
        setResizable(false);
        getContentPane().add(new JLabel("设置闹钟的时间："), BorderLayout.NORTH);
        getContentPane().add(createCenterPane(), BorderLayout.CENTER);
        getContentPane().add(createSouthPane(), BorderLayout.SOUTH);
    }

    public JPanel createCenterPane() {
        hourText = new JTextField();
        minuteText = new JTextField();

        hourText.setText(String.valueOf(Date.getToday(frame.getTimeZone()).hour));
        minuteText.setText(String.valueOf(Date.getToday(frame.getTimeZone()).minute));

        JPanel centerPane = new JPanel();
        centerPane.setLayout(new GridLayout(1, 5));
        centerPane.add(new JLabel());
        centerPane.add(hourText);
        centerPane.add(new JLabel(":", SwingConstants.CENTER));

        centerPane.add(minuteText);
        centerPane.add(new JLabel());

        return centerPane;
    }

    public JPanel createSouthPane() {
        JButton cancelButton = new JButton("取消");
        cancelButton.setMnemonic(KeyEvent.VK_C);
        cancelButton.addActionListener(this);

        JButton okButton = new JButton("确定");
        okButton.setMnemonic(KeyEvent.VK_O);
        okButton.addActionListener(this);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout(60, 60));
        southPanel.add(cancelButton, BorderLayout.WEST);
        southPanel.add(okButton, BorderLayout.EAST);

        return southPanel;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if ("确定".equals(arg0.getActionCommand())) {
            if (!hourText.getText().isEmpty() || !minuteText.getText().isEmpty()) {
                int hour = 0, minute = 0;
                try {
                    hour = Integer.parseInt(hourText.getText());
                    minute = Integer.parseInt(minuteText.getText());
                } catch (NumberFormatException e) {
                    hour = -1;
                }

                if (hour > 23 || hour < 0 || minute > 59 || minute < 0) {
                    new MessageFrame("提示", "时间值非法!", null).setVisible(true);
                }
                else {
                    setVisible(false);
                    frame.setClock(new Date(-1, -1, -1, hour, minute, -1));
                    frame.setAlarmed(false);
                }
            }

            else {
                new MessageFrame("提示", "时间值为空!", null).setVisible(true);
            }
        }

        if ("取消".equals(arg0.getActionCommand())) {
            setVisible(false);
        }
    }
}
