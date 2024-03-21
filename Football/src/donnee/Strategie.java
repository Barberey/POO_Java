package donnee;

import config.Config;
/**
 * Classe de donnee permettant de gerer la strategie de mouvement globale de l'equipe
 * 
 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
 *
 */
public class Strategie {
	
	/**
	 * Attribut definissant le positionnement de la ligne de defenseur
	 */
	private int colonneDefAct;
	/**
	 * Attribut definissant le positionnement minimum de la ligne de defenseur
	 */
	private int colonneDefMin;
	/**
	 * Attribut definissant le positionnement maximum de la ligne de defenseur
	 */
	private int colonneDefMax;
	
	
	/**
	 * Constructeur 
	 * 
	 * @param colonneDefAct {@link Integer} precisant la position des defenseurs au moment de le creation du gestionnaire
	 * @param cote {@link String} Precision sur le cote du terrain 
	 */
	public Strategie(int colonneDefAct, String cote) {
		this.colonneDefAct = colonneDefAct;
		if(cote.equals("G")) {
			colonneDefMin = colonneDefAct;
			colonneDefMax = Config.TERRAIN_WIDTH/2;
		}
		else {
			colonneDefMin = Config.TERRAIN_WIDTH/2;
			colonneDefMax = colonneDefAct;
		}
		
	}

	/**
	 * Methode verifiant la possibilite pour le bloc d'avancer sur le terrain
	 * 
	 * @return {@link Boolean} 
	 */
	public boolean avant() {
		return (colonneDefAct < colonneDefMax);
	}
	/**
	 * Methode verifiant la possibilite pour le bloc de reculer sur le terrain
	 * 
	 * @return {@link Boolean} 
	 */
	public boolean arriere() {
		return (colonneDefAct > colonneDefMin);
	}
	
	
	/**
	 * Getter de l'attribut {@link #colonneDefAct}
	 * 
	 * @return {@link Integer} representant la colonne actuel de la ligne de defense
	 */
	public int getColonneDefAct() {
		return colonneDefAct;
	}

	/**
	 * Methode permettant l'incrementation de la valeur de {@link #colonneDefAct} en utilisant {@link #avant()}
	 */
	public void increm() {
		if(avant()) {
			colonneDefAct+=1;
		}
	}
	/**
	 * Methode permettant le decrementation de la valeir de {@link #colonneDefAct} en utilisant {@link #arriere()}
	 */
	public void decrem() {
		if(arriere()) {
			colonneDefAct-=1;
		}
	}
	
	/**
	 * Getter de l'attribut {@link #colonneDefMin}
	 * 
	 * @return {@link Integer} representant la colonne minimale de la ligne de defense
	 */
	public int getColonneDefMin() {
		return colonneDefMin;
	}
	/**
	 * Setter de {@link #colonneDefMin}
	 * 
	 * @param colonneDefMin {@link Integer} remplacant la valeur de {@link #colonneDefMin}
	 */
	public void setColonneDefMin(int colonneDefMin) {
		this.colonneDefMin = colonneDefMin;
	}
	/**
	 * Getter de l'attribut {@link #colonneDefMax}
	 * 
	 * @return {@link Integer} representant la colonne maximale de la ligne de defense
	 */
	public int getColonneDefMax() {
		return colonneDefMax;
	}
	/**
	 * Setter de l'attribut {@link #colonneDefMax}
	 * 
	 * @param colonneDefMax {@link Integer} representant le colonne maximale pour le deplacement de la ligne de defense
	 */
	public void setColonneDefMax(int colonneDefMax) {
		this.colonneDefMax = colonneDefMax;
	}
	
	
}
