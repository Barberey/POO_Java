package moteur.manag;

import donnee.Equipe;

/**
 * Classe abstraite pour les deux managers
 * 
 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
 *
 */
public abstract class ManagAction {
	/**
	 * Attribute de type {@link Equipe} representant la premiere equipe
	 */
	private Equipe equipe1;
	/**
	 * Attribute de type {@link Equipe} representant la seconde equipe
	 */
	private Equipe equipe2;
	/**
	 * Attribut de type {@link Integer} permettant de preciser quelle equipe a le ballon
	 */
	private int poss;

	/**
	 * Constructeur de la classe
	 * 
	 * @param equipe1 {@link Equipe} pour la premiere equipe
	 * @param equipe2 {@link Equipe} pour la seconde equipe
	 * @param poss {@link Integer} pour la possession
	 */
	public ManagAction(Equipe equipe1, Equipe equipe2, int poss) {
		this.equipe1 = equipe1;
		this.equipe2 = equipe2;
		this.poss = poss;
	}

	/**
	 * Geeter de {@link #equipe1}
	 * 
	 * @return {@link #equipe1}
	 */
	public Equipe getEquipe1() {
		return equipe1;
	}
	/**
	 * Getter de {@link #equipe2}
	 * 
	 * @return {@link #equipe2}
	 */
	public Equipe getEquipe2() {
		return equipe2;
	}
	/**
	 * Getter de {@link #poss}
	 * 
	 * @return {@link #poss}
	 */
	public int getPoss() {
		return poss;
	}
	/**
	 * Methode gerant le changement de possession
	 * 
	 */
	public void changePoss() {
		if(poss==1) {
			poss =2;
		}
		else {
			poss = 1;
		}
	}
}
