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

		if (game.getState() == GameState.RUN){
			gereKeyRun(e);
			this.notifyHero();
		}
		else if (game.getState() == GameState.PAUSE){
			gereKeyPause(e);
		}
		else if (game.getState() == GameState.OVER){
			gereKeyEnd(e);
		}
	}

	private void gereKeyEnd(KeyEvent e) {
		game.newGame();
	}

	private void gereKeyPause(KeyEvent e) {
		switch (e.getKeyCode()){
			case KeyEvent.VK_DOWN:
				game.getOption().addCurrent(1);
				break;
			case KeyEvent.VK_UP:
				game.getOption().subCurrent(1);
				break;
			case KeyEvent.VK_ENTER:
				game.getOption().doOption(game);
				break;
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
			game.setState(GameState.RUN);
		}
	}

	private void gereKeyRun(KeyEvent e) {
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
			//Si on appuie sur 'f', commande joueur pour attaquer
			case 'f':
			case 'F':
				this.hero.attack();
				break;
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
			game.setState(GameState.PAUSE);
	}

	@Override
	/**
	 * met a jour les commandes quand le joueur relache une touche
	 */
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyChar()){
			case 'z':
			case'Z':
			case'q':
			case 'Q':
			case 's':
			case'S':
			case 'd':
			case'D':
				this.commandeEnCours = Cmd.IDLE;
				this.notifyHero();
				break;

		}

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
