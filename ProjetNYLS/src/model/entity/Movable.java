package model.entity;

import engine.Cmd;
import model.plateau.Square;
import model.plateau.Wall;

public abstract class Movable extends Entity {

	Square nextPos = null;


	public Movable(Square position) {
		super(position);
	}

	/**
	 * Verifie si la prochaine case soit est un mur, soit contient deja une entite
	 * Cette methode peut etre Override pour changer le deplacement (fantome)
	 * 		Attention, une case ne contient qu'une entite
	 * @return false (si mur ou entite), true si valide
	 */
	boolean canMove(){
		if(nextPos.getEntity() != null)
			return false;
		if(nextPos instanceof Wall)
			return false;
		return true;
	}



	public void move(Cmd dir){
		changeNextPos(dir);

		/*Rajouter un comportement ici,
		* On a la position actuelle et la prochaine position.
		* Tester tout ce qu'il faut ici, ou apres la verification
		* 	que l'on peut se deplacer.*/

		if(canMove()){
			this.getPos().changeEntity(null);
			this.nextPos.changeEntity(this);
			this.setPos(nextPos);
			this.nextPos = null;
		}
	}

	private void changeNextPos(Cmd dir){
		int x = 0, y = 0;
		switch(dir){
			case UP:
				y = -1;
				break;
			case DOWN:
				y = 1;
				break;
			case LEFT:
				x = -1;
				break;
			case RIGHT:
				x = 1;
				break;
			default: // idle, x=0, y=0
		}
		x += this.getPos().getPosX();
		y += this.getPos().getPosY();

		this.nextPos = this.getPos().getMap().getSquare(x, y);
	}

	private void collision(Movable m){
		this.comportement(m);
	}

	abstract void comportement(Movable m);
	public abstract String getType();
}
