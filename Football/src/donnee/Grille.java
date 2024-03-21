package donnee;
/**
 * Classe de donnee permettant d'initialiser une grille
 * 
 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
 *
 */
public class Grille {
	/**
	 * attribut  grille de {@link Case} qui sera la grille sur la quelle notre terrain sera
	 */
	private Case[][] grille;
	/**
	 * attribut {@link Integer} ligneMax qui sera la ligne maximum que notre grille va avoir 
	 */
	private int ligneMax;
	/**
	 * attribut {@link Integer} colonneMax qui sera la colonne maximum que notre grille va avoir
	 */
	private int colonneMax;
	/**
	 * Constructeur de la classe
	 * 
	 * @param ligneMax la ligne maximum de la grille
	 * @param colonneMax la colonne maximum de la grille
	 */
	public Grille(int ligneMax, int colonneMax) {
		this.ligneMax =ligneMax;
		this.colonneMax = colonneMax;
		
		grille = new Case[ligneMax][colonneMax];
		for(int i = 0; i<ligneMax; i++) {
			for(int j = 0; j<colonneMax; j++) {
				grille[i][j] = new Case(i, j);
			}
		}
	}
	/**
	 * Getter de {@link #grille}
	 * 
	 * @return grille de {@link Case} qui sera la grille sur laquelle notre terrain sera
	 */
	public Case[][] getGrille() {
		return grille;
	}
	/**
	 * Getter de {@link #ligneMax}
	 * 
	 * @return ligneMax la ligne maximum
	 */
	public int getLigneMax() {
		return ligneMax;
	}
	/**
	 * Getter de {@link #colonneMax}
	 * 
	 * @return colonneMax la colonne maximum
	 */
	public int getColonneMax() {
		return colonneMax;
	}
	/**
	 * Methode permettant de recuperer une {@link Case} a partir de la {@link #grille}
	 * 
	 * @param ligne la ligne de la grille
	 * @param colonne la colonne de grille
	 * @return la case recuperer grace a la ligne et a la colonne
	 */
	public Case getCase(int ligne, int colonne) {
		return grille[ligne][colonne];
	}
}
