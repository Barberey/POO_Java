package moteur.visitor;

import donnee.except.*;
import config.Config;
import donnee.Ballon;
import donnee.Case;
import donnee.Grille;
import donnee.Joueur;
import donnee.action.Arret;
import donnee.action.Interception;
import donnee.action.Passe;
import donnee.action.Tir;
import moteur.Utility;
/**
 * Classe permettant de realiser les actions chosit
 * 
 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
 *
 */
public class RealActionVisitor implements ActionVisitor<Boolean>{
	/**
	 * Methode permettant la realisation des passes
	 */
	@Override
	public Boolean visit(Passe action) throws HorsJeuException {
		Joueur cible = action.getCible();
		Case objectif = cible.getPosition();
		Ballon ballon = action.getBallon();
		Case oldPos = ballon.getPosition();
		Grille grille = action.getGrille();
		
		if(oldPos.equals(objectif)) {
			ballon.setJoueur(cible);
			cible.setBallon(true);
			action.getActeur().setBallon(false);
			return true;
		}
		else {
			Case newPos = Utility.calculDeplacement(grille, oldPos, objectif, Utility.calculCoeff(oldPos, objectif));
			ballon.setPosition(newPos);
			return false;
		}
	}
	/**
	 * Methode permettant la realisation des interceptions
	 */
	@Override
	public Boolean visit(Interception action) throws InterceptException, FauteException {
		Joueur acteur = action.getActeur();
		Joueur passeur = action.getPasse();
		Ballon ballon = action.getBallon();
		
		if(acteur.getPosition().equals(ballon.getPosition())) {
			passeur.setBallon(false);
			ballon.setPosition(acteur.getPosition());
			ballon.setJoueur(acteur);
			acteur.setBallon(true);
			if(Utility.calculDistance(passeur.getPosition(), acteur.getPosition())<10) {
				if(Utility.getRandomNumber(1, 4)==1) {
					throw new FauteException(passeur);
				}
				else {
					throw new InterceptException();
				}
			}
			else {
				throw new InterceptException();
			}
		}
		else if(Utility.calculDistance(ballon.getPosition(),acteur.getPosition())<3){
			if(Utility.getRandomNumber(1, 100) <= acteur.getStats().getDef() -15) {
				passeur.setBallon(false);
				ballon.setPosition(acteur.getPosition());
				ballon.setJoueur(acteur);
				acteur.setBallon(true);
				if(Utility.calculDistance(passeur.getPosition(), acteur.getPosition())<10) {
					if(Utility.getRandomNumber(1, 4)==1) {
						throw new FauteException(passeur);
					}
					else {
						throw new InterceptException();
					}
				}
				else {
					throw new InterceptException();
				}
			}
			return true;
		}
		else if(Utility.calculDistance(ballon.getPosition(),acteur.getPosition())<5){
			if(Utility.getRandomNumber(1, 100) <= acteur.getStats().getDef() - 25) {
				passeur.setBallon(false);
				ballon.setPosition(acteur.getPosition());
				ballon.setJoueur(acteur);
				acteur.setBallon(true);
				if(Utility.calculDistance(passeur.getPosition(), acteur.getPosition())<10) {
					if(Utility.getRandomNumber(1, 4)==1) {
						throw new FauteException(passeur);
					}
					else {
						throw new InterceptException();
					}
				}
				else {
					throw new InterceptException();
				}
			}
			return true;
		}
		else if(Utility.calculDistance(ballon.getPosition(),acteur.getPosition())<8){
			if(Utility.getRandomNumber(1, 100) <= acteur.getStats().getDef() - 30) {
				passeur.setBallon(false);
				ballon.setPosition(acteur.getPosition());
				ballon.setJoueur(acteur);
				acteur.setBallon(true);
				if(Utility.calculDistance(passeur.getPosition(), acteur.getPosition())<10) {
					if(Utility.getRandomNumber(1, 4)==1) {
						throw new FauteException(passeur);
					}
					else {
						throw new InterceptException();
					}
				}
				else {
					throw new InterceptException();
				}
			}
			return true;
		}
		else if(Utility.calculDistance(ballon.getPosition(),acteur.getPosition())<=10){
			if(Utility.getRandomNumber(1, 100) <= acteur.getStats().getDef() - 35) {
				passeur.setBallon(false);
				ballon.setPosition(acteur.getPosition());
				ballon.setJoueur(acteur);
				acteur.setBallon(true);
				if(Utility.calculDistance(passeur.getPosition(), acteur.getPosition())<10) {
					if(Utility.getRandomNumber(1, 4)==1) {
						throw new FauteException(passeur);
					}
					else {
						throw new InterceptException();
					}
				}
				else {
					throw new InterceptException();
				}
			}
			return true;
		}
		else {
			return true;
		}
	}
	/**
	 * Methode permettant la realisation des tirs
	 */
	@Override
	public Boolean visit(Tir action) throws ButException, ArretException, SixMetreException {
		Joueur gardien = action.getActeur();
		Ballon ballon = action.getBallon();
		Grille grille = action.getGrille();
		
		
		Case cible = action.getCible();
		Case oldPos = ballon.getPosition();
		
		if(oldPos.equals(cible)) {
			if(Config.POTO_HAUT>cible.getLigne()||cible.getLigne()>Config.POTO_BAS) {	
				throw new SixMetreException();
			}
			else {
				throw new ButException();
			}
		}
		else if(Utility.calculDistance(oldPos, gardien.getPosition()) < 10 && !(action.getArret())){
			Case newPos = Utility.calculDeplacement(grille, oldPos, cible, Utility.calculCoeff(oldPos, cible));
			ballon.setPosition(newPos);
			action.setArret(true);
			throw new ArretException(action);
		}
		else {
			Case newPos = Utility.calculDeplacement(grille, oldPos, cible, Utility.calculCoeff(oldPos, cible));
			ballon.setPosition(newPos);
			return false;
		}
	}
	/**
	 * Methode permettant la realisation des arrrets
	 */
	@Override
	public Boolean visit(Arret action) throws InterceptException, CornerException {
		Joueur acteur = action.getActeur();
		Ballon ballon = action.getBallon();
		
		if(acteur.getPosition().equals(ballon.getPosition())) {
			ballon.setPosition(acteur.getPosition());
			ballon.setJoueur(acteur);
			acteur.setBallon(true);
			throw new InterceptException();
		}
		
		if(Utility.getRandomNumber(1, 3)==1) {
			if(Utility.getRandomNumber(1, 100)<=acteur.getStats().getArret()) {
				ballon.setPosition(acteur.getPosition());
				ballon.setJoueur(acteur);
				acteur.setBallon(true);
				throw new InterceptException();
			}
			else {
				throw new CornerException(ballon);
			}
		}
		else {
			return true;
		}
	}
}
