package moteur;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import config.Config;
import donnee.Ballon;
import donnee.Case;
import donnee.Grille;
import donnee.Joueur;
import donnee.ZoneDepl;

/**
 * Classe qui regroupe differente fonction utiles
 * 
 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
 *
 */
public class Utility {

	/**
	 * Methode permetant la lecture d'image a partir du chemin
	 * 
	 * @param filePath
	 * @return {@link Image}
	 */
	public static Image readImage(String filePath) {
		try {
			return ImageIO.read(new File(filePath));
		} catch (IOException e) {
			System.err.println("-- Can not read the image file ! --");
			return null;
		}
	}
	
	/**
	 * Methode qui calcule le coefficient pour la trajectoire d'un deplacement
	 * 
	 * @param oldPos
	 * @param cible
	 * @return {@link Integer} le coeff
	 */
	public static int calculCoeff(Case oldPos, Case cible) {
		int coeff = 0;
		if (oldPos.getColonne()-cible.getColonne()!=0){
			coeff = (oldPos.getLigne()-cible.getLigne())*10/(oldPos.getColonne()-cible.getColonne());
		}
		return coeff;
	}
	
	/**
	 * Methode qui gere le deplacement 
	 * 
	 * @param grille
	 * @param oldPos
	 * @param cible
	 * @param coeff
	 * @return {@link Case} qui est la prochaine case du deplacement
	 */
	public static Case calculDeplacement(Grille grille, Case oldPos, Case cible, int coeff) {
		
		int nouvLigne;
		int nouvCol;
		
		if(oldPos.getColonne() < cible.getColonne()) {
			nouvCol = oldPos.getColonne()+2;
			if(oldPos.getLigne() < cible.getLigne() || oldPos.getLigne() > cible.getLigne()) {
				if (coeff>1 && coeff<6) {
					nouvLigne = oldPos.getLigne()+1;
					nouvCol = oldPos.getColonne()+2;
				} else if (coeff<-1 && coeff>-6) {
					nouvLigne = oldPos.getLigne()-1;
					nouvCol = oldPos.getColonne()+2;
				} else if (coeff<-300 || coeff>300){
					nouvLigne = oldPos.getLigne()+(1*(coeff/100));
				} else if (coeff<-100 || coeff>100){
					nouvLigne = oldPos.getLigne()+(1*(coeff/50));
					nouvCol = oldPos.getColonne()+1;
				} else if (coeff<-20 || coeff>20){
					nouvLigne = oldPos.getLigne()+(1*(coeff/10));
					nouvCol = oldPos.getColonne()+1;
				} else {
					nouvLigne = oldPos.getLigne()+(1*(coeff/6));
					nouvCol = oldPos.getColonne()+1;
				}
			}
			else {
				nouvLigne = oldPos.getLigne();
				nouvCol = oldPos.getColonne()+1;
			}
		}
		else if (oldPos.getColonne() > cible.getColonne()) {
			nouvCol = oldPos.getColonne()-2;
			if(oldPos.getLigne() < cible.getLigne() || oldPos.getLigne() > cible.getLigne()) {
				if (coeff>1 && coeff<6) {
					nouvLigne = oldPos.getLigne()-1;
					nouvCol = oldPos.getColonne()-2;
				}else if (coeff<-1 && coeff>-6) {
					nouvLigne = oldPos.getLigne()+1;
					nouvCol = oldPos.getColonne()-2;
				} else if (coeff<-300 || coeff>300){
					nouvLigne = oldPos.getLigne()-(1*(coeff/100));
				} else if (coeff<-100 || coeff>100){
					nouvLigne = oldPos.getLigne()-(1*(coeff/50));
					nouvCol = oldPos.getColonne()-1;
				}else if (coeff<-20 || coeff>20){
					nouvLigne = oldPos.getLigne()-(1*(coeff/10));
					nouvCol = oldPos.getColonne()-1;
				} else {
					nouvLigne = oldPos.getLigne()-(1*(coeff/6));
					nouvCol = oldPos.getColonne()-1;
				}
			}
			else {
				nouvLigne = oldPos.getLigne();
				nouvCol = oldPos.getColonne()-1;
			}
		}
		else {
			if (oldPos.getLigne()>cible.getLigne()) {
				nouvLigne = oldPos.getLigne()-1;
			} else {
				nouvLigne = oldPos.getLigne()+1;
			}
			nouvCol = oldPos.getColonne();
		}
		
		return grille.getCase(nouvLigne, nouvCol);
	}
	
	
	/**
	 * Methode pour calculer le positionnement des {@link Joueur} sur le terrain en fonction de la composition
	 * 
	 * @param joueurs
	 * @param colonne
	 * @param projectArr
	 * @param projectAv
	 * @return
	 */
	public static ArrayList<Joueur> calculPositionJoueur(ArrayList<Joueur> joueurs, int colonne, int projectArr, int projectAv){
		ArrayList<Joueur> fait = new ArrayList<>();
		int ecartLigne;
		
		int ligneMid = Config.TERRAIN_HEIGHT/2;
		int ecartMid = Config.TERRAIN_HEIGHT/10;
		
		if(joueurs.size()%2!=0) {
			ecartLigne = Config.TERRAIN_HEIGHT/joueurs.size();
			for(int i = 0; i<joueurs.size();i++) {
				if(i==0) {
					Joueur tmp = joueurs.get(i);
					tmp.setPosition(new Case(ligneMid, colonne));
					tmp.setPosTact(new Case(ligneMid, colonne));
					fait.add(tmp);
				}
				else if(i%2!=0) {
					Joueur tmp = joueurs.get(i);
					tmp.setPosition(new Case(ligneMid+ecartLigne,colonne));
					tmp.setPosTact(new Case(ligneMid+ecartLigne,colonne));
					fait.add(tmp);
				}
				else if(i%2==0) {
					Joueur tmp = joueurs.get(i);
					tmp.setPosition(new Case(ligneMid-ecartLigne,colonne));
					tmp.setPosTact(new Case(ligneMid-ecartLigne,colonne));
					fait.add(tmp);
					ecartLigne+=ecartLigne;
				}
			}
		}
		else {
			ecartLigne = 0;
			for(int i = 0; i<joueurs.size();i++) {
				if(i%2!=0) {
					Joueur tmp = joueurs.get(i);
					tmp.setPosition(new Case(ligneMid+ecartMid+ecartLigne,colonne));
					tmp.setPosTact(new Case(ligneMid+ecartMid+ecartLigne,colonne));
					fait.add(tmp);
					ecartLigne+=Config.TERRAIN_HEIGHT/joueurs.size();
				}
				else if(i%2==0) {
					Joueur tmp = joueurs.get(i);
					tmp.setPosition(new Case(ligneMid-ecartMid-ecartLigne,colonne));
					tmp.setPosTact(new Case(ligneMid-ecartMid-ecartLigne,colonne));
					fait.add(tmp);
				}
			}
		}
		creationZoneDepl(fait, projectArr, projectAv);
		return fait;
	}

	/**
	 * Creation et calcul de la zone de deplacement individuel des {@link Joueur}
	 * 
	 * @param joueurs
	 * @param projectArr
	 * @param projectAv
	 */
	private static void creationZoneDepl(ArrayList<Joueur> joueurs, int projectArr, int projectAv) {
		if(joueurs.size()%2!=0) {
			for(int i = 0; i<joueurs.size();i++){
				Joueur j = joueurs.get(i);
				if(i == 0) {
					if(joueurs.size()==1) {
						j.setZonedpl(new ZoneDepl(projectAv, projectArr, (Config.TERRAIN_HEIGHT/2)-10, (Config.TERRAIN_HEIGHT/2)-10));
					}
					else {
						j.setZonedpl(new ZoneDepl(projectAv, projectArr, (Config.TERRAIN_HEIGHT/10)/2, (Config.TERRAIN_HEIGHT/10)/2));
					}
				}
				else if((i!=0)&&(i>=(joueurs.size()-2))){
					if(i%2==0) {
						j.setZonedpl(new ZoneDepl(projectAv, projectArr, j.getPosition().getLigne()-10, (Config.TERRAIN_HEIGHT/2)-10));
					}
					else {
						j.setZonedpl(new ZoneDepl(projectAv, projectArr, (Config.TERRAIN_HEIGHT/2)-10, (Config.TERRAIN_HEIGHT-j.getPosition().getLigne())-10));
					}
				}
				else {
					j.setZonedpl(new ZoneDepl(projectAv, projectArr, (Config.TERRAIN_HEIGHT/10)/2, (Config.TERRAIN_HEIGHT/10)/2));
				}
			}
		}
		else {
			for(int i = 0; i<joueurs.size();i++) {
				Joueur j = joueurs.get(i);
				if(i>=(joueurs.size()-2)) {
					if(i%2==0) {
						j.setZonedpl(new ZoneDepl(projectAv, projectArr,j.getPosition().getLigne() - (Config.TERRAIN_HEIGHT/2) , Config.TERRAIN_HEIGHT - j.getPosition().getLigne()));
					}
					else {
						j.setZonedpl(new ZoneDepl(projectAv, projectArr, j.getPosition().getLigne()-10,(Config.TERRAIN_HEIGHT/10)));
					}
				}
				else {
					j.setZonedpl(new ZoneDepl(projectAv, projectArr, (Config.TERRAIN_HEIGHT/10)/2, (Config.TERRAIN_HEIGHT/10)/2));
				}
			}
		}
	}
	
	/**
	 * Methode qui calcule la distance entre deux cases
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static int calculDistance(Case x, Case y) {
		double etapInter = Math.pow((y.getLigne()-x.getLigne()), 2)+Math.pow((y.getColonne()-x.getColonne()), 2);
		return (int)(Math.sqrt(etapInter));
	}
	
	public static int getRandomNumber(int min, int max) {
		return (int) (Math.random() * (max + 1 - min)) + min;
	}
}