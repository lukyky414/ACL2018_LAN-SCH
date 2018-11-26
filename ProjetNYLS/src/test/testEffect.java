package test;

import static org.junit.Assert.*;

import org.junit.Test;

import model.entity.Goblin;
import model.entity.Hero;
import model.plateau.Map;
import model.plateau.SecretPassage;

public class testEffect {
	
	// Magic, Trap and Treasure are all too small to fail 

	@Test
	public void testSecretPassageHero() {
		Map m = new Map();
		m.setSize(2,2);
		m.setTileType(0, 0, "Square");
		m.setTileType(1,1,"Square");
		m.setTileType(0, 1, "Square");
		SecretPassage e = new SecretPassage(1,1);
		m.getSquare(0, 0).addEffect(e);
		Hero h = new Hero(m.getSquare(0, 0),1,1);
		e.trigger(h, m.getSquare(0, 0));
		assertEquals(h.getPos().getPosX(),1);
		assertEquals(h.getPos().getPosY(),1);
	}
	
	@Test
	public void testSecretPassageMonster() {
		Map m = new Map();
		m.setSize(2,2);
		m.setTileType(0, 0, "Square");
		m.setTileType(1,1,"Square");
		m.setTileType(0, 1, "Square");
		SecretPassage e = new SecretPassage(1,1);
		m.getSquare(0, 0).addEffect(e);
		Goblin h = new Goblin(m.getSquare(0, 0),1,1,null, 0);
		e.trigger(h, m.getSquare(0, 0));
		assertEquals(h.getPos().getPosX(),1);
		assertEquals(h.getPos().getPosY(),1);
	}
	@Test
	public void testSecretPassageBlocked() {
		Map m = new Map();
		m.setSize(2,2);
		m.setTileType(0, 0, "Square");
		m.setTileType(1,1,"Square");
		m.setTileType(0, 1, "Square");
		SecretPassage e = new SecretPassage(1,1);
		m.getSquare(0, 0).addEffect(e);
		Hero h = new Hero(m.getSquare(0, 0),1,1);
		Goblin gobuta = new Goblin(m.getSquare(1, 1),1,1,h, 0);
		e.trigger(h, m.getSquare(0, 0));
		assertEquals(h.getPos().getPosX(),0);
		assertEquals(h.getPos().getPosY(),0);
	}
	
	

}
