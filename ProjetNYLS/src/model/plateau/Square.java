package model.plateau;

import java.util.ArrayList;
import java.util.List;

public class Square {
	
	int posx;
	int posy;
	List<Effect> effets;

	public Square(int x, int y){
		posx=x;
		posy=y;
		effets = new ArrayList<Effect>();  // une setlist serai meilleure (mais il est tard ^^)
	}
	
	public void addEffect(Effect e){
		effets.add(e);
	}
}
