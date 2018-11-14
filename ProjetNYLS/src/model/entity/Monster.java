package model.entity;

import engine.Cmd;
import model.plateau.Square;
import model.plateau.Wall;

import java.util.Random;

public abstract class Monster extends Movable {

	static private Random rdm = new Random();
	private Hero target;

	public Monster(Square position, int hp, int atk, int cooldown, Hero target) {
		super(position,hp, atk, cooldown);
		this.target = target;
	}

	public abstract String getType();
	/**
	 * Deplacement aleatoire pour une ia facile.
	 *
	 * @return Cmd - la direction choisie
	 */
	protected Cmd iaEasy(){
		switch(rdm.nextInt(9)){
			case 0:
			case 1:
				return Cmd.DOWN;
			case 2:
			case 3:
				return Cmd.RIGHT;
			case 4:
			case 5:
				return Cmd.LEFT;
			case 6:
			case 7:
				return Cmd.UP;
			default:
				return Cmd.IDLE;
		}
	}

	/**
	 * Se rapprocher en X ou en Y.
	 * Maniere peu efficace, qui reduit d'abord la distance la plus courte.
	 *
	 * @return Cmd - la direction choisie
	 */
	protected Cmd iaMedium1(){
		return null;
	}

	/**
	 * Verifie si la prochaine case soit est un mur, soit contient deja une entite
	 * Cette methode peut etre Override pour changer le deplacement (fantome)
	 * 		Attention, une case ne contient qu'une entite
	 * 	On ne teste que si l'entité qui est en face est un joueur.
	 * 	Le monstre n'attaquera pas ses semblables
	 *
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

	/**
	 * Il faut aussi diminuer la vitesse de deplacement
	 *
	 * @return rien
	 */
	@Override
	public void move(){
		if(cooldown-- == 0){
			super.move();
		}
	}

}
