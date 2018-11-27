package engine;

import exceptions.CorruptDataException;
import model.GameState;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 *
 * moteur de game generique.
 * On lui passe un game et un afficheur et il permet d'executer un game.
 */
public class GameEngineGraphical {

	/**
	 * le game a executer
	 */
	private Game game;

	/**
	 * l'afficheur a utiliser pour le rendu
	 */
	private GamePainter gamePainter;

	/**
	 * le controlleur a utiliser pour recuperer les commandes
	 */
	private GameController gameController;

	/**
	 * l'interface graphique
	 */
	private GraphicalInterface gui;

	/**
	 * construit un moteur
	 * 
	 * @param game
	 *            game a lancer
	 * @param gamePainter
	 *            afficheur a utiliser
	 * @param gameController
	 *            controlleur a utiliser
	 *            
	 */
	public GameEngineGraphical(Game game, GamePainter gamePainter, GameController gameController) {
		// creation du game
		this.game = game;
		this.gamePainter = gamePainter;
		this.gameController = gameController;
	}

	/**
	 * permet de lancer le game
	 */
	public void run() throws InterruptedException {

		// creation de l'interface graphique
		this.gui = new GraphicalInterface(this.gamePainter,this.gameController);

		
		// boucle de game
		while((game.getState() != GameState.EXIT)){
			this.gameController.setPlayable(this.game.getHero());
			while (!this.game.isFinished()) {
				// fait evoluer le game
				this.game.evolve();
				// affiche le game
				this.gui.paint();
				// met en attente = fps
				Thread.sleep(15);
			}
			try {
				game.loadNextLevel();
			} catch (CorruptDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
