package donnee.except;

import donnee.Ballon;
/**
 * Classe de l'exception corner
 * 
 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
 *
 */
public class CornerException extends Exception{
	/**
	 * attribut {@link Ballon} le ballon du match
	 */
	private Ballon ballon;
	/**
	 * Constructeur de la classe
	 * 
	 * @param ballon le ballon du match
	 */
	public CornerException(Ballon ballon) {
		super();
		this.ballon = ballon;
	}
	/**
	 * Getter de {@link #ballon}
	 * 
	 * @return ballon le ballon du match
	 */
	public Ballon getCorner() {
		return ballon;
	}
}
