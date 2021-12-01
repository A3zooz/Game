package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class SecondScreen extends JFrame {
	public SecondScreen() {
		this.pack();
		this.setSize(600, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		this.repaint();
		this.revalidate();

	}

	public static void main(String[] args) {
		new SecondScreen();
	}

}
