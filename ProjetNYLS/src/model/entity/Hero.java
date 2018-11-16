package model.entity;

import model.factory.TextureFactory;
import model.plateau.Square;

import java.awt.*;

public class Hero extends Playable {
	public Hero(Square position, int hp, int atk) {
		super(position, hp, atk, 2);
		texture = TextureFactory.getInstance().getTexHero();
	}

	@Override
	public String getType() {
		return "Hero";
	}

	@Override
	public Image getTexture() {
		Image ret = texture;
		switch(orientation){
			case NORTH:
				ret = texture.getSubimage(0, 0, 50, 50);
				break;
			case WEST:
				ret = texture.getSubimage(0, 60, 50, 50);
				break;
			case SOUTH:
				ret = texture.getSubimage(0, 125, 50, 50);
				break;
			case EAST:
				ret = texture.getSubimage(0, 190, 50, 50);
				break;
		}
		return ret;
	}

}
