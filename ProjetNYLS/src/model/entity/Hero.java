package model.entity;

import model.plateau.Square;

public class Hero extends Playable {
	public Hero(Square position) {
		super(position, 7);
	}

	@Override
	public String getType() {
		return "Hero";
	}

	public void attack(Movable m){}
}
