package model.entity;

import model.plateau.Square;

public class Hero extends Playable {

	public Hero(Square position, int hp, int atk) {
		super(position, hp, atk, 14);
	}

	@Override
	public String getType() {
		return "Hero";
	}

}
