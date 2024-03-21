package donnee.action;


import donnee.Joueur;
import donnee.except.ArretException;
import donnee.except.ButException;
import donnee.except.CornerException;
import donnee.except.FauteException;
import donnee.except.HorsJeuException;
import donnee.except.InterceptException;
import donnee.except.SixMetreException;
import moteur.visitor.ActionVisitor;
/**
 * Classe interface des actions pour le jeu 
 * 
 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
 *
 */
public interface Action {
	/**
	 * Methode abstraite permettant de recuperer l'acteur
	 * 
	 * @return l'acteur de l'action
	 */
	Joueur getActeur();
	/**
	 * 
	 * @param <T>
	 * @param visitor
	 * @return
	 * @throws InterceptException
	 * @throws ButException
	 * @throws ArretException
	 * @throws FauteException
	 * @throws SixMetreException
	 * @throws HorsJeuException
	 * @throws CornerException
	 */
	<T> T accept(ActionVisitor<T> visitor) throws InterceptException, ButException, ArretException, FauteException, SixMetreException, HorsJeuException, CornerException;
	/**
	 * Methode permettant d'afficher l'action
	 * 
	 * @return l'affichage ecrit de l'action
	 */
	String toString();
	
}
