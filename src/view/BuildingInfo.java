package view;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;
import buildings.Building;
import buildings.*;
import buildings.Market;
import buildings.MilitaryBuilding;
import engine.*;
import exceptions.BuildingInCoolDownException;
import exceptions.MaxLevelException;
import exceptions.MaxRecruitedException;
import exceptions.NotEnoughGoldException;

@SuppressWarnings("serial")
public class BuildingInfo extends SecondScreen implements ActionListener {
	Frame view;
	int type;
	Game module;
	Building building;
	JButton upgradeButton = new JButton("Upgrade Building");
	JPanel panel = new JPanel();
	City city;
	JButton ARCH = new JButton();
	JButton INF = new JButton();
	JButton CAV = new JButton();
	JLabel infoArea = new JLabel();

	public BuildingInfo(Building building, Game module, int type, Frame view,City city) {
		this.city = city;
		ARCH.setIcon(new ImageIcon("images/ARCH.png"));
		//ARCH.setContentAreaFilled(false);
//		INF.setIcon(StartScreen.resizeIcon("images/INF.png",INF.getWidth(),INF.getHeight()));
//		CAV.setIcon(StartScreen.resizeIcon("images/CAV.png",CAV.getWidth(),CAV.getHeight()));
		INF.setIcon(new ImageIcon("images/INF.png"));
		CAV.setIcon(new ImageIcon("images/CAV.png"));
		this.view = view;
		// !flag = economicBuilding
		this.building = building;
		this.module = module;
		this.type = type;
		panel.setLayout(new BorderLayout());
		upgradeButton.addActionListener(this);
		upgradeButton.setActionCommand("upgrade");
		ARCH.addActionListener(this);
		ARCH.setActionCommand("archer");
		INF.addActionListener(this);
		INF.setActionCommand("infantry");
		CAV.addActionListener(this);
		CAV.setActionCommand("cavalry");
		panel.add(upgradeButton, BorderLayout.SOUTH);
		JLabel infoArea = new JLabel("Type: " + building.getClass() + "   Level: " + building.getLevel() + "");
		infoArea.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		panel.add(infoArea, BorderLayout.NORTH);
		JPanel p = new JPanel();
		p.setOpaque(false);
		panel.add(p, BorderLayout.EAST);
		panel.add(p, BorderLayout.WEST);
		if (type == 0) {
			infoArea.setText("Type: " + "Farm" + "   Level: " + building.getLevel() + "");
			// Farm
		} else {
			if (type == 1) {
				infoArea.setText("Type: " + "Market" + "   Level: " + building.getLevel() + "");
				// Market
			} else {
				if (type == 2) {
					infoArea.setText("Type: " + "ArcheryRange" + "   Level: " + building.getLevel() + "");
					panel.add(ARCH, BorderLayout.CENTER);
				} else {
					if (type == 3) {
						infoArea.setText("Type: " + "Stable" + "   Level: " + building.getLevel() + "");
						panel.add(CAV, BorderLayout.CENTER);
					} else {
						infoArea.setText("Type: "+ "Barracks"+"   Level: " + building.getLevel()+"");
						panel.add(INF);
					}
				}
			}
		}
//		panel.add(ARCH, BorderLayout.CENTER);
		this.add(panel);
		this.revalidate();
		this.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("upgrade")) {
			int temp = JOptionPane.showConfirmDialog(this,"The upgrade Cost is " + this.building.getUpgradeCost() + "");
			if (temp == JOptionPane.YES_OPTION) {
				try {
					this.module.getPlayer().upgradeBuilding(building);
					this.view.gold.setText("Gold: " + module.getPlayer().getTreasury() + "");
//					JLabel infoArea1 = new JLabel();
					BorderLayout layout = (BorderLayout)panel.getLayout();
					panel.remove(layout.getLayoutComponent(BorderLayout.NORTH));
					this.remove(panel);
				
					if(building instanceof Farm) 
						infoArea.setText("Type: " + "Farm" + "   Level: " + building.getLevel()+ "");
					if(building instanceof Market)
						infoArea.setText("Type: " + "Market" + "   Level: " + building.getLevel() + "");
					if(building instanceof ArcheryRange)
						infoArea.setText("Type: " + "ArcheryRange" + "   Level: " + building.getLevel() + "");
					if(building instanceof Stable)
						infoArea.setText("Type: " + "Stable" + "   Level: " + building.getLevel() + "");
					if(building instanceof Barracks)
						infoArea.setText("Type: " + "Barracks" + "   Level: " + building.getLevel() + "");
					
					infoArea.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
					panel.add(infoArea,BorderLayout.NORTH);
					panel.revalidate();
					panel.repaint();
					this.add(panel);
					this.revalidate();
					this.repaint();
			
				} catch (BuildingInCoolDownException | MaxLevelException |NotEnoughGoldException e1) {
					JOptionPane.showMessageDialog(this, e1.getMessage());
				}
			}
		}
		else {
			if(e.getActionCommand().equals("archer")||e.getActionCommand().equals("infantry")||e.getActionCommand().equals("cavalry")) {
				try {
					int temp =JOptionPane.showConfirmDialog(this, "The recruitmentCost is "+((MilitaryBuilding)building).getRecruitmentCost());
					if(temp==JOptionPane.YES_OPTION) {
					this.module.getPlayer().recruitUnit(e.getActionCommand(), city.getName());
					this.view.gold.setText("Gold: " + module.getPlayer().getTreasury() + "");
					}
				} catch (BuildingInCoolDownException | MaxRecruitedException | NotEnoughGoldException e1) {
					JOptionPane.showMessageDialog(this, e1.getMessage());
				}
				
			}
		}
	}

	public static void main(String[] args) {

	}

}
