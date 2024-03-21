package donnee.action;

import donnee.Ballon;
import donnee.Case;
import donnee.Grille;
import donnee.Joueur;
import donnee.except.FauteException;
import donnee.except.InterceptException;
import moteur.visitor.ActionVisitor;
/**
 * Classe permettant d'initialiser l'action interception
 * 
 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
 *
 */
public class Interception implements Action{
	/**
	 * attribut acteur qui sera l'acteur principale de l'action le joueur qui fait l'interception
	 */
	private Joueur acteur;
	/**
	 * attribut ballon le ballon du match
	 */
	private Ballon ballon;
	/**
	 * attrbut passe qui est le joueur qui fait la passe
	 */
	private Joueur passe;
	/**
	 * Constructeur de la classe
	 * 
	 * @param acteur l'acteur de l'action
	 * @param ballon le ballon de l'action
	 * @param passe le passeur de l'action
	 */
	public Interception(Joueur acteur, Ballon ballon, Joueur passe) {
		super();
		this.acteur = acteur;
		this.ballon = ballon;
		this.passe = passe;
	}
	/**
	 * Methode permettant de recuperer le ballon
	 * 
	 * @return ballon le ballon de l'action
	 */
	public Ballon getBallon() {
		return ballon;
	}
	/**
	 * Methode permettant d'initialiser le ballon
	 * 
	 * @param ballon le ballon du match
	 */
	public void setBallon(Ballon ballon) {
		this.ballon = ballon;
	}
	/**
	 * Methode permettant d'initialisez l'acteur de l'action
	 * 
	 * @param acteur qui est l'acteur de l'action
	 */
	public void setActeur(Joueur acteur) {
		this.acteur = acteur;
	}
	/**
	 * Methode permettant de recuperer le joueur qui fait la passe
	 * 
	 * @return passe le passeur de l'action
	 */
	public Joueur getPasse() {
		return passe;
	}
	/**
	 * Methode permettant de recuperer l'acteur
	 */
	@Override
	public Joueur getActeur() {
		return acteur;
	}
	/**
	 * Methode permettant d'appeler le visiteur
	 */
	@Override
	public <T> T accept(ActionVisitor<T> visitor) throws InterceptException, FauteException {
		try {
			return visitor.visit(this);
		} catch (InterceptException e) {
			throw new InterceptException();
		} catch (FauteException e) {
			throw new FauteException(e.getFaute());
		}
	}
	/**
	 * Methode permettant de recuperer l'affichage de l'action
	 */
	public String toString() {
		return "Inter";
	}
}
