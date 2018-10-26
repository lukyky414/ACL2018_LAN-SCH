package model.plateau;

public class Map {
	Square[][] cases;  //La case 0,0 est le coin haut-gauche
	
	public Map(){
		
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

	/**
	 * Retourne le carre a la position x y
	 * @param x la position horizontale
	 * @param y la position verticale
	 * @return la case si position dans la map, null sinon
	 */
	public Square getSquare(int x, int y){
		if(x < 0 || x > getWidth() || y < 0 || y > getHeigth())
			return null;
		return cases[y][x];
	}

	public String toString(){
		StringBuilder sb = new StringBuilder("");
		int width = getWidth();
		int height = getHeigth();
		for (int i = 0; i != height; i++){
			for (int j = 0; j != width; j++){
				if (cases[i][j].getIsWall()){
					sb.append("1");
				}
				else
					sb.append("0");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
