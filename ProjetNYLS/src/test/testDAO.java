package test;

import dao_Txt.MapTxtDAO;
import engine.Cmd;
import exceptions.CorruptDataException;
import model.entity.Hero;
import model.plateau.Map;

public class testDAO {
	
	public static void main(String[] args) throws CorruptDataException{
		MapTxtDAO d =  MapTxtDAO.getInstance();
		Map m = d.load(0);
		d.save(m);
				
	}

}
