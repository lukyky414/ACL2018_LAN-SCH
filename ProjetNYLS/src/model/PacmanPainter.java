package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import engine.Game;
import engine.GamePainter;
import model.entity.Entity;
import model.plateau.Map;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 *
 * afficheur graphique pour le game
 * 
 */
public class PacmanPainter implements GamePainter {

	/**
	 * la taille des cases
	 */
	protected static final int WIDTH = 500;
	protected static final int HEIGHT = 500;


	private Game game;
	/**
	 * appelle constructeur parent
	 *
     * @param game
     *            le jeutest a afficher
     */
	public PacmanPainter(Game game) {
		this.game = game;
	}

	/**
	 * methode  redefinie de Afficheur retourne une image du jeu
	 */
	@Override
	public void draw(BufferedImage im) {
		Graphics2D crayon = (Graphics2D) im.getGraphics();
		drawMap(crayon);
	}

	private void drawMap(Graphics2D crayon) {
		int x;
		int y;
		PacmanGame g = (PacmanGame) game;
		Map map = g.getMap();
		int width = map.getWidth();
		int height = map.getHeigth();
		int sizeX = WIDTH / width;
		int sizeY = HEIGHT / height;
		for (int i = 0; i != height; i++){
			for (int j = 0; j != width; j++){
				y = i * sizeY;
				x = j * sizeX;
				if (map.getSquare(j, i).getIsWall()) {
					crayon.setColor(Color.black);
					crayon.fillRect(x, y, sizeX, sizeY);
				}
				else {
					Entity ent = map.getSquare(j, i).getEntity();
					if (ent == null){}
					else {
						if (ent.getType().equals("Hero")) {
							drawHero(x, y, crayon);
						}
					}
				}
			}
		}
	}

	private void drawHero(int x, int y, Graphics2D crayon) {
		crayon.setColor(Color.blue);
		crayon.fillOval(x, y,20,20);
	}

	@Override
	public int getWidth() {
		return WIDTH;
	}

	@Override
	public int getHeight() {
		return HEIGHT;
	}

}
