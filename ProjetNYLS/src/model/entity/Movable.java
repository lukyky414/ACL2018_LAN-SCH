package model.entity;

import engine.Cmd;
import model.plateau.Square;
import model.plateau.Wall;

import java.awt.*;

public abstract class Movable extends Entity {
	protected Square nextPos = null;
	int cooldown;
	private int base_cooldown;

	public Movable(Square position, int hp, int atk, int cooldown) {
		super(position,hp,atk);
			this.base_cooldown = cooldown;
			this.cooldown = cooldown;
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
	 *
	 * @param cmd, la commande actuelle.
	 */
	public abstract void evolve(Cmd cmd);


	/**
	 * Bouge en fonction de la prochaine direction,
	 * Le deplacement ne s'effectue que si la prochaine position est valide (canMove).
	 *
	 * La gestion du cooldown pour eviter qu'une entite ne se deplace toutes les frames est gere dans les classes filles.
	 */
	public void move(){
		this.resetCooldown();
		if (canMove(this.nextPos)) {
			
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
			this.getPos().setEntity(null);
			this.setPos(nextPos);
			this.nextPos = null;

			this.getPos().triggerEffects(this);
		}
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

	/**
	 * Remet le cooldown a sa valeur de base.
	 * S'effectue apres chaques deplacements.
	 */
	private void resetCooldown(){
		this.cooldown = this.base_cooldown;
	}
}
