package model.entity;

import engine.Cmd;
import model.plateau.Square;
import model.plateau.Wall;

public class Ghost extends Monster{

	public Ghost(Square position, int hp, int atk) {
		super(position, hp, atk, 14);
	}

	@Override
	public String getType() {
		return "Ghost";
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
			this.nextPos = getNextPos(this.getPos(), cmd);
			//this.nextPos = getNextPos(this.getPos(), this.iaEasy());
		}
	}

	/**
	 * Verifie si la prochaine case contient deja une entite pour attaquer
	 * Permet de traverser des murs
	 *
	 * @return false (entite), true si valide
	 */
	@Override
	boolean canMove(){
		if(nextPos == null)
			return false;
		if(nextPos.getEntity() != null){
			if(nextPos.getEntity() instanceof Playable)
				this.attackEntity(nextPos.getEntity());
			return false;
		}
		return true;
	}
}
