package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import engine.GamePainter;
import model.entity.Entity;
import model.entity.Movable;
import model.factory.TextureFactory;
import model.plateau.*;

import javax.imageio.ImageIO;

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
	private int sizeX;
	private int sizeY;

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
		drawEntity(crayon);
	}

	private void drawEntity(Graphics2D crayon) {
		int x;
		int y;
		LabyrinthGame game = (LabyrinthGame) this.game;
		ArrayList<Movable> entities = game.getEntities();
		for (Entity entitie : entities){
			x = entitie.getPos().getPosX() * sizeX;
			y = entitie.getPos().getPosY() * sizeY;
			switch(entitie.getType()){
				case "Hero":
					drawHero(x, y, crayon, sizeX, sizeY);
					break;
				case "Goblin":
					drawGoblin(x, y, crayon, sizeX, sizeY);
					break;
				case "Ghost":
					drawGhost(x, y, crayon, sizeX, sizeY);
			}
		}
	}

	private void drawMap(Graphics2D crayon) {
		int x;
		int y;
		LabyrinthGame g = (LabyrinthGame) game;
		Map map = g.getMap();
		int width = map.getWidth();
		int height = map.getHeigth();
		sizeX = WIDTH / width;
		sizeY = HEIGHT / height;
		Image wall = TextureFactory.getInstance().getTexWall();
		wall = wall.getScaledInstance(sizeX, sizeY, Image.SCALE_DEFAULT);
		Square sq;

		for (int i = 0; i != height; i++){
			for (int j = 0; j != width; j++) {
				y = i * sizeY;
				x = j * sizeX;
				sq = map.getSquare(j, i);
				//Dessiner la case si c'est un mur
				if (sq instanceof Wall) {
					crayon.drawImage(wall, x, y, null);
				} else {
					crayon.setColor(Color.black);
					crayon.fillRect(x, y, sizeX, sizeY);
				}
				//Dessiner les effets de la case
				for (Effect e : sq) {
					if (e instanceof SecretPassage) {
						crayon.setColor(Color.RED);
						crayon.drawLine(x, y, x + sizeX, y + sizeY);
						crayon.drawLine(x + sizeX, y, x, y + sizeY);
					} else if (e instanceof Treasure) {
						crayon.setColor(Color.YELLOW);
						crayon.drawOval(x, y, sizeX, sizeY);

					}
				}
			}
		}
	}

	private void drawHero(int x, int y, Graphics2D crayon, int sizeX, int sizeY) {
		crayon.setColor(Color.blue);
		crayon.fillOval(x + ((sizeX - 20) / 2), y + ((sizeY - 20) / 2),20,20);
	}

	private void drawGoblin(int x, int y, Graphics2D crayon, int sizeX, int sizeY) {
		crayon.setColor(Color.green);
		crayon.fillOval(x + ((sizeX - 20) / 2), y + ((sizeY - 20) / 2),20,20);
	}

	private void drawGhost(int x, int y, Graphics2D crayon, int sizeX, int sizeY) {
		crayon.setColor(Color.red);
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
