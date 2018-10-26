package model.entity;

import model.plateau.Square;

public abstract class Monster extends Movable {

	public Monster(Square position) {
		super(position);
	}

	public void comportement(Movable m){
		this.attack(m);
	}

	abstract void attack(Movable m);
	public abstract String getType();

	/*private void comportement(Monster m){
		canMove = false;
	}

	private void comportement(Playable p){
		canMove = false;
		//poursuite
		//kill le playable
		// implémenter quand l'attaque sera au programme
	}*/
}
