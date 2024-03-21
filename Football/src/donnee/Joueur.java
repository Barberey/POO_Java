package donnee;
/**
 * Classe de donnee permettant d'initialiser un joueur
 * 
 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
 *
 */
public class Joueur {
	/**
	 * attribut {@link Case} position la position actuelle du joueur
	 */
	private Case position;
	/**
	 * attribut {@link Case} posTact la position dans la composition du joueur
	 */
	private Case posTact;
	/**
	 * attribut {@link String} nom le nom du joueur
	 */
	private String nom;
	/**
	 * attribut {@link String} prenom le prenom du joueur
	 */
	private String prenom;
	/**
	 * attribut {@link Statistique} stats les statistiques du joueur
	 */
	private Statistique stats;
	/**
	 * attribut {@link String} arche l'archetype du joueur
	 */
	private String arche;
	/**
	 * attribut {@link String} poste le poste du joueur
	 */
	private String poste;
	/**
	 * attribut {@link ZoneDepl} zonedpl la zone de deplacement du joueur
	 */
	private ZoneDepl zonedpl;
	/**
	 * Getter de {@link #zonedpl}
	 * 
	 * @return zonedpl la zone de deplacement du joueur
	 */
	public ZoneDepl getZonedpl() {
		return zonedpl;
	}
	/**
	 * Setter de {@link #zonedpl}
	 * 
	 * @param zonedpl la zone de deplacement que le joueur recevra
	 */
	public void setZonedpl(ZoneDepl zonedpl) {
		this.zonedpl = zonedpl;
	}
	/**
	 * attribut {@link Boolean} ballon permettant de savoir si le joueur possede le ballon ou non
	 */
	private Boolean ballon = false;
	/**
	 * Getter de {@link #ballon}
	 * 
	 * @return ballon vrai si le joueur possede le ballon faux sinon
	 */
	public Boolean getBallon() {
		return ballon;
	}
	/**
	 * Setter de {@link #ballon}
	 * 
	 * @param ballon le ballon qui sera attribue
	 */
	public void setBallon(Boolean ballon) {
		this.ballon = ballon;
	}
	/**
	 * Constructeur de la classe
	 * 
	 * @param position la position du joueur
	 * @param nom le nom du joueur
	 * @param prenom le prenom du joueur
	 * @param stats les statistiques du joueur
	 * @param arche l'archetype du joueur
	 */
	public Joueur(Case position, String nom, String prenom, Statistique stats, String arche) {
		this.position = position;
		this.nom = nom;
		this.prenom = prenom;
		this.stats = stats;
		this.arche = arche;
	}
	/**
	 * Getter de {@link #arche}
	 * 
	 * @return arche l'archetype du joueur
	 */
	public String getArche() {
		return arche;
	}
	/**
	 * Getter de {@link #position}
	 * 
	 * @return position la position du joueur
	 */
	public Case getPosition() {
		return position;
	}
	/**
	 * Getter de {@link #nom}
	 * 
	 * @return nom le nom du joueur
	 */
	public String getNom() {
		return nom;
	}
	/**
	 * Getter de {@link #prenom}
	 * 
	 * @return prenom le prenom du joueur
	 */
	public String getPrenom() {
		return prenom;
	}
	/**
	 * Getter de {@link #stats}
	 * 
	 * @return stats les statistiques du joueur
	 */
	public Statistique getStats() {
		return stats;
	}
	/**
	 * Setter de {@link #position}
	 * 
	 * @param position la position du joueur
	 */
	public void setPosition(Case position) {
		this.position = position;
	}
	/**
	 * Getter de {@link #poste}
	 * 
	 * @return poste le poste du joueur
	 */
	public String getPoste() {
		return poste;
	}
	/**
	 * Setter de {@link #poste}
	 * 
	 * @param poste le poste que le joueur aura
	 */
	public void setPoste(String poste) {
		if(poste.equals("ATT")||poste.equals("DEF")||poste.equals("MIL")||poste.equals("GAR")) {
			this.poste = poste;
		}
	}
	/**
	 * Getter de {@link #posTact}
	 * 
	 * @return posTact la position tactique du joueur
	 */
	public Case getPosTact() {
		return posTact;
	}
	/**
	 * Setter de {@link #posTact}
	 * 
	 * @param posTact la position tactique que le joueur aura
	 */
	public void setPosTact(Case posTact) {
		this.posTact = posTact;
	}
	/**
	 * Methode permettant d'avancer la position tactique du joueur
	 */
	public void avancPosTact() {
		posTact = new Case(posTact.getLigne(), posTact.getColonne()+1);
	}
	/**
	 * Methode permettant de reculer la position tactique du joueur
	 */
	public void reculPosTact() {
		posTact = new Case(posTact.getLigne(), posTact.getColonne()-1);
	}
	/**
	 * Methode permettant l'affichage d'un joueur
	 */
	@Override
	public String toString() {
		return "Joueur [nom=" + nom + ", prenom=" + prenom + ", stats=" + stats + ", arche=" + arche + "]";
	}
}
