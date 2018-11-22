package model.plateau;

import model.entity.Entity;

import java.util.Iterator;

public class Map implements Iterable<Square> {
	private Square[][] cases;  //La case 0,0 est le coin haut-gauche
	private boolean levelFinished;
	private int level;
	
	public Map(){
		levelFinished=false;
		level = 0;
	}

	
	public Map(int x, int y){
		setSize(x,y);
	}
	
	public void setSize(int x, int y){
		cases = new Square[y][x];
	}
	
	public int getWidth(){
		return cases[0].length;
	}
	public int getHeigth(){
		return cases.length;
	}
	
	public void setLevelNumber(int l) {
		level =l;
	}
	
	public int getLevelNumber() {
		return level;
	}
	public void setTileType(int x,int y ,String s){
		switch (s){
		case "Wall":
			cases[y][x]= new Wall(x, y,this);
			break;
		default:
			cases[y][x]= new Square(x, y,this);
			break;
		}
	}

	public void setEntity(int x, int y, Entity e){
		cases[y][x].setEntity(e);
	}

	/**
	 * Retourne le carre a la position x y
	 * @param x la position horizontale
	 * @param y la position verticale
	 * @return la case si position dans la map, null sinon
	 */
	public Square getSquare(int x, int y){
		if(x < 0 || x >= getWidth() || y < 0 || y >= getHeigth())
			return null;
		return cases[y][x];
	}

	public String toString(){
		StringBuilder sb = new StringBuilder("");
		int width = getWidth();
		int height = getHeigth();
		for (int i = 0; i != height; i++){
			for (int j = 0; j != width; j++){
				sb.append(cases[i][j].getChar());
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	/**
	 * signal au niveau qu'un trésor à été pris
	 * 
	 */

	public void treasureTaken() {
		levelFinished=true;
	}
	/**
	 * 
	 * @return true si le niveau est terminé
	 */
	public boolean isFinished(){
		return levelFinished;
	}
	
	public void addEffect(int x, int y, Effect e){
		cases[y][x].addEffect(e);
	}

	@Override
	public Iterator<Square> iterator() {
		return new MapIterator(this);
	}
}
