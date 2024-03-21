package donnee;
/**
 * Classe permettant d'initaliser la zone de deplacement d'un joueur
 * 
 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
 *
 */
public class ZoneDepl {
	/**
	 * attribut avant qui definie combien de case le joueur va vers l'avant
	 */
	private int avant;
	/**
	 * attribut arriere qui definie combien de case le joueur va vers l'arriere
	 */
	private int arriere;
	/**
	 * attribut haut qui definie combien de case le joueur va vers le haut
	 */
	private int haut;
	/**
	 * attribut bas qui definie combien de case le joueur va vers le bas
	 */
	private int bas;
	/**
	 * Constructeur de la classe
	 * 
	 * @param avant qui definie combien de case le joueur va vers l'avant
	 * @param arriere qui definie combien de case le joueur va vers l'arriere
	 * @param haut qui definie combien de case le joueur va vers le haut
	 * @param bas qui definie combien de case le joueur va vers le bas
	 */
	public ZoneDepl(int avant, int arriere, int haut, int bas) {
		this.avant = avant;
		this.arriere = arriere;
		this.haut = haut;
		this.bas = bas;
	}
	/**
	 * Getter de {@link #avant}
	 * 
	 * @return avant le nombre de case vers l'avant
	 */
	public int getAvant() {
		return avant;
	}
	/**
	 * Getter de {@link #arriere}
	 * 
	 * @return arriere le nombre de case vers l'arriere
	 */
	public int getArriere() {
		return arriere;
	}
	/**
	 * Getter de {@link #haut}
	 * 
	 * @return haut le nombre de case vers le haut
	 */
	public int getHaut() {
		return haut;
	}
	/**
	 * Getter de {@link #bas}
	 * 
	 * @return bas le nombre de case vers le bas
	 */
	public int getBas() {
		return bas;
	}
}
