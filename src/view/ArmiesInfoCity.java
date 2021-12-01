package view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import engine.City;
import engine.Game;
import exceptions.MaxCapacityException;
import units.Archer;
import units.Army;
import units.Cavalry;
import units.Unit;

@SuppressWarnings("serial")
public class ArmiesInfoCity extends SecondScreen implements ActionListener {
	boolean flagControlled;
	Game module;
	City city;
	ArrayList<Army> armies;
	ArrayList<JButton> buttons = new ArrayList<JButton>();
	JPanel panel = new JPanel();

	public ArmiesInfoCity(Game m, City c, ArrayList<Army> a, boolean flag) {
		this.module = m;
		this.armies = a;
		this.city = c;
		this.flagControlled = flag;
		panel.setLayout(new FlowLayout());
		if (!flagControlled) {
			for (int i = 0; i < armies.get(0).getUnits().size(); i++) {
				JButton b = new JButton("Unit " + (i + 1));
				b.addActionListener(this);
				buttons.add(b);
				panel.add(b);

			}
		} else {
			for (int i = 0; i < armies.size(); i++) {
				JButton b = new JButton("Army " + (i + 1));
				b.addActionListener(this);
				buttons.add(b);
				panel.add(b);
			}

		}
		this.add(panel);
		this.revalidate();
		this.repaint();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!flagControlled) {
			int r = buttons.indexOf((JButton) e.getSource());
			Unit y = armies.get(0).getUnits().get(r);
			String[] options = { "Show Info", "Relocate", "Intiate an army" };
			int x = JOptionPane.showOptionDialog(this, "Choose an option", null, JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
			if (x == 0) {
				String type = "";
				if (y instanceof Archer)
					type = "Archer";
				else {
					if (y instanceof Cavalry)
						type = "Cavalry";
					else
						type = "Infantry";
				}
				String res1 = ("Type: " + type + "\n" + "Level: " + y.getLevel() + "\n" + "Current Soldier Count: "
						+ y.getCurrentSoldierCount() + "\n" + "Max Soldier Count: " + y.getMaxSoldierCount() + "\n");
				JOptionPane.showMessageDialog(this, res1);
			} else {
				if (x == 1) {
					ArrayList<Army> temp = new ArrayList<Army>();
					for (Army xArmy : this.module.getPlayer().getControlledArmies()) {
						if (xArmy.getCurrentLocation().equals(city.getName())) {
							temp.add(xArmy);
						}
					}
					if (temp.size() == 0) {
						JOptionPane.showMessageDialog(this,
								"There are no controlled armies in this city you can relocate to");
					} else {
						String[] options1 = new String[temp.size()];
						for (int i = 0; i < options1.length; i++) {
							options1[i] = "Army " + (i + 1);
						}
						int x1 = JOptionPane.showOptionDialog(this, null, "Choose an army to relocate to",
								JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options1,
								options1[0]);
						Army tempArmy = temp.get(x1);
						try {
							tempArmy.relocateUnit(y);
							JOptionPane.showMessageDialog(this, "Unit relocated succesfully!!");
							this.dispose();
							if (city.getDefendingArmy().getUnits().size() == 0)
								JOptionPane.showMessageDialog(this, "You currently have no units in this army");
							else
								new ArmiesInfoCity(module, city, armies, flagControlled);
						} catch (MaxCapacityException e1) {
							JOptionPane.showMessageDialog(this, e1.getMessage());
						}

					}
				} else {
					this.module.getPlayer().initiateArmy(city, y);
					JOptionPane.showMessageDialog(this, "Army Succesfully intiated!!");
					if (city.getDefendingArmy().getUnits().size() == 0)
						JOptionPane.showMessageDialog(this, "Your defending army is now Empty");
					else
						new ArmiesInfoCity(module, city, armies, flagControlled);
					this.dispose();
				}
			}
		} else {

			int r = buttons.indexOf((JButton) e.getSource());
			Army chosen = armies.get(r);
			this.remove(panel);
			this.add(new ArmyInformation(this, armies, city, module, chosen, false,null));
			this.revalidate();
			this.repaint();

		}

	}

}
