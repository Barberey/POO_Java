package donnee.except;

import donnee.Case;
import donnee.Joueur;
/**
 * Classe de l'exception hors jeu
 * 
 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
 *
 */
public class HorsJeuException extends Exception{
	/**
	 * attribut hjPos la {@link Case} du joueur qui reçoit la passe et qui subi l'hors jeu
	 */
	private Case hjPos;
	/**
	 * Constructeur de la classe
	 * 
	 * @param att le joueur attaquant
	 */
	public HorsJeuException(Case hjPos) {
		super();
		this.hjPos = hjPos ;
	}
	/**
	 * Getter de {@link #hjPos}
	 * 
	 * @return att le joueur ayant fait hors jeu
	 */
	public Case getHJ() {
		return hjPos;
	}
}
