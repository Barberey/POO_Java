package donnee.action;

import donnee.Ballon;
import donnee.Case;
import donnee.Grille;
import donnee.Joueur;
import donnee.except.HorsJeuException;
import moteur.visitor.ActionVisitor;
/**
 * Classe permettant l'initialisation de l'action passe
 * 
 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
 *
 */
public class Passe implements Action {
	/**
	 * attribut cible celui que reçoit la passe
	 */
	private Joueur cible;
	/**
	 * attribut acteur l'acteur de l'action le passeur ici
	 */
	private Joueur acteur;
	/**
	 * attribut ballon le ballon de l'action
	 */
	private Ballon ballon;
	/**
	 * attribut grille la grille du terrain
	 */
	private Grille grille;
	/**
	 * Constructeur de la classe
	 * 
	 * @param cible le joueur cible pour la passe
	 * @param posCible la case qui sera l'endroit ou la passe sera effectuer
	 * @param acteur le passeur
	 * @param ballon le ballon du match
	 * @param grille la grille qui est le terrain
	 */
	public Passe(Joueur cible,Case posCible, Joueur acteur, Ballon ballon, Grille grille) {
		this.cible = cible;
		this.acteur = acteur;
		this.ballon = ballon;
		this.grille = grille;
	}
	/**
	 * Methode permettant de recuperer la grille
	 * 
	 * @return grille la grille
	 */
	public Grille getGrille() {
		return grille;
	}
	/**
	 * Methode permettant de recuperer la cible 
	 * 
	 * @return cible le joueur cible pour la passe
	 */
	public Joueur getCible() {
		return cible;
	}
	/**
	 * Methode permettant de recuperer le ballon
	 * 
	 * @return ballon le ballon du match
	 */
	public Ballon getBallon() {
		return ballon;
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
	public <T> T accept(ActionVisitor<T> visitor) throws HorsJeuException {
		try {
			return visitor.visit(this);
		} catch (HorsJeuException e) {
			throw new HorsJeuException(e.getHJ());
		}
	}
	/**
	 * Methode permettant d'afficher via une string la passe
	 */
	public String toString() {
		return "Passe: "+cible+" "+acteur;
	}
}
