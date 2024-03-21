package donnee.except;

import donnee.Case;
import donnee.Joueur;
/**
 * Classe de l'exception faute
 * 
 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
 *
 */
public class FauteException extends Exception{
	/**
	 * attribut faute le {@link Joueur} sur qui il y a faute
	 */
	private Joueur faute;
	/**
	 * Constructeur de la classe
	 * 
	 * @param faute le joueur qui a subi la faute
	 */
	public FauteException(Joueur faute) {
		super();
		this.faute = faute ;
	}
	/**
	 * Getter de {@link #faute}
	 * 
	 * @return faute le joueur qui a subi la faute
	 */
	public Joueur getFaute() {
		return faute;
	}
}
