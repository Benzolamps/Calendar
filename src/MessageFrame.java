
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * 
 * @author Benzolamps
 *
 */
public class MessageFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = -6544341078729021753L;
	private JButton okButton;
	private ActionListener a;

	public MessageFrame(String name, String text, ActionListener a) {
		super(name);
		this.a = a;
		setLayout(null);
		setSize(360, 200);
		setVisible(true);
		setResizable(false);
		JLabel messageLabel = new JLabel(text);
		messageLabel.setBounds(10, 10, getContentPane().getWidth() - 20, 50);
		getContentPane().add(messageLabel);
		getContentPane().add(createOKButton());
	}

	public JButton createOKButton() {
		okButton = new JButton("确定");
		okButton.setMnemonic(KeyEvent.VK_O);
		Point pt = new Point();
		pt.x = getContentPane().getWidth() - 90;
		pt.y = getContentPane().getHeight() - 30;
		okButton.setBounds(new Rectangle(pt, new Dimension(80, 20)));
		okButton.addActionListener(a);
		okButton.addActionListener(this);
		return okButton;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if ("确定".equals(e.getActionCommand())) {
			setVisible(false);
		}
	}
}
