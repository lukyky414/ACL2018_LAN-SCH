package model.plateau;

import model.entity.Entity;
import model.entity.Hero;

public class Magic extends Effect {

    //healing effect
    public void trigger(Entity h, Square s){
        if(h instanceof Hero)
            h.setHp(h.getHp()+1);
    }
}
