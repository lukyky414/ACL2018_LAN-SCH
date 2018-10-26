package dao_Txt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import model.plateau.Map;

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
				if(line.charAt(i)=='0'){
					m.setTileType(i,c,"Basic");
				}else{  //if == '1'
					m.setTileType(i,c,"Wall");
				}
			}
			c++;
		}
	}
	
	private void loadModifiers(Map m) throws IOException{
		String line;
		while((line = br.readLine()) != null){
			String[] s = line.split(" ");  //suivant le code, on load ennemie/ effet
		}
	}
	
	private void loadEffects(Map m){
		
	}
	
	private void loadEnnemies(Map m){
		
	}



	
	

}
