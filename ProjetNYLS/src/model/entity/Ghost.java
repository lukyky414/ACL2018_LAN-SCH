package model.entity;

import model.plateau.Square;

public class Ghost extends Monster{

	public Ghost(Square position) {
		super(position);
	}

	public void attack(Movable m){}

	@Override
	public String getType() {
		return "Ghost";
	}
}
