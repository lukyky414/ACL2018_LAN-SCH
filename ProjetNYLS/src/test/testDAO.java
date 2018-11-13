package test;

import dao_Txt.MapTxtDAO;
import engine.Cmd;
import model.entity.Hero;
import model.plateau.Map;

public class testDAO {
	
	public static void main(String[] args){
		MapTxtDAO d =  MapTxtDAO.getInstance();
		Map m = d.load(0);
				
	}

}
