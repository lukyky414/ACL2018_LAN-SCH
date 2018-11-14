package model.entity;

import engine.Cmd;
import model.plateau.Square;

public class Goblin extends Monster {
	
	public Goblin(Square position, int hp, int atk, Hero target) {
		super(position,hp, atk, 14, target);
	}


	@Override
	public String getType() {
		return "Goblin";
	}

	/**
	 * Choisis une nextPos avec une IA.
	 * On ne calcule pas a chaque Frame le chemin,
	 * mais a chaque deplacement.
	 *
	 * @return rien
	 */
	@Override
	public void evolve(Cmd cmd){
		if(cooldown == 0) {
			this.nextPos = getNextPos(this.getPos(), this.iaEasy());
		}
	}
}
