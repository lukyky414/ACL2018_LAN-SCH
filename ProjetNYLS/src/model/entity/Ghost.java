package model.entity;

import model.plateau.Square;

public class Ghost extends Monster{

	public Ghost(Square position) {
		super(position, 7);
	}

	public void attack(Movable m){}

	/**
	 * Un fantome peut traverser un mur. La fonction canMove en est donc modifie
	 *
	 * @return false (si entite), true si valide
	 */
	@Override
	boolean canMove(){
		if(nextPos == null)
			return false;
		if(nextPos.getEntity() != null)
			return false;
		return true;
	}

	@Override
	public String getType() {
		return "Ghost";
	}
}
