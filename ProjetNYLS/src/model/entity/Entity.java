package model.entity;

import model.plateau.Square;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {
	protected Square position;
	protected BufferedImage texture;
	protected int hp;
	protected int attack;
	protected Orientation orientation;

	public Entity(Square position, int hp, int atk){
		this.hp = hp;
		this.attack = atk;
		this.position = position;
		position.setEntity(this);
		orientation = orientation.NORTH;
		texture = null;

	}

	public Square getPos(){return position;}

	public void setPos(Square position){
		if(position == null)
			throw new NullPointerException("Position nulle a Entity");

		this.position = position;
	}


	//fonctions necessaires à l'attaque d'une entité
	public int getHp(){return this.hp;}
	public int getAttack(){return this.attack;}
	public boolean isDead(){
		if(this.getHp() <= 0)
			return true;
		else return false;
	}

	public void setHp(int pv){this.hp = pv;}
	public void diminuerHp(int attackPoint){
		this.hp = hp-attackPoint;
	}

	/*Une attaque se fait d'une entité à une autre
	Tant que l'un ou l'autre n'est pas mort, l'entité this va diminuer les points de vie de l'autre
	Et simultanément va voir ses points de vie baisser en fonction de l'attaque de son ennemi
	 */
	public void attackEntity(Entity e){
		while((!e.isDead()) && (!this.isDead()))
		{
			e.diminuerHp(this.attack);
			this.diminuerHp(e.getAttack());
			System.out.println("Vie de l'entite 1 : " + this.getHp() +"\nVie de l'entite 2 : " + e.getHp() + "\n" );
		}
		if(e.isDead())
			System.out.println("L'entite 2 est morte");
		if(this.isDead())
			System.out.println("L'entite 1 est morte");

	}

	public abstract String getType();

	public abstract Image getTexture();
	public Orientation getOrientation(){return orientation;}
	public void setOrientation(Orientation o){
		orientation=o;
	}
}
