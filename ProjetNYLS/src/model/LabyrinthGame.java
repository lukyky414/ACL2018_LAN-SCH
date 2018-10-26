package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import dao_Txt.MapTxtDAO;
import engine.Cmd;
import engine.Game;
import model.entity.Hero;
import model.plateau.Map;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 *
 *         Version avec personnage qui peut se deplacer. A completer dans les
 *         versions suivantes.
 * 
 */
public class LabyrinthGame implements engine.Game {

	/**
	 * constructeur avec fichier source pour le help
	 * 
	 */

	private Map map;

	private Hero hero;

	public LabyrinthGame(String source) {
		BufferedReader helpReader;
		try {
			helpReader = new BufferedReader(new FileReader(source));
			String ligne;
			while ((ligne = helpReader.readLine()) != null) {
				System.out.println(ligne);
			}
			helpReader.close();
		} catch (IOException e) {
			System.out.println("Help not available");
		}

		map = MapTxtDAO.getInstance().load(0);
		System.out.println(map.toString());

		hero = new Hero(map.getSquare(7,5));
	}

	public Map getMap(){
		return map;
	}

	/**
	 * faire evoluer le jeu suite a une commande
	 * 
	 * @param commande
	 */
	@Override
	public void evolve(Cmd commande) {
		System.out.println("Execute "+commande);
		hero.move(commande);
		System.out.println(hero.getPos());
	}

	/**
	 * verifier si le jeu est fini
	 */
	@Override
	public boolean isFinished() {
		// le jeu n'est jamais fini
		return false;
	}

}
