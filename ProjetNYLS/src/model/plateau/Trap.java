package model.plateau;

import model.entity.Entity;
import model.entity.Hero;

public class Trap extends Effect {

	private int damage;

	public Trap(int d){
		damage = d;
	}

	@Override
	public void trigger(Entity h, Square s) {
		if(h instanceof Hero)
			h.setHp(h.getHp()-damage);

	}

	@Override
	public int getType() {
		return 3;
	}

	public int getDamage(){
		return damage;
	}

}
