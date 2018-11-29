package dao_Txt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Random;

import exceptions.CorruptDataException;

import model.entity.Entity;
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
	public void save(Map m) {
		//File f  = new File("ProjetNYLS/ressources/maps/map-1.map");
		//ClassLoader classLoader = getClass().getClassLoader();
		//File f = new File(classLoader.getResource("maps/map-1.map").getFile());
		StringBuilder s = new StringBuilder();
		FileWriter fw =null;
		BufferedWriter bw=null;
		PrintWriter writer=null;
		try {
			/*if(!f.exists()){
				f.createNewFile();
			}*/
			//fw = new FileWriter(f);
			//bw = new BufferedWriter(fw);
			writer = new PrintWriter("map-1.map");
			s.append(m.getLevelNumber()+"\n");  //enregistre le numero du niveau pour pouvoir reprendre au même stade
			saveLayout(s,m);
			s.append("\n"); //toujours une ligne vide entre le layout et les modifiers
			saveHero(s,m);
			saveModifiers(s,m);
			
			writer.write(s.toString());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try{
				if(bw!=null)
					bw.close();
				if(fw!=null)
					fw.close();
				if(writer!=null)
					writer.close();
					
			} catch(IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	private void saveLayout(StringBuilder s, Map m){
		int h =m.getHeigth() ;
		int w = m.getWidth();
		s.append(w+" "+h+"\n");
		for(int i=0;i<h;i++){
			for(int j=0; j<w;j++){
				if(m.getSquare(j, i) instanceof Square &&m.getSquare(j, i).getClass()!=Square.class){  //si la case est un mur
					s.append("1");
				}else{
					s.append(0);
				}
			}
			s.append("\n");
		}
	}
	private void saveHero(StringBuilder s, Map m){
		for(Square sq :m){
			if(sq.getEntity() instanceof Hero){
				s.append("0 "+sq.getPosX()+" "+sq.getPosY()+" "+sq.getEntity().getHp()+" "+sq.getEntity().getAttack()+"\n");
			}
		}
	}
	
	private void saveModifiers(StringBuilder s, Map m){
		int x;
		int y;
		Entity entite;
		for(Square sq : m){
			x=sq.getPosX();
			y=sq.getPosY();
			for(Effect e : sq){
				s.append("1 "+e.getType()+" "+x+" "+y+" ");
				switch (e.getType()){
				case 1:
					break;
				case 2:
					s.append(((SecretPassage) e).getPosXSortie()+" "+((SecretPassage) e).getPosYSortie());
					break;
				case 3:
					s.append(((Trap)e).getDamage());
					break;
				case 4:
					s.append(((Magic)e).getHealing());
					break;
				}
				s.append("\n");
			}
			entite=sq.getEntity();
			if(entite!=null && !(entite instanceof Hero)){
				s.append("2 ");
				if(entite instanceof Goblin){
					s.append("1 ");
				}else if(entite instanceof Ghost){
					s.append("2 ");
				}
				s.append(x+" "+y+" "+entite.getHp()+" "+entite.getAttack()+" "+entite.getAttack()+" "+((Monster) entite).getDifficulty()+"\n");
			}
		}
		
	}

	/**
	 * load map number idMap
	 * to load the saved game, you need to use idMap = -1
	 * 
	 */
	@Override
	public Map load(int idMap) throws CorruptDataException{
		String nomMap = "map"+idMap+".map";
		Map m = new Map();
		 try {
			 //ClassLoader classLoader = getClass().getClassLoader();
			//File file = new File(classLoader.getResource("maps/"+nomMap).getFile());
			 if(idMap != -1) {
				 InputStream is = getClass().getResourceAsStream("/maps/"+nomMap);
				 InputStreamReader isr  = new InputStreamReader(is);
				 br = new BufferedReader(isr);
			 }else {
				 br = new BufferedReader(new FileReader("map-1.map"));
			 }
			 loadMapNumber(m);
			 loadMapSize(m);
			 loadMapTile(m);
			 Hero h = loadHero(m); //Obligatoire pour donner une cible aux monstres crées aprés
			 loadModifiers(m,h);
	     } catch (IOException |NullPointerException n) {
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
	
	private void loadMapNumber(Map m) throws IOException {
		String line;
		String[] s;
		if((line = br.readLine()) != null){
			s= line.split(" ");
			m.setLevelNumber(Integer.valueOf(s[0]));
		}
		
	}
	
	private Hero loadHero(Map m) throws IOException, CorruptDataException {
		Hero h =null ;
		String line;
		String[] s;
		if((line = br.readLine()) != null){
			s= line.split(" ");
			if(s[0].equals("0")){	
				int posx = Integer.parseInt(s[1]);  //coordonnées en x et y de la case sur laquelle s'applique l'effet
				int posy = Integer.parseInt(s[2]);
				if(posx<0 || posy<0){
					int[] posAlea = generationPosHero(m);
					posx = posAlea[0];
					posy = posAlea[1];
				}
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

	private int[] generationPosHero(Map m ){
		Random rand = new Random();
		boolean good = false;
		int newX = 0;
		int newY = 0;
		while(!good) {
			good = true;
			newX = rand.nextInt(m.getWidth());
			newY = rand.nextInt(m.getHeigth());
			if(m.getSquare(newX,newY).getEntity() != null){
				good=false;
			}
			if ( m.getSquare(newX,newY).getIsWall()) {
				good = false;
			}
			if (m.getSquare(newX,newY).iterator().hasNext()){ // si effet on recommence
				good = false;
			}
		}
		return new int[] {newX,newY};

	}


	private void loadMapSize(Map m) throws IOException{
		String l = br.readLine();
		String[] t = l.split(" ");
		m.setSize(Integer.parseInt(t[0]),Integer.parseInt(t[1]));  //longueur puis hauteur
	}
	
	private void loadMapTile(Map m) throws IOException{
		int height = m.getHeigth();
		int width = m.getWidth();
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
			int d = Integer.parseInt(s[4]);
			e=new Trap(d);
			break;
		case "4":
			int h = Integer.parseInt(s[4]);
			e=new Magic(h);
			break;
		default:
			throw new CorruptDataException("Probléme de formatage des effets");
		}
		m.addEffect(posx, posy, e);
	}
	
	private void loadEnnemies(Map m, String[] s, Hero h) throws CorruptDataException{
		int posx = Integer.parseInt(s[2]);  //coordonnées en x et y de la case sur laquelle s'applique l'effet
		int posy = Integer.parseInt(s[3]);
		if(posx<0 || posy<0){
			int[] posAlea = generationPosEnnemie(m , Integer.valueOf(s[1]));
			posx = posAlea[0];
			posy = posAlea[1];
		}
		int vie = Integer.parseInt(s[4]);
		int attaque= Integer.parseInt(s[5]);
		int difficulte = Integer.parseInt(s[6]);
		if(difficulte<0 || difficulte >3){
			throw new CorruptDataException("Probléme de formatage des ennemies(difficulte)");
		}
		Square sq = m.getSquare(posx, posy);
		Monster mon =null;
		switch(s[1]){
		case "1":
			mon = new Goblin(sq,vie,attaque,h, difficulte);
			break;
		case "2":
			mon = new Ghost(sq,vie,attaque,h, difficulte);
			break;
		default:
			throw new CorruptDataException("Probléme de formatage des ennemies(type)");
		}
		sq.setEntity(mon);

	}

	private int[] generationPosEnnemie(Map m, int type ){
		Random rand = new Random();
		boolean good = false;
		int newX = 0;
		int newY = 0;
		while(!good) {
			good = true;
			newX = rand.nextInt(m.getWidth());
			newY = rand.nextInt(m.getHeigth());
			if(m.getSquare(newX,newY).getEntity() != null){
				good=false;
			}
			if (type==1 && m.getSquare(newX,newY).getIsWall()) {
				good = false;
			}
		}
		return new int[] {newX,newY};

	}

	
	

}
