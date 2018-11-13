package model.entity;

import engine.Cmd;
import model.plateau.Square;
import model.plateau.Wall;

public abstract class Movable extends Entity {

	Square nextPos = null;
	int cooldown, base_cooldown;


	public Movable(Square position, int cooldown) {
		super(position);
		this.base_cooldown = cooldown;
		this.cooldown = 0;
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
		System.out.println(cooldown);
		if(cooldown-- == 0) {
			if (canMove()) {
				this.getPos().setEntity(null);
				this.nextPos.setEntity(this);
				this.setPos(nextPos);
				this.nextPos = null;
			}
			cooldown = base_cooldown;
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

	private void collision(Movable m){
		this.comportement(m);
	}

	abstract void comportement(Movable m);
	public abstract String getType();
}
