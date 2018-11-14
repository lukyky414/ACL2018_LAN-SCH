package model.entity;

import engine.Cmd;
import model.plateau.Square;
import model.plateau.Wall;

import java.awt.*;

public abstract class Movable extends Entity {


	protected Square nextPos = null;
	protected int cooldown;
	protected int base_cooldown;

	public Movable(Square position, int hp, int atk, int cooldown) {
		super(position,hp,atk);
			this.base_cooldown = cooldown;
			this.cooldown = cooldown;
	}

	/**
	 * Verifie si la prochaine case soit est un mur, soit contient deja une entite
	 * Cette methode peut etre Override pour changer le deplacement (fantome)
	 * 		Attention, une case ne contient qu'une entite
	 * @return false (si mur ou entite), true si valide
	 */
	boolean canMove(){
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
	 *
	 * @return rien
	 */
	public abstract void evolve(Cmd cmd);


	/**
	 * Bouge en fonction de la prochaine direction,
	 * seulement si le cooldown est a zero.
	 *
	 * @return rien
	 */
	public void move(){
		this.resetCooldown();
		if (canMove()) {
			
			int x = nextPos.getPosX() - this .getPos().getPosX();
			int y = nextPos.getPosY() - this .getPos().getPosY();
			
			switch(x){
			case(-1):
				this.setOrientation(Orientation.WEST);
				break;
			case(1):
				this.setOrientation(Orientation.EAST);
				break;
			case(0):
				switch(y){
				case(-1):
					this.setOrientation(Orientation.NORTH);
					break;
				case(1):
					this.setOrientation(Orientation.SOUTH);
					break;
				}
			}
			System.out.println(this.getType()+" : "+orientation);
			
			
			
			this.getPos().setEntity(null);
			this.nextPos.setEntity(this);
			this.setPos(nextPos);
			this.nextPos = null;

			this.getPos().triggerEffects(this);
		}
	}
	/**
	 * Retourne, en fonction d'une direction et d'une case donnee,
	 * la prochaine position.
	 *
	 * @return Une position
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

	public void resetCooldown(){
		this.cooldown = this.base_cooldown;
	}

	public int getCooldown(){
		return this.cooldown;
	}


	public abstract String getType();
	public abstract Image getTexture();
}
