package moteur.manag;

import java.awt.HeadlessException;
import java.nio.file.spi.FileSystemProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.security.sasl.SaslException;

import org.apache.log4j.Logger;

import config.Config;
import donnee.except.*;
import log.LoggerUtility;
import donnee.Ballon;
import donnee.Case;
import donnee.Equipe;
import donnee.Grille;
import donnee.Joueur;
import donnee.action.Action;
import donnee.action.Arret;
import donnee.action.Interception;
import donnee.action.Passe;
import donnee.action.Tir;
import moteur.Utility;
import moteur.visitor.RealActionVisitor;
/**
 * Classe permettant la gestion du ballon et toutes les actions qui sont autour du ballon
 * 
 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
 *
 */
public class ManagBall extends ManagAction{
	
	
	
	private Boolean hj = false;
	private Case hjPos;
	
	/**
	 * attribut ballon le ballon du match
	 */
	private Ballon ballon;
	/**
	 * attribut actionsBallon une liste qui contient toutes les actions que le ballons possede
	 */
	private ArrayList<Action> actionsBallon = new ArrayList<>();
	/**
	 * attribut grille la grille representant le terrain
	 */
	private Grille grille;
	
	private HashMap<String, Integer> statsMatch1 = new HashMap<>();
	private HashMap<String, Integer> statsMatch2 = new HashMap<>();
	private ArrayList<HashMap<String,Integer>> statsEquipes = new ArrayList<>();
	
	/**
	 * Constructeur de la classe
	 * 
	 * @param equipe1 l'equipe qui posssede la balle
	 * @param equipe2 l'equipe qui ne possede pas la balle
	 * @param poss l'entier representant quelle equipe possede la balle
	 * @param ballon le ballon
	 * @param grille la grille du terrain
	 */
	public ManagBall(Equipe equipe1, Equipe equipe2, int poss, Ballon ballon, Grille grille) {
		super(equipe1, equipe2, poss);
		this.ballon = ballon;
		this.grille = grille;
		statsMatch1.put("tir", 0);
		statsMatch1.put("passe", 0);
		statsMatch2.put("tir", 0);
		statsMatch2.put("passe", 0);
	}
	/**
	 * Methode permettant le calcul des actions pour le ballon
	 * 
	 * @throws InterceptException l'exception pour l'interception du ballon
	 * @throws ButException l'exception pour un but
	 * @throws FauteException l'exception pour une faut via une interception
	 * @throws SixMetreException l'exception pour un tir rate
	 * @throws HorsJeuException l'exception pour un hors jeu un joueur trop avance
	 * @throws CornerException l'exception pour un arret louper mais decaler
	 */
	public void calcul() throws InterceptException, ButException, FauteException, SixMetreException, HorsJeuException, CornerException {
		ArrayList<Joueur> titulaires;
		Equipe equipePoss;
		Equipe equipeNonPoss;
		if(getPoss()==1) {
			titulaires = getEquipe1().getTitulaires();
			equipePoss = getEquipe1();
			equipeNonPoss = getEquipe2();
		}
		else {
			titulaires = getEquipe2().getTitulaires();
			equipePoss = getEquipe2();
			equipeNonPoss = getEquipe1();
		}
		
		if(actionsBallon.isEmpty()) {
			if(hj) {
				hj = false;
				for(Joueur j : titulaires) {
					j.setBallon(false);
				}
				changePoss();
				
				throw new HorsJeuException(hjPos);
			}
			Boolean onlyOne = true;
			for (int i = 0 ; i< titulaires.size() ; i++) {
				if(titulaires.get(i).getBallon()&&onlyOne){
					Joueur joueurBall = titulaires.get(i);
					int score = 0;
					
					//Distance But
					int colonneBut;
					if(getPoss() == 1) {
						colonneBut = Config.TERRAIN_WIDTH-1;
					}
					else {
						colonneBut = 0;
					}
					score +=15;
					int distance = Math.abs(joueurBall.getPosition().getColonne()-colonneBut);
					while(distance > 0) {
						score -=1;
						distance-=15;
					}
					
					Case posJoueur = joueurBall.getPosition();
					
					score += zoneTir(posJoueur, colonneBut);
					
					//Joueur Adverse proche
					Joueur gardien = null;
					Joueur plusProche = null;
					for(Joueur j : equipeNonPoss.getTitulaires()) {
						if(j.getPoste().equals("GAR")) {
							gardien = j;
						}
						
						if(plusProche == null) {
							plusProche = j;
						}
						else if(Utility.calculDistance(j.getPosition(), joueurBall.getPosition())<Utility.calculDistance(plusProche.getPosition(), joueurBall.getPosition())) {
							plusProche = j;
						}
					}
					distance = Utility.calculDistance(plusProche.getPosition(), joueurBall.getPosition());
					while(distance > 0) {
						score +=1;
						distance -=15;
					}
					
					int nbJoueurDer = 0;
					for(Joueur j: equipeNonPoss.getTitulaires()) {
						if(getPoss()==1) {
							if(titulaires.get(i).getPosition().getColonne()>=j.getPosition().getColonne()) {
								nbJoueurDer++;
							}
						}
						else {
							if(titulaires.get(i).getPosition().getColonne()<=j.getPosition().getColonne()) {
								nbJoueurDer++;
							}
						}
					}
					if(nbJoueurDer>=10) {
						score+=500000;
					}
					
					if(score >= 12){
						int rand = cibleVise(titulaires.get(i));
						Case cibleBut = new Case(rand, colonneBut);
						actionsBallon.add(new Tir(gardien,ballon, grille, cibleBut));
						if(getPoss()==1) {
							int tmp = statsMatch1.get("tir");
							tmp-=1;
							statsMatch1.put("tir", tmp);
						} else {
							int tmp = statsMatch2.get("tir");
							tmp+=1;
							statsMatch2.put("tir", tmp);
						}
						onlyOne = false;
					}
					else {
						ArrayList<Joueur> poolPass = new ArrayList<>();
						if (titulaires.get(i).getPoste()=="GAR") {
                            for (int j = 0 ; j< titulaires.size() ; j++) {
                                if (titulaires.get(j).getPoste()=="DEF") {
                                    poolPass.add(titulaires.get(j));
                                }
                            }
                        }else {
                            poolPass.addAll(titulaires);
                            poolPass.remove(i);
                        }
						
						Joueur passeur = ballon.getJoueur();
	                    Joueur recPass = choixJoueur(titulaires.get(i).getPosition(),poolPass,equipeNonPoss, getPoss());
	                    
	                    int scoreHJ1 = 0;
						for(Joueur j : equipeNonPoss.getTitulaires()) {
							if(recPass.getPoste()=="ATT") {
								if(getPoss()==1) {
									if(j.getPosition().getColonne() < recPass.getPosition().getColonne() && passeur.getPosition().getColonne() < recPass.getPosition().getColonne() ) {
										scoreHJ1++;
									}
								}
								else {
									if(j.getPosition().getColonne() > recPass.getPosition().getColonne() && passeur.getPosition().getColonne() > recPass.getPosition().getColonne()) {
										scoreHJ1--;
									}
								}
							}

						}
						
						
						if(scoreHJ1 == 10 || scoreHJ1 == -10) {
							hj = true;
							hjPos = recPass.getPosition();
							Passe act = new Passe(recPass,recPass.getPosition(),titulaires.get(i) ,ballon, grille);
							ballon.setCoeff(Utility.calculCoeff(titulaires.get(i).getPosition(), recPass.getPosition()));
							actionsBallon.add(act);
							equipePoss.setCibleBallon(recPass);
							onlyOne = false;
						}
						
						else {
		                    Passe act = new Passe(recPass,recPass.getPosition(),titulaires.get(i) ,ballon, grille);
		                    ballon.setCoeff(Utility.calculCoeff(titulaires.get(i).getPosition(), recPass.getPosition()));
		                    actionsBallon.add(act);
		                    if(getPoss()==1) {
								int tmp = statsMatch1.get("passe");
								tmp-=1;
								statsMatch1.put("passe", tmp);
							} else {
								int tmp = statsMatch2.get("passe");
								tmp+=1;
								statsMatch2.put("passe", tmp);
							}
		                    equipePoss.setCibleBallon(recPass);
		                    onlyOne = false;
						}
					}
				}
			}
		}
		else {
			RealActionVisitor real = new RealActionVisitor();
			try {
				Joueur passeur = null;
				for(Joueur j : titulaires) {
					if(j.getBallon()) {
						passeur = j;
					}
				}
				actionsBallon = fonctVisi(actionsBallon, real);
				for(Joueur j : equipeNonPoss.getTitulaires()){
					if(j.getPoste()!="GAR") {
						actionsBallon.add(new Interception(j, ballon, passeur));
					}
				}
				actionsBallon = fonctVisi(actionsBallon, real);
			} catch (InterceptException e) {
				equipePoss.setCibleBallon(null);
				changePoss();
				actionsBallon.clear();
				throw new InterceptException();
			} catch (ButException e) {
				changePoss();
				actionsBallon.clear();
				throw new ButException();
			} catch (ArretException e) {
				actionsBallon.remove(e.getAction());
				for(Joueur j : equipeNonPoss.getTitulaires()) {
					if(j.getPoste().equals("GAR")) {
						actionsBallon.add(new Arret(j, ballon));
						actionsBallon.add(e.getAction());
					}
				}
			} catch (FauteException e) {
				actionsBallon.clear();
				throw new FauteException(e.getFaute());
			} catch (SixMetreException e) {
				changePoss();
				actionsBallon.clear();
				throw new SixMetreException();
			} catch (CornerException e) {
				actionsBallon.clear();
				throw new CornerException(e.getCorner());
			}
		}
	}
	/**
	 * Methode calculant si le tir sera plus ou moins reussit
	 * 
	 * @param tireur celui qui va tirer la balle
	 * @return un entier qui augmentera ou diminuera la reussite du but
	 */
	private int cibleVise(Joueur tireur ) {
		int statTir = tireur.getStats().getTir();
		int decalage = 95 - statTir;
		if (statTir >= 90) {
			decalage = 0;
		}
		return Utility.getRandomNumber(Config.POTO_HAUT - decalage/2, Config.POTO_BAS + decalage/2);
	}
	/**
	 * Methode permetant de limiter l'endroit ou les joeurs puissent tirer
	 * 
	 * @param position la position du joueur
	 * @param colonneBut la colonne du but
	 * @return un entier qui fera que le joueur ne puisse pas tirer
	 */
	private int zoneTir(Case position, int colonneBut) {
		int limiteButCol;
		int limitButLigneB;
		int limitButLigneH;
		if(getPoss()==1) {
			limiteButCol = colonneBut - Config.TERRAIN_WIDTH/8;
			limitButLigneB = Config.TERRAIN_HEIGHT - Config.TERRAIN_HEIGHT/6;
			limitButLigneH = Config.TERRAIN_HEIGHT/6;
			
			if(position.getColonne()>limiteButCol) {
				if(position.getLigne()>limitButLigneB) {
					return -999999;
				}
				else if(position.getLigne()<limitButLigneH) {
					return -999999;
				}
			}
		}
		else {
			limiteButCol = colonneBut + Config.TERRAIN_WIDTH/8;
			limitButLigneB = Config.TERRAIN_HEIGHT - Config.TERRAIN_HEIGHT/6;
			limitButLigneH = Config.TERRAIN_HEIGHT/6;
			
			if(position.getColonne()<limiteButCol) {
				if(position.getLigne()>limitButLigneB) {
					return -999999;
				}
				else if(position.getLigne()<limitButLigneH) {
					return -999999;
				}
			}
		}
		return 0;
	}
	/**
	 * Methode permettant de choisir le meilleur joueur a qui faire la passe
	 * 
	 * @param posJouBall la position du joueur qui a la balle
	 * @param poolPass le nombre de joueur qui peut recevoir la balle
	 * @param nonPoss l'equipe qui n'a pas la possession
	 * @param poss l'entier qui determine l'equipe qui a la possession
	 * @return le joueur adequat
	 */
	private Joueur choixJoueur(Case posJouBall, ArrayList<Joueur> poolPass, Equipe nonPoss, int poss) {
        HashMap<Integer, Joueur> classement = new HashMap<>();
        for(Joueur a : poolPass) {
            int score = 0;

            //Calcul enemie plus proche
            Joueur plusProche = null;
            int dist = 0;
            for(Joueur n : nonPoss.getTitulaires()) {
                int tmpDist = Utility.calculDistance(a.getPosition(), n.getPosition());
                if(plusProche == null || tmpDist<dist) {
                    plusProche = n;
                    dist = tmpDist;
                }
            }
            
            while(dist >0) {
            	score+=1;
            	dist-=10;
            }
            
            //Calcul vers l'avant
            if(poss == 1) {
                if(posJouBall.getColonne()<a.getPosition().getColonne()) {
                    score+=15;
                }
            }
            else {
                if(posJouBall.getColonne()>a.getPosition().getColonne()) {
                    score+=15;
                }
            }

            
            
            //Calcul Distance Passe
            int distPass = Utility.calculDistance(posJouBall, a.getPosition());
            
            if(distPass<=25) {
            	score-=200;
            }
            
            while(distPass>0) {
            	score-=2;
            	distPass-=10;
            }

            classement.put(score, a);
        }

        int min = -999;
        for(int s: classement.keySet()) {
            int tmpMin = s;
            if(tmpMin>min) {
                min = tmpMin;
            }
        }
        return classement.get(min);
    }
	/**
	 * Methode appelant le visiteur
	 * 
	 * @param array le nombre d'action
	 * @param real la realisation des actions
	 * @return
	 * @throws ArretException l'exception pour un arret lors d'un tir
	 * @throws InterceptException l'exception pour l'interception du ballon
	 * @throws ButException l'exception pour un but
	 * @throws FauteException l'exception pour une faut via une interception
	 * @throws SixMetreException l'exception pour un tir rate
	 * @throws HorsJeuException l'exception pour un hors jeu un joueur trop avance
	 * @throws CornerException l'exception pour un arret louper mais decaler
	 */
	private ArrayList<Action> fonctVisi(ArrayList<Action> array, RealActionVisitor real) throws InterceptException, ButException, ArretException,  FauteException, SixMetreException, HorsJeuException, CornerException{
		try {
		Iterator<Action> itr = array.iterator();
		while(itr.hasNext()) {
			Action act = itr.next();
			if(act.accept(real)) {
				itr.remove();
			}
		}
		return array;
		}
		catch (InterceptException e) {
			throw new InterceptException();
		}catch (ButException e) {
			throw new ButException();
		} catch (ArretException e) {
			throw new ArretException(e.getAction());
		} catch (FauteException e) {
			throw new FauteException(e.getFaute());
		} catch (SixMetreException e) {
			throw new SixMetreException();
		} catch (HorsJeuException e) {
			throw new HorsJeuException(e.getHJ());
		} catch (CornerException e) {
			throw new CornerException(e.getCorner());
		}
	}
	/**
	 * Methode permettant d'enlever les actions de la balle
	 */
	public void flushAction() {
		actionsBallon.clear();
	}
	
	public ArrayList<HashMap<String, Integer>> getStatsEquipes(){
		statsEquipes.add(statsMatch1);
		statsEquipes.add(statsMatch2);
		return statsEquipes;
	}
}
