import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.ParseException;
import java.util.TimeZone;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import javafx.scene.media.AudioClip;

/**
 *
 * 万年历主界面类
 *
 * @author Benzolamps
 *
 */
public class CalendarFrame extends JFrame implements ActionListener, FocusListener {
    private static final long serialVersionUID = 4726876818484902015L;
    private JPanel southPanel;
    private JPanel northPanel;
    private JEditorPane[] dayText;
    private JEditorPane infoText;
    private JEditorPane[] thisText = new JEditorPane[32];
    private JLabel timeText;
    private JButton[] button;
    private final static char[] WEEK = { '日', '一', '二', '三', '四', '五', '六' };
    private JMenuBar menuBar;
    private Date date;
    private Date today;
    private TimeZone z;
    private Date clock = new Date(-1, -1, -1, -1, -1, -1);
    private boolean isAlarmed = false;
    private AudioClip audio = new AudioClip(CalendarFrame.class.getResource("/wav/Alarm.wav").toString());

    // CalendarFrame.class.getResource() 获取当前路径资源
    public CalendarFrame() throws ParseException, IOException {
        super("万年历");
        audio.setCycleCount(AudioClip.INDEFINITE); // AudioClip无限循环播放
        z = TimeZone.getDefault();
        setLayout(new BorderLayout());
        setSize(400, 600);
        setResizable(false);
        setJMenuBar(createMenuBar());
        getContentPane().add(createSouthPanel(), BorderLayout.CENTER);
        getContentPane().add(createNorthPanel(), BorderLayout.NORTH);
        timeText = new JLabel(" ", SwingConstants.RIGHT);
        getContentPane().add(timeText, BorderLayout.SOUTH);
        today = Date.getToday(TimeZone.getDefault());
        date = today.clone();
        Timer timer = new Timer(1000, this);
        timer.start();
        showOneMonth();
        setVisible(true);
    }

    private JPanel createNorthPanel() throws IOException {
        northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());
        infoText = new JEditorPane();
        infoText.setContentType("text/html");
        infoText.setBackground(getBackground());
        infoText.setEditable(false);
        northPanel.add(infoText, BorderLayout.CENTER);
        button = new JButton[4];
        button[0] = new JButton("<<");
        button[1] = new JButton("<");
        button[2] = new JButton(">");
        button[3] = new JButton(">>");

        for (JButton b : button) {
            b.addActionListener(this);
        }

        JPanel westPanel = new JPanel();
        westPanel.add(button[0], BorderLayout.WEST);
        westPanel.add(button[1], BorderLayout.EAST);
        northPanel.add(westPanel, BorderLayout.WEST);

        JPanel eastPanel = new JPanel();
        eastPanel.add(button[2], BorderLayout.WEST);
        eastPanel.add(button[3], BorderLayout.EAST);
        northPanel.add(eastPanel, BorderLayout.EAST);

        return northPanel;
    }

    private JPanel createSouthPanel() {
        int sumDay = 0;
        southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(7, 7, 5, 20));
        dayText = new JEditorPane[42];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                JPanel tempPanel = new JPanel();
                tempPanel.setLayout(new BorderLayout());
                if (i == 0) {
                    JEditorPane weekText = new JEditorPane();
                    weekText.setContentType("text/html");
                    weekText.setBackground(getBackground());
                    weekText.setEditable(false);
                    weekText.setSelectionColor(getBackground());
                    weekText.setText("<center><br/><font size=5><font face=楷体>" + WEEK[j] + " </font></center>");
                    tempPanel.add(weekText, SwingConstants.CENTER);
                } else {
                    dayText[sumDay] = new JEditorPane();
                    dayText[sumDay].setEditable(false);
                    dayText[sumDay].setContentType("text/html");
                    dayText[sumDay].setBackground(getBackground());
                    dayText[sumDay].setSelectionColor(new Color(0, 255, 255));
                    dayText[sumDay].addFocusListener(this);
                    tempPanel.add(dayText[sumDay]);

                    sumDay++;
                }
                southPanel.add(tempPanel);
            }
        }
        return southPanel;
    }

    private JMenuBar createMenuBar() {
        JMenuItem worldTimeMenu = new JMenuItem("世界时间(W)");
        worldTimeMenu.setMnemonic(KeyEvent.VK_W);
        worldTimeMenu.addActionListener(this);

        JMenuItem clockMenu = new JMenuItem("设置闹钟(C)");
        clockMenu.setMnemonic(KeyEvent.VK_C);
        clockMenu.addActionListener(this);

        JMenu toolMenu = new JMenu("工具(T)");
        toolMenu.setMnemonic(KeyEvent.VK_T);
        toolMenu.add(worldTimeMenu);
        toolMenu.add(clockMenu);

        menuBar = new JMenuBar();
        menuBar.add(toolMenu);

        return menuBar;
    }

    public void showOneMonth() throws ParseException {
        Date firstDay = new Date(date.year, date.month, 1, date.hour, date.minute, date.second);
        int s = 0;
        for (int i = 0; i < firstDay.getWeekday(); i++) {
            dayText[i].setText(null);
        }
        for (int i = firstDay.getWeekday(); i < 41; i++) {
            dayText[i].setText(null);
            if (s < date.getDayCount()) {
                Date d = new Date(date.year, date.month, ++s, date.hour, date.minute, date.second);
                String month = new Lunar(d).getDay();
                month = month.replace("初一", new Lunar(d).getMonth() + "月");
                if (SolarTerm.getSolarTerm(d) != null) {
                    month = SolarTerm.getSolarTerm(d);
                }

                String temp = "<center>" + s + "<br/><font face=楷体>" + month + "</font></center>";

                dayText[i].setText(temp);
                thisText[s] = dayText[i];
            }

            String str = "<center><font face=仿宋><b>";
            str = str + date.getDateString();
            str = str + "<br/>" + new Lunar(date).toString();
            str = str + "<br/>星期" + WEEK[date.getWeekday() % 7];
            int t = Festival.getFestival(date).length;
            for (int j = 0; j < t; j++) {
                str = str + "，" + Festival.getFestival(date)[j];
            }
            if (SolarTerm.getSolarTerm(date) != null) {
                str = str + "，" + SolarTerm.getSolarTerm(date);
            }
            str = str + "</b></center>";
            infoText.setText(str);

        }

        if (today.year == date.year && today.month == date.month) {
            String m = null;

            try {
                m = new Lunar(today).getDay();
                m = m.replace("初一", new Lunar(today).getMonth() + "月");
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (SolarTerm.getSolarTerm(today) != null)
                m = SolarTerm.getSolarTerm(today);

            String str = "<center><font color=blue>";

            str = str + today.day;
            str = str + "<br/><font face=楷体>" + m + "</font></center>";
            thisText[today.day].setText(str);
        }

        for (JEditorPane e : dayText) {
            e.setBackground(getBackground());
        }

        thisText[date.day].setBackground(new Color(0, 255, 255));

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // 响应timer的刷新事件
        {
            timeText.setText(Date.getNowString(z));
            if (Date.getToday(z).hour == getClock().hour && Date.getToday(z).minute == getClock().minute && !isAlarmed) {
                new MessageFrame("提示", "闹钟时间到!", this).setVisible(true);
                audio.play();
                isAlarmed = true;
            }
        }

        if ("<<".equals(arg0.getActionCommand())) {
            date.year--;
            if (date.year == 1901) {
                button[0].setEnabled(false);
                if (date.month == 1) {
                    button[1].setEnabled(false);
                }
            }
            button[2].setEnabled(true);
            button[3].setEnabled(true);

            try {
                showOneMonth();
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if ("<".equals(arg0.getActionCommand())) {
            if (date.month == 1) {
                date.month = 12;
                date.year--;
            } else {
                date.month--;
                date.day = Math.min(date.getDayCount(), date.day);
            }

            if (date.year == 1901) {
                button[0].setEnabled(false);
                if (date.month == 1) {
                    button[1].setEnabled(false);
                }
            }

            if (date.year != 2100 || date.month != 12) {
                button[2].setEnabled(true);
                if (date.year != 2100) {
                    button[3].setEnabled(true);
                }
            }

            try {
                showOneMonth();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (">>".equals(arg0.getActionCommand())) {
            date.year++;
            if (date.year == 2100) {
                button[3].setEnabled(false);
                if (date.month == 12) {
                    button[2].setEnabled(false);
                }
            }
            button[0].setEnabled(true);
            button[1].setEnabled(true);

            try {
                showOneMonth();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (">".equals(arg0.getActionCommand())) {
            if (date.month == 12) {
                date.month = 1;
                date.year++;
            } else {
                date.month++;
                date.day = Math.min(date.getDayCount(), date.day);
            }

            if (date.year == 2100) {
                button[3].setEnabled(false);
                if (date.month == 12) {
                    button[2].setEnabled(false);
                }
            }

            if (date.year != 1901 || date.month != 1) {
                button[1].setEnabled(true);
                if (date.year != 1901) {
                    button[0].setEnabled(true);
                }
            }

            try {
                showOneMonth();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if ("世界时间(W)".equals(arg0.getActionCommand())) {
            new TimeZoneFrame(this).setVisible(true);
        }

        if ("设置闹钟(C)".equals(arg0.getActionCommand())) {
            new ClockFrame(this).setVisible(true);
        }

        if ("确定".equals(arg0.getActionCommand())) {
            audio.stop();
        }
    }

    @Override
    public void focusGained(FocusEvent arg0) {
        for (int i = 1; i <= date.getDayCount(); i++) {
            if (thisText[i] == arg0.getComponent()) {
                // thisText[i].setBackground(new Color(0, 255, 255));
                date.day = i;
                try {
                    showOneMonth();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void focusLost(FocusEvent arg0) {
    }

    public void setAlarmed(boolean isAlarmed) {
        this.isAlarmed = isAlarmed;
    }

    public boolean getAlarmed() {
        return isAlarmed;
    }

    public Date getClock() {
        return clock;
    }

    public void setClock(Date clock) {
        this.clock = clock;
    }

    public Date getToday() {
        return today;
    }

    public void setToday(Date today) {
        this.today = today;
    }

    public TimeZone getTimeZone() {
        return z;
    }

    public void setTimeZone(TimeZone z) {
        this.z = z;
    }

    public static void main(String[] args) throws ParseException, IOException {
        JFrame.setDefaultLookAndFeelDecorated(true);
        new CalendarFrame().setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
