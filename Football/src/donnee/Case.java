package donnee;
/**
 * Classe de donnee initialisant une case 
 * 
 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
 *
 */
public class Case {
	/**
	 * attribut {@link Integer} ligne un entier qui sera l'axe x par rapport a la grille
	 */
	private int ligne;
	/**
	 * attribut {@link Integer} colonne un entier qui sera l'axe y par rapport a la grille
	 */
	private int colonne;
	/**
	 * Constructeur de la classe
	 * 
	 * @param ligne la ligne de la case sous forme de {@link Integer}t
	 * @param colonne la colonne de la case sous forme de {@link Integer}
	 */
	public Case(int ligne, int colonne) {
		this.ligne = ligne;
		this.colonne = colonne;
	}
	/**
	 * Methode permettant de recuperer {@link #ligne}
	 * 
	 * @return ligne {@link Integer} la ligne de la case
	 */
	public int getLigne() {
		return ligne;
	}
	/**
	 * Methode permettant de recuperer {@link #colonne}
	 * 
	 * @return colonne {@link Integer} la colonne de la case
	 */
	public int getColonne() {
		return colonne;
	}
	/**
	 * Methode permettant de savoir si une case est egale a une autre case
	 * 
	 * @param pos qui sera la {@link Case} que l'on va comparer
	 * @return un {@link Boolean} qui est vrai si c'est egale faux sinon
	 */
	public Boolean equals(Case pos) {
		return pos.getColonne() == colonne && pos.getLigne() == ligne;
	}
}
