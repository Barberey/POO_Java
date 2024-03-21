package donnee.except;

import donnee.action.Action;
/**
 * Classe de l'exception arret
 * 
 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
 *
 */
public class ArretException extends Exception {
	/**
	 * attribut {@link Action} qui est l'action qui a ete effectuer
	 */
	private Action action;
	/**
	 * Constructeur de la classes
	 * 
	 * @param action l'action qui a ete effectuer
	 */
    public ArretException(Action action) {
		super();
		this.action = action;
	}
    /**
     * Getter de {@link #action}
     * 
     * @return action l'action effectuer
     */
    public Action getAction() {
    	return action;
    }
}
