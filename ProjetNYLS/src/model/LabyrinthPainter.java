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
	protected static final int WIDTH = 800;
	protected static final int HEIGHT = 800;
	private FontMetrics metrics;
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
		Font font = new Font("Serif", Font.PLAIN, 30);
		crayon.setFont(font);
		metrics = crayon.getFontMetrics(font);
		LabyrinthGame game = (LabyrinthGame) this.game;
		GameState state = game.getState();
		switch (state){
			case RUN:
				drawMap(crayon);
				drawEntity(crayon);
			break;
			case PAUSE:
				drawScreenPause(crayon);
			break;
			default:
			break;
		}

	}

	private void writeText(Graphics2D crayon, String text, int y){
		int x = (WIDTH - metrics.stringWidth(text)) / 2;
		crayon.drawString(text, x, y);
	}

	private void drawScreenPause(Graphics2D crayon) {
		int y = HEIGHT / 6;
		crayon.setColor(Color.BLACK);
		crayon.fillRect(0,0, WIDTH, HEIGHT);

		crayon.setColor(Color.blue);
		writeText(crayon, "Continue", y);
		writeText(crayon, "New game", y * 2);
		writeText(crayon, "Save", y * 3);
		writeText(crayon, "Load", y * 4);
		writeText(crayon, "Exit", y * 5);
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
		int x = 0;
		int y = 0;
		int i = 0;
		int j = 0;
		LabyrinthGame g = (LabyrinthGame) game;
		Map map = g.getMap();
		int width = map.getWidth();
		int height = map.getHeigth();
		sizeX = WIDTH / width;
		sizeY = HEIGHT / height;
		Image texWall = TextureFactory.getInstance().getTexWall();
		Image wall = texWall.getScaledInstance(sizeX + 1, sizeY, Image.SCALE_DEFAULT);
		Square sq;

		for (i = 0; i != height ; i++){
			if (i == height - 1)
				wall = texWall.getScaledInstance(sizeX, sizeY + 10, Image.SCALE_DEFAULT);
			for (j = 0; j != width - 1; j++) {
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
				drawEffect(crayon, x, y, sq);
			}
		}
		wall = texWall.getScaledInstance(sizeX + 10, sizeY, Image.SCALE_DEFAULT);
		x = x + sizeX;
		for (i = 0; i != height; i++){
			if (i == height - 1)
				wall = texWall.getScaledInstance(sizeX + 10, sizeY + 10, Image.SCALE_DEFAULT);
			y = i * sizeY;
			crayon.drawImage(wall, x, y, null);
		}
	}

	private void drawEffect(Graphics2D crayon, int x, int y, Square sq) {
		Image spriteEffect;
		for (Effect e : sq) {
			spriteEffect = e.getTexture();
			if (e instanceof SecretPassage) {
				crayon.setColor(Color.RED);
				crayon.drawLine(x, y, x + sizeX, y + sizeY);
				crayon.drawLine(x + sizeX, y, x, y + sizeY);
			} else if (e instanceof Treasure) {
				spriteEffect = spriteEffect.getScaledInstance(sizeX, sizeY, Image.SCALE_DEFAULT);
				crayon.drawImage(spriteEffect, x, y, null);

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
