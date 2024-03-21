package moteur;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import org.apache.log4j.Logger;

import chrono.Chronometer;
import config.Config;
import donnee.Ballon;
import donnee.except.ButException;
import donnee.except.CornerException;
import donnee.except.FauteException;
import donnee.except.HorsJeuException;
import donnee.Case;
import donnee.Composition;
import donnee.Equipe;
import donnee.Grille;
import donnee.except.InterceptException;
import donnee.except.SixMetreException;
import gui.MainGui;
import log.LoggerUtility;
import donnee.Joueur;
import donnee.Strategie;
import donnee.action.Action;
import donnee.action.Passe;
import moteur.manag.ManagBall;
import moteur.manag.ManagDepl;
import moteur.visitor.RealActionVisitor;

/**
 * Classe pour le manager qui permet la liaison entre {@link MainGui}, {@link ManagBall} et {@link ManagDepl}
 * 
 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
 *
 */
public class Manager {
	
	private static Logger logger = LoggerUtility.getLogger(Manager.class, "text");
	
	/**
	 * Attribut de type {@link Ballon} pour le ballon
	 */
	private Ballon ballon;
	/**
	 * Attribut {@link Equipe} representant l'equipe gauche
	 */
	private Equipe equipeG;
	/**
	 * Attribut {@link Equipe} representant l'equipe droite
	 */
	private Equipe equipeD;
	/**
	 * Attribut de type {@link Grille} representant la grille
	 */
	private Grille grille;
	/**
	 * {@link ArrayList} regroupant les deux equipes
	 */
	private ArrayList<Equipe> equipes = new ArrayList<>();
	/**
	 * {@link Integer} permettant de garder le nombre de but de l'equipe Gauche
	 */
	private int butG = 0;
	/**
	 * {@link Integer} permettant de garder le nombre de but de l'equipe Droite
	 */
	private int	butD = 0;
	
	/**
	 * {@link HashMap} qui contient les diverses stats realise au cours du match pour l'equipe gauche
	 */
	private HashMap<String, Integer> statsDivers1 = new HashMap<String, Integer>();
	/**
	 * {@link HashMap} qui contient les diverses stats realise au cours du match pour l'equipe droite
	 */
	private HashMap<String, Integer> statsDivers2 = new HashMap<String, Integer>();
	/**
	 * {@link ArrayList} regroupant {@link #statsDivers1} et {@link #statsDivers2}
	 */
	private ArrayList<HashMap<String,Integer>> statsMatch = new ArrayList<>();
	
	/**
	 * {@link ArrayList} qui regroupe les lignes a afficher au cours du match sur l'historique des actions
	 */
	private ArrayList<String> actions = new ArrayList<>();
	
	/**
	 * {@link Boolean} qui permet de reperer les arret de jeu
	 */
	private Boolean jeu = false;
	/**
	 * {@link Boolean} qui permet de reperer les mi-temps
	 */
	private Boolean miTemps = false;
	
	/**
	 * {@link Boolean} qui permet de reperer les coups Francs
	 */
	private Boolean coupFrancs = false;
	/**
	 * {@link Joueur} qui subit une faute
	 */
	private Joueur victime;
	/**
	 * {@link Ballon} pour envoyer les informations pour les hors-jeu
	 */
	private Ballon balle;
	/**
	 * {@link Integer} pour reperer le type d'arret de jeu
	 */
	private int variable;
	
	/**
	 * {@link Boolean} pour reperer les six metres
	 */
	private Boolean sixMetre = false;
	
	/**
	 * {@link ManagDepl} pour gerer les deplacements
	 */
	private ManagDepl managDepl;
	/**
	 * {@link ManagBall} pour gerer les actions du ballon
	 */
	private ManagBall managBall;
	
	/**
	 * {@link Chronometer} pour gerer le temps 
	 */
	private Chronometer chrono;
	
	/**
	 * Constructeur du manager
	 * 
	 * @param grille
	 * @param compo
	 * @param joueurs
	 * @param equipeDcolor
	 * @param equipeGcolor
	 * @param chrono
	 */
	public Manager(Grille grille, Composition compo, ArrayList<Joueur> joueurs, String equipeDcolor, String equipeGcolor, Chronometer chrono) {
		this.grille = grille;
		this.chrono = chrono;
		Composition equipeAdverse = choixCompoAdv();
		if(equipeAdverse == null) {
			equipeAdverse = compo;
		}
		equipeG = Build.creeEquipe(joueurs, null, compo, "G", "src/ressources/"+equipeGcolor+".png");
		equipeD = Build.creeEquipe(Build.creeJoueurs(4, 2, 4, 1), null, equipeAdverse, "D", "src/ressources/"+equipeDcolor+".png");
		ballon = new Ballon(equipeG.getTitulaires().get(0).getPosition(), equipeG.getTitulaires().get(0));
		equipeG.setPossession(true);
		equipeD.setPossession(false);
		equipeG.getTitulaires().get(0).setBallon(true);
		equipes.add(equipeG);
		equipes.add(equipeD);
		managDepl = new ManagDepl(equipeG, equipeD, 1, ballon);
		managBall = new ManagBall(equipeG, equipeD, 1, ballon, grille);
		initStatsDivers();
	}
	
	/**
	 * Methode qui initialise {@link #statsDivers1} et {@link #statsDivers2}
	 */
	private void initStatsDivers() {
		statsDivers1.put("tir", 0);
		statsDivers1.put("passe", 0);
		statsDivers1.put("inter", 0);
		statsDivers1.put("faute", 0);
		statsDivers1.put("poss", 0);
		
		statsDivers2.put("tir", 0);
		statsDivers2.put("passe", 0);
		statsDivers2.put("inter", 0);
		statsDivers2.put("faute", 0);
		
	}
	
	/**
	 * Methode qui permet d'augmenter l'occurence des actions realiser dans {@link #statsDivers1} et {@link #statsDivers2}
	 * 
	 * @param key {@link String} qui correspond a une cles de la {@link HashMap}
	 */
	private void updateStats(String key) {
		int tmp;
		if(equipeG.getPossession()) {
			tmp = statsDivers1.get(key);
			tmp-=1;
			statsDivers1.put(key, tmp);
		}else {
			tmp = statsDivers2.get(key);
			tmp+=1;
			statsDivers2.put(key, tmp);
		}
			
	}
	
	/**
	 * Methode principale qui correspond a notre boucle de jeu principale
	 */
	public void advance() {
		if(jeu == true) {
			coupFrancs = false;
			try {
				managBall.calcul();
				managDepl.calcul();
			} catch (InterceptException e) {
				managDepl.changePoss();
				changPoss();
				updateStats("inter");
			} catch (ButException e) {
				if (equipeG.getPossession()) {
					butG++;
				}
				else {
					butD++;
				}
				managDepl.changePoss();
				changPoss();
				jeu = false;
				String msg = "But de "+ballon.getJoueur().getNom()+" "+ballon.getJoueur().getPrenom()+" [" + chrono.getMinute()+":"+chrono.getSecond()+"]\n";
				actions.add(msg);
				logger.info(msg);
			} catch (FauteException e) {
				jeu = false;
				coupFrancs = true;
				victime = e.getFaute();
				variable = 0;
				changPoss();
				updateStats("faute");
				changPoss();
				String msg = "Faute [" + chrono.getMinute()+":"+chrono.getSecond()+"]\n"; 
				actions.add(msg);
				logger.info(msg);
			} catch (SixMetreException e) {
				managDepl.changePoss();
				changPoss();
				jeu = false;
				sixMetre = true;
				String msg = "Sortie de but [" + chrono.getMinute()+":"+chrono.getSecond()+"]\n";
				actions.add(msg);
				logger.info(msg);
			} catch (HorsJeuException e) {
				managDepl.changePoss();
				changPoss();
				jeu = false;
				coupFrancs = true;
				victime = JoueurFactory.creeJoueur("Att", e.getHJ());
				variable = 1;
				String msg = "Hors-Jeu [" + chrono.getMinute()+":"+chrono.getSecond()+"]\n";
				actions.add(msg);
				logger.info(msg);
			} catch (CornerException e) {
				jeu = false;
				coupFrancs = true;
				balle = e.getCorner();
				variable = 2;
				String msg = "Corner [" + chrono.getMinute()+":"+chrono.getSecond()+"]\n"; 
				actions.add(msg);
				logger.info(msg);
			}
			
		}
		else{
			if(sixMetre){
				sixMetre = false;
				if(equipeG.getPossession()) {
					sixMetre(equipeG);
				}
				else {
					sixMetre(equipeD);
				}
				jeu = true;
			}
			else {
				if(coupFrancs) {
					if( variable == 0 || variable == 1) {
						coupFrancs = false;
						coupFrancs(victime, variable, null);
						jeu = true;

					}
					else {
						coupFrancs(null, variable, balle);
						jeu = true;
						
					}
					
				}
				else {
					if(miTemps) {
		                if(equipeG.getPossession()) {
		                    changPoss();
		                    managDepl.changePoss();
		                    managBall.changePoss();
		                    miTemps=false;
		                }
		                managBall.flushAction();
		            }
					kickOff();
					jeu = true;
				}
			}
		}
	}
	
	/**
	 * Methode qui choisi la composition de l'equipe adverse a partir de composition stocke dans un fichier
	 * 
	 * @return {@link Composition} de l'equipe adverse
	 */
	private Composition choixCompoAdv() {
		try {
			ArrayList<String> listeCompo = new ArrayList<>();
			BufferedReader reader = new BufferedReader(new FileReader("./src/ressources/compo.txt"));
			String line;
			while ((line = reader.readLine()) != null) {
				listeCompo.add(line);
			}
			reader.close();
			
			int random = Utility.getRandomNumber(0, listeCompo.size()-1);
			String tmp = listeCompo.get(random);
			String compo[] = tmp.split("&&");
			
			return new Composition(Integer.parseInt(compo[2]), Integer.parseInt(compo[1]), Integer.parseInt(compo[0]), 1);
						
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}
	
	/**
	 * Methode pour gerer le changement de possession
	 */
	private void changPoss(){
		if(equipeG.getPossession()) {
			equipeG.setPossession(false);
			equipeD.setPossession(true);
		}
		else {
			equipeD.setPossession(false);
			equipeG.setPossession(true);
		}
	}
	
	/**
	 * Methode qui gere les arrets de jeu determiner par {@link #variable}
	 * 
	 * @param j {@link Joueur} concerner par l'arret de jeu
	 * @param variable {@link Integer} qui precise le type d'arret de jeu
	 * @param balle
	 */
	private void coupFrancs(Joueur j, int variable, Ballon balle) {
		
		Case faute = null;
		
		if(variable == 1 || variable == 0) {
			faute = j.getPosition();
		}
		
		if(equipeG.getPossession()) {
			replaceHaut("G", equipeG.getTitulaires(), equipeG);
			replaceBas("D", equipeD.getTitulaires(), equipeD);	
		}
		else {
			replaceBas("G", equipeG.getTitulaires(), equipeG);
			replaceHaut("D", equipeD.getTitulaires(), equipeD);
		}
		managDepl.updateStrat();
		
		if(variable == 0) {
			j.setPosition(faute);
			ballon.setJoueur(j);
			j.setBallon(true);
			ballon.setPosition(j.getPosition());
		}
		
		else if (variable == 1) {
			ballon.getJoueur().setBallon(false);
			ballon.setJoueur(null);
			Joueur gardien = null;
			Equipe equipeNonPoss = null;
			if(equipeD.getPossession()) {
				equipeNonPoss = equipeD;
				for (Joueur joueur : equipeNonPoss.getTitulaires() ) {
					if (joueur.getPoste()=="GAR" && (faute.getColonne()>=Config.TERRAIN_WIDTH*3/4)){
						gardien = joueur;
						break;
					}
					else if(joueur.getPoste()=="MIL") {
						gardien = joueur;
					}
				}
			}
			else {
				equipeNonPoss = equipeG;
				for (Joueur joueur : equipeNonPoss.getTitulaires() ) {
					if (joueur.getPoste()=="GAR" && (faute.getColonne()<=Config.TERRAIN_WIDTH/4)){
						gardien = joueur;
						break;
					}
					else if(joueur.getPoste()=="MIL") {
						gardien = joueur;
					}
				}
			}
			gardien.setPosition(faute);
			ballon.setJoueur(gardien);
			gardien.setBallon(true);
			ballon.setPosition(gardien.getPosition());
			managDepl.setCentreur(gardien);
		}
		else if (variable == 2) {
			coupFrancs = false;
			Case corner = null;
			Equipe equipeNonPoss;
			if(equipeD.getPossession()) {
				equipeNonPoss = equipeD;
			}
			else {
				equipeNonPoss = equipeG;
			}
			
			Joueur centreur = null;
			for (Joueur joueur : equipeNonPoss.getTitulaires()) {
				if (joueur.getPoste()=="ATT") {
					centreur = joueur;
				}
			}
			if ((Config.TERRAIN_HEIGHT)/2>balle.getPosition().getLigne()) {
				if((Config.TERRAIN_WIDTH)/2>balle.getPosition().getColonne()) {
					corner = new Case(5, 5);
				}
				else {
					corner = new Case(5, Config.TERRAIN_WIDTH-5);
				}
			}
			else {
				if((Config.TERRAIN_WIDTH)/2>balle.getPosition().getColonne()) {
					corner = new Case(Config.TERRAIN_HEIGHT-5, 5);
				}
				else {
					corner = new Case(Config.TERRAIN_HEIGHT-5, Config.TERRAIN_WIDTH-5);
				}
			}
			centreur.setPosition(corner);
			ballon.setJoueur(centreur);
			centreur.setBallon(true);
			ballon.setPosition(centreur.getPosition());
			managDepl.setCentreur(centreur);
		}
		managBall.flushAction();

	}
	
	/**
	 * Methode qui gere la mise en place des sorties de but
	 * 
	 * @param equipePos {@link Equipe} qui a la possession
	 */
	private void sixMetre (Equipe equipePos) {
		ballon.getJoueur().setBallon(false);
		ballon.setJoueur(null);
		
		if(equipeG.getPossession()) {
			replaceHaut("G", equipeG.getTitulaires(), equipeG);
			replaceBas("D", equipeD.getTitulaires(), equipeD);	
		}
		else {
			replaceBas("G", equipeG.getTitulaires(), equipeG);
			replaceHaut("D", equipeD.getTitulaires(), equipeD);
		}
		managDepl.updateStrat();
		
		
		Joueur gardienSixMetre = null;
		for (Joueur j : equipePos.getTitulaires()) {
			if(j.getPoste()=="GAR") {
				gardienSixMetre = j;
			}
		}
		
		Case sixMetre = gardienSixMetre.getPosition();
		
		gardienSixMetre.setPosition(sixMetre);
		ballon.setJoueur(gardienSixMetre);
		gardienSixMetre.setBallon(true);
		ballon.setPosition(sixMetre);
	}
		
	/**
	 * Methode qui gere le mise en place des engagements
	 */
	private void kickOff() {
		ballon.getJoueur().setBallon(false);
		ballon.setJoueur(null);
		
		replaceBas("G", equipeG.getTitulaires(), equipeG);
		replaceBas("D", equipeD.getTitulaires(), equipeD);	
		managDepl.updateStrat();
		
		int ligneMid = Config.TERRAIN_HEIGHT/2;
		Joueur plusProch = null;
		
		if(equipeG.getPossession()) {
			ArrayList<Joueur> joueursG = equipeG.getTitulaires();
			for(Joueur j : joueursG) {
				if(j.getPoste().equals("ATT")) {
					if(plusProch==null) {
						plusProch = j;
					}
					else if((Math.abs(j.getPosition().getLigne()-ligneMid))<(Math.abs(plusProch.getPosition().getLigne()-ligneMid))) {
						plusProch = j;
					}
				}
			}
		}
		else {
			ArrayList<Joueur> joueursD = equipeD.getTitulaires();
			for(Joueur j : joueursD) {
				if(j.getPoste().equals("ATT")) {
					if(plusProch==null) {
						plusProch = j;
					}
					else if((Math.abs(j.getPosition().getLigne()-ligneMid))<(Math.abs(plusProch.getPosition().getLigne()-ligneMid))) {
						plusProch = j;
					}
				}
			}
		}
		
		
		
		plusProch.setPosition(new Case(ligneMid, Config.TERRAIN_WIDTH/2));
		ballon.setPosition(plusProch.getPosition());
		ballon.setJoueur(plusProch);
		plusProch.setBallon(true);
	}
	
	/**
	 * Methode qui replace l'equipe dans leur position haute
	 * 
	 * @param cote{@link String} precisant le cote
	 * @param joueurs {@link ArrayList} de {@link Joueur} regrouppant les effectifs a replacer
	 * @param equipe {@link Equipe} representant l'equipe qu'on represente
	 */
	private void replaceHaut(String cote, ArrayList<Joueur> joueurs, Equipe equipe) {
		int ecartCol = (Config.TERRAIN_WIDTH/2)/4;
		Strategie strat;
		
		ArrayList<Joueur> gar = new ArrayList<>();
		ArrayList<Joueur> def = new ArrayList<>();
		ArrayList<Joueur> mil = new ArrayList<>();
		ArrayList<Joueur> att = new ArrayList<>();
		
		for(Joueur j : joueurs) {
			if(j.getPoste().equals("ATT")) {
				att.add(j);
			}
			else if(j.getPoste().equals("DEF")) {
				def.add(j);
			}
			else if(j.getPoste().equals("MIL")) {
				mil.add(j);
			}
			else if(j.getPoste().equals("GAR")) {
				gar.add(j);
			}
		}
		
		if(cote.equals("G")) {
			Utility.calculPositionJoueur(gar, 0+Config.PLAYER_SIZE, 0, 10);
			Utility.calculPositionJoueur(def, Config.TERRAIN_WIDTH/2, 50, 50);
			strat = new Strategie(Config.TERRAIN_WIDTH/2, "G");
			strat.setColonneDefMin(ecartCol+Config.PLAYER_SIZE);
			equipe.setStrats(strat);
			Utility.calculPositionJoueur(mil, (Config.TERRAIN_WIDTH/2)+ecartCol+Config.PLAYER_SIZE, 50, 50);
			Utility.calculPositionJoueur(att, (Config.TERRAIN_WIDTH/2)+ecartCol*2+Config.PLAYER_SIZE,40,60);
		}
		else if(cote.equals("D")) {
			Utility.calculPositionJoueur(gar, Config.TERRAIN_WIDTH-Config.PLAYER_SIZE, 0, 10);
			Utility.calculPositionJoueur(def, Config.TERRAIN_WIDTH/2, 50, 50);
			strat = new Strategie(Config.TERRAIN_WIDTH/2, "D");
			strat.setColonneDefMax(Config.TERRAIN_WIDTH-ecartCol-Config.PLAYER_SIZE);
			equipe.setStrats(strat);
			Utility.calculPositionJoueur(mil, (Config.TERRAIN_WIDTH/2)-(ecartCol)-Config.PLAYER_SIZE, 50, 50);
			Utility.calculPositionJoueur(att, (Config.TERRAIN_WIDTH/2)-(ecartCol*2)-Config.PLAYER_SIZE,40,60);
		}
		
		
	}
	
	/**
	 * Methode pour replacer l'equiper dans leur position de base 
	 * 
	 * @param cote {@link String} precisant le cote
	 * @param joueurs {@link ArrayList} de {@link Joueur} regrouppant les effectifs a replacer
	 * @param equipe {@link Equipe} representant l'equipe qu'on represente
	 */
	private void replaceBas(String cote, ArrayList<Joueur> joueurs, Equipe equipe) {
		int ecartCol = (Config.TERRAIN_WIDTH/2)/4;
		Strategie strat;
		
		ArrayList<Joueur> gar = new ArrayList<>();
		ArrayList<Joueur> def = new ArrayList<>();
		ArrayList<Joueur> mil = new ArrayList<>();
		ArrayList<Joueur> att = new ArrayList<>();
		
		for(Joueur j : joueurs) {
			if(j.getPoste().equals("ATT")) {
				att.add(j);
			}
			else if(j.getPoste().equals("DEF")) {
				def.add(j);
			}
			else if(j.getPoste().equals("MIL")) {
				mil.add(j);
			}
			else if(j.getPoste().equals("GAR")) {
				gar.add(j);
			}
		}
		
		if(cote.equals("G")) {
			Utility.calculPositionJoueur(gar, 0+Config.PLAYER_SIZE, 0, 10);
			Utility.calculPositionJoueur(def, ecartCol+Config.PLAYER_SIZE, 50, 50);
			strat = new Strategie(ecartCol+Config.PLAYER_SIZE, "G");
			equipe.setStrats(strat);
			Utility.calculPositionJoueur(mil, ecartCol*2+Config.PLAYER_SIZE, 50, 50);
			Utility.calculPositionJoueur(att, ecartCol*3+Config.PLAYER_SIZE,40,60);
		}
		else if(cote.equals("D")) {
			Utility.calculPositionJoueur(gar, Config.TERRAIN_WIDTH-Config.PLAYER_SIZE, 0, 10);
			Utility.calculPositionJoueur(def, Config.TERRAIN_WIDTH-ecartCol-Config.PLAYER_SIZE, 50, 50);
			strat = new Strategie(Config.TERRAIN_WIDTH-ecartCol-Config.PLAYER_SIZE, "D");
			equipe.setStrats(strat);
			Utility.calculPositionJoueur(mil, Config.TERRAIN_WIDTH-ecartCol*2-Config.PLAYER_SIZE, 50, 50);
			Utility.calculPositionJoueur(att, Config.TERRAIN_WIDTH-ecartCol*3-Config.PLAYER_SIZE,40,60);
		}
		
		
	}
	
	/**
	 * Getter de {@link #ballon}
	 * @return {@link Ballon}
	 */
	public Ballon getBallon() {
		return ballon;
	}

	/**
	 * Getter de {@link #equipeG}
	 * @return {@link Equipe}
	 */
	public Equipe getEquipeG() {
		return equipeG;
	}
	/**
	 * Getter de {@link #equipeD}
	 * @return {@link Equipe}
	 */
	public Equipe getEquipeD() {
		return equipeD;
	}
	/**
	 * Getter de {@link #jeu}
	 * @return {@link Boolean}
	 */
	public Boolean getJeu() {
		return jeu;
	}
	/**
	 * Getter de {@link #coupFrancs}
	 * @return {@link Boolean}
	 */
	public Boolean getCoupFrancs() {
		return coupFrancs;
	}
	/**
	 * Setter de {@link #jeu}
	 * @param jeu
	 */
	public void setJeu(Boolean jeu) {
		this.jeu = jeu;
		miTemps = true;
	}
	/**
	 * Getter de {@link #butG}
	 * @return {@link Integer}
	 */
	public int getButG() {
		return butG;
	}
	/**
	 * Getter de {@link #butD}
	 * @return {@link Integer}
	 */
	public int getButD() {
		return butD;
	}
	
	/**
	 * Methode pour recuperer les stats du match
	 * 
	 * @return {@link ArrayList} qui regroupe {@link #statsDivers1} et {@link #statsDivers2}
	 */
	public ArrayList<HashMap<String, Integer>> getStatsMatch(){
		ArrayList<HashMap<String, Integer>> array = managBall.getStatsEquipes();
		statsDivers1.put("tir", array.get(0).get("tir"));
		statsDivers1.put("passe", array.get(0).get("passe"));
		
		statsDivers2.put("tir", array.get(1).get("tir"));
		statsDivers2.put("passe", array.get(1).get("passe"));
		
		statsMatch.add(statsDivers1);
		statsMatch.add(statsDivers2);
		
		return statsMatch;
	}
	
	/**
	 * Getter de {@link #actions}
	 * 
	 * @return {@link ArrayList} de ligne a afficher pendant le match
	 */
	public ArrayList<String> getActions(){
		return actions;
	}
	
	/**
	 * Getter de {@link #getVariable()}
	 * @return {@link Integer} permettant de reperer le type d'arret de jeu
	 */
	public int getVariable() {
		return variable;
	}
	
}