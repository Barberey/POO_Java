package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.jfree.chart.ChartPanel;

import chrono.Chronometer;
import config.Config;
import donnee.Ballon;
import donnee.Case;
import donnee.Composition;
import donnee.except.FinJeuException;
import donnee.except.MiTempsException;
import donnee.Grille;
import donnee.Joueur;
import gui.ChoixDesEquipes.GestBoutFen;
import gui.chart.ChartManager;
import moteur.Build;
import moteur.Manager;
/**
 * Classe permettant l'affichage de notre simulation de match de foot
 * 
 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
 *
 */
public class MainGui extends JFrame implements Runnable{
	
	private JTextArea textAction;
	
	/**
	 * attribut grille la grille du terrain
	 */
	private Grille grille;
	/**
	 * terrain le terrain sur lequelle nos joueurs vont jouer
	 */
	private Terrain terrain;
	/**
	 * attribut manag le manager
	 */
	private Manager manag;
	/**
	 * attribut chrono le chronometre
	 */
	private Chronometer chrono = new Chronometer();
	/**
	 * attribut minute l'affichage des minutes
	 */
	private JLabel minute = new JLabel("00");
	/**
	 * attribut deup l'affichage de deux points entre les minutes et les secondes
	 */
	private JLabel deup = new JLabel(":");
	/**
	 * attribut seconde l'affichage des secondes
	 */
	private JLabel seconde = new JLabel("00");
	/**
	 * attribut butG l'affichage des buts pour l'equipe de gauche
	 */
	private JLabel butG = new JLabel("0");
	/**
	 * attribut dp les deux points entre les deux buts
	 */
	private JLabel dp = new JLabel(":");
	/**
	 * attribut butD l'affichage des buts pour l'equipe de droite
	 */
	private JLabel butD = new JLabel("0");
	/**
	 * attribut bool un booleen
	 */
    private Boolean bool = true;
	/**
	 * attribut contenu contenant le contenu de la frame
	 */
	private Container contenu = getContentPane();
	
	private JPanel score = new JPanel();
	/**
	 * Constructeur de la classe
	 * 
	 * @param titre le titre de la frame
	 * @param compo la composition de notre equipe
	 * @param joueurs la liste des joueurs chosi pour notre equipe
	 * @param equipeD la couleur de l'equipe droite
	 * @param equipeG la couleur de l'equipe de gauche
	 */
	public MainGui(String titre, Composition compo, ArrayList<Joueur> joueurs, String equipeD, String equipeG) {
		super(titre);
		setAlwaysOnTop(true);
	    setExtendedState(JFrame.MAXIMIZED_BOTH);
		setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
		setLayout(null);
		
		grille = Build.creeGrille();
		manag = new Manager(grille, compo, joueurs, equipeD, equipeG,chrono);
		
		ChartManager chart = new ChartManager(manag.getEquipeG(), manag.getEquipeD());
		ChartPanel typeCountBar = new ChartPanel(chart.getTypeCountBarStatsEquipes());
		typeCountBar.setBounds(Config.TERRAIN_WIDTH*Config.BLOCK_SIZE+100, 0, 300, 600);
		contenu.add(typeCountBar);
		
		minute.setFont(new Font("Serif", Font.BOLD, 100));
		deup.setFont(new Font("Serif", Font.BOLD, 50));
		seconde.setFont(new Font("Serif", Font.BOLD, 100));
		JPanel horloge = new JPanel();
		horloge.add(minute);
		horloge.add(deup);
		horloge.add(seconde);
		horloge.setBounds(((Config.TERRAIN_WIDTH*Config.BLOCK_SIZE)/2)+150, Config.TERRAIN_HEIGHT*Config.BLOCK_SIZE+50, 300, 125);
		contenu.add(horloge);

		terrain = new Terrain(grille, manag);
		terrain.setBounds(0, 0, Config.TERRAIN_WIDTH*Config.BLOCK_SIZE, Config.TERRAIN_HEIGHT*Config.BLOCK_SIZE);
		contenu.add(terrain);
		
		textAction = new JTextArea();
		textAction.setBounds(0, Config.TERRAIN_HEIGHT*Config.BLOCK_SIZE+50, 300, 125);
		contenu.add(textAction);
		
		butG.setFont(new Font("Serif", Font.BOLD, 100));
		dp.setFont(new Font("Serif", Font.BOLD, 50));
		butD.setFont(new Font("Serif", Font.BOLD, 100));
		score.add(butG);
		score.add(dp);
		score.add(butD);
		score.setBounds(((Config.TERRAIN_WIDTH*Config.BLOCK_SIZE)/2) - 100, Config.TERRAIN_HEIGHT*Config.BLOCK_SIZE+50, 200, 125);
		contenu.add(score);
		
		ImageIcon imageIcon = new ImageIcon("src/ressources/fondMatch.jpg"); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(Config.WINDOW_WIDTH+100, Config.WINDOW_HEIGHT+100,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		imageIcon = new ImageIcon(newimg);  // transform it back
		JLabel jLabel = new JLabel();
		jLabel.setIcon(imageIcon);
		jLabel.setBounds(0, 0, Config.WINDOW_WIDTH+100, Config.WINDOW_HEIGHT+100);
		contenu.add(jLabel);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
	/**
	 * Methode permettant d'executer notre simulation
	 */
	@Override
	public void run() {
        while (bool) {
            if(manag.getJeu() || manag.getCoupFrancs()) {
                try {
                    Thread.sleep(Config.GAME_SPEED);

                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                if(manag.getCoupFrancs()) {
                	if(manag.getVariable() == 1 || manag.getVariable()==0) {
                		manag.advance();
                    	updateAction();
                        try {
    						Thread.sleep(4000);
    					} catch (InterruptedException e) {
    						e.printStackTrace();
    					}
                	}
                	else if(manag.getVariable() == 2) {
                		Ballon ballon = manag.getBallon();
                		while(ballon.getPosition().getColonne()!=0 && ballon.getPosition().getColonne()!=Config.TERRAIN_WIDTH-1) {
							if (ballon.getPosition().getColonne()>150) {
		                        if (ballon.getPosition().getLigne()>Config.TERRAIN_HEIGHT/2) {
		                            Case newPose = new Case(ballon.getPosition().getLigne()+1,ballon.getPosition().getColonne()+1);
		                            ballon.setPosition(newPose);
		                        } else {
		                            Case newPose = new Case(ballon.getPosition().getLigne()-1,ballon.getPosition().getColonne()+1);
		                            ballon.setPosition(newPose);
		                        }
		                        
		                    } else if (ballon.getPosition().getColonne()<150) {
		                        if (ballon.getPosition().getLigne()>Config.TERRAIN_HEIGHT/2) {
		                            Case newPose = new Case(ballon.getPosition().getLigne()+1,ballon.getPosition().getColonne()-1);
		                            ballon.setPosition(newPose);
		                        } else {
		                            Case newPose = new Case(ballon.getPosition().getLigne()-1,ballon.getPosition().getColonne()-1);
		                            ballon.setPosition(newPose);
		                        }
		                    }
							terrain.repaint();
							try {
								Thread.sleep(50);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
		                }
                		manag.advance();
                		try {
    						Thread.sleep(4000);
    					} catch (InterruptedException e) {
    						e.printStackTrace();
    					}
                	}
                	
                	
            	}
                else {
                	manag.advance();
                	updateAction();
                    terrain.repaint();
                }
                
                try {
                    chrono.increment();
                    updateScore();
                } catch (FinJeuException e) {
                    bool = false;
                    try {
						Thread.sleep(3000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					dispose();
					try {
						ApresJeu aj = new ApresJeu("Statistique",manag.getStatsMatch(),manag.getActions(), score);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
                    
                } catch (MiTempsException e) {
					manag.setJeu(false);
				}
                updateChronoValue();
            }
            else {
                manag.advance();
                updateAction();
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
	
	private void updateAction() {
		ArrayList<String> actions = manag.getActions();
		textAction.setText("");
		Stack<String> pile = new Stack<>();
		for(String s : actions) {
			pile.push(s);
		}
		for(int i = 0; i<pile.size();i++) {
			String s = pile.pop();
			textAction.append(s);
		}
		textAction.repaint();
	}
	
	/**
	 * Methode permettant l'actualisation des scores lors du match
	 */
	private void updateScore() {
		butG.setText(Integer.toString(manag.getButG()));
		butD.setText(Integer.toString(manag.getButD()));
	}
	/**
	 * Methode permettant l'actualisation du chronometre lors du match
	 */
	private void updateChronoValue() {
		int min = chrono.getMinute().getValue();
		int sec = chrono.getSecond().getValue();
		seconde.setText(chrono.transform(sec));
		minute.setText(chrono.transform(min));
		
	}
}