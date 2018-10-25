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

	@Override
	boolean canMove(int dir){
		return false;
	}

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
