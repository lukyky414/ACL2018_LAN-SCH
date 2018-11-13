package model.entity;

import model.plateau.Square;

public class Goblin extends Monster {
	public Goblin(Square position) {
		super(position, 7);
	}

	public void attack(Movable m){}

	@Override
	public String getType() {
		return "Goblin";
	}
}
