package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import config.Config;
/**
 * Classe permettant l'affichage des regles
 * 
 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
 *
 */
public class Regles extends JFrame {
	/**
	 * constructeur de la classe
	 * 
	 * @param title le titre de la frame
	 */
	public Regles(String title) {
		super(title);
		Container contain = getContentPane();
        setAlwaysOnTop(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
		setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
		setLayout(null);
		
		JLabel label1 = new JLabel("RÈGLES");
		label1.setFont(new Font("Serif", Font.BOLD, 60));
		label1.setForeground(Color.white);
		label1.setBounds((int) (Config.WINDOW_WIDTH/2.23), Config.WINDOW_HEIGHT/10, 300, 50);
		contain.add(label1);
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Le football est universellement populaire mais les règles de jeu peuvent varier selon les pays et les ligues, cependant les règles de base restent les mêmes pour tous les niveaux.");
		JLabel label2 = new JLabel("Le but du football est de marquer des points en envoyant le ballon dans le but de l'équipe adverse. Chaque équipe est composée de onze joueurs, y compris un gardien de but.");
		JLabel label3 = new JLabel("Le jeu commence par un coup d'envoi au centre du terrain. L'équipe qui gagne le coup d'envoi prend le contrôle de la balle et tente de marquer un but en avançant vers le but de l'équipe adverse.");
		JLabel label4 = new JLabel("Les joueurs peuvent passer la balle en la faisant rouler ou en la lançant, sauf pour le gardien de but qui peut utiliser ses mains dans la surface de réparation.");
		JLabel label5 = new JLabel("Les joueurs doivent également respecter les règles de hors-jeu. Un joueur est hors-jeu s'il se trouve plus près du but adverse que le ballon et le dernier défenseur de l'équipe adverse.");
		JLabel label6 = new JLabel("Si un joueur commet une faute, l'arbitre peut donner un coup franc à l'équipe adverse. Si la faute est commise dans la surface de réparation, l'arbitre peut également accorder un penalty.");
		JLabel label7 = new JLabel("Le jeu dure deux mi-temps de 45 minutes avec une pause de 15 minutes entre les deux. En cas d'égalité, il y a prolongation ou tirs au but pour déterminer le vainqueur.");
		JLabel label8 = new JLabel("Voilà, j'espère que cela t'a aidé à comprendre les règles du football pour un jeu vidéo. Amuse-toi bien !");
		label.setFont(new Font("Serif", Font.BOLD, 17));
		label2.setFont(new Font("Serif", Font.BOLD, 17));
		label3.setFont(new Font("Serif", Font.BOLD, 17));
		label4.setFont(new Font("Serif", Font.BOLD, 17));
		label5.setFont(new Font("Serif", Font.BOLD, 17));
		label6.setFont(new Font("Serif", Font.BOLD, 17));
		label7.setFont(new Font("Serif", Font.BOLD, 17));
		label8.setFont(new Font("Serif", Font.BOLD, 17));
		panel.setBounds(Config.TERRAIN_WIDTH-400, Config.WINDOW_HEIGHT/2-100, Config.WINDOW_WIDTH, 260);
		panel.add(label);
		panel.add(label2);
		panel.add(label3);
		panel.add(label4);
		panel.add(label5);
		panel.add(label6);
		panel.add(label7);
		panel.add(label8);
		contain.add(panel);

		JButton back = new JButton("Retour");
		back.setBounds(Config.WINDOW_WIDTH - 100,0,200,50);
		back.setBackground(Color.WHITE);
		back.addActionListener(new GestBack());
		back.setActionCommand("back");
		contain.add(back);
		
		ImageIcon imageIcon = new ImageIcon("src/ressources/fond.png"); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(Config.WINDOW_WIDTH+100, Config.WINDOW_HEIGHT+100,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		imageIcon = new ImageIcon(newimg);  // transform it back
		JLabel jLabel = new JLabel();
		jLabel.setIcon(imageIcon);
		jLabel.setBounds(0, 0, Config.WINDOW_WIDTH+100, Config.WINDOW_HEIGHT+100);
		contain.add(jLabel);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	/**
	 * Classe permettant l'attribution d'action aux boutons
	 * 
	 * @author Boyan CHEYNET, Guillaume LEGUAY et Romain RIEDEL
	 *
	 */
	public class GestBack implements ActionListener{
		/**
		 * Methode permettant l'attribution des actions
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("back")) {
				dispose();
				try {
					IHM ihm = new IHM("Football");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}		
}
