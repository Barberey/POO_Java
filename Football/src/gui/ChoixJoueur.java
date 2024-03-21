package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.plot.SpiderWebPlot;

import config.Config;
import donnee.Composition;
import donnee.Joueur;
import donnee.Statistique;
import gui.ChoixDesPostes.GestBoutCompteur;
import moteur.Build;

/**
 * Classe du GUI pour le choix des joueurs 
 * 
 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
 *
 */
public class ChoixJoueur extends JFrame {
	/**
	 * Attribut {@link String} pour preciser le skin des joueurs de l'equipe Droit
	 */
	private String equipeDColor;
	/**
	 * Attribut {@link String} pour preciser le skin des joueurs de l'equipe Gauche
	 */
	private String equipeGColor;
	/**
	 * 
	 */
	private Composition compo;
	/**
	 * 
	 */
	private ArrayList<Joueur> att = null;
	/**
	 * 
	 */
	private ArrayList<Joueur> mil = null;
	/**
	 * 
	 */
	private ArrayList<Joueur> def = null;
	/**
	 * 
	 */
	private ArrayList<Joueur> gar = null;
	/**
	 * 
	 */
	private int limitePoste;
	
	private ArrayList<Joueur> joueurAlea;
	private ArrayList<Joueur> joueurChoisi = new ArrayList<>();
	
	
	private JLabel typeJoueur = new JLabel();
	
	private JPanel panneauJoueur = new JPanel();
	private JPanel panneauChoisi = new JPanel();
	
	public ChoixJoueur(String title, Composition compo, String equipeDColor, String equipeGColor) {
		super(title);
		this.compo = compo;
		this.equipeDColor = equipeDColor;
		this.equipeGColor = equipeGColor;
		typeJoueur.setText("Attaquants");
		limitePoste = compo.getNbJoueurPrPos().get("ATT");
		init();
		
	}
	
	public ChoixJoueur(String title, Composition compo, ArrayList<Joueur> att, String equipeDColor, String equipeGColor ) {
		super(title);
		this.compo = compo;
		this.att = att;
		this.equipeDColor = equipeDColor;
		this.equipeGColor = equipeGColor;
		typeJoueur.setText("Milieux");
		limitePoste = compo.getNbJoueurPrPos().get("MIL");
		init();
	}
	
	public ChoixJoueur(String title, Composition compo, ArrayList<Joueur> att, ArrayList<Joueur> mil, String equipeDColor, String equipeGColor) {
		super(title);
		this.compo = compo;
		this.att = att;
		this.mil = mil;
		this.equipeDColor = equipeDColor;
		this.equipeGColor = equipeGColor;
		typeJoueur.setText("Defenseurs");
		limitePoste = compo.getNbJoueurPrPos().get("DEF");
		init();
	}
	
	public ChoixJoueur(String title, Composition compo, ArrayList<Joueur> att, ArrayList<Joueur> mil, ArrayList<Joueur> def, String equipeDColor, String equipeGColor) {
		super(title);
		this.compo = compo;
		this.att = att;
		this.mil = mil;
		this.def = def;
		this.equipeDColor = equipeDColor;
		this.equipeGColor = equipeGColor;
		typeJoueur.setText("Gardien");
		limitePoste = compo.getNbJoueurPrPos().get("GAR");
		init();
	}
	
	public ChoixJoueur(String title, Composition compo, ArrayList<Joueur> att, ArrayList<Joueur> mil, ArrayList<Joueur> def, ArrayList<Joueur> gar, String equipeDColor, String equipeGColor) {
		super(title);
		this.compo = compo;
		this.att = att;
		this.mil = mil;
		this.def = def;
		this.gar = gar;
		this.equipeDColor = equipeDColor;
		this.equipeGColor = equipeGColor;
		transition();
	}
	
	private void transition() {
		dispose();
		ArrayList<Joueur> equipe = new ArrayList<>();
		equipe.addAll(att);
		equipe.addAll(mil);
		equipe.addAll(def);
		equipe.addAll(gar);
		MainGui main = new MainGui("Football", compo, equipe, equipeDColor, equipeGColor);
		Thread gameThread = new Thread(main);
		gameThread.start();
	}
	
	private void init() {
		Container content = getContentPane();
		setAlwaysOnTop(true);
	    setExtendedState(JFrame.MAXIMIZED_BOTH);
		setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
		setLayout(null);
		
		JLabel label1 = new JLabel("CHOIX DES JOUEURS :");
		label1.setFont(new Font("Serif", Font.BOLD, 50));
		label1.setBounds((int) (Config.WINDOW_WIDTH/3.3), Config.WINDOW_HEIGHT/1000, 1000, 50);
		content.add(label1);
		
		typeJoueur.setFont(new Font("Serif", Font.BOLD, 35));
		typeJoueur.setBounds((int) (Config.WINDOW_WIDTH/2.2), Config.WINDOW_HEIGHT/15, 1000, 50);
		content.add(typeJoueur);
		
		JButton back = new JButton("Retour");
		back.setBounds(Config.WINDOW_WIDTH-100,0,200,50);
		back.setBackground(Color.WHITE);
		back.addActionListener(new GestBoutCompteur());
		back.setActionCommand("back");
		content.add(back);
		
		JButton next = new JButton("Suivant");
		next.setBounds(Config.WINDOW_WIDTH-100,Config.WINDOW_HEIGHT-20,200,50);
		next.setBackground(Color.WHITE);
		next.addActionListener(new GestBoutCompteur());
		next.setActionCommand("next");
		content.add(next);
		
		
		initPanelJoueur();
		panneauJoueur.setBounds((int) (Config.WINDOW_WIDTH/20), Config.WINDOW_HEIGHT/5, 650, 500);
		content.add(panneauJoueur);
		
		initPanelChoisi();
		panneauChoisi.setBounds((int) (Config.WINDOW_WIDTH-500), Config.WINDOW_HEIGHT/5, 650, 500);
		content.add(panneauChoisi);
		
		
		JPanel panneauSelection = new JPanel();
		content.add(panneauSelection);
		
		ImageIcon imageIcon = new ImageIcon("src/ressources/fond.png"); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(Config.WINDOW_WIDTH+100, Config.WINDOW_HEIGHT+100,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		imageIcon = new ImageIcon(newimg);  // transform it back
		JLabel jLabel = new JLabel();
		jLabel.setIcon(imageIcon);
		jLabel.setBounds(0, 0, Config.WINDOW_WIDTH+100, Config.WINDOW_HEIGHT+100);
		content.add(jLabel);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void initPanelChoisi() {
		panneauChoisi.setLayout(new GridLayout(0, 2));
		ArrayList<Component> panneaux = descriptionJoueur(joueurChoisi, false);
		for(Component p : panneaux) {
			panneauChoisi.add(p);
		}
	}
	
	private void initPanelJoueur() {
		panneauJoueur.setLayout(new GridLayout(0,2));
		if(typeJoueur.getText().equals("Attaquants")) {
			joueurAlea = Build.creeJoueurs(10, 0, 0, 0);
		}
		else if(typeJoueur.getText().equals("Milieux")) {
			joueurAlea = Build.creeJoueurs(0, 10, 0, 0);
		}
		else if(typeJoueur.getText().equals("Defenseurs")) {
			joueurAlea = Build.creeJoueurs(0, 0, 10, 0);
		}
		else {
			joueurAlea = Build.creeJoueurs(0, 0, 0, 10);
		}
		
		ArrayList<Component> panneaux = descriptionJoueur(joueurAlea, true);
		for(Component p : panneaux) {
			panneauJoueur.add(p);
		}
	}
	
	private ArrayList<Component> descriptionJoueur(ArrayList<Joueur> joueurs,Boolean bool){
		ArrayList<Component> panneaux = new ArrayList<>();
		panneaux.add(new JLabel("Nom | Prenom | Tir | Passe | Defense | Vitesse"));
		panneaux.add(new JLabel("Graphe"));
		for(int i = 1; i<=joueurs.size();i++) {
			Joueur j = joueurs.get(i-1);
			Statistique stats = j.getStats();
			String s = Integer.toString(i)+". ";
			s+= j.getNom();
			s+=" | "+j.getPrenom();
			s+=" | "+Integer.toString(stats.getTir());
			s+=" | "+Integer.toString(stats.getPasse());
			s+=" | "+Integer.toString(stats.getDef());
			s+=" | "+Integer.toString(stats.getVitesse());
			JLabel info = new JLabel(s);
			if(bool) {
				info.addMouseListener(new MouseHandlerChoix());
			}
			else {
				info.addMouseListener(new MouseHandlerAnnul());
			}
			panneaux.add(info);
			
			panneaux.add(new JLabel("Graphe"));
		}
		return panneaux;
	}
	
	public class GestBoutCompteur implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("next"))	{
				if(joueurChoisi.size()==limitePoste) {
					if(att==null) {
						dispose();
						ChoixJoueur choix = new ChoixJoueur(getTitle(), compo, joueurChoisi,equipeDColor,equipeGColor);
					}
					else if(mil==null) {
						dispose();
						ChoixJoueur choix = new ChoixJoueur(getTitle(), compo,att, joueurChoisi,equipeDColor,equipeGColor);
					}
					else if(def==null) {
						dispose();
						ChoixJoueur choix = new ChoixJoueur(getTitle(), compo,att,mil, joueurChoisi,equipeDColor,equipeGColor);
					}
					else if(gar==null) {
						dispose();
						ChoixJoueur choix = new ChoixJoueur(getTitle(), compo,att,mil,def, joueurChoisi,equipeDColor,equipeGColor);
					}
				}
				
			}
			else if(e.getActionCommand().equals("back")) {
				dispose();
				ChoixDesPostes cde = new ChoixDesPostes("Choix Postes",equipeDColor,equipeGColor);
			}
		}
		
	}
	
	private void reloadJoueurChoisi() {
		panneauChoisi.removeAll();
		ArrayList<Component> panneaux = descriptionJoueur(joueurChoisi, false);
		for(Component p : panneaux) {
			panneauChoisi.add(p);
		}
		setAlwaysOnTop(true);
	    setExtendedState(JFrame.MAXIMIZED_BOTH);
		setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
		repaint();
	}
	
	private void reloadJoueurAlea() {
		panneauJoueur.removeAll();
		ArrayList<Component> panneaux = descriptionJoueur(joueurAlea, true);
		for(Component p : panneaux) {
			panneauJoueur.add(p);
		}
		setAlwaysOnTop(true);
	    setExtendedState(JFrame.MAXIMIZED_BOTH);
		setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
		repaint();
	}
	
	public class MouseHandlerChoix implements MouseListener{
		
		
		
		@Override
		public void mouseClicked(MouseEvent e) {
			if(joueurChoisi.size()<limitePoste) {
				JLabel j = (JLabel) e.getComponent();
				int place = Integer.parseInt(j.getText().substring(0, 1));
				joueurChoisi.add(joueurAlea.get(place-1));
				joueurAlea.remove(place-1);
				
				reloadJoueurChoisi();
				reloadJoueurAlea();
				setAlwaysOnTop(true);
			    setExtendedState(JFrame.MAXIMIZED_BOTH);
				setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
				repaint();
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			JLabel j = (JLabel) e.getComponent();
			j.setForeground(Color.blue);
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			JLabel j = (JLabel) e.getComponent();
			j.setForeground(Color.black);
		}
		
	}
	
	public class MouseHandlerAnnul implements MouseListener{
		
		
		
		@Override
		public void mouseClicked(MouseEvent e) {
			JLabel j = (JLabel) e.getComponent();
			int place = Integer.parseInt(j.getText().substring(0, 1));
			joueurAlea.add(joueurChoisi.get(place-1));
			joueurChoisi.remove(place-1);
			
			reloadJoueurChoisi();
			reloadJoueurAlea();
			setAlwaysOnTop(true);
		    setExtendedState(JFrame.MAXIMIZED_BOTH);
			setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
			repaint();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			JLabel j = (JLabel) e.getComponent();
			j.setForeground(Color.blue);
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			JLabel j = (JLabel) e.getComponent();
			j.setForeground(Color.black);
		}
		
	}
	
}
