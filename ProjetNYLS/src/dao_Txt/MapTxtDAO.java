package dao_Txt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import exceptions.CorruptDataException;

import model.entity.Ghost;
import model.entity.Goblin;
import model.entity.Hero;
import model.entity.Monster;
import model.plateau.Effect;
import model.plateau.Magic;
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
	public Map load(int idMap) throws CorruptDataException{
		String nomMap = "map"+idMap+".map";
		Map m = new Map();
		 try {
			 ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource("maps/"+nomMap).getFile());
			 br = new BufferedReader(new FileReader(file));
			 loadMapSize(m);
			 loadMapTile(m);
			 Hero h = loadHero(m); //Obligatoire pour donner une cible aux monstres crées aprés
			 loadModifiers(m,h);
	     } catch (IOException e) {
	    	 throw new CorruptDataException("Probléme de formatage du fichier");
	     }finally {
	         try {
	        	 if (br != null) {
	                    br.close();
	                }
	            } catch (IOException ex) {
	            	throw new CorruptDataException("Probléme de formatage du fichier");
	            }
	        }
		 return m;
	}
	
	private Hero loadHero(Map m) throws IOException, CorruptDataException {
		Hero h =null ;
		String line;
		String[] s;
		if((line = br.readLine()) != null){
			s= line.split(" ");
			System.out.println(s[0]);
			if(s[0].equals("0")){	
				int posx = Integer.parseInt(s[1]);  //coordonnées en x et y de la case sur laquelle s'applique l'effet
				int posy = Integer.parseInt(s[2]);
				int vie = Integer.parseInt(s[3]);
				int attaque= Integer.parseInt(s[4]);
				Square sq = m.getSquare(posx, posy);
				h=new Hero(sq,vie,attaque);
				sq.setEntity(h);
			}else{
				throw new CorruptDataException("Probléme de formatage du fichier");
			}
		}
		return h;
		
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
	
	private void loadModifiers(Map m, Hero h) throws IOException, CorruptDataException{
		String line;
		
		while((line = br.readLine()) != null){
			String[] s = line.split(" ");  //suivant le code, on load ennemie/ effet
			switch(s[0]){
			case "1":
				loadEffects(m, s);
				break;
			case "2":
				loadEnnemies(m,s,h);
				break;
			default:
				throw new CorruptDataException("Probléme de formatage des modificateurs: code inconnu "+s[0]);
			}
		}
	}
	
	private void loadEffects(Map m, String [] s) throws CorruptDataException{
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
		case "4":
			e=new Magic();
			break;
		default:
			throw new CorruptDataException("Probléme de formatage des effets");
		}
		m.addEffect(posx, posy, e);
	}
	
	private void loadEnnemies(Map m, String[] s, Hero h) throws CorruptDataException{
		int posx = Integer.parseInt(s[2]);  //coordonnées en x et y de la case sur laquelle s'applique l'effet
		int posy = Integer.parseInt(s[3]);
		int vie = Integer.parseInt(s[4]);
		int attaque= Integer.parseInt(s[5]);
		Square sq = m.getSquare(posx, posy);
		Monster mon =null;
		switch(s[1]){
		case "1":
			mon = new Goblin(sq,vie,attaque,h);
			break;
		case "2":
			mon = new Ghost(sq,vie,attaque,h);
			break;
		default:
			throw new CorruptDataException("Probléme de formatage des ennemies");
		}
		sq.setEntity(mon);

	}



	
	

}
