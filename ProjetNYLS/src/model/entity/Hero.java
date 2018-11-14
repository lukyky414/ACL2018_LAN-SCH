package model.entity;

import model.factory.TextureFactory;
import model.plateau.Square;

import java.awt.*;

public class Hero extends Playable {
	public Hero(Square position, int hp, int atk) {
		super(position, hp, atk, 14);
		texture = TextureFactory.getInstance().getTexHero();
	}

	@Override
	public String getType() {
		return "Hero";
	}

	@Override
	public Image getTexture() {
		return texture;
	}

}
