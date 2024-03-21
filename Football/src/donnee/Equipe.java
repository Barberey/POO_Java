package donnee;

import java.util.ArrayList;
/**
 * Classe de donnee permettant l'initialisation d'un equipe 
 * 
 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
 *
 */
public class Equipe {
	/**
	 * attribut {@link ArrayList} de {@link Joueur} titulaires contenant tous les joueurs qui vont jouer sur le terrain
	 */
	private ArrayList<Joueur> titulaires;
	/**
	 * attribut {@link ArrayList} de {@link Joueur} remplacant contenant tous les joueurs qui ne vont pas jouer mais qui sont quand même cree
	 */
	private ArrayList<Joueur> remplacant;
	/**
	 * attribut {@link Strategie} strats l'emplacement des joueurs titulaires
	 */
	private Strategie strats;
	/**
	 * attribut {@link Composition} compo la composition que les joueurs titulaires vont jouer
	 */
	private Composition compo;
	/**
	 * attribut {@link String} img la chemin pour avoir la couleur de l'equipe
	 */
	private String img;
	/**
	 * attribut {@link Boolean} possesssion qui nous permet de savoir si l'equipe possede la balle ou non
	 */
	private Boolean possession;
	/**
	 * attribut {@link Joueur} cibleBallon qui le joueur ayant la balle
	 */
	private Joueur cibleBallon;
	/**
	 * Getter de {@link #cibleBallon}
	 * 
	 * @return cibleBallon {@link Joueur} le joueur ayant la balle
	 */
	public Joueur getCibleBallon() {
		return cibleBallon;
	}
	/**
	 * Setter de {@link #cibleBallon}
	 * 
	 * @param cibleBallon {@link Joueur} celui qui possedera la balle
	 */
	public void setCibleBallon(Joueur cibleBallon) {
		this.cibleBallon = cibleBallon;
	}
	/**
	 * Getter de {@link #possession}
	 * 
	 * @return possession {@link Boolean} renvoie vrai si l'equipe possede la balle faux sinon 
	 */
	public Boolean getPossession() {
		return possession;
	}
	/**
	 * Setter de {@link #possession}
	 * 
	 * @param possession {@link Boolean} donne la possession ou non de la balle
	 */
	public void setPossession(Boolean possession) {
		this.possession = possession;
	}
	/**
	 * Constructeur de la classe
	 * 
	 * @param titulaires {@link ArrayList} de {@link Joueur} ceux qui vont jouer
	 * @param remplacant {@link ArrayList} de {@link Joueur} ceux qui ne vont pas jouer 
	 * @param strats {@link Strategie} l'emplacement selon la compo
	 * @param compo	la {@link Composition} que l'utilisateur aura choisi pour son equipe
	 * @param img  {@link String} le chemin qui definira la couleur du joueur
	 */
	public Equipe(ArrayList<Joueur> titulaires, ArrayList<Joueur> remplacant, Strategie strats, Composition compo,
			String img) {
		this.titulaires = titulaires;
		this.remplacant = remplacant;
		this.strats = strats;
		this.compo = compo;
		this.img = img;
	}
	/**
	 * Getter de {@link #img}
	 * 
	 * @return img {@link String} qui sera le chemin vers la couleur de l'equipe
	 */
	public String getImg() {
		return img;
	}
	/**
	 * Getter de {@link #titulaires}
	 * 
	 * @return titulaires  {@link ArrayList} des {@link Joueur} qui seront sur le terrain
	 */
	public ArrayList<Joueur> getTitulaires() {
		return titulaires;
	}
	/**
	 * Setter de {@link #titulaires}
	 * 
	 * @param titulaires {@link ArrayList} comprenant les {@link Joueur} qui seront sur le terrain
	 */
	public void setTitulaires(ArrayList<Joueur> titulaires) {
		this.titulaires = titulaires;
	}
	/**
	 * Getter de {@link Strategie}
	 * 
	 * @return strats la {@link Strategie} que l'equipe utilisera
	 */
	public Strategie getStrats() {
		return strats;
	}
	/**
	 * Setter de {@link #strats}
	 * 
	 * @param strats la {@link Strategie} qui va etre donnee
	 */
	public void setStrats(Strategie strats) {
		this.strats = strats;
	}
	/**
	 * Getter de {@link #compo}
	 * 
	 * @return compo la composition que l'equipe va utiliser
 	 */
	public Composition getCompo() {
		return compo;
	}
	/**
	 * Setter de {@link #compo}
	 * 
	 * @param compo la {@link Composition} que l'equipe va utiliser
	 */
	public void setCompo(Composition compo) {
		this.compo = compo;
	}
	
	
}
