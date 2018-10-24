package model.entity;

import model.plateau.Square;

public abstract class Movable extends Entity {
	public static final int HAUT = 0, DROITE = 1, BAS = 2, GAUCHE = 3;

	Square nextPos = null;

	public Movable(Square position) {
		super(position);
	}

	private boolean canMove(int dir){

		return true;
	}

	public void move(int dir){
		changeNextPos(dir);
		if(canMove(dir)){

		}
	}

	private void changeNextPos(int dir){

	}
}
