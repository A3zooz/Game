package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class Frame extends JFrame {
	JPanel details = new JPanel();
	JLabel turnCount = new JLabel();
	JLabel name = new JLabel();
	JLabel gold = new JLabel();
	JLabel food = new JLabel();
	public Frame() {
		
		details.add(name);
		details.add(turnCount);
		details.add(gold);
		details.add(food);
		this.setPreferredSize(new Dimension(1920, 1080));
		this.pack();
		this.add(details);
		details.setOpaque(false);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		this.revalidate();
		this.repaint();
		ImageIcon img = new ImageIcon("images/sword.png");
		this.setIconImage(img.getImage());
	}
	
	public static void main(String[] args) {
		new Frame();
	}

}
