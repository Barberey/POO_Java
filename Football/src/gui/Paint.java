package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import config.Config;
import donnee.Ballon;
import donnee.Case;
import donnee.Equipe;
import donnee.Grille;
import donnee.Joueur;
import moteur.Utility;


/**
 * Classe gerant le dessin des differents elements a afficher
 * 
 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
 *
 */
public class Paint {
	/**
	 * Methode pour dessiner la grille
	 * 
	 * @param grille
	 * @param graphics
	 */
	public void paint(Grille grille, Graphics graphics) {
		int blockSize = Config.BLOCK_SIZE;
		Case[][] blocks = grille.getGrille();

		for (int lineIndex = 0; lineIndex < grille.getLigneMax(); lineIndex++) {
			for (int columnIndex = 0; columnIndex < grille.getColonneMax(); columnIndex++) {
				Case block = blocks[lineIndex][columnIndex];

				if ((lineIndex + columnIndex) % 2 == 0) {
					graphics.setColor(Color.GRAY);
					graphics.fillRect(block.getColonne() * blockSize, block.getLigne() * blockSize, blockSize, blockSize);
				}
			}
		}
	}
	
	/**
	 * Methode qui dessine le terrain
	 * 
	 * @param graphics
	 */
	public void paint(Graphics2D graphics) {
		int blockSize = Config.BLOCK_SIZE;
		graphics.drawImage(Utility.readImage("src/ressources/terrain.png"), 0, 0, blockSize*Config.TERRAIN_WIDTH, blockSize*Config.TERRAIN_HEIGHT, null, null);
	}

	/**
	 * Methode qui permet le dessin d'une equipe complete
	 * 
	 * @param equipe
	 * @param graphics
	 */
	public void paint(Equipe equipe, Graphics graphics) {
		
		for(Joueur joueur : equipe.getTitulaires()) {
			Case position = joueur.getPosition();
			int blockSize = Config.BLOCK_SIZE;

			int y = position.getLigne();
			int x = position.getColonne();


			graphics.drawImage(Utility.readImage(equipe.getImg()), (x-Config.PLAYER_SIZE/2)*blockSize, (y-Config.PLAYER_SIZE/2)*blockSize, blockSize*Config.PLAYER_SIZE, blockSize*Config.PLAYER_SIZE, null, null);

		}
	}
	/**
	 * Methode pour dessiner le ballon
	 * 
	 * @param ballon
	 * @param graphics
	 */
	public void paint(Ballon ballon, Graphics2D graphics) {
		Case position = ballon.getPosition();
		int blockSize = Config.BLOCK_SIZE;

		int y = position.getLigne();
		int x = position.getColonne();

		graphics.drawImage(Utility.readImage("src/ressources/ballon.png"), x*blockSize, y*blockSize, blockSize*8, blockSize*8, null, null);
	}
}
