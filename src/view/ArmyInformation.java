package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.text.ViewFactory;

import engine.City;
import engine.Game;
import exceptions.FriendlyCityException;
import exceptions.FriendlyFireException;
import exceptions.TargetNotReachedException;
import units.Archer;
import units.Army;
import units.Cavalry;
import units.Status;
import units.Unit;

@SuppressWarnings("serial")
public class ArmyInformation extends JPanel implements ActionListener {
	JTextArea info = new JTextArea();
	JMenuBar buttons = new JMenuBar();
	JButton Army = new JButton("Back to Armies Screen");
	Game module;
	Army army;
	City city;
	ArrayList<Army> armiestemp = new ArrayList<Army>();
	boolean flagAttack;
	SecondScreen view;
	JButton attack = new JButton("Attack");
	Frame vFrame;

	public ArmyInformation(SecondScreen v, ArrayList<Army> at, City c, Game m, Army a, boolean flag, Frame vFrame) {
		this.vFrame = vFrame;
		this.armiestemp = at;
		this.city = c;
		this.flagAttack = flag;
		this.module = m;
		// this.city = city;
		this.view = v;
		this.army = a;
		// this.flagAttack = flag;
		this.setLayout(new BorderLayout());
		// this.add(Army,BorderLayout.SOUTH);
		String res = "";
		res += ("Status: " + army.getCurrentStatus().name() + "\n");

		if (army.getCurrentStatus().equals(Status.MARCHING)) {
			res += "Location: ";

			res += (army.getCurrentLocation() + " to " + army.getTarget() + "\n");
			res += ("Turns until target is reached: " + army.getDistancetoTarget() + "\n");

		} else {

			if (army.getCurrentStatus().equals(Status.BESIEGING)) {
				String target = army.getCurrentLocation();
				res += ("The City being Sieged is: " + target + "\n");
				City targetCity = null;
				for (City x : module.getAvailableCities()) {
					if (x.getName().equals(target))
						targetCity = x;
				}
				res += ("This city has been under siege for :" + targetCity.getTurnsUnderSiege() + " turns" + "\n");
			} else {
				res += "Location: ";
				res += (army.getCurrentLocation() + "\n");
			}

		}

		info.setText(res);
		info.setEditable(false);
		JPanel panel2 = new JPanel();
		panel2.add(info);
		this.add(panel2, BorderLayout.NORTH);
		JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		ArrayList<Unit> tempArrayList = new ArrayList<Unit>();
		// if (flagAttack) {
		tempArrayList = army.getUnits();
		// } else {
		// tempArrayList = city.getDefendingArmy().getUnits();
		// }
		for (Unit y : tempArrayList) {
			JTextArea unit = new JTextArea();
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
			unit.setText(res1);
			unit.setEditable(false);
			panel1.add(unit);
			panel1.repaint();
			panel1.revalidate();

		}
		this.add(panel1, BorderLayout.CENTER);
		JMenu target = new JMenu("Choose a Target");
		target.addActionListener(this);
		target.setActionCommand("target");
		JMenuItem Cairo = new JMenuItem("Cairo");
		JMenuItem Rome = new JMenuItem("Rome");
		JMenuItem Sparta = new JMenuItem("Sparta");
		Cairo.addActionListener(this);
		Rome.addActionListener(this);
		Sparta.addActionListener(this);
		Cairo.setActionCommand("Cairo");
		Sparta.setActionCommand("Sparta");
		Rome.setActionCommand("Rome");
		target.add(Cairo);
		target.add(Sparta);
		target.add(Rome);
		buttons.setLayout(new FlowLayout());
		// if (flagAttack)
		Army.addActionListener(this);
		buttons.add(Army);
		buttons.add(target);
		boolean flag12 = true;
		for(City xCity : module.getPlayer().getControlledCities()) {
			if((army.getCurrentLocation().toLowerCase()).equals(xCity.getName().toLowerCase())||army.getCurrentStatus().equals(Status.MARCHING)) {
				flag12 = false;
				break;
			}
		}
		if (flag12) {
			attack.addActionListener(this);
			buttons.add(attack);
		}

		this.add(buttons, BorderLayout.SOUTH);
		this.repaint();
		this.revalidate();
	}

	public static void main(String[] args) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// if (flagAttack) {
		if (e.getActionCommand().equals("Cairo") || e.getActionCommand().equals("Sparta")
				|| e.getActionCommand().equals("Rome")) {
			boolean f = false;
			for (Army xArmy : module.getPlayer().getControlledArmies()) {
				if (xArmy.getTarget().equals(e.getActionCommand()) && xArmy != army)
					f = true;
			}
			if (f)
				JOptionPane.showMessageDialog(this, "You already have an army targeting this city");
			else {
				if (army.getCurrentLocation().equals(e.getActionCommand()))
					JOptionPane.showMessageDialog(this, "You've already reached this city");

				else {
					int reply = JOptionPane.showConfirmDialog(this, "Are you sure you want to attack this city?",
							"Confirm message", JOptionPane.YES_NO_OPTION);
					if (reply == JOptionPane.YES_OPTION) {

						try {
							module.targetCity(army, e.getActionCommand());
							JOptionPane.showMessageDialog(this, "City succesfuly targeted");
							view.remove(this);
							view.add(new ArmyInformation(view, armiestemp, city, module, army, flagAttack, vFrame));
							view.revalidate();
							view.repaint();
						} catch (FriendlyCityException e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(this, e1.getMessage());
						}

					} else {
						JOptionPane.showMessageDialog(this, "Target sufficiently cleared");
					}
				}
			}
		}
		// }
		else {
			if (e.getActionCommand().equals("Back to Armies Screen")) {
				// JOptionPane.showMessageDialog(this, "test");
				if (flagAttack) {
					view.dispose();
					new ArmiesScreen(module, vFrame);
					view.revalidate();
					view.repaint();
				} else {
					view.dispose();
					new ArmiesInfoCity(module, city, armiestemp, true);
				}
			} else {
				if (e.getActionCommand().equals("Attack")) {
					module.seigeFlag = false;
					City tempCity = null;
					for (City xCity : module.getAvailableCities()) {
						if (xCity.getName().equals(army.getCurrentLocation()))
							tempCity = xCity;
					}
					tempCity.setUnderSiege(false);

					String[] options = { "Attack", "AutoResolve" };
					int x = JOptionPane.showOptionDialog(view,
							"You have been sieging the city " + army.getCurrentLocation() + " for "
									+ tempCity.getTurnsUnderSiege(),
							"If you pressed X the Battle will be AUTORESOLVED", JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
					if (x == 0) {
						view.dispose();
						try {
							vFrame.getContentPane().removeAll();
							vFrame.revalidate();
							vFrame.repaint();
							vFrame.add(new BattleView("images/empire.jpg", vFrame, module, army,
									tempCity.getDefendingArmy()));
							vFrame.revalidate();
							vFrame.repaint();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						vFrame.revalidate();
						vFrame.repaint();
					} else {
						for (int i = 0; i < this.module.getPlayer().getControlledArmies().size(); i++) {
							if (this.module.getPlayer().getControlledArmies().get(i).getCurrentLocation().toLowerCase()
									.equals(tempCity.getName().toLowerCase())) {

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
		}

	}
}
