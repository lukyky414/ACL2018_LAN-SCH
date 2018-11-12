package model.plateau;

import model.entity.Entity;

public class SecretPassage extends Effect {
	private int destX;
	private int destY;
	public SecretPassage(int x, int y){
		destX=x;
		destY=y;
	}

	@Override
	public void trigger(Entity h, Square s) {
		Square destination = s.getMap().getSquare(destX, destY);
		if(destination.getEntity()==null){
			h.setPos(destination);
			destination.setEntity(h);
			s.setEntity(null);
		}
	}

}
