package model.plateau;


public class Wall extends Square {

	public Wall(int x, int y, Map m) {
		super(x, y, m);
		isWall = true;
	}

}
