package model.plateau;

import model.entity.Entity;
import model.entity.Hero;
import model.factory.TextureFactory;

public class Magic extends Effect {
    private int healing;

    public Magic(int heal){
        super();
        healing = heal;
        texture= TextureFactory.getTexHealingPotion();
    }

    //healing effect
    public void trigger(Entity h, Square s){
        if(h instanceof Hero) {
            h.setHp(h.getHp() + healing);
            s.addSuppr(getType());
        }
    }

    public int getHealing(){
        return healing;
    }

	@Override
	public int getType() {
		return 4;
	}
}
