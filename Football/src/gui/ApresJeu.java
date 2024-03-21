package gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartPanel;

import config.Config;
import donnee.Joueur;
import gui.IHM.GestBout;
import gui.chart.ChartManager;
import moteur.Build;
import moteur.Utility;
/**
 * Classe qui permet l'affichage après que la simulation soit terminee
 * 
 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
 *
 */
public class ApresJeu extends JFrame {
	/**
	 * attribut contain qui est le contenu de la frame
	 */
	private Container contain = getContentPane();
	/**
	 * Attribut {@link HashMap} qui rassemble les differentes actions realiser par les deuc equipes
	 */
	private ArrayList<HashMap<String, Integer>> array;
	/**
	 * Attribut {@link ArrayList} liste complete des actions  
	 */
	private ArrayList<String> actions = new ArrayList<>();
	/**
	 * attribut de type {@link JTextArea} textAction qui est le contenant de nos actions
	 */
	private JTextArea textAction;
	
	/**
	 * Constructeur de la classe
	 * 
	 * @param title le titre de la frame
	 * @throws IOException
	 */
	public ApresJeu (String title, ArrayList<HashMap<String, Integer>> array, ArrayList<String> actions, JPanel score) throws IOException{
		super(title);
		this.array = array;
		this.actions = actions;
        setAlwaysOnTop(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
		setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
		setLayout(null);
		
		JLabel label = new JLabel("Statistique");
		label.setFont(new Font("Serif", Font.BOLD, 100));
		label.setBounds((int) (Config.WINDOW_WIDTH/2.7), Config.WINDOW_HEIGHT/8, 1000, 100);
		label.setForeground(Color.white);
		contain.add(label);

		JButton play = new JButton("Rejouer");
		play.setBounds((int) (Config.WINDOW_WIDTH/2.14),Config.WINDOW_HEIGHT/2 -100,200,50);
		play.setBackground(Color.CYAN);
		play.addActionListener(new GestBout());
		play.setActionCommand("replay");
		contain.add(play);
		
		JButton rules = new JButton("Menu");
		rules.setBounds((int) (Config.WINDOW_WIDTH/2.14),Config.WINDOW_HEIGHT/2,200,50);
		rules.setBackground(Color.CYAN);
		rules.addActionListener(new GestBout());
		rules.setActionCommand("menu");
		contain.add(rules);
		
		//ajouter statistiques
		contain.add(initStat());
		
		score.setBounds(((Config.WINDOW_WIDTH)*7/8)-50, Config.WINDOW_HEIGHT/3, 200, 125);
		contain.add(score);
		
		textAction = new JTextArea();
		textAction.setBounds((int) (Config.WINDOW_WIDTH/8)-100, Config.WINDOW_HEIGHT/10, 200, 600);
		updateAction();
		contain.add(textAction);
		
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
	 * Methode qui initialise le panneaux pour les stats
	 * 
	 * @return {@link ChartPanel} qui contient le graphique de stats
	 */
	private ChartPanel initStat() {
		ChartManager charts = new ChartManager(array);
		ChartPanel typeCountBar = new ChartPanel(charts.getTypeStackedBarStatsMatch());
		typeCountBar.setBounds((int) (Config.WINDOW_WIDTH/2.14 - 300), Config.WINDOW_HEIGHT*3/4 , 800, 200);
		return typeCountBar;
		
	}
	/**
	 * Methode qui update les actions realiser pendant le match
	 */
	private void updateAction() {
		textAction.setText("");
		for(int i = 0; i<actions.size();i++) {
			String s = actions.get(i);
			textAction.append(s);
		}
		textAction.repaint();
	}
	
	
	/**
	 * Classe pour le fonctionnement des boutons
	 * 
	 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
	 *
	 */
    public class GestBout implements ActionListener {
    	/**
    	 * Methode permettant de decrire ce que fait chaque bouton
    	 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("replay")) {
				dispose();
	        	ChoixDesEquipes cde = new ChoixDesEquipes("Choix Equipes");
	        }
	        else if (e.getActionCommand().equals("menu")) {
	        	dispose();
	        	try {
					IHM menu = new IHM("FOOTBALL");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
	        }
		}
	}
}