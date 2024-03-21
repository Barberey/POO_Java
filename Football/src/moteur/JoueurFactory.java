package moteur;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import donnee.Case;
import donnee.Joueur;
import donnee.Statistique;
import log.LoggerUtility;
/**
 * 
 * Factory qui gere la creation des {@link Joueur}
 * 
 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
 *
 */
public class JoueurFactory {
	
	private static Logger logger = LoggerUtility.getLogger(JoueurFactory.class, "text");
	
	/**
	 * Methode de creation de {@link Joueur} appelant {@link #genStat(String)} et {@link #getNom(String)}
	 * 
	 * @param arche
	 * @param pos
	 * @return {@link Joueur}
	 */
	public static Joueur creeJoueur(String arche, Case pos) {
		Statistique stats = genStat(arche);
		String prenom = getNom("src/ressources/prenom.txt");
		String nom = getNom("src/ressources/nom.txt");
		Joueur j = new Joueur(pos, nom, prenom, stats, arche);
		logger.info("Joueur cree :" + j.toString());
		return j;
	}
	
	/**
	 * Methode qui gere la creation des {@link Statistique} des {@link Joueur}
	 * 
	 * @param arche
	 * @return {@link Statistique}
	 */
	private static Statistique genStat(String arche) {
		HashMap<String, Integer> stats = new HashMap<>();
		stats.put("end", 100);
		stats.put("zon", 50);
		stats.put("vit", 50 + getRandomNumber(-10, 40));
		switch (arche) {
		case "Def": {
			stats.put("def", 80 + getRandomNumber(-10, 20));
			stats.put("tir", 30 + getRandomNumber(-10, 20));
			stats.put("pas", 80 + getRandomNumber(-10, 20));
			stats.put("arr", 10 + getRandomNumber(-10, 20));
			break;
		}
		
		case "Att": {
			stats.put("def", 30 + getRandomNumber(-10, 20));
			stats.put("tir", 80 + getRandomNumber(-10, 20));
			stats.put("pas", 65 + getRandomNumber(-10, 20));
			stats.put("arr", 10 + getRandomNumber(-10, 20));
			break;
		}
		case "Mil": {
			stats.put("def", 50 + getRandomNumber(-10, 20));
			stats.put("tir", 50 + getRandomNumber(-10, 20));
			stats.put("pas", 80 + getRandomNumber(-10, 20));
			stats.put("arr", 10 + getRandomNumber(-10, 20));
			break;
		}
		case "Gar": {
			stats.put("def", 40 + getRandomNumber(-10, 20));
			stats.put("tir", 40 + getRandomNumber(-10, 20));
			stats.put("pas", 40 + getRandomNumber(-10, 20));
			stats.put("arr", 75 + getRandomNumber(-10, 20));
			break;
		}
		
		
		default:
			throw new IllegalArgumentException("Unexpected value: " + arche);
		}
		return new Statistique(stats.get("def"), stats.get("tir"), stats.get("pas"), stats.get("zon"), stats.get("end"), stats.get("vit"), stats.get("arr"));
	}
	
	/**
	 * Methode qui recupere des noms aleatoire a partir d'un fichier
	 * 
	 * @param fileName
	 * @return {@link String} Nom recuperer
	 */
	private static String getNom(String fileName) {
		ArrayList<String> noms = new ArrayList<>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = reader.readLine()) != null) {
				noms.add(line);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return noms.get(getRandomNumber(0, noms.size()-1));
	}
	
	/**
	 * Methode qui retourne un nombre aleatoire entre min et max
	 * 
	 * @param min
	 * @param max
	 * @return {@link Integer}
	 */
	private static int getRandomNumber(int min, int max) {
		return (int) (Math.random() * (max + 1 - min)) + min;
	}
}
