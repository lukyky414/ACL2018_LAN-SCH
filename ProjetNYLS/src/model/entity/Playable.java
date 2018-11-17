package model.entity;

import engine.Cmd;
import model.plateau.Square;

import java.awt.*;

public abstract class Playable extends Movable{

	private Cmd lastCmd;
	private Cmd firstCmd;

	public Playable(Square position, int hp, int atk, int cooldown) {
		super(position,hp, atk, cooldown);
		lastCmd = Cmd.IDLE;
		firstCmd = Cmd.IDLE;
	}

	/**
	 * Choisis une nextPos avec la direction appuyee.
	 * Il n'est pas necessaire de limiter la vitesse a laquelle une commande est prise en compte.
	 *
	 * Cette methode est appellee directement depuis le LabyrinthController pour plus de reactivite.
	 *
	 * @param cmd, la commande actuelle
	 */
	public void evolve(Cmd cmd){
		if(cmd != Cmd.IDLE || firstCmd != Cmd.IDLE)
			lastCmd = cmd;
		if(lastCmd == Cmd.IDLE)
			firstCmd = Cmd.IDLE;
	}

	/**
	 * Permet de remettre a zero le lastCmd ou firstCmd pour la prise en compte d'une commande,
	 * et de limiter la vitesse de deplacement
	 */
	@Override
	public void move() {
		if(cooldown-- == 0) {
			this.nextPos = Movable.getNextPos(this.getPos(), lastCmd);
			firstCmd = lastCmd;
			lastCmd = Cmd.IDLE;
			super.move();
		}
	}

	/**
	 * Override de la fonction canMove() de Movable.
	 * On implémente en plus le cas où l'entité qui se trouve sur la prochaine case
	 * est un monstre pour l'attaquer.
	 *
	 * @return true si la case est valide (voir classe Movable).
	 */
	@Override
	boolean canMove(){
		if(nextPos != null && nextPos.getEntity() != null){
			if(nextPos.getEntity() instanceof Monster)
				this.attackEntity(nextPos.getEntity());
		}
		return super.canMove();
	}
}
