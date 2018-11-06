package model.plateau;

import model.entity.Entity;

public class Treasure extends Effect {

	@Override
	public void trigger(Entity h,Square s) {// on considere que seul le heros peut activer les effets
		s.treasureTaken();
	}

}
