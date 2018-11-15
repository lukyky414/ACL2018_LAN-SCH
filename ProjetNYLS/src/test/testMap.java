package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import junit.framework.Assert;
import model.plateau.Map;
import model.plateau.Square;
import model.plateau.Wall;
import static org.junit.Assert.*;



public class testMap {
	@Test
	public void testSetTyleType() {
		Map m= new Map();
		m.setSize(2, 3);
		m.setTileType(0, 0, "Wall");
		m.setTileType(1,1,"Square");
		System.out.println(m.getSquare(0, 0).getClass());
		assertTrue(m.getSquare(0, 0)instanceof Wall);
		assertTrue(m.getSquare(1, 1)instanceof Square);
	}
	
	@Test
	public void testSetTyleTypeWrongCoordinate() {
		Map m= new Map();
		m.setSize(1, 1);
		m.setTileType(-1, 0, "Wall");
		assertTrue(m.getSquare(0, 0) == null);
	}

}
