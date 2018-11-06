package model.entity;

import model.plateau.Square;

public class Goblin extends Monster {
	
	public Goblin(Square position, int hp, int atk) {
		super(position,hp, atk, 14);
	}


	@Override
	public String getType() {
		return "Goblin";
	}
}
