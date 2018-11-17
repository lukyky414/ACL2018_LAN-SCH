package model.plateau;

import model.entity.Entity;
import model.entity.Hero;
import model.factory.TextureFactory;

public class Treasure extends Effect {

	public Treasure(){
		super();
		texture = TextureFactory.getTexChest();
	}

	@Override
	public void trigger(Entity h,Square s) {// on considere que seul le heros peut activer cet effet
		if(h instanceof Hero){
			s.treasureTaken();
		}
	}

	@Override
	public int getType() {
		return 1;
	}

}
