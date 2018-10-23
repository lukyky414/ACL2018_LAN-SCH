package model.plateau;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Square {
	
	int posx;
	int posy;
	Set<Effect> effets;

	public Square(int x, int y){
		posx=x;
		posy=y;
		effets = new HashSet<Effect>();  
	}
	
	public void addEffect(Effect e){
		effets.add(e);
	}
}
