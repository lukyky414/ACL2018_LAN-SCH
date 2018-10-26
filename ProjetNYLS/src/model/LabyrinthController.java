package model;

import java.awt.event.KeyEvent;

import engine.Cmd;
import engine.GameController;


/**
 * @author Horatiu Cirstea, Vincent Thomas
 *
 * controleur de type KeyListener
 * 
 */
public class LabyrinthController implements GameController {

	/**
	 * commande en cours
	 */
	private Cmd commandeEnCours;
	
	/**
	 * construction du controleur par defaut le controleur n'a pas de commande
	 */
	public LabyrinthController() {
		this.commandeEnCours = Cmd.IDLE;
	}

	/**
	 * quand on demande les commandes, le controleur retourne la commande en
	 * cours
	 * 
	 * @return commande faite par le joueur
	 */
	public Cmd getCommand() {
		return this.commandeEnCours;
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
			break;
		// si on appuie sur 'q',commande joueur est gauche
		case 'q':
		case 'Q':
			this.commandeEnCours = Cmd.LEFT;
			break;
		// si on appuie sur 's',commande joueur est bas
		case 's':
		case 'S':
			this.commandeEnCours = Cmd.DOWN;
			break;
		// si on appuie sur 'd',commande joueur est droite
		case 'd':
		case 'D':
			this.commandeEnCours = Cmd.RIGHT;
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
