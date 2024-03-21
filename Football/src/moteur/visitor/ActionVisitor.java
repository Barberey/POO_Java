package moteur.visitor;

import donnee.*;
import donnee.action.Arret;
import donnee.action.Interception;
import donnee.action.Passe;
import donnee.action.Tir;
import donnee.except.ArretException;
import donnee.except.ButException;
import donnee.except.CornerException;
import donnee.except.FauteException;
import donnee.except.HorsJeuException;
import donnee.except.InterceptException;
import donnee.except.SixMetreException;
/**
 * Classe interface des actions qui seront a realiser
 * 
 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
 *
 * @param <T>
 */
public interface ActionVisitor<T> {
	/**
	 * Methode pour realiser les passes
	 * 
	 * @param action l'action chosit
	 * @return true si l'action est realiser
	 * @throws HorsJeuException l'exception si la passe est un hors jeu
	 */
	T visit(Passe action) throws HorsJeuException;
	/**
	 * Methode pour realiser les tirs
	 * 
	 * @param action l'action chosit
	 * @return
	 * @throws ButException si le tir est un but
	 * @throws ArretException sir le but a ete arrete
	 * @throws SixMetreException sir le tir n'est pas cadre
	 */
	T visit(Tir action) throws ButException, ArretException, SixMetreException;
	/**
	 * Methode pour realiser les interceptions
	 * 
	 * @param action l'action chosit
	 * @return
	 * @throws InterceptException si la balle est interceptee
	 * @throws FauteException si lors de l'interception il y a faute
	 */
	T visit(Interception action) throws InterceptException, FauteException;
	/**
	 * Methode pour realiser les arrets
	 * 
	 * @param action l'action chosit
	 * @return
	 * @throws InterceptException si il arrive a intercepter la balle
	 * @throws CornerException si la balle passe derriere la limite apres l'arret
	 */
	T visit(Arret action) throws InterceptException, CornerException;
	
}
