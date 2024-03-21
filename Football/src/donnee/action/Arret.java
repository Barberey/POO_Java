package donnee.action;

import donnee.Ballon;
import donnee.Joueur;
import donnee.except.CornerException;
import donnee.except.InterceptException;
import moteur.visitor.ActionVisitor;
/**
 * Classe d'action Arret qui initialise l'action arret
 * 
 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
 *
 */
public class Arret implements Action {
	/**
	 * attribut acteur l'acteur principale de l'action
	 */
	private Joueur acteur;
	/**
	 * attribut ballon le ballon
	 */
	private Ballon ballon;
	/**
	 * Constructeur de la classe
	 * 
	 * @param acteur l'acteur de l'action
	 * @param ballon le ballon
	 */
	public Arret(Joueur acteur, Ballon ballon) {
		super();
		this.acteur = acteur;
		this.ballon = ballon;
	}
	/**
	 * Methode permettant de recuperer le ballon
	 * 
	 * @return ballon le ballon
	 */
	public Ballon getBallon() {
		return ballon;
	}
	/**
	 * Methode permettant d'attribuer le ballon
	 * 
	 * @param ballon le ballon
	 */
	public void setBallon(Ballon ballon) {
		this.ballon = ballon;
	}
	/**
	 * Methode permettant de recuperer l'acteur de l'action
	 */
	@Override
	public Joueur getActeur() {
		return acteur;
	}
	/**
	 * Methode permettant d'appeler le visiteur
	 */
	@Override
	public <T> T accept(ActionVisitor<T> visitor) throws InterceptException, CornerException {
			return visitor.visit(this);
	}
	/**
	 * Methode permettant d'afficher l'action
	 */
	public String toString() {
		return "Arret";
	}


}
