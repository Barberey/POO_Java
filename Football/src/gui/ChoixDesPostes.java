package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import config.Config;
import donnee.Composition;
/**
 * Classe permettant le choix de la composition de notre equipe
 * 
 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
 *
 */
public class ChoixDesPostes extends JFrame {
	/**
	 * attribut counter1 le compteur pour attaquant  
	 */
	public Counter counter1 = new Counter(0);
	/**
	 * attribut counter2 le compteur pour les milieux de terrain
	 */
	public Counter counter2 = new Counter(0);;
	/**
	 * attribut counter3 le compteur pour les defenseurs
	 */
	public Counter counter3 = new Counter(0);;
	/**
	 * attribut nombreDeJoueursAtt le label affichant le compteur d'attaquant
	 */
	JLabel nombreDeJoueursAtt = new JLabel("0");
	/**
	 * attribut nombreDeJoueursMil le label affichant le compteur de milieu
	 */
	JLabel nombreDeJoueursMil = new JLabel("0");
	/**
	 * attribut nombreDeJoueursDef le label affichant le compteur de defenseur
	 */
	JLabel nombreDeJoueursDef = new JLabel("0");
	/**
	 * attribut equipeDcolor la couleur de l'equipe de droite
	 */
	public String equipeDcolor;
	/**
	 * attribut equipeGcolor la couleur de l'equipe de gauche
	 */
	public String equipeGcolor;
	/**
	 * Constructeur de la classe
	 * 
	 * @param title le titre de la frame
	 * @param equipeDcolor la couleur de l'equipe de droite
	 * @param equipeGcolor la couleur de l'equipe de gauche
	 * @throws HeadlessException
	 */
	public ChoixDesPostes(String title,String equipeDcolor, String equipeGcolor) throws HeadlessException {
		super(title);
		this.equipeDcolor = equipeDcolor;
		this.equipeGcolor = equipeGcolor;
		
		
		Container contain = getContentPane();
		setAlwaysOnTop(true);
	    setExtendedState(JFrame.MAXIMIZED_BOTH);
		setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
		setLayout(null);
		
		JLabel label1 = new JLabel("CHOIX DES POSTES :");
		label1.setFont(new Font("Serif", Font.BOLD, 60));
		label1.setForeground(Color.white);
		label1.setBounds((int) (Config.WINDOW_WIDTH/3.3), Config.WINDOW_HEIGHT/10, 1000, 80);
		contain.add(label1);
		
		JButton back = new JButton("Retour");
		back.setBounds(Config.WINDOW_WIDTH-100,0,200,50);
		back.setBackground(Color.WHITE);
		back.addActionListener(new GestBoutCompteur());
		back.setActionCommand("back");
		contain.add(back);
		
		JButton next = new JButton("Suivant");
		next.setBounds(Config.WINDOW_WIDTH-100,Config.WINDOW_HEIGHT-20,200,50);
		next.setBackground(Color.WHITE);
		next.addActionListener(new GestBoutCompteur());
		next.setActionCommand("next");
		contain.add(next);
		
		JLabel label = new JLabel("ATTAQUANTS :");
		label.setFont(new Font("Serif", Font.BOLD, 30));
		label.setBounds((int) (Config.WINDOW_WIDTH/3.5), Config.WINDOW_HEIGHT/2 - 100, 300, 50);
		label.setForeground(new Color(255,255,255));
		contain.add(label);
		
		nombreDeJoueursAtt.setFont(new Font("Serif", Font.BOLD, 30));
		nombreDeJoueursAtt.setBounds((int) (Config.WINDOW_WIDTH/2) + 100, Config.WINDOW_HEIGHT/2 - 100, 50, 50);
		nombreDeJoueursAtt.setForeground(new Color(255,255,255));
		contain.add(nombreDeJoueursAtt);
		
		JButton plus1 = new JButton("+");
		plus1.setBounds((int) (Config.WINDOW_WIDTH/2) + 180,Config.WINDOW_HEIGHT/2 - 100,50,50);
		plus1.setBackground(Color.cyan);
		plus1.addActionListener(new GestBoutCompteur());
		plus1.setActionCommand("plus1");
		contain.add(plus1);
		
		JButton moins1 = new JButton("-");
		moins1.setBounds((int) (Config.WINDOW_WIDTH/2) - 18,Config.WINDOW_HEIGHT/2 - 100,50,50);
		moins1.setBackground(Color.cyan);
		moins1.addActionListener(new GestBoutCompteur());
		moins1.setActionCommand("moins1");
		contain.add(moins1);
		
		
		JLabel label2 = new JLabel("MILIEUX :");
		label2.setFont(new Font("Serif", Font.BOLD, 30));
		label2.setBounds((int) (Config.WINDOW_WIDTH/3.5), Config.WINDOW_HEIGHT/2, 300, 50);
		label2.setForeground(new Color(255,255,255));
		contain.add(label2);
		
		nombreDeJoueursMil.setFont(new Font("Serif", Font.BOLD, 30));
		nombreDeJoueursMil.setBounds((int) (Config.WINDOW_WIDTH/2) + 100, Config.WINDOW_HEIGHT/2, 50, 50);
		nombreDeJoueursMil.setForeground(new Color(255,255,255));
		contain.add(nombreDeJoueursMil);
		
		JButton plus2 = new JButton("+");
		plus2.setBounds((int) (Config.WINDOW_WIDTH/2) + 180,Config.WINDOW_HEIGHT/2,50,50);
		plus2.setBackground(Color.cyan);
		plus2.addActionListener(new GestBoutCompteur());
		plus2.setActionCommand("plus2");
		contain.add(plus2);
		
		JButton moins2 = new JButton("-");
		moins2.setBounds((int) (Config.WINDOW_WIDTH/2) - 18,Config.WINDOW_HEIGHT/2,50,50);
		moins2.setBackground(Color.cyan);
		moins2.addActionListener(new GestBoutCompteur());
		moins2.setActionCommand("moins2");
		contain.add(moins2);
		
		
		JLabel label3 = new JLabel("DEFENSEURS :");
		label3.setFont(new Font("Serif", Font.BOLD, 30));
		label3.setBounds((int) (Config.WINDOW_WIDTH/3.5), Config.WINDOW_HEIGHT/2 + 100, 300, 50);
		label3.setForeground(new Color(255,255,255));
		contain.add(label3);
		
		nombreDeJoueursDef.setFont(new Font("Serif", Font.BOLD, 30));
		nombreDeJoueursDef.setBounds((int) (Config.WINDOW_WIDTH/2) + 100, Config.WINDOW_HEIGHT/2 +100, 50, 50);
		nombreDeJoueursDef.setForeground(new Color(255,255,255));
		contain.add(nombreDeJoueursDef);
		
		JButton plus3 = new JButton("+");
		plus3.setBounds((int) (Config.WINDOW_WIDTH/2) + 180,Config.WINDOW_HEIGHT/2 +100,50,50);
		plus3.setBackground(Color.cyan);
		plus3.addActionListener(new GestBoutCompteur());
		plus3.setActionCommand("plus3");
		contain.add(plus3);
		
		JButton moins3 = new JButton("-");
		moins3.setBounds((int) (Config.WINDOW_WIDTH/2) - 18,Config.WINDOW_HEIGHT/2 +100,50,50);
		moins3.setBackground(Color.cyan);
		moins3.addActionListener(new GestBoutCompteur());
		moins3.setActionCommand("moins3");
		contain.add(moins3);
		
		JLabel erreur = new JLabel("Veuillez attribuer le nombre de joueurs que vous voulez par poste et que le nombre de poste attribuer soit égaux à 10");
		erreur.setFont(new Font("Serif", Font.BOLD, 20));
		erreur.setBounds(Config.WINDOW_WIDTH/6, Config.WINDOW_HEIGHT-150, 1000, 50);
		erreur.setForeground(new Color(255,255,255));
		contain.add(erreur);
		
		ImageIcon imageIcon = new ImageIcon("src/ressources/fond.png"); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(Config.WINDOW_WIDTH+100, Config.WINDOW_HEIGHT+100,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		imageIcon = new ImageIcon(newimg);  // transform it back
		JLabel jLabel = new JLabel();
		jLabel.setIcon(imageIcon);
		jLabel.setBounds(0, 0, Config.WINDOW_WIDTH+100, Config.WINDOW_HEIGHT+100);
		contain.add(jLabel);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	/**
	 * Classe permettant d'attribuer des actions a des boutons
	 * 
	 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
	 *
	 */
	public class GestBoutCompteur implements ActionListener {
		/**
		 * Methode permettant l'attribution d'action aux boutons
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if((counter1.getValue() + counter2.getValue() + counter3.getValue()) <10) {
				if(e.getActionCommand().equals("plus1") && counter1.getValue() < 5) {
						counter1.setValue(Integer.valueOf(nombreDeJoueursAtt.getText()));
						counter1.increment();
						nombreDeJoueursAtt.setText(String.valueOf(counter1.getValue()));
				}
				else if(e.getActionCommand().equals("plus2")  && counter2.getValue() < 5) {
					counter2.setValue(Integer.valueOf(nombreDeJoueursMil.getText()));
					counter2.increment();
					nombreDeJoueursMil.setText(String.valueOf(counter2.getValue()));
				}
				else if(e.getActionCommand().equals("plus3") && counter3.getValue() < 5) {
					counter3.setValue(Integer.valueOf(nombreDeJoueursDef.getText()));
					counter3.increment();
					nombreDeJoueursDef.setText(String.valueOf(counter3.getValue()));
				}
			}
			if(e.getActionCommand().equals("moins1")) {
				counter1.setValue(Integer.valueOf(nombreDeJoueursAtt.getText()));
				counter1.decrement();
				nombreDeJoueursAtt.setText(String.valueOf(counter1.getValue()));
			}
			else if(e.getActionCommand().equals("moins2")) {
				counter2.setValue(Integer.valueOf(nombreDeJoueursMil.getText()));
				counter2.decrement();
				nombreDeJoueursMil.setText(String.valueOf(counter2.getValue()));
			}
			else if(e.getActionCommand().equals("moins3")) {
				counter3.setValue(Integer.valueOf(nombreDeJoueursDef.getText()));
				counter3.decrement();
				nombreDeJoueursDef.setText(String.valueOf(counter3.getValue()));
			}
			if(e.getActionCommand().equals("next"))	{
				if((counter1.getValue() + counter2.getValue() + counter3.getValue()) == 10) {
					dispose();
					Composition compo = new Composition(counter1.getValue(), counter2.getValue(), counter3.getValue(), 1);
					ChoixJoueur choix = new ChoixJoueur("Choix Joueur", compo, equipeDcolor, equipeGcolor);
					
				}
			}
			else if(e.getActionCommand().equals("back")) {
				dispose();
				ChoixDesEquipes cde = new ChoixDesEquipes("Choix Equipes");
			}
		}
		
	}
	/**
	 * Classe pour les compteurs
	 * 
	 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
	 *
	 */
	public class Counter {
		/**
		 * attribut value la valeur qui sera notre compteur
		 */
		private int value;
		/**
		 * Constructeur de la classe
		 * 
		 * @param value le compteur
		 */
		public Counter(int value) {
			this.value = value;
		}
		/**
		 * Methode permettant de recuperer le compteur
		 * 
		 * @return value le compteur
		 */
		public int getValue() {
			return value;
		}
		/**
		 * Methode permettant d'attribuer le compteur
		 * 
		 * @param value le compteur
		 */
		public void setValue(int value) {
			this.value = value;
		}
		/**
		 * Methode permettant d'incrementer le compteur
		 */
		public void increment() {
			if (value < 10) {
				value++;
			}
		}
		/**
		 * Methode permettant de decrementer le compteur
		 */
		public void decrement() {
			if (value > 0) {
				value--;
			}
		}
		/**
		 * Methode permettant d'initialiser le compteur
		 */
		public void init() {
			value = 0;
		}
	}
}
