package donnee.action;

import donnee.except.*;
import donnee.Ballon;
import donnee.Case;
import donnee.Grille;
import donnee.Joueur;
import moteur.visitor.ActionVisitor;
/**
 * Classe permettant d'initialiser l'action tir
 * 
 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
 *
 */
public class Tir implements Action{
	/**
	 * attribut acteur l'acteur de l'action qui sera le gardien
	 */
	private Joueur acteur;
	/**
	 * attribut ballon le ballon du match
	 */
	private Ballon ballon;
	/**
	 * attribut grille la grille qui represente le terrain
	 */
	private Grille grille;
	/**
	 * attribut cible la case ou le tireur va viser
	 */
	private Case cible;
	/**
	 * attribut arret si le gardien l'a arretee vrai sinon faux
	 */
	private Boolean arret = false;
	/**
	 * Constructeur de la classe
	 * 
	 * @param gardien l'acteur de l'action
	 * @param ballon le ballon du match
	 * @param gille la grille representant le terrain
	 * @param cible la case ou le tireur vise
	 */
	public Tir (Joueur gardien, Ballon ballon, Grille gille, Case cible) {
		this.acteur = gardien;
		this.ballon = ballon;
		this.grille = gille;
		this.cible = cible;
	}
	/**
	 * Methode permettant de recuperer la case ou le tireur va tirer
	 * 
	 * @return cible la case ou la balle arrive
	 */
	public Case getCible() {
		return cible;
	}
	/**
	 * Methode permettant d'initialiser la case on le tir va
	 * 
	 * @param cible la case viser
	 */
	public void setCible(Case cible) {
		this.cible = cible;
	}
	/**
	 * Methode permettant de recuperer le ballon du match
	 * 
	 * @return ballon le ballon du match
	 */
	public Ballon getBallon() {
		return ballon;
	}
	/**
	 * Methode permettant d'initialiser le ballon
	 * 
	 * @param ballon qui sera initialiser
	 */
	public void setBallon(Ballon ballon) {
		this.ballon = ballon;
	}
	/**
	 * Methode permettant de recuperer la grille du terrain
	 * 
	 * @return grille la grille du terrain
	 */
	public Grille getGrille() {
		return grille;
	}
	/**
	 * Methode permettant d'initialiser la grille
	 * 
	 * @param grille la grille du terrain
	 */
	public void setGrille(Grille grille) {
		this.grille = grille;
	}
	/**
	 * Methode permetttant de recuperer l'acteur
	 */
	@Override
	public Joueur getActeur() {
		return acteur;
	}
	/**
	 * Methode permettant de recuperer si le tir a ete arrete
	 * 
	 * @return arret vrai si l'arret a ete realiser faux sinon
	 */
	public Boolean getArret() {
		return arret;
	}
	/**
	 * Methode permettant d'initialiser l'arret
	 * 
	 * @param arret si l'arret a ete fait ou non
	 */
	public void setArret(Boolean arret) {
		this.arret = arret;
	}
	/**
	 * Methode permettant d'appeler le visiteur
	 */
	public <T> T accept(ActionVisitor<T> visitor) throws ButException, ArretException, SixMetreException {
		try {
			return visitor.visit(this);
		} catch (ButException e) {
			throw new ButException();
		} catch (ArretException e) {
			throw new ArretException(e.getAction());
		} catch (SixMetreException e) {
			throw new SixMetreException();
		}
	}
	/**
	 * Methode permettant de recuperer la string de l'action
	 */
	public String toString() {
		return "Tir";
	}
}
