package model.entity;

import model.plateau.Square;

public abstract class Playable extends Movable{

	public Playable(Square position) {
		super(position);
	}
}
