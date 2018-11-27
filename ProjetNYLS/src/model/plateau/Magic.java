package model.plateau;

import model.entity.Entity;
import model.entity.Hero;
import model.factory.TextureFactory;

public class Magic extends Effect {

    public Magic(){
        super();
        texture= TextureFactory.getTexHealingPotion();
    }

    //healing effect
    public void trigger(Entity h, Square s){
        if(h instanceof Hero) {
            h.setHp(h.getHp() + 1);
            s.addSuppr(getType());
        }
    }

	@Override
	public int getType() {
		return 4;
	}
}
