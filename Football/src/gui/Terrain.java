package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import donnee.Grille;
import moteur.Manager;
/**
 * Classe qui represente notre terrain
 * 
 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
 *
 */
public class Terrain extends JPanel{
	/**
	 * Attribut {@link Paint} qui permet le dessin de tous les elements
	 */
	private Paint paint;
	/**
	 * Attribut {@link Manager} qui nous permet de recuper tous les elements que l'ont doit afficher
	 */
	private Manager manag;
	/**
	 * Attribut {@link Grille} qui represente la grille de deplacement
	 */
	private Grille grille;
	/**
	 * Constructeur du terrain
	 * 
	 * @param grille
	 * @param manag
	 */
	public Terrain(Grille grille, Manager manag) {
		this.manag = manag;
		paint = new Paint();
		this.grille = grille;
	}
	
	/**
	 * Methode qui appelle dans l'ordre les elements a afficher
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		paint.paint(g2);
		//paint.paint(grille, g);
		
		paint.paint(manag.getEquipeG(), g);
		paint.paint(manag.getEquipeD(), g);
		
		paint.paint(manag.getBallon(), g2);
	}
}
