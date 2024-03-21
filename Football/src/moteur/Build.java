package moteur;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import config.Config;
import donnee.*;

/**
 * Builder qui permet la creation des equipe et de la grille
 * 
 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
 *
 */
public class Build {
	
	/**
	 * Methode pour creer la grille
	 * 
	 * @return {@link Grille} qui a etait creer
	 */
	public static Grille creeGrille() {
		return new Grille(Config.TERRAIN_HEIGHT, Config.TERRAIN_WIDTH);
	}
	
	/**
	 * 
	 * @param att {@link Integer} qui represent le nombre d'attaquants
	 * @param mil {@link Integer} qui represent le nombre de milieux
	 * @param def {@link Integer} qui represent le nombre de defenseurs
	 * @param gar {@link Integer} qui represent le nombre gardien
	 * @return {@link ArrayList} qui contient tous les {@link Joueur} creer
	 */
	public static ArrayList<Joueur> creeJoueurs(int att, int mil, int def, int gar){
		ArrayList<Joueur> joueurs = new ArrayList<>();
		if(att>0) {
			joueurs.add(JoueurFactory.creeJoueur("Att", null));
			att-=1;
		}
		if(mil>0) {
			joueurs.add(JoueurFactory.creeJoueur("Mil", null));
			mil-=1;
		}
		if(def>0) {
			joueurs.add(JoueurFactory.creeJoueur("Def", null));
			def-=1;
		}
		if(gar>0) {
			joueurs.add(JoueurFactory.creeJoueur("Gar", null));
			gar-=1;
		}
		if(gar>0||att>0||mil>0||def>0||gar>0) {
			joueurs.addAll(creeJoueurs(att, mil, def, gar));
		}
		
		return joueurs;
	}
	
	/**
	 * Methode permettant la creation complete d'une equipe 
	 * 
	 * @param joueurs
	 * @param rempla
	 * @param compo
	 * @param cote
	 * @param img
	 * @return {@link Equipe}
	 */
	public static Equipe creeEquipe(ArrayList<Joueur> joueurs, ArrayList<Joueur> rempla, Composition compo, String cote, String img) {
		ArrayList<Joueur> titu = new ArrayList<>();
		ArrayList<Joueur> def = new ArrayList<>();
		ArrayList<Joueur> mil = new ArrayList<>();
		ArrayList<Joueur> att = new ArrayList<>();
		HashMap<String, Integer> dispo = compo.getNbJoueurPrPos();
		
		Strategie strat = null;
		
		int ecartCol = (Config.TERRAIN_WIDTH/2)/4;
		for(int i = 0; i<dispo.get("ATT");i++) {
			att.add(joueurs.get(i));
			joueurs.get(i).setPoste("ATT");
		}
		for(Joueur c: att) {
			joueurs.remove(c);
		}
		for(int i = 0; i<dispo.get("MIL");i++) {
			mil.add(joueurs.get(i));
			joueurs.get(i).setPoste("MIL");
		}
		for(Joueur c: mil) {
			joueurs.remove(c);
		}
		for(int i = 0; i<dispo.get("DEF");i++) {
			def.add(joueurs.get(i));
			joueurs.get(i).setPoste("DEF");
		}
		for(Joueur c: def) {
			joueurs.remove(c);
		}
		
		joueurs.get(0).setPoste("GAR");
		
		if(cote.equals("G")) {
			titu.addAll(Utility.calculPositionJoueur(joueurs, 0+Config.PLAYER_SIZE, 0, 10));
			titu.addAll(Utility.calculPositionJoueur(def, ecartCol+Config.PLAYER_SIZE, 50, 50));
			strat = new Strategie(ecartCol+Config.PLAYER_SIZE, "G");
			titu.addAll(Utility.calculPositionJoueur(mil, ecartCol*2+Config.PLAYER_SIZE, 50, 50));
			titu.addAll(Utility.calculPositionJoueur(att, ecartCol*3+Config.PLAYER_SIZE,40,60));
		}
		else if(cote.equals("D")) {
			titu.addAll(Utility.calculPositionJoueur(joueurs, Config.TERRAIN_WIDTH-Config.PLAYER_SIZE, 0, 10));
			titu.addAll(Utility.calculPositionJoueur(def, Config.TERRAIN_WIDTH-ecartCol-Config.PLAYER_SIZE, 50, 50));
			strat = new Strategie(Config.TERRAIN_WIDTH-ecartCol-Config.PLAYER_SIZE, "D");
			titu.addAll(Utility.calculPositionJoueur(mil, Config.TERRAIN_WIDTH-ecartCol*2-Config.PLAYER_SIZE, 50, 50));
			titu.addAll(Utility.calculPositionJoueur(att, Config.TERRAIN_WIDTH-ecartCol*3-Config.PLAYER_SIZE,40,60));
		
		}
		
		return new Equipe(titu, rempla, strat, compo, img);
		
		
	}
}
