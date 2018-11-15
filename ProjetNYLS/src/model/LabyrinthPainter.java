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
		Image sprite;
		LabyrinthGame game = (LabyrinthGame) this.game;
		ArrayList<Movable> entities = game.getEntities();
		for (Entity entitie : entities){
			x = entitie.getPos().getPosX() * sizeX;
			y = entitie.getPos().getPosY() * sizeY;
			sprite = entitie.getTexture();
			if (sprite != null) {
				sprite = sprite.getScaledInstance(sizeX, sizeY, Image.SCALE_DEFAULT);
				crayon.drawImage(sprite, x, y, null);
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
		Image spriteEffect;
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
					spriteEffect = e.getTexture();
					if (e instanceof SecretPassage) {
						crayon.setColor(Color.RED);
						crayon.drawLine(x, y, x + sizeX, y + sizeY);
						crayon.drawLine(x + sizeX, y, x, y + sizeY);
					} else if (e instanceof Treasure) {
						spriteEffect = spriteEffect.getScaledInstance(sizeX, sizeY - 10, Image.SCALE_DEFAULT);
						crayon.drawImage(spriteEffect, x, y, null);

					}
				}
			}
		}
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
