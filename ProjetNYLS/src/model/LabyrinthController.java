package model;

import java.awt.event.KeyEvent;

import engine.Cmd;
import engine.GameController;
import model.entity.Playable;


/**
 * @author Horatiu Cirstea, Vincent Thomas
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
	 * Garder le hero pour plus de reactivite
	 * dans les deplacements
	 */
	private Playable hero;
	private LabyrinthGame game;

	/**
	 * Construction du controleur par defaut le controleur n'a pas de commande
	 * @param game
	 */
	public LabyrinthController(LabyrinthGame game) {
		this.game = game;
		this.commandeEnCours = Cmd.IDLE;
	}

	/**
	 * Quand on demande les commandes, le controleur retourne la commande en
	 * cours.
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
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
			if (game.getState() == GameState.RUN)
				game.setState(GameState.PAUSE);
			else
				game.setState(GameState.RUN);
		}

		this.notifyHero();
	}

	@Override
	/**
	 * met a jour les commandes quand le joueur relache une touche
	 */
	public void keyReleased(KeyEvent e) {
		this.commandeEnCours = Cmd.IDLE;
		this.notifyHero();
	}

	@Override
	/**
	 * ne fait rien
	 */
	public void keyTyped(KeyEvent e) {

	}

	/**
	 * Permet de modifier le hero
	 */
	@Override
	public void setPlayable(Playable hero) {
		this.hero = hero;
	}

	/**
	 * Notifie le hero qu'une touche est appuyee
	 * Le tout pour un controle plus naturel.
	 */
	private void notifyHero(){
		if(hero != null)
			hero.evolve(this.commandeEnCours);
	}


}
