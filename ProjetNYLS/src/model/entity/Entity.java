package model.entity;

import model.plateau.Square;

public abstract class Entity {
	private Square position;

	public Entity(Square position){
		this.position = position;
		position.setEntity(this);
	}

	public Square getPos(){return position;}

	public void setPos(Square position){
		if(position == null)
			throw new NullPointerException("Position nulle a Entity");

		this.position = position;
	}

	public abstract String getType();
	public void activateEffect(){
		position.triggerEffects(this);
	}
}
