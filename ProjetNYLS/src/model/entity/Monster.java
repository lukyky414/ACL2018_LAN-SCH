package model.entity;

import engine.Cmd;
import model.plateau.Square;

public abstract class Monster extends Movable {

	public Monster(Square position, int cooldown) {
		super(position, cooldown);
	}

	public void comportement(Movable m){
		this.attack(m);
	}

	abstract void attack(Movable m);
	public abstract String getType();

	@Override
	public void evolve(Cmd cmd){
		this.nextPos = getNextPos(this.getPos(), Cmd.RIGHT);
	}

	/*private void comportement(Monster m){
		canMove = false;
	}

	private void comportement(Playable p){
		canMove = false;
		//poursuite
		//kill le playable
		// implémenter quand l'attaque sera au programme
	}*/
}
