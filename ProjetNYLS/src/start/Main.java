package start;

import model.LabyrinthGame;
import model.LabyrinthPainter;
import engine.GameEngineGraphical;
import model.LabyrinthController;

/**
 * lancement du moteur avec le jeu
 */
public class Main {

	public static void main(String[] args) throws InterruptedException {

		// creation du jeu particulier et de son afficheur
		LabyrinthGame game = new LabyrinthGame("helpFilePacman.txt");
		LabyrinthPainter painter = new LabyrinthPainter(game);
		LabyrinthController controller = new LabyrinthController();

		// classe qui lance le moteur de jeu generique
		GameEngineGraphical engine = new GameEngineGraphical(game, painter, controller);
		engine.run();
	}

}
