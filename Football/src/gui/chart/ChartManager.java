package gui.chart;

import java.util.ArrayList;
import java.util.HashMap;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import donnee.Equipe;
import donnee.Joueur;
import donnee.Statistique;
/**
 * Classe permettant de gerer le creation des differents panneaux statistiques
 * 
 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
 *
 */
public class ChartManager {
	/**
	 * Attribut de type {@link Equipe} pour la premiere equipe
	 */
	private Equipe equipe1;
	/**
	 * Attribut de type {@link Equipe} pour la seconde equipe
	 */
	private Equipe equipe2;
	/**
	 * Attribut de type {@link ArrayList} de {@link HashMap} pour les differentes statistiques en match
	 */
	private ArrayList<HashMap<String, Integer>> array;
	
	/**
	 * Constructeur de la classe pour la generation des comparaisons de stat afficher pendant le match
	 * 
	 * @param equipe1
	 * @param equipe2
	 */
	public ChartManager(Equipe equipe1, Equipe equipe2) {
		this.equipe1 = equipe1;
		this.equipe2 = equipe2;
	}
	/**
	 * Constructeur pour l'affichage des actions realiser pendant le match
	 * 
	 * @param array
	 */
	public ChartManager(ArrayList<HashMap<String, Integer>> array) {
		this.array = array;
	}

	
	/**
	 * Methode qui renvoie un graphique pour le comparaison des deux equipes
	 * 
	 * @return {@link JFreeChart} CountBar qui compare les stats moyennes des equipes
	 */
	public JFreeChart getTypeCountBarStatsEquipes() {
		HashMap<String, Double> moyStatEquipe1 = calculMoyStat(equipe1.getTitulaires());
		HashMap<String, Double> moyStatEquipe2 = calculMoyStat(equipe2.getTitulaires());
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.setValue(moyStatEquipe1.get("tir"), "Equipe 1", "Tir");
		dataset.setValue(moyStatEquipe1.get("pass"), "Equipe 1", "Passe");
		dataset.setValue(moyStatEquipe1.get("def"), "Equipe 1", "Defense");
		dataset.setValue(moyStatEquipe1.get("vit"), "Equipe 1", "Vitesse");

		dataset.setValue(moyStatEquipe2.get("tir"), "Equipe 2", "Tir");
		dataset.setValue(moyStatEquipe2.get("pass"), "Equipe 2", "Passe");
		dataset.setValue(moyStatEquipe2.get("def"), "Equipe 2", "Defense");
		dataset.setValue(moyStatEquipe2.get("vit"), "Equipe 2", "Vitesse");
		return ChartFactory.createBarChart("", "Comparaison Stats", "Stats (x10)", dataset, PlotOrientation.HORIZONTAL, true, true, false);
	}
	
	/**
	 * Methode qui renvoi un graphique sur la comparaison es actions realise pendant le match
	 * 
	 * @return {@link JFreeChart} StackedBar qui montre les differentes actions des deux equipes
	 */
	public JFreeChart getTypeStackedBarStatsMatch() {
		HashMap<String, Integer> statEquipe1 = array.get(0);
		HashMap<String, Integer> statEquipe2 = array.get(1);
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.setValue(statEquipe1.get("tir"), "Equipe 1", "Tir");
		dataset.setValue(statEquipe1.get("passe"), "Equipe 1", "Passe");
		dataset.setValue(statEquipe1.get("inter"), "Equipe 1", "Interception");
		dataset.setValue(statEquipe1.get("faute"), "Equipe 1", "Faute");
		
		dataset.setValue(statEquipe2.get("tir"), "Equipe 2", "Tir");
		dataset.setValue(statEquipe2.get("passe"), "Equipe 2", "Passe");
		dataset.setValue(statEquipe2.get("inter"), "Equipe 2", "Interception");
		dataset.setValue(statEquipe2.get("faute"), "Equipe 2", "Faute");
		
		return ChartFactory.createStackedBarChart("Graphe Stats","Categories", "", dataset, PlotOrientation.HORIZONTAL, true, true,false);
	}
	
	
	/**
	 * Methode qui calcule les moyennes des stats d'un effectif de {@link Joueur}
	 * 
	 * @param arr {@link ArrayList} contenant les titulairs d'une equipe
	 * @return {@link HashMap} contenant les stats et leur moyenne au sein de l'equipe
	 */
	private HashMap<String, Double> calculMoyStat(ArrayList<Joueur> arr) {
		HashMap<String, Double> map = new HashMap<>();
		int totalTir = 0;
		int totalPass = 0;
		int totalDef = 0;
		int totalVit = 0;
		for(Joueur j : arr) {
			Statistique stat = j.getStats();
			totalTir += stat.getTir();
			totalPass += stat.getPasse();
			totalDef += stat.getDef();
			totalVit += stat.getVitesse();
		}
		map.put("tir", (totalTir/arr.size())/10.0);
		map.put("pass", (totalPass/arr.size())/10.0);
		map.put("def", (totalDef/arr.size())/10.0);
		map.put("vit", (totalVit/arr.size())/10.0);
		return map;
	}
}
