package model.entity;

import model.plateau.Square;

public abstract class Entity {
	private Square position;
	private int hp;
	private int attack;

	public Entity(Square position, int hp, int atk){
		this.hp = hp;
		this.attack = atk;
		this.position = position;
		position.setEntity(this);
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

	public void diminuerHp(int attackPoint){
		this.hp = hp-attackPoint;
	}

	/*Une attaque se fait d'une entité à une autre
	Tant que l'un ou l'autre n'est pas mort, l'entité this va diminuer les points de vie de l'autre
	Et simultanément va voir ses points de vie baisser en fonction de l'attaque de son ennemi
	 */
	public void attackEntity(Entity e){
		do{
			e.diminuerHp(this.attack);
			this.diminuerHp(e.getAttack());
		}while(!(e.isDead()) || !(this.isDead()));

	}

	public abstract String getType();
}
