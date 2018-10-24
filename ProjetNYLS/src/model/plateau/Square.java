package model.plateau;

import model.entity.Entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Square {
	
	int posx;
	int posy;
	Set<Effect> effets;

	Entity entity;

	public Square(int x, int y){
		posx=x;
		posy=y;
		effets = new HashSet<Effect>();  
	}
	
	public void addEffect(Effect e){
		effets.add(e);
	}

	public Entity getEntity(){return entity;}
	public void changeEntity(Entity entity){this.entity = entity;}
}
