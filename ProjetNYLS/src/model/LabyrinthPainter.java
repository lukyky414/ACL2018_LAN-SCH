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
import model.option.Option;
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

		LabyrinthGame game = (LabyrinthGame) this.game;
		GameState state = game.getState();
		switch (state){
			case RUN:
				drawMap(crayon);
				drawEntity(crayon);
			break;
			case PAUSE:
				makeFont(crayon, "Serif", 30);
				drawScreenPause(crayon);
			break;
			case OVER:
				makeFont(crayon, "Serif", 60);
				drawMap(crayon);
				drawScreenOver(crayon);
			break;
			default:
			break;
		}
	}

	private void makeFont(Graphics2D crayon, String name, int size){
		Font font = new Font(name, Font.PLAIN, size);
		crayon.setFont(font);
		metrics = crayon.getFontMetrics(font);
	}

	private void drawScreenOver(Graphics2D crayon) {
		crayon.setColor(Color.RED);
		int y = HEIGHT / 2;
		writeText(crayon, "YOU DIED", y);
	}

	private void writeText(Graphics2D crayon, String text, int y){
		int x = (WIDTH - metrics.stringWidth(text)) / 2;
		crayon.drawString(text, x, y);
	}

	private void drawScreenPause(Graphics2D crayon) {
		Option opt = ((LabyrinthGame) this.game).getOption();
		String[] listOption = opt.getListeOption();
		crayon.setColor(Color.BLACK);
		crayon.fillRect(0,0, WIDTH, HEIGHT);
		int scale = HEIGHT / (listOption.length + 1);
		int y = 0;
		for (int i = 0; i != listOption.length; i++){
			y = y + scale;
			if (i == opt.getCurrent())
				crayon.setColor(Color.RED);
			else
				crayon.setColor(Color.BLUE);
			writeText(crayon, listOption[i], y);
		}
		crayon.setColor(Color.blue);
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
			drawLifeBar(crayon, x, y, entitie);
		}
	}

	private void drawLifeBar(Graphics2D crayon, int x, int y, Entity e){
		Rectangle r = new Rectangle();
		Rectangle r2 = new Rectangle();
		crayon.setColor(Color.red);

		r.height = (int) ((1d/12d) * (double)sizeY);
		r.width = (int) (((double)e.getHp() / (double)e.getHpMax()) * (0.8d * (double)sizeX));

		r.x = (int) ((double)x + (0.1d * (double)sizeX));
		//r.y = (int) ((double)y + (1d/12d) * (double)sizeY);
		r.y = y - r.height;

		r2.x = r.x;
		r2.y = r.y;
		r2.height = r.height;
		r2.width = (int) (0.8d * (double)sizeX);

		crayon.drawRect(r2.x, r2.y, r2.width, r2.height);
		crayon.fillRect(r.x, r.y, r.width, r.height);
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
			if(spriteEffect!=null) {
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
