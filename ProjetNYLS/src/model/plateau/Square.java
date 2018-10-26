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
	Map map;

	Entity entity;

	public Square(int x, int y,Map m){
		posx=x;
		posy=y;
		effets = new HashSet<Effect>();
		map=m;
	}
	
	public void addEffect(Effect e){
		effets.add(e);
	}

	public Entity getEntity(){return entity;}
	public void changeEntity(Entity entity){this.entity = entity;}
	public int getPosX(){return posx;}
	public int getPosY(){return posy;}
	public Map getMap(){ return map;}

	@Override
	public String toString() {
		return "Square(" + posx + "," + posy + ")";
	}
}
