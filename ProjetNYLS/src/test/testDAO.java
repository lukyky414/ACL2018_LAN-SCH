package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import dao_Txt.MapTxtDAO;
import engine.Cmd;
import exceptions.CorruptDataException;
import model.entity.Entity;
import model.entity.Hero;
import model.plateau.Effect;
import model.plateau.Map;
import model.plateau.Treasure;

public class testDAO {
	@Test
	public void testSaveLoad(){
		MapTxtDAO d =  MapTxtDAO.getInstance();
		Map m = new Map();
		m.setLevelNumber(2);
		m.setSize(2, 2);
		m.setTileType(0, 0, "Square");
		m.setTileType(0, 1, "Wall");
		m.setTileType(1, 0, "Square");
		m.setTileType(1, 1, "Square");
		m.setEntity(0, 0, new Hero(m.getSquare(0, 0),4,3));
		m.addEffect(1, 1, new Treasure());
		d.save(m);
		try {
			Map m2 = d.load(-1);
			assertEquals(m2.getSquare(0, 0).getIsWall(),false);
			assertEquals(m2.getSquare(1, 0).getIsWall(),false);
			assertEquals(m2.getSquare(1, 1).getIsWall(),false);
			assertEquals(m2.getSquare(0, 1).getIsWall(),true);
			Entity h =m2.getSquare(0, 0).getEntity();
			assertEquals((h instanceof Hero) ,true);
			assertEquals(h.getAttack(),3);
			assertEquals(h.getHp(),4);
			for(Effect e: m2.getSquare(1, 1)) {
				assertEquals(e.getType(),1);
			}
			
		} catch (CorruptDataException e) {
			assert(false):"Données non conformes/fichier non accessible";
		}
	}
	@Test(expected = CorruptDataException.class)
	public void testLoadException() throws CorruptDataException {
		MapTxtDAO d =  MapTxtDAO.getInstance();
		Map m = d.load(Integer.MAX_VALUE);
	}

}
