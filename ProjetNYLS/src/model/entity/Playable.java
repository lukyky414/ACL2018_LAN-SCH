package model.entity;

import engine.Cmd;
import model.plateau.Square;

public abstract class Playable extends Movable{

	private Cmd lastCmd;

	public Playable(Square position, int cooldown) {
		super(position, cooldown);
		lastCmd = Cmd.IDLE;
	}

	public void comportement(Movable m){
		this.attack(m);
	}

	abstract void attack(Movable m);
	public abstract String getType();

	/**
	 * Choisis une nextPos avec la direction appuyee.
	 *
	 * @return rien
	 */
	public void evolve(Cmd cmd){
		if(cmd != Cmd.IDLE){
			lastCmd = cmd;
		}

		this.nextPos = Movable.getNextPos(this.getPos(), lastCmd);
	}

	/**
	 * Permet de remettre a zero le lastCmd.
	 *
	 * @return rien
	 */
	@Override
	public void move() {
		if(cooldown == 0)
			lastCmd = Cmd.IDLE;
		super.move();
	}

}
