package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import buildings.*;
import engine.*;
import exceptions.*;
import units.Army;

@SuppressWarnings("serial")
public class CityView extends JPanelWithBackground implements ActionListener {
	Game module;
	City city;
	JButton farmButton = new JButton(new ImageIcon("images/Mill.png"));
	JButton marketButton = new JButton(new ImageIcon("images/MR.png"));
	JButton archeryRangeButton = new JButton(new ImageIcon("images/AR.png"));
	JButton stableButton = new JButton(new ImageIcon("images/SS.png"));
	JButton barracksButton = new JButton(new ImageIcon("images/BR.png"));
	JButton back = new JButton("World Map");
	JButton armiesButton = new JButton(new ImageIcon("images/Castle.png"));
	Frame view;
	JMenu menu = new JMenu("  Build  ");
	JMenu submenu1 = new JMenu("Military Buildings");
	JMenu submenu2 = new JMenu("Economical Buildings");
	JMenuItem i1 = new JMenuItem("Farm");
	JMenuItem i2 = new JMenuItem("Market");
	JMenuItem i3 = new JMenuItem("Stable");
	JMenuItem i4 = new JMenuItem("Barracks");
	JMenuItem i5 = new JMenuItem("ArcheryRange");
	boolean flagfram= false;boolean flagmarket= false;boolean flagstable= false;boolean flagarcheryrange= false;boolean flagbarracks = false;
	public CityView(Frame view, City city, Game module) throws IOException {

		super("images/CV.png");
//		boolean flagfram= false,flagmarket= false,flagstable= false,flagarcheryrange= false,flagbarracks = false;
		for(EconomicBuilding x:city.getEconomicalBuildings()) {
			if(x instanceof Farm)
				flagfram = true;
			if(x instanceof Market)
				flagmarket = true;
		}
		for(MilitaryBuilding x: city.getMilitaryBuildings()) {
			if(x instanceof ArcheryRange)
				flagarcheryrange = true;
			if(x instanceof Stable)
				flagstable = true;
			if(x instanceof Barracks)
				flagbarracks = true;
		}
		farmButton.setVisible(flagfram);
		marketButton.setVisible(flagmarket);
		archeryRangeButton.setVisible(flagarcheryrange);
		stableButton.setVisible(flagstable);
		barracksButton.setVisible(flagbarracks);
		JMenuBar bar = new JMenuBar();
		menu.add(submenu1);
		menu.add(submenu2);
		submenu1.add(i3);
		submenu1.add(i4);
		submenu1.add(i5);
		submenu2.add(i1);
		submenu2.add(i2);
		bar.add(menu);
		menu.setSize(100, 30);
		bar.setBounds(200, 0, 50, 40);
		this.add(bar);
		back.setBounds(0, 0, 200, 30);
		farmButton.setBounds(180, 100, 300, 300);
		marketButton.setBounds(180, 430, 285, 249);
		archeryRangeButton.setBounds(1000, 100, 350, 222);
		stableButton.setBounds(1000, 430, 350, 260);
		barracksButton.setBounds(600, 100, 303, 248);
		armiesButton.setBounds(580, 400, 342, 292);
		armiesButton.setOpaque(false);
		armiesButton.setContentAreaFilled(false);
		armiesButton.setBorderPainted(false);
		barracksButton.setOpaque(false);
		barracksButton.setContentAreaFilled(false);
		barracksButton.setBorderPainted(false);
		stableButton.setOpaque(false);
		stableButton.setContentAreaFilled(false);
		stableButton.setBorderPainted(false);
		archeryRangeButton.setOpaque(false);
		archeryRangeButton.setContentAreaFilled(false);
		archeryRangeButton.setBorderPainted(false);
		marketButton.setOpaque(false);
		marketButton.setContentAreaFilled(false);
		marketButton.setBorderPainted(false);
		farmButton.setOpaque(false);
		farmButton.setContentAreaFilled(false);
		farmButton.setBorderPainted(false);
		this.view = view;
		this.module = module;
		this.city = city;
		this.setLayout(null);
		this.add(farmButton);
		this.add(marketButton);
		this.add(archeryRangeButton);
		this.add(stableButton);
		this.add(barracksButton);
		this.add(back);
		this.add(armiesButton);
		i1.addActionListener(this);
		i1.setActionCommand("FarmItem");
		i2.addActionListener(this);
		i2.setActionCommand("MarketItem");
		i3.addActionListener(this);
		i3.setActionCommand("StableItem");
		i4.addActionListener(this);
		i4.setActionCommand("BarracksItem");
		i5.addActionListener(this);
		i5.setActionCommand("ArcheryRangeItem");
		farmButton.addActionListener(this);
		farmButton.setActionCommand("Farm");
		marketButton.addActionListener(this);
		marketButton.setActionCommand("Market");
		archeryRangeButton.addActionListener(this);
		archeryRangeButton.setActionCommand("ArcheryRange");
		stableButton.addActionListener(this);
		stableButton.setActionCommand("Stable");
		barracksButton.addActionListener(this);
		barracksButton.setActionCommand("Barracks");
		back.addActionListener(this);
		back.setActionCommand("Back");
		armiesButton.addActionListener(this);
		armiesButton.setActionCommand("Armies");
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("Farm")) {
			for (EconomicBuilding x : city.getEconomicalBuildings()) {
				if (x instanceof Farm) {
					BuildingInfo farm = new BuildingInfo(x, module, 0, view, city);
				}
			}

		} else {
			if (e.getActionCommand().equals("ArcheryRange")) {
				for (MilitaryBuilding xBuilding : city.getMilitaryBuildings()) {
					if (xBuilding instanceof ArcheryRange) {
						BuildingInfo archeryRange = new BuildingInfo(xBuilding, module, 2, view, city);
					}
				}
			} else if (e.getActionCommand().equals("Stable")) {
				for (MilitaryBuilding xBuilding : city.getMilitaryBuildings()) {
					if (xBuilding instanceof Stable) {
						BuildingInfo stable = new BuildingInfo(xBuilding, module, 3, view, city);
					}
				}

			} else {
				if (e.getActionCommand().equals("Barracks")) {
					for (MilitaryBuilding xBuilding : city.getMilitaryBuildings()) {
						if (xBuilding instanceof Barracks) {
							BuildingInfo barracks = new BuildingInfo(xBuilding, module, 4, view, city);
						}
					}
				}
			}
			if (e.getActionCommand().equals("Back")) {
				view.remove(this);
				try {
					view.add(new WorldMap(module, this.view));
					view.revalidate();
					view.repaint();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			} else {
				if (e.getActionCommand().equals("FarmItem")) {
					try {

						this.module.getPlayer().build("farm", this.city.getName());
						this.flagfram = true;
						farmButton.setVisible(true);
						this.view.gold.setText("Gold: " + module.getPlayer().getTreasury() + "");
					} catch (NotEnoughGoldException | AlreadyBuildException e1) {
						JOptionPane.showMessageDialog(view, e1.getMessage());
						// e1.printStackTrace();
					}
				} else {
					if (e.getActionCommand().equals("MarketItem")) {
						try {

							this.module.getPlayer().build("market", this.city.getName());
							this.flagmarket = true;
							marketButton.setVisible(true);
							this.view.gold.setText("Gold: " + module.getPlayer().getTreasury() + "");
						} catch (NotEnoughGoldException | AlreadyBuildException e1) {
							JOptionPane.showMessageDialog(view, e1.getMessage());
							// e1.printStackTrace();
						}
					} else {
						if (e.getActionCommand().equals("StableItem")) {
							try {

								this.module.getPlayer().build("stable", this.city.getName());
								this.flagstable = true;
								stableButton.setVisible(true);
								this.view.gold.setText("Gold: " + module.getPlayer().getTreasury() + "");
							} catch (NotEnoughGoldException | AlreadyBuildException e1) {
								JOptionPane.showMessageDialog(view, e1.getMessage());
								// e1.printStackTrace();
							}
						} else {
							if (e.getActionCommand().equals("BarracksItem")) {
								try {

									this.module.getPlayer().build("barracks", this.city.getName());
									this.flagbarracks = true;
									barracksButton.setVisible(true);
									this.view.gold.setText("Gold: " + module.getPlayer().getTreasury() + "");
								} catch (NotEnoughGoldException | AlreadyBuildException e1) {
									JOptionPane.showMessageDialog(view, e1.getMessage());
									// e1.printStackTrace();
								}
							} else {
								if (e.getActionCommand().equals("ArcheryRangeItem")) {
									try {

										this.module.getPlayer().build("archeryrange", this.city.getName());
										this.flagarcheryrange = true;
										archeryRangeButton.setVisible(true);
										this.view.gold.setText("Gold: " + module.getPlayer().getTreasury() + "");
									} catch (NotEnoughGoldException | AlreadyBuildException e1) {
										JOptionPane.showMessageDialog(view, e1.getMessage());
										// e1.printStackTrace();
									}
								} else {
									if (e.getActionCommand().equals("Armies")) {
										String[] options = { "Defending Army", "Controlled Armies" };
										int x = JOptionPane.showOptionDialog(view, "Choose an army to display", null,
												JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
												options, options[0]);
										ArrayList<Army> temp = new ArrayList<>();
										if (x == 0) {
											temp.add(city.getDefendingArmy());
											if (city.getDefendingArmy().getUnits().size() == 0)
												JOptionPane.showMessageDialog(this,
														"Your defending Army is currently empty");
											else
												new ArmiesInfoCity(module, city, temp, false);
										} else {
											if (x == 1) {
												for (Army xArmy : this.module.getPlayer().getControlledArmies()) {
													if (xArmy.getCurrentLocation().equals(city.getName()))
														temp.add(xArmy);
												}
												if (temp.size() == 0)
													JOptionPane.showMessageDialog(this,
															"There are currently no controlled armies in this city");
												else
													new ArmiesInfoCity(module, city, temp, true);
											}
										}
//										new ArmyInformation(module, city.getDefendingArmy(), city, false);
									}else {
										if (e.getActionCommand().equals("Market")) {
											for (EconomicBuilding x : city.getEconomicalBuildings()) {
												if (x instanceof Market) {
													BuildingInfo market = new BuildingInfo(x, module, 1, view, city);
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}