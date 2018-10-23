package dao_Txt;

import java.io.BufferedReader;
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
			 br = new BufferedReader(new FileReader("map/"+nomMap));
	        /* String line;
	         while ((line = br.readLine()) != null) {
	        	 System.out.println(line);
	         }*/
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
		m.setSize(Integer.parseInt(t[0]),Integer.parseInt(t[1]));
	}
	
	private void loadMapTile(Map m) throws IOException{
		int height = m.getHeigth();
		int width = m.getWidth();
		int c = 0;
		String line;
		while((line = br.readLine()) != null && c<height){ 
			for(int i =0;i<width;i++){
				if(line.charAt(i)=='0'){
					m.setTileType(c,i,"Basic");
				}else{  //if == '1'
					m.setTileType(c,i,"Wall");
				}
			}
			c++;
		}
	}
	
	private void loadEffects(Map m){
		
	}
	
	private void loadEnnemies(Map m){
		
	}



	
	

}
