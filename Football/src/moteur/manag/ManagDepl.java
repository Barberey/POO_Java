package moteur.manag;

import java.util.ArrayList;

import config.Config;
import donnee.Ballon;
import donnee.Case;
import donnee.Equipe;
import donnee.Joueur;
import donnee.Strategie;
import moteur.Utility;

/**
 * Classe qui permet de déplacer les joueurs sur le terrain
 * 
 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
 *
 */
public class ManagDepl extends ManagAction{

	/**
	 * attribut strat1 contenant la stratégie de l'equipe 1
	 */
	private Strategie strat1;
	/**
	 * attribut strat2 contenant la strtégie de l'equipe 2
	 */
	private Strategie strat2;
	/**
	 * attribut ballon contenant le ballon
	 */
	private Ballon ballon;
	/**
	 * attribut centreur contenant le joueur qui fait un centre
	 */
	private Joueur centreur = null;
	
	/**
	 * Constructeur de la classe
	 * 
	 * @param equipe1 la premiere equipe
	 * @param equipe2 la deuxieme equipe
	 * @param poss entier 0 ou 1 pour determiner quelle equipe a la possession
	 * @param ballon le ballon
	 */
	public ManagDepl(Equipe equipe1, Equipe equipe2, int poss, Ballon ballon) {
		super(equipe1,equipe2,poss);
		strat1=equipe1.getStrats();
		strat2=equipe2.getStrats();
		this.ballon = ballon;
	}
	
	/**
	 * classe permettant le calcul de tout les deplacement des joueurs, en appelant les autres méthode de déplacement
	 */
	public void calcul() {
		if(centreur!=null) {
			replacementJoueurZoneTactique(centreur);
			if(centreur.getPosition().equals(centreur.getPosTact())) {
				centreur = null;
			}
		}
		int poss = getPoss();
        if(poss == 1) {
            ArrayList<Joueur> joueursPoss = getEquipe1().getTitulaires();
            ArrayList<Joueur> joueursNonPoss = getEquipe2().getTitulaires();

            avancAll(joueursPoss, strat1, joueursNonPoss, strat2, getPoss());
            mouvJoueurPoss(joueursPoss, joueursNonPoss);
            //mouvJoueurNoPoss(getEquipe1(), getEquipe2());


            if(!strat1.avant()) {
            mouvJoueurPoss(joueursPoss, joueursNonPoss);
           	mouvJoueurPoss(joueursPoss, joueursNonPoss);
           	
            }

            if(!strat2.avant()){
            	mouvJoueurNoPoss(getEquipe1(), getEquipe2());
            }
        }
        else {
            ArrayList<Joueur> joueursPoss = getEquipe2().getTitulaires();
            ArrayList<Joueur> joueursNonPoss = getEquipe1().getTitulaires();


            avancAll(joueursPoss, strat2, joueursNonPoss, strat1, getPoss());
            mouvJoueurAdversePoss(joueursPoss, joueursNonPoss);
            //mouvJoueurNoPoss(getEquipe2(), getEquipe1());

            if(!strat2.arriere()){
            	mouvJoueurAdversePoss(joueursPoss, joueursNonPoss);
            	mouvJoueurAdversePoss(joueursPoss, joueursNonPoss);
            }

            if(!strat1.arriere()){
            	mouvJoueurNoPoss(getEquipe2(), getEquipe1());
            }
        }
	}
	
	/**
	 * methode permettant d'attribuer le role de centreur a un joueur qui va centrer
	 * 
	 * @param centreur celui qui fera un centre
	 */
	public void setCentreur(Joueur centreur) {
		this.centreur = centreur;
	}
    
	/**
	 * methode qui permet de faire monter tout le bloc de l'equipe lorsqu'elle recupere le ballon, et inversement lorsqu'elle perd le ballon, faire descendre tout le bloc de l'equipe
	 * 
	 * @param joueursPoss equipe ayant la possession du ballon
	 * @param stratPoss strategie des joueurs de l'equipe qui possede le ballon
	 * @param joueursNonPoss equipe n'ayant pas la possession du ballon
	 * @param stratNonPoss strategie des joueurs de l'equipe qui ne possede pas le ballon
	 * @param poss entier qui determine quelle equipe a la possession, par un 0 ou 1
	 */
    private void avancAll(ArrayList<Joueur> joueursPoss, Strategie stratPoss,ArrayList<Joueur> joueursNonPoss, Strategie stratNonPoss, int poss) {
        if(poss == 1) {
        	if(stratPoss.avant()) {
        		for(Joueur j : joueursPoss) {
                	replacementJoueurZoneTactique(j);
                    if(j.getPoste()!="GAR") {
                        Case oldPose = j.getPosition();
                        j.setPosition(new Case(oldPose.getLigne(),oldPose.getColonne()+1));
                        j.avancPosTact();
                    }
                }
                stratPoss.increm();
            }
        	
        	if(stratNonPoss.avant()) {
        		for(Joueur j : joueursNonPoss) {
                    if(j.getPoste()!="GAR") {
                        Case oldPose = j.getPosition();
                        j.setPosition(new Case(oldPose.getLigne(),oldPose.getColonne()+1));
                        j.avancPosTact();
                    	replacementJoueurZoneTactique(j);
                    }
                }
                stratNonPoss.increm();
        	}
        }
        else {
        	if(stratPoss.arriere()) {
        		for(Joueur j : joueursPoss) {
                    if(j.getPoste()!="GAR") {
                        Case oldPose = j.getPosition();
                        j.setPosition(new Case(oldPose.getLigne(),oldPose.getColonne()-1));
                        j.reculPosTact();
                    	replacementJoueurZoneTactique(j);
                    }
                }
                stratPoss.decrem();
            }
        	
        	if(stratNonPoss.arriere()) {
        		for(Joueur j : joueursNonPoss) {
                    if(j.getPoste()!="GAR") {
                        Case oldPose = j.getPosition();
                        j.setPosition(new Case(oldPose.getLigne(),oldPose.getColonne()-1));
                        j.reculPosTact();
                    	replacementJoueurZoneTactique(j);
                    }
                }
                stratNonPoss.decrem();
        	}
        }
    	
            
    }
    
    /**
     * methode permettant l'actualisation de la strategie
     */
    public void updateStrat() {
		strat1=getEquipe1().getStrats();
		strat2=getEquipe2().getStrats(); 
	}
    
    /**
     * methode permettant les deplacement de l'equipe de gauche sur le terrain, lorsqu'elle est en possession du ballon
     * 
     * @param JoueursEq1 equipe qui possede le ballon
     * @param JoueursEq2 equipe qui ne possede pas le ballon
     */
	public void mouvJoueurPoss(ArrayList<Joueur> JoueursEq1, ArrayList<Joueur> JoueursEq2) {
		for(Joueur j1 : JoueursEq1) {
			if(j1.getPoste()!="GAR") {
				
				for(Joueur j2 : JoueursEq2) {
					Case oldPose = j1.getPosition();
					
					if(j2.getPoste()=="GAR") {
						if (ballon.getPosition().getColonne() > 430 ) {
							oldPose = j2.getPosition();
							if (ballon.getPosition().getLigne() > j2.getPosition().getLigne() && j2.getPosition().getLigne()>=Config.POTO_HAUT && j2.getPosition().getLigne()<Config.POTO_BAS) {
								j2.setPosition(new Case(oldPose.getLigne()+1,oldPose.getColonne()));
								oldPose = j2.getPosition();
							} else if (ballon.getPosition().getLigne() < j2.getPosition().getLigne() && j2.getPosition().getLigne()>Config.POTO_HAUT && j2.getPosition().getLigne()<=Config.POTO_BAS) {
								j2.setPosition(new Case(oldPose.getLigne()-1,oldPose.getColonne()));
								oldPose = j2.getPosition();
							}
						}else if (ballon.getPosition().getColonne() > 300 ) {
							int centre = 150 ;
							if (ballon.getPosition().getLigne() > centre) {
								oldPose = j2.getPosition();
								int posGardien = centre + ((ballon.getPosition().getLigne() - centre)/10);
								if ((j2.getPosition().getLigne() < posGardien) && (j2.getPosition().getLigne() <= 165)) {
									j2.setPosition(new Case(oldPose.getLigne()+1,oldPose.getColonne()));
									oldPose = j2.getPosition();
								} else if (j2.getPosition().getLigne() > posGardien) {
									j2.setPosition(new Case(oldPose.getLigne()-1,oldPose.getColonne()));
									oldPose = j2.getPosition();
								}
							} else if (ballon.getPosition().getLigne() < centre) {
								oldPose = j2.getPosition();
								int posGardien = centre - ((centre - ballon.getPosition().getLigne())/10);
								if ((j2.getPosition().getLigne() > posGardien) && (j2.getPosition().getLigne() >= 135)) {
									j2.setPosition(new Case(oldPose.getLigne()-1,oldPose.getColonne()));
									oldPose = j2.getPosition();
								} else if (j2.getPosition().getLigne() < posGardien) {
									j2.setPosition(new Case(oldPose.getLigne()+1,oldPose.getColonne()));
									oldPose = j2.getPosition();
								}
							}
						} else {
							replacementJoueurZoneTactique(j2);
						}
					}
					
					
					if(j2.getPoste()!="GAR") {
						
						if (((j1.getPosition().getColonne()-j2.getPosition().getColonne())<60 && (j1.getPosition().getColonne()-j2.getPosition().getColonne())>-60) && ((j1.getPosition().getLigne()-j2.getPosition().getLigne())<60 && (j1.getPosition().getLigne()-j2.getPosition().getLigne())>-60)) {
							
							// si le joueur est marqué de trop proche, il redescend sur le terrain
							if ((j1.getPosTact().getColonne()-j1.getZonedpl().getArriere()<j1.getPosition().getColonne()) && ((((j1.getPosition().getColonne()-j2.getPosition().getColonne())<5) && ((j1.getPosition().getColonne()-j2.getPosition().getColonne())>-5)) && (((j1.getPosition().getLigne()-j2.getPosition().getLigne())<5) && ((j1.getPosition().getLigne()-j2.getPosition().getLigne())>-5)))){
								j1.setPosition(new Case(oldPose.getLigne(),oldPose.getColonne()-1));
								oldPose = j1.getPosition();
							}
							
							//si le joueur est proche d'un ennemi, il s'écarte
							if (((j1.getPosition().getColonne()-j2.getPosition().getColonne())<=15) && ((j1.getPosition().getColonne()-j2.getPosition().getColonne())>=-15)){

								//si il est dans la zone (-15,15) en colonne d'un joueur adverse
								if (j1.getPosition().getColonne()-j2.getPosition().getColonne()==0){
									j1.setPosition(new Case(oldPose.getLigne(),oldPose.getColonne()+getRandomNumber(-1,1)));
									oldPose = j1.getPosition();
								}
								if ((j1.getPosition().getColonne()-j2.getPosition().getColonne()<=15) && ((j1.getPosition().getColonne()-j2.getPosition().getColonne())>0)) {
									j1.setPosition(new Case(oldPose.getLigne(),oldPose.getColonne()+1));
									oldPose = j1.getPosition();
								}else if ((j1.getPosition().getColonne()-j2.getPosition().getColonne()>=-15) && ((j1.getPosition().getColonne()-j2.getPosition().getColonne())<0)){
									j1.setPosition(new Case(oldPose.getLigne(),oldPose.getColonne()-1));
									oldPose = j1.getPosition();
								}
								

								//reste dans sa zone tactique en colonne
								if ((j1.getPosTact().getColonne()+j1.getZonedpl().getAvant())<=j1.getPosition().getColonne()) {
									j1.setPosition(new Case(oldPose.getLigne(),oldPose.getColonne()-1));
								}else if ((j1.getPosTact().getColonne()-j1.getZonedpl().getArriere())>=j1.getPosition().getColonne()) {
									j1.setPosition(new Case(oldPose.getLigne(),oldPose.getColonne()+1));
								}
								oldPose = j1.getPosition();
								
								
							}
							
							if (((j1.getPosition().getLigne()-j2.getPosition().getLigne())<=20) && ((j1.getPosition().getLigne()-j2.getPosition().getLigne())>=-20)) {
							
								//si il est dans la zone (-20,20) en ligne d'un joueur adverse
								if ((j1.getPosition().getLigne()-j2.getPosition().getLigne())==0){
									j1.setPosition(new Case(oldPose.getLigne()+getRandomNumber(-1,1),oldPose.getColonne()));
									oldPose = j1.getPosition();
								}
								if (((j1.getPosition().getLigne()-j2.getPosition().getLigne())<=20) && ((j1.getPosition().getLigne()-j2.getPosition().getLigne())>0)) {
									j1.setPosition(new Case(oldPose.getLigne()+1,oldPose.getColonne()));
									oldPose = j1.getPosition();
								}else if (((j1.getPosition().getLigne()-j2.getPosition().getLigne())>=-20) && ((j1.getPosition().getLigne()-j2.getPosition().getLigne())<0)) {
									j1.setPosition(new Case(oldPose.getLigne()-1,oldPose.getColonne()));
									oldPose = j1.getPosition();
								}
								
								
								//reste dans sa zone tactique en ligne
								if ((j1.getPosTact().getLigne()-j1.getZonedpl().getHaut())>=j1.getPosition().getLigne()) {
									j1.setPosition(new Case(oldPose.getLigne()+1,oldPose.getColonne()));
								}else if ((j1.getPosTact().getLigne()+j1.getZonedpl().getBas())<=j1.getPosition().getLigne()) {
									j1.setPosition(new Case(oldPose.getLigne()-1,oldPose.getColonne()));
								}
								oldPose = j1.getPosition();
								
							}	
							
						
							// si suffisement loin en ligne, monte en colonne
							else if ((j1.getPoste()!="DEF") && ((j1.getPosTact().getColonne()+j1.getZonedpl().getAvant()>j1.getPosition().getColonne()) && (((j1.getPosition().getLigne()-j2.getPosition().getLigne())>=20) || ((j1.getPosition().getLigne()-j2.getPosition().getLigne())<=-20)))){
								j1.setPosition(new Case(oldPose.getLigne(),oldPose.getColonne()+1));
								oldPose = j1.getPosition();
							}
						}
						
					}
				}
			}
		}
	}
	
	
	/**
     * methode permettant les deplacement de l'equipe de droite sur le terrain, lorsqu'elle est en possession du ballon
     * 
     * @param JoueursEq1 equipe qui ne possede pas le ballon
     * @param JoueursEq2 equipe qui possede le ballon
     */
	public void mouvJoueurAdversePoss(ArrayList<Joueur> JoueursEq1, ArrayList<Joueur> JoueursEq2) {
		for(Joueur j1 : JoueursEq1) {
			if(j1.getPoste()!="GAR") {
				
				for(Joueur j2 : JoueursEq2) {
					Case oldPose = j1.getPosition();
					
					if(j2.getPoste()=="GAR") {
						 if (ballon.getPosition().getColonne() < 20 ) {
							 oldPose = j2.getPosition();
							if (ballon.getPosition().getLigne() > j2.getPosition().getLigne() && j2.getPosition().getLigne()>=Config.POTO_HAUT && j2.getPosition().getLigne()<Config.POTO_BAS) {
								j2.setPosition(new Case(oldPose.getLigne()+1,oldPose.getColonne()));
								oldPose = j2.getPosition();
							} else if (ballon.getPosition().getLigne() < j2.getPosition().getLigne() && j2.getPosition().getLigne()>Config.POTO_HAUT && j2.getPosition().getLigne()<=Config.POTO_BAS) {
								j2.setPosition(new Case(oldPose.getLigne()-1,oldPose.getColonne()));
								oldPose = j2.getPosition();
							}
						}else if (ballon.getPosition().getColonne() < 150 ) {
							int centre = 150 ;
							if (ballon.getPosition().getLigne() > centre) {
								oldPose = j2.getPosition();
								int posGardien = centre + ((ballon.getPosition().getLigne() - centre)/10);
								if ((j2.getPosition().getLigne() < posGardien) && (j2.getPosition().getLigne() <= 165)) {
									j2.setPosition(new Case(oldPose.getLigne()+1,oldPose.getColonne()));
									oldPose = j2.getPosition();
								} else if (j2.getPosition().getLigne() > posGardien) {
									j2.setPosition(new Case(oldPose.getLigne()-1,oldPose.getColonne()));
									oldPose = j2.getPosition();
								}
							} else if (ballon.getPosition().getLigne() < centre) {
								oldPose = j2.getPosition();
								int posGardien = centre - ((centre - ballon.getPosition().getLigne())/10);
								if ((j2.getPosition().getLigne() > posGardien) && (j2.getPosition().getLigne() >= 135)) {
									j2.setPosition(new Case(oldPose.getLigne()-1,oldPose.getColonne()));
									oldPose = j2.getPosition();
								} else if (j2.getPosition().getLigne() < posGardien) {
									j2.setPosition(new Case(oldPose.getLigne()+1,oldPose.getColonne()));
									oldPose = j2.getPosition();
								}
							}
						} else {
							replacementJoueurZoneTactique(j2);
						}
					}
					
					if(j2.getPoste()!="GAR") {
						
						if (((j1.getPosition().getColonne()-j2.getPosition().getColonne())<60 && (j1.getPosition().getColonne()-j2.getPosition().getColonne())>-60) && ((j1.getPosition().getLigne()-j2.getPosition().getLigne())<60 && (j1.getPosition().getLigne()-j2.getPosition().getLigne())>-60)) {
							
							// si le joueur est marqué de trop proche, il redescend sur le terrain
							if ((j1.getPosTact().getColonne()+j1.getZonedpl().getArriere()>j1.getPosition().getColonne()) && ((((j1.getPosition().getColonne()-j2.getPosition().getColonne())<5) && ((j1.getPosition().getColonne()-j2.getPosition().getColonne())>-5)) && (((j1.getPosition().getLigne()-j2.getPosition().getLigne())<5) && ((j1.getPosition().getLigne()-j2.getPosition().getLigne())>-5)))){
								j1.setPosition(new Case(oldPose.getLigne(),oldPose.getColonne()+1));
								oldPose = j1.getPosition();
							}
							
							//si le joueur est proche d'un ennemi, il s'écarte
							if (((j1.getPosition().getColonne()-j2.getPosition().getColonne())<=15) && ((j1.getPosition().getColonne()-j2.getPosition().getColonne())>=-15)){

								//si il est dans la zone (-15,15) en colonne d'un joueur adverse
								if (j1.getPosition().getColonne()-j2.getPosition().getColonne()==0){
									j1.setPosition(new Case(oldPose.getLigne(),oldPose.getColonne()-getRandomNumber(-1,1)));
									oldPose = j1.getPosition();
								}
								if ((j1.getPosition().getColonne()-j2.getPosition().getColonne()<=15) && ((j1.getPosition().getColonne()-j2.getPosition().getColonne())>0)) {
									j1.setPosition(new Case(oldPose.getLigne(),oldPose.getColonne()+1));
									oldPose = j1.getPosition();
								}else if ((j1.getPosition().getColonne()-j2.getPosition().getColonne()>=-15) && ((j1.getPosition().getColonne()-j2.getPosition().getColonne())<0)){
									j1.setPosition(new Case(oldPose.getLigne(),oldPose.getColonne()-1));
									oldPose = j1.getPosition();
								}
								

								//reste dans sa zone tactique en colonne
								if ((j1.getPosTact().getColonne()+j1.getZonedpl().getAvant())<=j1.getPosition().getColonne()) {
									j1.setPosition(new Case(oldPose.getLigne(),oldPose.getColonne()-1));
								}else if ((j1.getPosTact().getColonne()-j1.getZonedpl().getArriere())>=j1.getPosition().getColonne()) {
									j1.setPosition(new Case(oldPose.getLigne(),oldPose.getColonne()+1));
								}
								oldPose = j1.getPosition();
								
								
								
							}

							if (((j1.getPosition().getLigne()-j2.getPosition().getLigne())<=20) && ((j1.getPosition().getLigne()-j2.getPosition().getLigne())>=-20)) {
							
								//si il est dans la zone (-20,20) en ligne d'un joueur adverse
								if ((j1.getPosition().getLigne()-j2.getPosition().getLigne())==0){
									j1.setPosition(new Case(oldPose.getLigne()+getRandomNumber(-1,1),oldPose.getColonne()));
									oldPose = j1.getPosition();
								}
								if (((j1.getPosition().getLigne()-j2.getPosition().getLigne())<=20) && ((j1.getPosition().getLigne()-j2.getPosition().getLigne())>0)) {
									j1.setPosition(new Case(oldPose.getLigne()+1,oldPose.getColonne()));
									oldPose = j1.getPosition();
								}else if (((j1.getPosition().getLigne()-j2.getPosition().getLigne())>=-20) && ((j1.getPosition().getLigne()-j2.getPosition().getLigne())<0)) {
									j1.setPosition(new Case(oldPose.getLigne()-1,oldPose.getColonne()));
									oldPose = j1.getPosition();
								}
								
								
								//reste dans sa zone tactique en ligne
								if ((j1.getPosTact().getLigne()-j1.getZonedpl().getHaut())>=j1.getPosition().getLigne()) {
									j1.setPosition(new Case(oldPose.getLigne()+1,oldPose.getColonne()));
								}else if ((j1.getPosTact().getLigne()+j1.getZonedpl().getBas())<=j1.getPosition().getLigne()) {
									j1.setPosition(new Case(oldPose.getLigne()-1,oldPose.getColonne()));
								}
								oldPose = j1.getPosition();
								
							}	
							
						
							// si suffisement loin en ligne, monte en colonne
							else if ((j1.getPoste()!="DEF") && ((j1.getPosTact().getColonne()+j1.getZonedpl().getAvant()>j1.getPosition().getColonne()) && (((j1.getPosition().getLigne()-j2.getPosition().getLigne())>=20) || ((j1.getPosition().getLigne()-j2.getPosition().getLigne())<=-20)))){
								j1.setPosition(new Case(oldPose.getLigne(),oldPose.getColonne()-1));
								oldPose = j1.getPosition();
								

								//reste dans sa zone tactique en colonne
								if ((j1.getPosTact().getColonne()+j1.getZonedpl().getAvant())<=j1.getPosition().getColonne()) {
									j1.setPosition(new Case(oldPose.getLigne(),oldPose.getColonne()-1));
								}else if ((j1.getPosTact().getColonne()-j1.getZonedpl().getArriere())>=j1.getPosition().getColonne()) {
									j1.setPosition(new Case(oldPose.getLigne(),oldPose.getColonne()+1));
								}
								oldPose = j1.getPosition();
								
								
							}
						}
						
					}
				}
			}
		}
	}
	
	
	
	
	/**
	 * methode qui permet de realiser les deplacements des joueurs de l'equipe qui ne possede pas le ballon afin de réaliser le marquage des joueurs de l'equipe adverse
	 * 
     * @param equip1 equipe qui possede le ballon
     * @param equip2 equipe qui ne possede pas le ballon
	 */
	public void mouvJoueurNoPoss(Equipe equip1, Equipe equip2) {
		ArrayList<Joueur> JoueursEq1 = equip1.getTitulaires();
		ArrayList<Joueur> JoueursEq2 = equip2.getTitulaires();
		ArrayList<Joueur> tmp = new ArrayList<>();
		tmp.addAll(JoueursEq1);
		for(Joueur j2 : JoueursEq2) {
			if(j2.getPoste()!="GAR") {
				JoueursEq1 = tmp;
				for(Joueur j1 : JoueursEq1) {
					Case oldPose = j2.getPosition();
					if(j1.getPoste()!="GAR") {
						
						if (((j1.getPosition().getColonne()-j2.getPosition().getColonne())<200 && (j1.getPosition().getColonne()-j2.getPosition().getColonne())>-200) && ((j1.getPosition().getLigne()-j2.getPosition().getLigne())<70 && (j1.getPosition().getLigne()-j2.getPosition().getLigne())>-70)) {
							
							if  (((j1.getPoste()=="DEF")&&(j2.getPoste()=="ATT"))&&(j1.equals(equip1.getCibleBallon()))){
								
								if ((j2.getPosition().getColonne()-j1.getPosition().getColonne())>=1){
									j2.setPosition(new Case(oldPose.getLigne(),oldPose.getColonne()-1));
									oldPose = j2.getPosition();
								}
								else if ((j2.getPosition().getColonne()-j1.getPosition().getColonne())<=-1){
									j2.setPosition(new Case(oldPose.getLigne(),oldPose.getColonne()+1));
									oldPose = j2.getPosition();
								}
								if ((j2.getPosition().getLigne()-j1.getPosition().getLigne())>=1) {
									j2.setPosition(new Case(oldPose.getLigne()-1,oldPose.getColonne()));
									oldPose = j2.getPosition();
								}
								else if ((j2.getPosition().getLigne()-j1.getPosition().getLigne())<=-1) {
									j2.setPosition(new Case(oldPose.getLigne()+1,oldPose.getColonne()));
									oldPose = j2.getPosition();
								}
							}
							
							if ((j1.getPoste()=="MIL")&&(j2.getPoste()=="MIL") || ((j1.getPoste()=="ATT")&&(j2.getPoste()=="DEF"))){
								
								tmp.remove(j1);
								
								if ((j2.getPosition().getColonne()-j1.getPosition().getColonne())>=1){
									j2.setPosition(new Case(oldPose.getLigne(),oldPose.getColonne()-1));
									oldPose = j2.getPosition();
								}
								else if ((j2.getPosition().getColonne()-j1.getPosition().getColonne())<=-1){
									j2.setPosition(new Case(oldPose.getLigne(),oldPose.getColonne()+1));
									oldPose = j2.getPosition();
								}
								if ((j2.getPosition().getLigne()-j1.getPosition().getLigne())>=1) {
									j2.setPosition(new Case(oldPose.getLigne()-1,oldPose.getColonne()));
									oldPose = j2.getPosition();
								}
								else if ((j2.getPosition().getLigne()-j1.getPosition().getLigne())<=-1) {
									j2.setPosition(new Case(oldPose.getLigne()+1,oldPose.getColonne()));
									oldPose = j2.getPosition();
								}

								break;
							}
							if (!(j1.equals(equip1.getCibleBallon()))){
								//Si le défenseur n'a pas ou ne reçoit pas la balle, l'attaquant se replace sur sa position tactique
								if (j2.getPoste()=="ATT") {
									replacementJoueurZoneTactique(j2);
								}

								//Si le défenseur n'a pas ou ne reçoit pas la balle, le défenseur se replace sur sa position tactique
								if (j1.getPoste()=="DEF") {
									replacementJoueurZoneTactique(j1);
								}
						
							}
						}
					}
				}
			}
		}
	}
	
	
	/**
	 * methode qui permet le replacement d'un joueur dans sa zone tactique
	 * 
	 * @param j1 joueur que l'on souhaite replacer dans sa zone tactique
	 */
	public void replacementJoueurZoneTactique(Joueur j1) {
		Case oldPose = j1.getPosition();
		if (j1.getPosition()!=j1.getPosTact()) {
			if ((j1.getPosition().getColonne()>j1.getPosTact().getColonne())){
				j1.setPosition(new Case(oldPose.getLigne(),oldPose.getColonne()-1));
				oldPose = j1.getPosition();
			}
			else if ((j1.getPosition().getColonne()<j1.getPosTact().getColonne())){
				j1.setPosition(new Case(oldPose.getLigne(),oldPose.getColonne()+1));
				oldPose = j1.getPosition();
			}
			if ((j1.getPosition().getLigne()>j1.getPosTact().getLigne())) {
				j1.setPosition(new Case(oldPose.getLigne()-1,oldPose.getColonne()));
				oldPose = j1.getPosition();
			}
			else if ((j1.getPosition().getLigne()<j1.getPosTact().getLigne())) {
				j1.setPosition(new Case(oldPose.getLigne()+1,oldPose.getColonne()));
				oldPose = j1.getPosition();
			}
		}
	}
	
	/**
	 * methode qui donne un nombre aleatoire entre deux nombres donnés en paramètre
	 * 
	 * @param min entier minimum
	 * @param max entier maximum
	 * @return entier aléatoire entre min et max
	 */
	private int getRandomNumber(int min, int max) {
		return (int) (Math.random() * (max + 1 - min)) + min;
	}
	
}