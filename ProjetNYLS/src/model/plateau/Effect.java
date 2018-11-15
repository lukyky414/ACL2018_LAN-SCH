package model.plateau;

import model.entity.Entity;

import java.awt.image.BufferedImage;

public abstract class Effect {

	protected BufferedImage texture;

	protected Effect(){
		texture = null;
	}

	public BufferedImage getTexture(){
		return texture;
	}

	public abstract void trigger(Entity h,Square s);
	
	public abstract int getType();

}
