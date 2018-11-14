package model.entity;

import engine.Cmd;
import model.factory.TextureFactory;
import model.plateau.Square;

import java.awt.*;

public class Goblin extends Monster {
	
	public Goblin(Square position, int hp, int atk, Hero target) {
		super(position,hp, atk, 14, target);
		texture = TextureFactory.getInstance().getTexGoblin();
	}


	@Override
	public String getType() {
		return "Goblin";
	}

	@Override
	public Image getTexture() {
		Image ret = null;
		switch(orientation){
			case NORTH:
				ret = texture.getSubimage(0, 138, 60, 65);
				break;
			case WEST:
				ret = texture.getSubimage(0, 193, 60, 65);
				break;
			case SOUTH:
				ret = texture.getSubimage(0, 0, 60, 70);
				break;
			case EAST:
				ret = texture.getSubimage(0, 60, 60, 70);
				break;
		}
		return ret;
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
