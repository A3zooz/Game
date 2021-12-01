package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

import engine.*;
import exceptions.FriendlyFireException;
import units.*;

public class BattleView extends JPanelWithBackground implements ActionListener {
	public Frame f;
	public Game module;
	public Army carmy;
	public Army enemyarmy;
	ButtonGroup g2;
	ButtonGroup g1;
	Unit c;
	Unit enem;
	JPanel controlledd = new JPanel();
	JPanel enemy = new JPanel();
	JPanel loginfo = new JPanel();
	JPanel info = new JPanel();
	JPanel loog = new JPanel();
	JTextArea log = new JTextArea(200, 50);
	JButton attack = new JButton("Attack");
	ArrayList<CButton> contrlb;
	ArrayList<CButton> enemyb;
	JScrollPane sc;
	CButton enemb;
	CButton cntrlc;

	public BattleView(String fileName, Frame f, Game module, Army carmy, Army enemyarmy) throws IOException {
		super(fileName);
		attack.addActionListener(this);
		attack.setPreferredSize(new Dimension(640, 75));

		this.f = f;
		this.module = module;
		this.carmy = carmy;
		this.enemyarmy = enemyarmy;
		this.setLayout(new BorderLayout());
		log.setForeground(Color.white);
		log.setEditable(false);
		
		info.setBackground(Color.black);
		log.setBackground(Color.black);
		loginfo.setLayout(new BorderLayout());
		g1 = new ButtonGroup();
		g2 = new ButtonGroup();
		log.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		log.setLineWrap(true);
		log.setWrapStyleWord(true);
		sc=new JScrollPane(log);
		sc.setPreferredSize(new Dimension(454,1080));
		sc.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED );
		//sc.setWheelScrollingEnabled(true);
//		JScrollBar vertical = sc.getVerticalScrollBar();
//		vertical.setValue( vertical.getMaximum() );
		loginfo.setPreferredSize(new Dimension(454, 1080));
		loginfo.add(sc, BorderLayout.NORTH);

		// loog.setBackground(Color.black);
		// loginfo.add(loog);
		// loginfo.setBackground(Color.black);
		loginfo.add(info, BorderLayout.SOUTH);
		// loginfo.setOpaque(false);
		contrlb = new ArrayList<>();
		enemyb = new ArrayList<>();
		// loginfo.add(info,BorderLayout.SOUTH);
		controlledd.setPreferredSize(new Dimension(454, 1080));
		enemy.setPreferredSize(new Dimension(454, 1080));

		int i = 0;
		for (Unit xUnit : carmy.getUnits()) {
			CButton x = new CButton(this, 100, 100, carmy.getUnits().get(i).getClass().getSimpleName(), xUnit);
			String m = "Controlled " + carmy.getUnits().get(i).getClass().getSimpleName() + " of level "
					+ xUnit.getLevel() + "\n" + "Current Soldier Count: " + xUnit.getCurrentSoldierCount()+
					"\n\n";
			x.setActionCommand(m);
			i++;
			contrlb.add(x);
			controlledd.add(x);
			g1.add(x);
		}
		i = 0;
		for (Unit xUnit : enemyarmy.getUnits()) {
			CButton x = new CButton(this, 100, 100, enemyarmy.getUnits().get(i).getClass().getSimpleName(), xUnit);
			String m = "Enemy " + enemyarmy.getUnits().get(i).getClass().getSimpleName() + " of level " + xUnit.getLevel()
					+ "\n" + "Current Soldier Count: " + xUnit.getCurrentSoldierCount() + "\n\n";
			i++;
			x.setActionCommand(m);
			enemyb.add(x);
			enemy.add(x);
			g2.add(x);
		}
		controlledd.add(attack, BorderLayout.SOUTH);
		controlledd.setOpaque(false);
		enemy.setOpaque(false);
		this.add(loginfo, BorderLayout.CENTER);
		this.add(controlledd, BorderLayout.WEST);
		this.add(enemy, BorderLayout.EAST);
		f.add(this);
		f.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		for (CButton b : contrlb) {
			
			if (e.getSource() == b) {
				for(CButton bb:contrlb) {
					bb.setBackground(Color.darkGray);
				}
				log.setForeground(Color.green);
				log.append(b.getActionCommand());
				
				//log.append("Please Choose Enemy Unit to attack\n");
			
				c = ((CButton) e.getSource()).getU();
				cntrlc=(CButton) e.getSource();
				cntrlc.setBackground(Color.green);
				
			}
		}
		for (CButton b : enemyb) {
			
			if (e.getSource() == b) {
				for(CButton bb:enemyb) {
					bb.setBackground(Color.darkGray);
				}
				log.setForeground(Color.red);
				log.append(b.getActionCommand());
				
				//log.append("Please Choose a controlled unit to attack with\n");
				enemb=(CButton) e.getSource();
				enem = ((CButton) e.getSource()).getU();
				// b.set
				enemb.setBackground(Color.green);
				
			}
		}
			if(e.getSource()==attack) {
				if(c==null && enem==null)
					JOptionPane.showMessageDialog(this, "Choose units");
				else if(enem==null)
					JOptionPane.showMessageDialog(this, "Choose enemy unit");
				else if(c == null)
					JOptionPane.showMessageDialog(this, "Choose controlled unit");
				else {
					for(CButton b: contrlb) {
						if(b.getBackground()==Color.green)
							b.setBackground(Color.darkGray);
						if(b.getBackground()==Color.red)
							b.setBackground(Color.darkGray);
					}
					for(CButton b: enemyb) {
						if(b.getBackground()==Color.green)
							b.setBackground(Color.darkGray);
						if(b.getBackground()==Color.red)
							b.setBackground(Color.darkGray);
					}
					
					int cc=enem.getCurrentSoldierCount();
					try {
						c.attack(enem);
					} catch (FriendlyFireException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(f, e1.getMessage());
					}
					log.setForeground(Color.green);
					log.append("Nice Job!\nEnemy lost "+(cc-enem.getCurrentSoldierCount())+" soldiers\nCurrent soldier count: "+enem.getCurrentSoldierCount()+"\n\n");
					
					
					if (enem.getCurrentSoldierCount() == 0) {
						log.setForeground(Color.yellow);
						log.append("Enemy Unit was destroyed\nPlease choose another enemy unit");
						for (CButton bb : enemyb) {
							if (bb.getU().deadFlag) {
								enemyb.remove(bb);
								enemy.remove(bb);
								enemy.repaint();
								enemy.revalidate();
								this.repaint();
								this.revalidate();
								break;
							}
						}

				

						
					}
					
					
					if(enemyarmy.getUnits().isEmpty()) {
						JOptionPane.showMessageDialog(f, "You WIN, You now control "+ carmy.getCurrentLocation());
						module.occupy(carmy, carmy.getCurrentLocation());
						f.remove(this);
						try {
							f.add(new WorldMap(module, f));
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						f.repaint();
						f.revalidate();
						
					}
					Unit unit1 =null;
					Unit unit2 = null;
					CButton temp=null;
					try {
						unit1 = enemyarmy.getUnits().get((int) (Math.random() * enemyarmy.getUnits().size()));
					    unit2 = carmy.getUnits().get((int) (Math.random() * carmy.getUnits().size()));
					    for(CButton bb: contrlb) {
					    	if(bb.u==unit2) {
					    		temp=bb;
					    	}
					    }
					    
					} catch (IndexOutOfBoundsException e2) {
						
					}
					temp.setBackground(Color.red);
					cc=unit2.getCurrentSoldierCount();
					try {
						unit1.attack(unit2);
					} catch (FriendlyFireException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					log.setSelectedTextColor(Color.red);
					log.append("Oh No!\nYou lost "+(cc-unit2.getCurrentSoldierCount())+" soldiers\nCurrent soldier count: "+unit2.getCurrentSoldierCount()+"\n\n");
					
					if (unit2.getCurrentSoldierCount() == 0) {
						log.setForeground(Color.magenta);
						log.append("Controlled Unit was destroyed\nPlease choose another unit to attack with\n\n");
						for (CButton bb : contrlb) {
							if (bb.getU().deadFlag) {
								contrlb.remove(bb);
								controlledd.remove(bb);
								controlledd.repaint();
								controlledd.revalidate();
								this.repaint();
								this.revalidate();
								break;
							}
						}

					
					
					
				}
					if(carmy.getUnits().isEmpty()) {
						JOptionPane.showMessageDialog(this, "You LOSE");
						this.module.getPlayer().getControlledArmies().remove(carmy);
						f.remove(this);
						try {
							f.add(new WorldMap(module, f));
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						f.repaint();
						f.revalidate();
						
					}
					c=null;
					enem=null;
				}
				
			}
			
			
	}
	

	public static void main(String[] args) {
		Game g = null;
		Army a = new Army("Cairo");
		Archer aa = new Archer(1, 20000, 2, 2, 2);
		Cavalry aa1 = new Cavalry(2, 2, 2, 2, 2);
		Infantry aa2 = new Infantry(2, 2, 2, 2, 2);
		Archer aa3 = new Archer(2, 2, 2, 2, 2);
		aa.setParentArmy(a);
		a.getUnits().add(aa);
		aa1.setParentArmy(a);
		a.getUnits().add(aa1);
		aa2.setParentArmy(a);
		a.getUnits().add(aa2);
		aa3.setParentArmy(a);
		a.getUnits().add(aa3);
		Archer we = new Archer(1, 20000, 2, 2, 2);
		Cavalry wee = new Cavalry(2, 2, 2, 2, 2);
		Infantry awa2 = new Infantry(2, 2, 2, 2, 2);
		Archer a3aa = new Archer(2, 2, 2, 2, 2);
		we.setParentArmy(a);
		a.getUnits().add(we);
		wee.setParentArmy(a);
		a.getUnits().add(wee);
		awa2.setParentArmy(a);
		a.getUnits().add(awa2);
		a3aa.setParentArmy(a);
		a.getUnits().add(a3aa);
//		a.getUnits().add(new Archer(2,2,2,2,2));
//		a.getUnits().add(new Archer(2,2,2,2,2));
//		a.getUnits().add(new Archer(2,2,2,2,2));
//		a.getUnits().add(new Archer(2,2,2,2,2));
//		a.getUnits().add(new Archer(2,2,2,2,2));
		Army b = new Army("Sparta");
		Cavalry aaa = new Cavalry(1, 50, 2, 2, 2);
		aaa.setParentArmy(b);
		b.getUnits().add(aaa);
//		b.getUnits().add(new Archer(2,2,2,2,2));
//		b.getUnits().add(new Archer(2,2,2,2,2));
//		b.getUnits().add(new Archer(2,2,2,2,2));
//		b.getUnits().add(new Archer(2,2,2,2,2));
//		b.getUnits().add(new Archer(2,2,2,2,2));

		try {
			g = new Game("Ahmed", "Cairo");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			new BattleView("images/BattleBack.jpg", new Frame(), g, a, b);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
