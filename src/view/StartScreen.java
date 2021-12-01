package view;
import java.awt.*;
import java.awt.desktop.ScreenSleepEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;
import engine.Game;
import exceptions.AlreadyBuildException;
import exceptions.*;

public class StartScreen extends JPanelWithBackground implements ActionListener {
	Frame view = new Frame();
	Game module;
	JButton b = new JButton("Start Game!");
	JTextField inp = new JTextField(16);
	JButton Name = new JButton("Enter");
	JButton Rome = new JButton("Rome");
	JButton Cairo = new JButton("Cairo");
	JButton Sparta = new JButton("Sparta");
	JLabel label1 = new JLabel("Please Enter Your Name!");
	JLabel label2 = new JLabel("Please Choose your City!");
	JPanel panel1 = new JPanel();
	JPanel panel2 = new JPanel();
	JButton temp = new JButton();
	String playerName = null;
	String City = null;
	

	public StartScreen() throws IOException {

		super("images/Start.jpg");
		this.setLayout(new GridLayout(3,0));
		label1.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		label2.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		label1.setForeground(Color.WHITE);
		label2.setForeground(Color.WHITE);

		panel1.setLayout(new FlowLayout());
		panel1.setSize(100, 100);
		panel1.add(label1);
		panel1.add(inp);
		Name.addActionListener(this);
		Name.setActionCommand("Name");
		panel1.add(Name);

		panel2.setLayout(new FlowLayout());
		panel2.setSize(100, 100);
		panel2.add(label2);
		Rome.addActionListener(this);
		Rome.setActionCommand("Rome");
		panel2.add(Rome);
		Cairo.addActionListener(this);
		Cairo.setActionCommand("Cairo");
		panel2.add(Cairo);
		Sparta.addActionListener(this);
		Sparta.setActionCommand("Sparta");
		panel2.add(Sparta);

		b.addActionListener(this);
		b.setActionCommand("Start");
		this.setOpaque(true);
		panel1.setOpaque(false);
		panel2.setOpaque(false);
		JPanel panel3 = new JPanel(new BorderLayout());
		panel3.add(b, BorderLayout.SOUTH);
		panel3.setOpaque(false);
		b.setPreferredSize(new Dimension(768, 50));

		this.add(panel1, BorderLayout.NORTH);
		this.add(panel2, BorderLayout.EAST);
		this.add(panel3, BorderLayout.SOUTH);
		view.add(this);

		view.revalidate();
		view.repaint();

	}
	///////////////////////////////////////////////////////////////////////////////////////
	public static Icon resizeIcon(String path, int resizedWidth, int resizedHeight) {
		ImageIcon icon = new ImageIcon(path);
	    Image img = icon.getImage();  
	    Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);  
	    return new ImageIcon(resizedImage);
	}
	////////////////////////////////////////////////////////////////////////////////////////

	public static void main(String[] args) {
		try {
			StartScreen screen=new StartScreen();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Game game = new Game("Omar", "Cairo");
			try {
				game.getPlayer().build("Farm", "Cairo");
			} catch (NotEnoughGoldException | AlreadyBuildException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("Cairo") || e.getActionCommand().equals("Sparta")
				|| e.getActionCommand().equals("Rome")) {
			if (City == null) {
				((JButton) e.getSource()).setBackground(Color.GREEN);
				City = e.getActionCommand();
				temp = (JButton) e.getSource();
			} else {
				if (temp != (JButton) e.getSource()) {
					((JButton) e.getSource()).setBackground(Color.GREEN);
					City = e.getActionCommand();
					temp.setBackground(Color.WHITE);
					temp = (JButton) e.getSource();
				} else {
					City = null;
					((JButton) e.getSource()).setBackground(Color.WHITE);
					temp = null;
				}
			}
		} else {
			if (e.getActionCommand().equals("Name")) {
				if (inp.getText() == null) {
					JOptionPane.showMessageDialog(view, "Please enter a name");
				} else {
					JOptionPane.showMessageDialog(view, "Success!");
					playerName = inp.getText();
				}
			} else {

				if (playerName == null) {
					JOptionPane.showMessageDialog(view, "Please enter a name");
				} else {
					if (City == null) {
						JOptionPane.showMessageDialog(view, "Please choose a City");
					} else {
						try {
							module = new Game(playerName, City);
							view.remove(this);
							WorldMap wMap = new WorldMap(module,view);
							view.add(wMap);
							view.repaint();
							view.revalidate();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		}

	}
}
