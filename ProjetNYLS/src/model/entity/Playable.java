package model.entity;

import engine.Cmd;
import model.plateau.Square;

public abstract class Playable extends Movable{

	private Cmd lastCmd;

	public Playable(Square position, int hp, int atk, int cooldown) {
		super(position,hp, atk, cooldown);
		lastCmd = Cmd.IDLE;
	}


	public abstract String getType();

	/**
	 * Choisis une nextPos avec la direction appuyee.
	 * Il n'est pas necessaire de limiter la vitesse
	 * a laquelle une commande est prise en compte.
	 *
	 * @return rien
	 */
	public void evolve(Cmd cmd){
		//if(cmd != Cmd.IDLE)
			lastCmd = cmd;
	}

	/**
	 * Permet de remettre a zero le lastCmd,
	 * et de limiter la vitesse de deplacement
	 *
	 * @return rien
	 */
	@Override
	public void move() {
		if(cooldown-- == 0) {
			this.nextPos = Movable.getNextPos(this.getPos(), lastCmd);
			lastCmd = Cmd.IDLE;
			super.move();
		}
	}

	/** @Override de la fonction canMove() de Movable.
	 * On implémente en plus le cas où l'entité qui se trouve sur la prochaine case
	 * est un monstre. Si oui, on attaque.
	 */
	@Override
	boolean canMove(){
		if(nextPos == null)
			return false;
		if(nextPos.getEntity() != null){
			if(nextPos.getEntity() instanceof Monster)
				this.attackEntity(nextPos.getEntity());
		}
		return super.canMove();
	}
}
