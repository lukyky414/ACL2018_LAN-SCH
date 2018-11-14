package model.plateau;

import java.util.Iterator;

public class MapIterator implements Iterator<Square> {
	Map map;
	int x, y;
	int w, h;

	public MapIterator(Map map){
		this.map = map;
		x = y = 0;
		w = map.getWidth();
		h = map.getHeigth();
	}

	@Override
	public boolean hasNext() {
		return x < w && y < h;
	}

	@Override
	public Square next() {
		Square res = map.getSquare(x,y);

		x++;
		if(x == w){
			y++;
			x = 0;
		}


		return res;
	}
}
