package view;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import engine.City;
import engine.Game;
import units.Archer;
import units.Army;
import units.Unit;

@SuppressWarnings("serial")
public class ArmiesScreen extends SecondScreen implements ActionListener {
	Game module;
	ArrayList<JButton> buttons = new ArrayList<>();
	JPanel panel = new JPanel();
	//City city;
	Frame view;

	//boolean controlledflag;
	public ArmiesScreen(Game module,Frame view) {
		this.view = view;
		panel.setLayout(new FlowLayout());
		this.module = module;
		//if(controlledflag)
		for (int i = 0; i < this.module.getPlayer().getControlledArmies().size(); i++) {
			JButton b = new JButton();
			b.setText("Army " + (i+1));
			b.addActionListener(this);
			buttons.add(b);
			panel.add(b);
		}
		this.add(panel);
		this.repaint();
		this.revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		if(e.getActionCommand().equals("Defending")) {
//			this.remove(panel);
//			this.add(new ArmyInformation(this.module, city.getDefendingArmy(), city, controlledflag));
//			this.revalidate();
//			this.repaint();
//		}
//		else {
		JButton temp = (JButton) e.getSource();
		int r = buttons.indexOf(temp);
		Army tempp = this.module.getPlayer().getControlledArmies().get(r);
		this.remove(panel);
		this.add(new ArmyInformation(this,null,null,this.module,tempp,true,view));
		this.revalidate();
		this.repaint();
		//}
	}

	public static void main(String[] args) {
		Game a;
		try {
			a = new Game("Omar", "Cairo");
			Army b1 = new Army("Sparta");
			Army b2 = new Army("Sparta");
			Army b3 = new Army("Sparta");
			Army b4 = new Army("Sparta");
			Army b5 = new Army("Sparta");
			Army b6 = new Army("Sparta");
			Army b7 = new Army("Sparta");
			Army b8 = new Army("Sparta");
			Army b9 = new Army("Sparta");
			Army b10 = new Army("Sparta");
			Army b11 = new Army("Sparta");
			Army b12 = new Army("Sparta");
			Army b13 = new Army("Sparta");
			Army b14 = new Army("Sparta");
			Army b15 = new Army("Sparta");
			ArrayList<Unit> c = new ArrayList<Unit>();
			c.add(new Archer(1,2,3,4,5));
			c.add(new Archer(1,2,3,4,5));
			c.add(new Archer(1,2,3,4,5));
			c.add(new Archer(1,2,3,4,5));
			c.add(new Archer(1,2,3,4,5));
			c.add(new Archer(1,2,3,4,5));
			
			b1.setUnits(c);
			a.getPlayer().getControlledArmies().add(b1);
			a.getPlayer().getControlledArmies().add(b2);
			a.getPlayer().getControlledArmies().add(b3);
			a.getPlayer().getControlledArmies().add(b4);
			a.getPlayer().getControlledArmies().add(b5);
			a.getPlayer().getControlledArmies().add(b6);
			a.getPlayer().getControlledArmies().add(b7);
			a.getPlayer().getControlledArmies().add(b8);
			a.getPlayer().getControlledArmies().add(b9);
			a.getPlayer().getControlledArmies().add(b10);
			a.getPlayer().getControlledArmies().add(b11);
			a.getPlayer().getControlledArmies().add(b12);
			a.getPlayer().getControlledArmies().add(b13);
			a.getPlayer().getControlledArmies().add(b14);
			a.getPlayer().getControlledArmies().add(b15);
//			new ArmiesScreen(a,);	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
