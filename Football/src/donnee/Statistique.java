package donnee;
/**
 * Classe de donnee permettant d'initialiser les statistiques d'un joueur
 * 
 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
 *
 */
public class Statistique {
	/**
	 * attribut def la defense du joueur
	 */
	private int def;
	/**
	 * attribut tir le tir du joueur
	 */
	private int tir;
	/**
	 * attribut passe la passe du joueur
	 */
	private int passe;
	/**
	 * attribut zoneAction la zone d'action du joueur
	 */
	private int zoneAction;
	/**
	 * attribut endurance l'endurance du joueur
	 */
	private int endurance;
	/**
	 * attribut vitesse la vitesse du joueur
	 */
	private int vitesse;
	/**
	 * attribut arret l'aarret du joueur
	 */
	private int arret;
	/**
	 * Constructeur de la classe
	 * 
	 * @param def la defense du joueur
	 * @param tir le tir du joueur
	 * @param passe la passe du joueur
	 * @param zoneAction la zone d'action du joueur
	 * @param endurance l'endurance du joueur
	 * @param vitesse la vitesse du joueur 
	 * @param arret l'arret du joueur
	 */
	public Statistique(int def, int tir, int passe, int zoneAction, int endurance, int vitesse, int arret) {
		this.def = def;
		this.tir = tir;
		this.passe = passe;
		this.zoneAction = zoneAction;
		this.endurance = endurance;
		this.vitesse = vitesse;
		this.arret = arret;
	}
	/**
	 * Methode permettant d'afficher les statistiques d'un joueur
	 */
	@Override
	public String toString() {
		return "Statistique [def=" + def + ", tir=" + tir + ", passe=" + passe + ", zoneAction=" + zoneAction
				+ ", endurance=" + endurance + ", vitesse=" + vitesse + "]";
	}
	/**
	 * Gettere de {@link #def}
	 * 
	 * @return def la defense du joueur
	 */
	public int getDef() {
		return def;
	}
	/**
	 * Getter de {@link #tir}
	 * 
	 * @return tir le tir du joueur 
	 */
	public int getTir() {
		return tir;
	}
	/**
	 * Getter de {@link #passe}
	 * 
	 * @return passe la passe du joueur 
	 */
	public int getPasse() {
		return passe;
	}
	/**
	 * Getter de {@link #zoneAction}
	 * 
	 * @return zoneAction la zone d'action du joueur 
	 */
	public int getZoneAction() {
		return zoneAction;
	}
	/**
	 * Getter de {@link #endurance}
	 * 
	 * @return endurance l'endurance du joueur 
	 */
	public int getEndurance() {
		return endurance;
	}
	/**
	 * Getter de {@link #vitesse}
	 * 
	 * @return vitesse la vitesse du joueur 
	 */
	public int getVitesse() {
		return vitesse;
	}
	/**
	 * Getter de {@link #arret}
	 * 
	 * @return arret le arret du joueur 
	 */
	public int getArret() {
		return arret;
	}
	
	
}
