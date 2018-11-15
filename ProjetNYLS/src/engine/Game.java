package engine;

import exceptions.CorruptDataException;
import model.entity.Hero;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 *
 *         un jeu qui peut evoluer (avant de se terminer) sur un plateau width x
 *         height
 */
public interface Game {

	/**
	 * methode qui contient l'evolution du jeu en fonction de la commande
	 * 
	 * @param userCmd
	 *            commande utilisateur
	 */
	public void evolve(Cmd userCmd);

	/**
	 * Chaque jeu doit posseder un hero. Voidi la maniere de le recuperer
	 *
	 * @return le hero*/
	public Hero getHero();

	/**
	 * @return true si et seulement si le jeu est fini
	 */
	public boolean isFinished();
	
	/**
	 * @throws CorruptDataException 
	 * 
	 */
	public void loadNextLevel() throws CorruptDataException;

}
