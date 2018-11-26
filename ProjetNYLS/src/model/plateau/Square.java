package model.plateau;

import model.entity.Entity;

import java.util.*;

public class Square implements Iterable<Effect> {
	
	int posx;
	int posy;
	boolean isWall;
	Set<Effect> effets;
	Map map;

	Entity entity;

	public Square(int x, int y,Map m){
		posx=x;
		posy=y;
		entity = null;
		effets = new HashSet<Effect>();
		map=m;
		isWall = false;
	}
	
	public void addEffect(Effect e){
		effets.add(e);
	}

	public Entity getEntity(){return entity;}

	public void setEntity(Entity entity){this.entity = entity;}

	public int getPosX(){return posx;}
	public int getPosY(){return posy;}
	public Map getMap(){ return map;}

	@Override
	public String toString() {
		return "Square(" + posx + "," + posy + ")";
	}

    public boolean getIsWall() {
        return isWall;
    }

    public char getChar() {
		char ret = '0';
		if (isWall == true)
			ret = '1';
		if (entity != null){
			if (entity.getType().equals("Hero"))
				ret = 'h';
		}
		return ret;
	}
    
    public void treasureTaken(){
    	map.treasureTaken();
    }
    
    public void triggerEffects(Entity h){
    	for (Effect e: effets){
    		e.trigger(h,this);
    	}
    }

	public ArrayList<Entity> lookAround(){
		ArrayList<Entity> entitiesAround = new ArrayList<Entity>();
		int x = this.getPosX();
		int y = this.getPosY();
		Square posUp = this.getMap().getSquare(x, y-1);
		Square posLeft = this.getMap().getSquare(x-1, y);
		Square posRight = this.getMap().getSquare(x+1, y);
		Square posDown = this.getMap().getSquare(x, y+1);

		if(posUp.getEntity() != null)
			entitiesAround.add(posUp.getEntity());
		if(posLeft.getEntity() != null)
			entitiesAround.add(posLeft.getEntity());
		if(posRight.getEntity() != null)
			entitiesAround.add(posRight.getEntity());
		if(posDown.getEntity() != null)
			entitiesAround.add(posDown.getEntity());
		return entitiesAround;
	}

	@Override
	public Iterator<Effect> iterator() {
		return effets.iterator();
	}
}
