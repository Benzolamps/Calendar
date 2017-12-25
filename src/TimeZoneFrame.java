import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.TimeZone;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * 
 * @author Benzolamps
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class TimeZoneFrame extends JFrame implements ActionListener
{
	private static final long serialVersionUID = -2062761489324857999L;
	private JPanel northPanel, centerPanel, southPanel;
	private JLabel timeChanged;
	private JComboBox zoneCombo;
	private String zoneID[];
	private TimeZone zoneSelected;
	private CalendarFrame frame;
	private Date date;
	
	
	public TimeZoneFrame(CalendarFrame frame)
	{
		super("设置时区");
		this.frame = frame;
		setLayout(new BorderLayout(50,50));
		setSize(320, 240);
		setVisible(true);
		setResizable(false);
		Timer timer = new Timer(1000, this);
		timer.start();
		add(createNorthPanel(), BorderLayout.NORTH);
		add(createCenterPanel(), BorderLayout.CENTER);
		add(createSouthPanel(), BorderLayout.SOUTH);
		for (int i = 0; i < TimeZone.getAvailableIDs().length; i++)
		{
			if(TimeZone.getAvailableIDs()[i].equals(frame.getTimeZone().getID()))
			{
				zoneCombo.setSelectedIndex(i);
			}
		}
		changeTime();
	}
	
	private JPanel createNorthPanel()
	{
		zoneCombo = new JComboBox();
		
		zoneID = new String[TimeZone.getAvailableIDs().length];
		for (int i = 0; i < zoneID.length; i++)
		{
			TimeZone z =  TimeZone.getTimeZone(TimeZone.getAvailableIDs()[i]);
			zoneID[i] = TimeZone.getAvailableIDs()[i];
			int o = z.getRawOffset() / (1000 * 3600);
			zoneCombo.addItem(String.format("%s    (%s%d:00)", zoneID[i], (o < 0)?"":"+", o));	
		}
		northPanel = new JPanel();
		northPanel.setLayout(new BorderLayout(3, 3));
		northPanel.add(zoneCombo, BorderLayout.CENTER);
		northPanel.add(new JLabel("请选择时区："), BorderLayout.NORTH);
		
		return northPanel;
	}
	
	private JPanel createCenterPanel()
	{
		
		timeChanged = new JLabel(" ");
		
		centerPanel = new JPanel();
		centerPanel.add(timeChanged, BorderLayout.WEST);
		
		return centerPanel;	
	}
	
	private JPanel createSouthPanel()
	{
		JButton cancelButton = new JButton("取消");
		cancelButton.setMnemonic(KeyEvent.VK_C);
		cancelButton.addActionListener(this);
		
		JButton okButton = new JButton("确定");
		okButton.setMnemonic(KeyEvent.VK_O);
		okButton.addActionListener(this);
		
		southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout(60, 60));
		southPanel.add(cancelButton, BorderLayout.WEST);
		southPanel.add(okButton, BorderLayout.EAST);
		
		return southPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		// TODO Auto-generated method stub
		changeTime();
		
		if ("确定".equals(arg0.getActionCommand()))
		{
			setVisible(false);
			frame.setToday(date);
			frame.setTimeZone(zoneSelected);
			try 
			{
				frame.showOneMonth();
			} 
			catch (ParseException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if ("取消".equals(arg0.getActionCommand()))
		{
			setVisible(false);
		}
	}
	
	private void changeTime()
	{
		zoneSelected = TimeZone.getTimeZone(TimeZone.getAvailableIDs()[zoneCombo.getSelectedIndex()]);	
		date = Date.getToday(zoneSelected);
		String str = "当前日期与时间：";
		str = str + date.getDateString();
		str = str + "，" + Date.getNowString(zoneSelected);
		timeChanged.setText(str);
	}
}
