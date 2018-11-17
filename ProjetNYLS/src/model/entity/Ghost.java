package model.entity;

import engine.Cmd;
import model.factory.TextureFactory;
import model.plateau.Square;
import model.plateau.Wall;

import java.awt.*;

public class Ghost extends Monster{

	public Ghost(Square position, int hp, int atk, Hero target) {
		super(position,hp, atk, 5, target, 1);
		texture = TextureFactory.getTexGhost();
	}

	@Override
	public String getType() {
		return "Ghost";
	}

	@Override
	public Image getTexture() {
		Image ret = null;
		switch(orientation){
			case NORTH:
				ret = texture.getSubimage(0, 12, 70, 76);
				break;
			case WEST:
				ret = texture.getSubimage(0, 238, 70, 76);
				break;
			case SOUTH:
				ret = texture.getSubimage(0, 166, 70, 76);
				break;
			case EAST:
				ret = texture.getSubimage(0, 88, 70, 76);
				break;
		}
		return ret;
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
