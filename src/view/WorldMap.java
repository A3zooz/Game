package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;
import engine.*;
import exceptions.FriendlyCityException;
import exceptions.FriendlyFireException;
import exceptions.TargetNotReachedException;
import units.Army;

@SuppressWarnings("serial")
public class WorldMap extends JPanelWithBackground implements ActionListener {
	Game module;
	JButton Rome = new JButton(new ImageIcon("images/RomeIcon.png"));
	JButton Cairo = new JButton(new ImageIcon("images/CairoIcon.png"));
	JButton Sparta = new JButton(new ImageIcon("images/SpartaIcon.png"));
	JButton Armies = new JButton("Display Armies");
	JButton temp;
	City chosenCity;
	public Frame view;
	JButton EndTurn = new JButton("End This Turn");

	public WorldMap(Game module, Frame view) throws IOException {
		super("images/Wmap.jpeg");
		this.view = view;
		this.setLayout(new BorderLayout());
		this.module = module;
		Rome.setOpaque(false);
		Rome.setContentAreaFilled(false);
		Rome.setBorderPainted(false);
		Rome.setBounds(1000, 200, 175, 175);
		Cairo.setOpaque(false);
		Cairo.setContentAreaFilled(false);
		Cairo.setBorderPainted(false);
		Cairo.setBounds(175, 525, 175, 175);
		Sparta.setOpaque(false);
		Sparta.setContentAreaFilled(false);
		Sparta.setBorderPainted(false);
		Sparta.setBounds(100, 20, 175, 175);
		view.details.setLayout(new FlowLayout(FlowLayout.LEFT, 200, 0));
		view.turnCount.setText("Turn: " + module.getCurrentTurnCount() + " / " + module.getMaxTurnCount());
		view.name.setText(this.module.getPlayer().getName());
		view.gold.setText("Gold: " + module.getPlayer().getTreasury());
		view.food.setText("Food: " + module.getPlayer().getFood());
		this.view.add(view.details);
		Rome.addActionListener(this);
		Rome.setActionCommand("Rome");
		Cairo.addActionListener(this);
		Cairo.setActionCommand("Cairo");
		Sparta.addActionListener(this);
		Sparta.setActionCommand("Sparta");
		Armies.addActionListener(this);
		Armies.setActionCommand("Armies");
		EndTurn.addActionListener(this);
		EndTurn.setActionCommand("EndTurn");
		Armies.setOpaque(false);
		EndTurn.setBounds(0, 0, 200, 100);
		this.add(Armies, BorderLayout.EAST);
		this.view.add(view.details, BorderLayout.NORTH);
		this.add(EndTurn);
		view.details.setOpaque(false);
		this.revalidate();
		this.repaint();
		this.add(Rome);
		this.add(Cairo);
		this.add(Sparta);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		temp = (JButton) e.getSource();
		if (e.getActionCommand().equals("Cairo") || e.getActionCommand().equals("Sparta")
				|| e.getActionCommand().equals("Rome")) {
			boolean flag = false;
			for (City x : module.getPlayer().getControlledCities()) {
				if (x.getName().equals(e.getActionCommand())) {
					flag = true;
					chosenCity = x;
					break;
				}
			}
			if (!flag) {
				JOptionPane.showMessageDialog(this, "You dont control this City");
			} else {
				view.remove(this);
				try {
					view.add(new CityView(view, chosenCity, module));
					view.repaint();
					view.revalidate();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		}
		if (e.getActionCommand().equals("Armies")) {
			if (this.module.getPlayer().getControlledArmies().size() != 0)
				new ArmiesScreen(module,view);
			else {
				JOptionPane.showMessageDialog(this, "You have no controlled armies");
			}

		}
		if (e.getActionCommand().equals("EndTurn")) {
			this.module.endTurn();
			this.view.gold.setText("Gold:" + this.module.getPlayer().getTreasury());
			this.view.food.setText("Food: " + this.module.getPlayer().getFood());
			this.view.turnCount.setText("Turns:" + this.module.getCurrentTurnCount() + " /50");
			if (module.attackFlag) {
				module.attackFlag = false;
				String[] options = { "Attack", "Seige", "AutoResolve" };
				int x = JOptionPane.showOptionDialog(view,
						"You reached the city " + module.tempArmy.getCurrentLocation(), "If you pressed X you will start sieging",
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
				if (x == 0) {
					view.remove(this);
					try {
						City tempCity = null;

						for (City xCity : module.getAvailableCities()) {
							if (xCity.getName().equals(module.tempArmy.getCurrentLocation()))
								tempCity = xCity;
						}
						view.add(new BattleView("images/BattleBack.jpg", view, module, module.tempArmy,
								tempCity.getDefendingArmy()));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					view.revalidate();
					view.repaint();
				} else {
					if (x ==  1|| x == JOptionPane.CLOSED_OPTION) {
						for (City xCity : module.getAvailableCities()) {
							if (xCity.getName().equals(module.tempArmy.getCurrentLocation()))
								try {
									module.getPlayer().laySiege(module.tempArmy, xCity);
								} catch (TargetNotReachedException | FriendlyCityException e1) {
									// TODO Auto-generated catch block
									JOptionPane.showMessageDialog(view, e1.getMessage());
								}
						}

					} else {
						if (x == 2) {
							for (int i = 0; i < this.module.getPlayer().getControlledArmies().size(); i++) {
								City tempCity = null;
								for (City xCity : module.getAvailableCities()) {
									if (xCity.getName().equals(module.tempArmy.getCurrentLocation()))
										tempCity = xCity;
								}
								if (this.module.getPlayer().getControlledArmies().get(i).getCurrentLocation()
										.toLowerCase().equals(tempCity.getName().toLowerCase())) {

									try {
										this.module.autoResolve(this.module.getPlayer().getControlledArmies().get(i),
												tempCity.getDefendingArmy());
										JOptionPane.showMessageDialog(this, module.res);
									} catch (FriendlyFireException e1) {
										// TODO Auto-generated catch block
										JOptionPane.showMessageDialog(view, e1.getMessage());
									}
								}
							}

						}
						
					}
				}

			} else {
				if (module.seigeFlag) {
					module.seigeFlag =false;
					String[] options = { "Attack", "AutoResolve" };
					int x = JOptionPane.showOptionDialog(view,
							"You have been sieging the city " + module.tempCity.getName() + " for 3 turns",
							"If you pressed X the battle will be AUTORESOLVED", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
							options, options[0]);
					if (x == 0) {
						view.remove(this);
						try {
							City tempCity = null;

							for (City xCity : module.getAvailableCities()) {
								if (xCity.getName().equals(module.tempArmy.getCurrentLocation()))
									tempCity = xCity;
							}
							view.add(new BattleView("images/BattleBack.jpg", view, module, module.tempArmy,
									tempCity.getDefendingArmy()));
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						view.revalidate();
						view.repaint();
					} else {
							for (int i = 0; i < this.module.getPlayer().getControlledArmies().size(); i++) {
								City tempCity = null;
								for (City xCity : module.getAvailableCities()) {
									if (xCity.getName().equals(module.tempArmy.getCurrentLocation()))
										tempCity = xCity;
								}
								if (this.module.getPlayer().getControlledArmies().get(i).getCurrentLocation()
										.toLowerCase().equals(tempCity.getName().toLowerCase())) {

									try {
										this.module.autoResolve(this.module.getPlayer().getControlledArmies().get(i),
												tempCity.getDefendingArmy());
										JOptionPane.showMessageDialog(this, module.res);
									} catch (FriendlyFireException e1) {
										// TODO Auto-generated catch block
										JOptionPane.showMessageDialog(view, e1.getMessage());
									}
								}
							}
					}

				}
			}

			if (this.module.isGameOver()) {
				view.removeAll();
				if (this.module.getPlayer().getControlledCities().size() == this.module.getAvailableCities().size()) {
					JOptionPane.showMessageDialog(view, "You Win!");
				} else {
					JOptionPane.showMessageDialog(view, "You Lose!");
				}
			}

		}

	}

}
