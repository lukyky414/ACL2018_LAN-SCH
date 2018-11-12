package model;

import java.awt.event.KeyEvent;

import engine.Cmd;
import engine.GameController;


/**
 * @author Horatiu Cirstea, Vincent Thomas
 * Modifie par Lucas SCHWAB
 *
 * controleur de type KeyListener
 * 
 */
public class LabyrinthController implements GameController {

	/**
	 * Bouton actuellement appuye
	 */
	private Cmd commandeEnCours;

	/**
	 * Dernier bouton appuye
	 */
	private Cmd lastCommande;
	
	/**
	 * Construction du controleur par defaut le controleur n'a pas de commande
	 */
	public LabyrinthController() {
		this.commandeEnCours = Cmd.IDLE;
		this.lastCommande = Cmd.IDLE;
	}

	/**
	 * Quand on demande les commandes, le controleur retourne la commande en
	 * cours, ou la derniere commande apuye.
	 * Ceci pour un deplacement plus naturel, un appuie tres court sur une
	 * touche sera quand meme pris en compte
	 * 
	 * @return commande faite par le joueur
	 */
	public Cmd getCommand() {
		Cmd res = this.commandeEnCours;
		if(res == Cmd.IDLE){
			res = this.lastCommande;
			this.lastCommande = Cmd.IDLE;
		}
		return res;
	}

	@Override
	/**
	 * met a jour les commandes en fonctions des touches appuyees
	 */
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyChar()) {
		// si on appuie sur 'z',commande joueur est haut
		case 'z':
		case 'Z':
			this.commandeEnCours = Cmd.UP;
			this.lastCommande = Cmd.UP;
			break;
		// si on appuie sur 'q',commande joueur est gauche
		case 'q':
		case 'Q':
			this.commandeEnCours = Cmd.LEFT;
			this.lastCommande = Cmd.LEFT;
			break;
		// si on appuie sur 's',commande joueur est bas
		case 's':
		case 'S':
			this.commandeEnCours = Cmd.DOWN;
			this.lastCommande = Cmd.DOWN;
			break;
		// si on appuie sur 'd',commande joueur est droite
		case 'd':
		case 'D':
			this.commandeEnCours = Cmd.RIGHT;
			this.lastCommande = Cmd.RIGHT;
			break;
		}

	}

	@Override
	/**
	 * met a jour les commandes quand le joueur relache une touche
	 */
	public void keyReleased(KeyEvent e) {
		this.commandeEnCours = Cmd.IDLE;
	}

	@Override
	/**
	 * ne fait rien
	 */
	public void keyTyped(KeyEvent e) {

	}

}
