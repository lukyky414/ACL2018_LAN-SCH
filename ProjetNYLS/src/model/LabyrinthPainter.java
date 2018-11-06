package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import engine.GamePainter;
import model.entity.Entity;
import model.plateau.*;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 *
 * afficheur graphique pour le game
 * 
 */
public class LabyrinthPainter implements GamePainter {

	/**
	 * la taille des cases
	 */
	protected static final int WIDTH = 500;
	protected static final int HEIGHT = 500;


	private engine.Game game;
	/**
	 * appelle constructeur parent
	 *
     * @param game
     *            le jeutest a afficher
     */
	public LabyrinthPainter(engine.Game game) {
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
		LabyrinthGame g = (LabyrinthGame) game;
		Map map = g.getMap();
		int width = map.getWidth();
		int height = map.getHeigth();
		int sizeX = WIDTH / width;
		int sizeY = HEIGHT / height;
		Square sq;

		for (int i = 0; i != height; i++){
			for (int j = 0; j != width; j++){
				y = i * sizeY;
				x = j * sizeX;
				sq = map.getSquare(j, i);

				//Dessiner la case si c'est un mur
				if (sq instanceof Wall) {
					crayon.setColor(Color.black);
					crayon.fillRect(x, y, sizeX, sizeY);
				}

				//Dessiner les effets de la case
				for (Effect e : sq) {
					if(e instanceof SecretPassage){
						crayon.setColor(Color.RED);
						crayon.drawLine(x, y, x + sizeX, y + sizeY);
						crayon.drawLine(x + sizeX, y, x, y + sizeY);
					}
					else if(e instanceof Treasure){
						crayon.setColor(Color.YELLOW);
						crayon.drawOval(x, y, sizeX, sizeY);

					}
				}

				//Dessiner l'entite habitant la case
				Entity ent = sq.getEntity();
				if (ent == null){}
				else {
					if (ent.getType().equals("Hero")) {
						drawHero(x, y, crayon, sizeX, sizeY);
					}
				}
			}
		}
	}

	private void drawHero(int x, int y, Graphics2D crayon, int sizeX, int sizeY) {
		crayon.setColor(Color.blue);
		crayon.fillOval(x + ((sizeX - 20) / 2), y + ((sizeY - 20) / 2),20,20);
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
