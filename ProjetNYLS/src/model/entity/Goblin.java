package model.entity;

import engine.Cmd;
import model.factory.TextureFactory;
import model.plateau.Square;

import java.awt.*;

public class Goblin extends Monster {
	
	public Goblin(Square position, int hp, int atk, Hero target, int difficulty) {
		super(position,hp, atk, 10, target, difficulty);
		texture = TextureFactory.getTexGoblin();
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
				ret = texture.getSubimage(0, 138, 60, 58);
				break;
			case WEST:
				ret = texture.getSubimage(0, 193, 60, 65);
				break;
			case SOUTH:
				ret = texture.getSubimage(0, 0, 60, 65);
				break;
			case EAST:
				ret = texture.getSubimage(0, 60, 60, 65);
				break;
		}
		return ret;
	}
}
