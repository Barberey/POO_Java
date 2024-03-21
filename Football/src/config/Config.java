package config;

import java.awt.Dimension;
import java.awt.Toolkit;
/**
 * Classe contenant toutes les configurations utiles
 * 
 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
 *
 */
public class Config {
	/**
	 * attribut {@link Integer} BLOCK_SIZE la taille d'une case
	 */
	public static final int BLOCK_SIZE = 2;
	/**
	 * attribut {@link Integer} PLAYER_SIZE la taille des joueurs
	 */
	public static final int PLAYER_SIZE = 13;
	/**
	 * attribut {@link Integer} GAME_SPEED la vitesse du jeu
	 */
	public static final int GAME_SPEED = 60;
	/**
	 * attribut {@link Dimension} screenSize la taille d'ecran de chacun
	 */
	public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	/**
	 * attribut {@link Integer} WINDOW_WIDTH la largeur de la fenetre
	 */
	public static final int WINDOW_WIDTH = (int)screenSize.getWidth()-100;
	/**
	 * attribut {@link Integer} WINDOW_HEIGHT la hauteur de la fenetre
	 */
	public static final int WINDOW_HEIGHT = (int)screenSize.getHeight()-100;
	/**
	 * attribut {@link Integer} TERRAIN_HEIGHT la hauteur du terrain
	 */
	public static final int TERRAIN_HEIGHT = 300;
	/**
	 * attribut {@link Integer} TERRAIN_WIDTH la largeur du terrain
	 */
	public static final int TERRAIN_WIDTH = (int)(TERRAIN_HEIGHT*1.5);
	/**
	 * attribut {@link Integer} TAILLE_ENTRE la taille entre les cages diviser par deux
	 */
	public static final int TAILLE_ENTRE = (int) (TERRAIN_HEIGHT*(0.1076470588235294))/2;
	/**
	 * attribut {@link Integer} POTO_BAS l'emplacement du poteau du bas 
	 */
	public static final int POTO_BAS = (TERRAIN_HEIGHT/2) + TAILLE_ENTRE;
	/**
	 * attribut {@link Integer} POTO_HAUT l'emplacement du poteau du haut
	 */
	public static final int POTO_HAUT = (TERRAIN_HEIGHT/2) - TAILLE_ENTRE;
}
