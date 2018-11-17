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
		setPos(position);
		orientation = Orientation.NORTH;
		texture = null;

	}

	public Square getPos(){return position;}

	/**
	 * Permet de changer la position d'une entite
	 * Verifie que la position n'est pas nulle pour tout probleme futurs
	 * Verifie aussi que la position n'est pas deja occupee.
	 *
	 * @param position, la prochaine position de l'entite
	 */
	public void setPos(Square position){
		if(position == null)
			throw new NullPointerException("Position nulle a Entity");
		if(position.getEntity() != null)
			throw new NullPointerException("Position possede deja une entite"); //TODO changer le type d'exception

		this.position = position;
		position.setEntity(this);
	}


	//fonctions necessaires à l'attaque d'une entité
	public int getHp(){return this.hp;}
	public int getAttack(){return this.attack;}

	/**
	 * Permet de savoir si une entite est vivante ou non.
	 *
	 * @return false si elle possede encore des pv, false sinon
	 */
	public boolean isDead(){
		if(this.getHp() <= 0)
			return true;
		else return false;
	}

	public void setHp(int pv){this.hp = pv;}

	/**
	 * Diminue les points de vie de l'entite de la valeur precise
	 *
	 * @param attackPoint, les degat que ce prend l'entite
	 */
	public void diminuerHp(int attackPoint){
		this.hp = hp-attackPoint;
	}

	/**
	 * Une attaque se fait d'une entité à une autre
	 * Tant que l'un ou l'autre n'est pas mort, l'entité this va diminuer les points de vie de l'autre
	 * Et simultanément va voir ses points de vie baisser en fonction de l'attaque de son ennemi
	 *
	 * @param e, l'entite attaquee
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

	/**
	 * Permet d'eviter l'utilisation de "instance of"
	 *
	 * @return le type de l'entite sous forme de String.
	 */
	public abstract String getType();

	/**
	 * Retourne la texture de l'entite en fonction de sa direction.
	 * Est definie dans les classes filles.
	 *
	 * @return la texture correspondante.
	 */
	public abstract Image getTexture();

	/**
	 * Permet d'avoir l'orientation graphique d'une entite
	 *
	 * @return l'orientation
	 */
	public Orientation getOrientation(){return orientation;}

	/**
	 * Change l'orientation graphique d'une entite
	 *
	 * @param o, la prochaine orientation
	 */
	public void setOrientation(Orientation o){
		orientation=o;
	}
}
