package test;

import dao_Txt.MapTxtDAO;

public class testDAO {
	
	public static void main(String[] args){
		MapTxtDAO d =  MapTxtDAO.getInstance();
		d.load(0);
				
	}

}
