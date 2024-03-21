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

import config.Config;
import donnee.Joueur;
import moteur.Build;
import moteur.Utility;
/**
 * Classe permettant l'affichage de la premierer frame
 * 
 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
 *
 */
public class IHM extends JFrame {
	/**
	 * attribut contain le contenant du contenu de la frame
	 */
	Container contain = getContentPane();
	/**
	 * Constructeur de la frame
	 * 
	 * @param title le titre de la frame
	 * @throws IOException
	 */
	public IHM(String title) throws IOException {
		super(title);
        setAlwaysOnTop(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
		setSize(Config.WINDOW_WIDTH, Config.TERRAIN_HEIGHT);
		setLayout(null);
		
		JLabel label = new JLabel("FOOTBALL");
		label.setFont(new Font("Serif", Font.BOLD, 100));
		label.setBounds((int) (Config.WINDOW_WIDTH/2.9), Config.WINDOW_HEIGHT/8, 1000, 100);
		label.setForeground(Color.white);
		contain.add(label);

		JButton play = new JButton("Jouer");
		play.setBounds((int) (Config.WINDOW_WIDTH/2.14),Config.WINDOW_HEIGHT/2 -100,200,50);
		play.setBackground(Color.CYAN);
		play.addActionListener(new GestBout());
		play.setActionCommand("play");
		contain.add(play);
		
		JButton rules = new JButton("Règles");
		rules.setBounds((int) (Config.WINDOW_WIDTH/2.14),Config.WINDOW_HEIGHT/2,200,50);
		rules.setBackground(Color.CYAN);
		rules.addActionListener(new GestBout());
		rules.setActionCommand("rules");
		contain.add(rules);
		
		JButton leave = new JButton("Quitter");
		leave.setBounds((int) (Config.WINDOW_WIDTH/2.14),Config.WINDOW_HEIGHT/2 + 100,200,50);
		leave.setBackground(Color.CYAN);
		leave.addActionListener(new GestBout());
		leave.setActionCommand("leave");
		contain.add(leave);
		
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
	 * Classe permettant l'attribution d'action aux boutons
	 * 
	 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
	 *
	 */
    public class GestBout implements ActionListener {
    	/**
    	 * Methode permettant l'attribution des actions
    	 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("play")) {
				dispose();
	        	ChoixDesEquipes cde = new ChoixDesEquipes("Choix Equipes");
	        }
	        else if (e.getActionCommand().equals("rules")) {
	        	dispose();
	        	Regles rules = new Regles("Regles");
	        }
	        else if (e.getActionCommand().equals("leave")) {
	        	System.exit(0);
	        }
		}
        
    }
}
