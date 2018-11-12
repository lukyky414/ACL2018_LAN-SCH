package dao_Txt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import model.entity.Ghost;
import model.entity.Goblin;
import model.entity.Hero;
import model.entity.Monster;
import model.plateau.Effect;
import model.plateau.Map;
import model.plateau.SecretPassage;
import model.plateau.Square;
import model.plateau.Trap;
import model.plateau.Treasure;

public class MapTxtDAO implements MapDAO{

	private static MapTxtDAO instance = null;
	private BufferedReader br = null;
	
	private MapTxtDAO(){
		
		
	}
	
	
	public static MapTxtDAO getInstance(){
		if(instance==null){
			instance = new MapTxtDAO();
		}
		return instance;
	}
	
	@Override
	public void save(Map m) {  //Normalement pas utilisé, peut être utile pour creation de map
		
		
	}

	@Override
	public Map load(int idMap) {
		String nomMap = "map"+idMap+".map";
		Map m = new Map();
		 try {
			 ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource("maps/"+nomMap).getFile());
			 br = new BufferedReader(new FileReader(file));
			 loadMapSize(m);
			 loadMapTile(m);
			 loadModifiers(m);
	     } catch (IOException e) {
	            e.printStackTrace();
	     }finally {
	         try {
	        	 if (br != null) {
	                    br.close();
	                }
	            } catch (IOException ex) {
	                ex.printStackTrace();
	            }
	        }
		 return m;
	}
	
	private void loadMapSize(Map m) throws IOException{
		String l = br.readLine();
		String[] t = l.split(" ");
		m.setSize(Integer.parseInt(t[0]),Integer.parseInt(t[1]));  //longueur puis hauteur
	}
	
	private void loadMapTile(Map m) throws IOException{
		int height = m.getHeigth();
		int width = m.getWidth();
		System.out.println("height : " + height);
		int c = 0;
		String line;
		while((line = br.readLine()) != null && c<height){ 
			for(int i =0;i<width;i++){
				char character = line.charAt(i);
				if(character =='1'){
					m.setTileType(i,c,"Wall");
				}else{  //if != 1
					m.setTileType(i,c,"basic");
					Square tmp = m.getSquare(i, c);
				}
			}
			c++;
		}
	}
	
	private void loadModifiers(Map m) throws IOException{
		String line;
		while((line = br.readLine()) != null){
			String[] s = line.split(" ");  //suivant le code, on load ennemie/ effet
			switch(s[0]){
			case "1":
				loadEffects(m, s);
				break;
			case "2":
				loadEnnemies(m,s);
				break;
			}
		}
	}
	
	private void loadEffects(Map m, String [] s){
		int posx = Integer.parseInt(s[2]);  //coordonnées en x et y de la case sur laquelle s'applique l'effet
		int posy = Integer.parseInt(s[3]);
		Effect e = null;
		switch(s[1]){
		case "1":
			e=new Treasure();
			break;
		case "2":
			int x= Integer.parseInt(s[4]);
			int y = Integer.parseInt(s[5]);
			e=new SecretPassage(x,y);
			break;
		case "3":
			e=new Trap();
			break;
		default:
			// throw IncorrectFileException
		}
		m.addEffect(posx, posy, e);
	}
	
	private void loadEnnemies(Map m, String[] s){
		int posx = Integer.parseInt(s[2]);  //coordonnées en x et y de la case sur laquelle s'applique l'effet
		int posy = Integer.parseInt(s[3]);
		Square sq = m.getSquare(posx, posy);
		Monster mon =null;
		switch(s[1]){
		case "1":
			mon = new Goblin(sq);
			break;
		case "2":
			mon = new Ghost(sq);
			break;
		default:
			// throw IncorrectFileException
		}
		sq.setEntity(mon);

	}



	
	

}
