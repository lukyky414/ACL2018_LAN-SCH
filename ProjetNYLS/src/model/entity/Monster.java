package model.entity;

import model.plateau.Square;

public abstract class Monster extends Movable {

	public Monster(Square position) {
		super(position);
	}


	private void comportement(Monster m){
		canMove = false;
	}

	private void comportement(Playable p){
		canMove = false;
		//poursuite
		//kill le playable
		// implémenter quand l'attaque sera au programme
	}
}
