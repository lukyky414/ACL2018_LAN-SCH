package test;

import dao_Txt.MapTxtDAO;
import engine.Cmd;
import model.entity.Hero;
import model.plateau.Map;

public class testDAO {
	
	public static void main(String[] args){
		MapTxtDAO d =  MapTxtDAO.getInstance();
		Map m = d.load(0);

		Hero moi = new Hero(m.getSquare(1,1));

		System.out.println("1,1");
		System.out.println(moi.getPos());

		moi.move(Cmd.RIGHT);
		System.out.println("2,1");
		System.out.println(moi.getPos());

		moi.move(Cmd.RIGHT);
		System.out.println("3,1");
		System.out.println(moi.getPos());

		moi.move(Cmd.UP);
		System.out.println("3,1");
		System.out.println(moi.getPos());
				
	}

}
