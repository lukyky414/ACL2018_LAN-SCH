package model.entity;

import engine.Cmd;
import model.plateau.Square;
import model.plateau.Wall;
import org.easymock.internal.matchers.Or;

import java.awt.*;

public abstract class Movable extends Entity {
	protected Square nextPos = null;

	public Movable(Square position, int hp, int atk, int cooldown) {
		super(position,hp,atk, cooldown);

	}

	/**
	 * Verifie si la prochaine case soit est un mur, soit contient deja une entite
	 * Cette methode peut etre Override pour changer le deplacement (fantome)
	 *
	 * @return false (si mur ou entite), true si valide
	 */
	protected boolean canMove(Square nextPos){
		if(nextPos == null)
			return false;
		if(nextPos.getEntity() != null)
			return false;
		if(nextPos instanceof Wall)
			return false;
		return true;
	}

	/**
	 * Choisis une nextPos avec ou sans direction.
	 * Cette methode sera precisee dans les classes filles.
	 */
	public abstract void evolve();


	/**
	 * Bouge en fonction de la prochaine direction,
	 * Le deplacement ne s'effectue que si la prochaine position est valide (canMove).
	 *
	 * La gestion du cooldown pour eviter qu'une entite ne se deplace toutes les frames est gere dans les classes filles.
	 */
	public void move(){
		this.resetCooldown();
		if (canMove(this.nextPos)) {

			switch(getDirection(getPos(), nextPos)){
				case LEFT:
					setOrientation(Orientation.WEST);
					break;
				case RIGHT:
					setOrientation(Orientation.EAST);
					break;
				case UP:
					setOrientation(Orientation.NORTH);
					break;
				case DOWN:
					setOrientation(Orientation.SOUTH);
					break;
			}
			this.getPos().setEntity(null);
			this.setPos(nextPos);
			this.nextPos = null;

		}
		this.getPos().triggerEffects(this);
	}

	/**
	 * Donne la direction par rapport au deplacement d'une position vers une autre.
	 *
	 * @param pos1, la position de depart
	 * @param pos2, la position d'arrivee
	 *
	 * @return la direction permettant d'aller de pos1 vers pos2
	 */
	protected static Cmd getDirection(Square pos1, Square pos2){
		int x = pos2.getPosX() - pos1.getPosX();
		int y = pos2.getPosY() - pos1.getPosY();

		switch(x){
			case(1):
				return Cmd.RIGHT;
			case(-1):
				return Cmd.LEFT;
			case(0):
				switch(y){
					case(1):
						return Cmd.DOWN;
					case(-1):
						return Cmd.UP;
				}
		}

		return Cmd.IDLE;
	}

	/**
	 * Retourne une postion, en fonction d'une direction et d'une case donnee.
	 *
	 * @param pos, la position de depart
	 * @param dir, la direction de deplacement
	 *
	 * @return La prochaine direction
	 */
	public static Square getNextPos(Square pos, Cmd dir){
		int x = 0, y = 0;
		switch(dir){
			case UP:
				y = -1;
				break;
			case DOWN:
				y = 1;
				break;
			case LEFT:
				x = -1;
				break;
			case RIGHT:
				x = 1;
				break;
			default: // idle, x=0, y=0
		}
		x += pos.getPosX();
		y += pos.getPosY();

		return pos.getMap().getSquare(x, y);
	}

}
