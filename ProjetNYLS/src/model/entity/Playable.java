package model.entity;

import model.plateau.Square;

public abstract class Playable extends Movable{

	public Playable(Square position) {
		super(position);
	}

	private void comportement(Goblin g){
		canMove = false;
		//à implementer quand l'attaque sera dispo
	}

	private void comportement(Ghost g){
		canMove = false;
		//perso meurt ? à implémenter quand on abordera l'attaque
	}
}
