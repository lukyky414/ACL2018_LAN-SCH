package model.entity;

import engine.Cmd;
import model.plateau.Square;
import model.plateau.Wall;

public abstract class Monster extends Movable {

	public Monster(Square position, int hp, int atk, int cooldown) {
		super(position,hp, atk, cooldown);
	}


	public abstract String getType();

	@Override
	public void evolve(Cmd cmd){
		this.nextPos = getNextPos(this.getPos(), Cmd.RIGHT);
	}

	/**
	 * Verifie si la prochaine case soit est un mur, soit contient deja une entite
	 * Cette methode peut etre Override pour changer le deplacement (fantome)
	 * 		Attention, une case ne contient qu'une entite
	 * 	On ne teste que si l'entité qui est en face est un joueur.
	 * 	Le monstre n'attaquera pas ses semblables
	 * @return false (si mur ou entite), true si valide
	 */
	@Override
	boolean canMove(){
		if(nextPos.getEntity() != null){
			if(nextPos.getEntity() instanceof Playable)
				this.attackEntity(nextPos.getEntity());
		}
		return super.canMove();
	}
}
