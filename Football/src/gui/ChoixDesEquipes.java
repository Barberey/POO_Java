package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import config.Config;
/**
 * Classe qui permet l'affichage de la selection des couleurs
 * 
 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
 *
 */
public class ChoixDesEquipes extends JFrame {
	/**
	 * attribut equipes1 contenant la première equipe chosit
	 */
	private ArrayList<String> equipes1 = new ArrayList<>();
	/**
	 * attribut equipes2 contenant la deuxième equipe generer toute seule
	 */
	private ArrayList<String> equipes2 = new ArrayList<>();
	/**
	 * attribut equipeDcolor la couleur pour l'equipe de droite
	 */
	private String equipeDcolor = null;
	/**
	 * attribut equipeGcolor la couleur pour l'equipe de gauche
	 */
	private String equipeGcolor = null;
	/**
	 * attribut contain contenant le contenu de la frame
	 */
	Container contain = getContentPane();
	/**
	 * Constructeur de la classe
	 * 
	 * @param title le titre de la frame
	 */
	public ChoixDesEquipes(String title) {
		super(title);

        setAlwaysOnTop(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
		setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
		setLayout(null);
		
		JLabel label = new JLabel("CHOIX DES ÉQUIPES :");
		label.setFont(new Font("Serif", Font.BOLD, 60));
		label.setForeground(Color.white);
		label.setBounds((int) (Config.WINDOW_WIDTH/3.3), Config.WINDOW_HEIGHT/10, 1000, 80);
		contain.add(label);
		
		JLabel team1 = new JLabel("Équipe allié :");
		team1.setFont(new Font("Serif", Font.BOLD, 30));
		team1.setBounds((int) (Config.WINDOW_WIDTH/5), Config.WINDOW_HEIGHT/4, 300, 80);
		team1.setForeground(new Color(255,255,255));
		contain.add(team1);
		
		equipes1.add("France");
		equipes1.add("Chine");
		equipes1.add("Uruguay");
		equipes1.add("Jamaïque");
		equipes1.add("Pologne");
		equipes1.add("Espagne");
		
		int ligne = 0;
		
		for(int iterator = 0; iterator < equipes1.size(); iterator++) {
			JButton equipe = new JButton(equipes1.get(iterator));
			int tabulation = iterator%3;
			if(iterator%3==0 && iterator!=0) {
				ligne = ligne +150;
			}
			equipe.setBounds(Config.WINDOW_WIDTH/10 + (tabulation*150),(int) (Config.WINDOW_HEIGHT/2.5 +ligne),100,100);
			equipe.setBackground(Color.pink);
			equipe.addActionListener(new GestBoutFen());
			equipe.setActionCommand(equipes1.get(iterator));
			contain.add(equipe);
		}
		
		JLabel team2 = new JLabel("Équipe adverse :");
		team2.setFont(new Font("Serif", Font.BOLD, 30));
		team2.setBounds((int) (Config.WINDOW_WIDTH/2 + 200), Config.WINDOW_HEIGHT/4, 300, 80);
		team2.setForeground(new Color(255,255,255));
		contain.add(team2);
		
		equipes2.add("France");
		equipes2.add("Chine");
		equipes2.add("Uruguay");
		equipes2.add("Jamaïque");
		equipes2.add("Pologne");
		equipes2.add("Espagne");
		
		ligne = 0;
		for(int iterator = 0; iterator < equipes2.size(); iterator++) {
			JButton equipe = new JButton(equipes2.get(iterator));
			int tabulation = iterator%3;
			if(iterator%3==0 && iterator!=0) {
				ligne = ligne +150;
				equipe.setBounds(Config.WINDOW_WIDTH/2+(tabulation*150)+130,(int) (Config.WINDOW_HEIGHT/2.5 +ligne),100,100);
			}
			else {
				equipe.setBounds(Config.WINDOW_WIDTH/2+(tabulation*150)+130,(int) (Config.WINDOW_HEIGHT/2.5 +ligne),100,100);
			}
			equipe.setBackground(Color.green);
			equipe.addActionListener(new GestBoutFen());
			equipe.setActionCommand(equipes2.get(iterator));
			contain.add(equipe);
		}
		
		JLabel erreur = new JLabel("Veuillez choisir une équipe différente pour les deux camps");
		erreur.setFont(new Font("Serif", Font.BOLD, 20));
		erreur.setBounds(Config.WINDOW_WIDTH/4, Config.WINDOW_HEIGHT-150, 1000, 50);
		erreur.setForeground(new Color(255,255,255));
		contain.add(erreur);
		
		JButton next = new JButton("Suivant");
		next.setBounds(Config.WINDOW_WIDTH-100,Config.WINDOW_HEIGHT-20,200,50);
		next.setBackground(Color.WHITE);
		next.addActionListener(new GestBoutFen());
		next.setActionCommand("next");
		contain.add(next);
		
		JButton back = new JButton("Retour");
		back.setBounds(Config.WINDOW_WIDTH-100,0,200,50);
		back.setBackground(Color.WHITE);
		back.addActionListener(new GestBoutFen());
		back.setActionCommand("back");
		contain.add(back);
		
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
	 * Classe permettant d'attribuer des actions a nos boutons
	 * 
	 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
	 *
	 */
	public class GestBoutFen implements ActionListener {
		/**
		 * Methode permettant d'attribuer les actions
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("next"))	{
				if (!(equipeGcolor == null || equipeDcolor == null)) {
					dispose();
					ChoixDesPostes cdp = new ChoixDesPostes("Choix Poste", equipeDcolor, equipeGcolor);
				}
			}
			else if(e.getActionCommand().equals("back")) {
				dispose();
				try {
					IHM ihm = new IHM("Football");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			else if (equipeGcolor == null) {
				if(e.getActionCommand().equals("Chine")) {
					equipeGcolor = "chi";
				}
				else if (e.getActionCommand().equals("France")) {
					equipeGcolor = "fra";
				}
				else if (e.getActionCommand().equals("Jamaïque")) {
					equipeGcolor = "jam";
				}
				else if (e.getActionCommand().equals("Uruguay")) {
					equipeGcolor = "uru";
				}
				else if (e.getActionCommand().equals("Pologne")) {
					equipeGcolor = "pol";
				}
				else if (e.getActionCommand().equals("Espagne")) {
					equipeGcolor = "esp";
				}
			}
			else if (equipeDcolor == null) {
				if(e.getActionCommand().equals("Chine") && equipeGcolor!="chi") {
					equipeDcolor = "chi";
				}
				else if (e.getActionCommand().equals("France") && equipeGcolor!="fra") {
					equipeDcolor = "fra";
				}
				else if (e.getActionCommand().equals("Jamaïque") && equipeGcolor!="jam") {
					equipeDcolor = "jam";
				}
				else if (e.getActionCommand().equals("Uruguay") && equipeGcolor!="uru") {
					equipeDcolor = "uru";
				}
				else if (e.getActionCommand().equals("Pologne") && equipeGcolor!="pol") {
					equipeDcolor = "pol";
				}
				else if (e.getActionCommand().equals("Espagne") && equipeGcolor!="esp") {
					equipeDcolor = "esp";
				}
			}
			
		}
	}	
}