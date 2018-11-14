package model.plateau;

import model.entity.Entity;
import model.entity.Hero;

public class Treasure extends Effect {

	@Override
	public void trigger(Entity h,Square s) {// on considere que seul le heros peut activer cet effet
		if(h instanceof Hero){
			s.treasureTaken();
		}
	}

}
