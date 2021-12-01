package view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;

import javax.swing.*;

import units.Unit;

public class CButton extends JButton{
	Unit u;
	public CButton(ActionListener x,int width,int height,String name,Unit u) {
		// TODO Auto-generated constructor stub
		super(name);
		this.u=u;
		this.addActionListener(x);
		this.setPreferredSize(new Dimension(width,height));
		String uname=u.getClass().getSimpleName();
		switch(uname) {
		case "Archer": this.setIcon(new ImageIcon("images/bow.png"));this.setBackground(Color.darkGray);break;
		case "Cavalry": this.setIcon(new ImageIcon("images/horse.png"));this.setBackground(Color.darkGray);break;
		case "Infantry": this.setIcon(new ImageIcon("images/spear.png"));this.setBackground(Color.darkGray);break;
		}
	}
	public Unit getU() {	
		return u;
	}
	public void setU(Unit u) {
		this.u = u;
	}
}
