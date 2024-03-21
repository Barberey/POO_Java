package donnee;
/**
 * Classe de donnee pour initialiser un ballon
 * 
 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
 *
 */
public class Ballon {
	/**
	 * Attribut {@link Case} permettant de positionner le ballon
	 */
	private Case position;
	/**
	 * Attribut {@link Joueur} permettant de savoir qui possède le ballon
	 */
	private Joueur joueur;
	/**
	 * Attribut {@link Integer} permettant de connaître le coef de la balle lors d'une passe
	 */
	private int coeff;
	/**
	 * Constructeur de la classe
	 * 
	 * @param position {@link Case} position du ballon
	 * @param joueur {@link Joueur} le joueur ayant le ballon
	 */
	public Ballon(Case position, Joueur joueur) {
		this.position = position;
		this.joueur = joueur;
	}
	/**
	 * Methode permettant de recuperer le joueur ayant le ballon
	 * 
	 * @return joueur {@link Joueur} qui possède la balle
	 */
	public Joueur getJoueur() {
		return joueur;
	}
	/**
	 * Methode permettant d'attribuer au ballon un joueur
	 * 
	 * @param joueur {@link Joueur} qui possedera la balle
	 */
	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}
	/**
	 * Methode permettant de positionner le ballon
	 * 
	 * @param position {@link Case} où le ballon sera positionner
	 */
	public void setPosition(Case position) {
		this.position = position;
	}
	/**
	 * Methode permettant de recuperer la position du ballon
	 * 
	 * @return position la {@link Case} position du ballon
	 */
	public Case getPosition() {
		return position;
	}
	/**
	 * Methode permettant de recuperer le coeff du ballon
	 * 
	 * @return coeff l'entier {@link Integer} coefficient de la trajectoire du ballon
	 */
	public int getCoeff() {
		return coeff;
	}
	/**
	 * Methode qui permet de d'attribuer le coeff au ballon
	 * 
	 * @param coeff l'entier {@link Integer} coefficient qui est attribue au ballon
	 */
	public void setCoeff(int coeff) {
		this.coeff = coeff;
	}
	
	
}
