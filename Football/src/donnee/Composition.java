package donnee;

import java.util.HashMap;
/**
 * Classe de donnee permettant d'avoir la composition d'une equipe
 * 
 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
 *
 */
public class Composition {
	/**
	 * attribut {@link HashMap} nbJoueurPrPos qui aura la composition de l'equipe que l'on va choisir
	 */
	private HashMap<String, Integer> nbJoueurPrPos = new HashMap<>();
	/**
	 * Methode permettant de rentrer dans la HashMap le nombre d'attaquants, de milieux, de defenseurs et de gardien
	 * 
	 * @param nbAtt {@link Integer} le nombre d'attaquants que l'on a choisi
	 * @param nbMil {@link Integer} le nombre de milieux qu'on a choisi
	 * @param nbDef {@link Integer} le nombre de defenseurs que l'on a choisi
	 * @param nbGar {@link Integer} le nombre de gardien que l'on a choisi
	 */
	public Composition(int nbAtt, int nbMil ,int nbDef, int nbGar) {
		if((nbAtt+nbMil+nbDef+nbGar)==11) {
			nbJoueurPrPos.put("ATT", nbAtt);
			nbJoueurPrPos.put("MIL", nbMil);
			nbJoueurPrPos.put("DEF", nbDef);
			nbJoueurPrPos.put("GAR", nbGar);
		}
	}
	/**
	 * Getter de {@link #nbJoueurPrPos}
	 * 
	 * @return nbJoueurPrPos la {@link HashMap} contenant la composition
	 */
	public HashMap<String, Integer> getNbJoueurPrPos() {
		return nbJoueurPrPos;
	}
}
