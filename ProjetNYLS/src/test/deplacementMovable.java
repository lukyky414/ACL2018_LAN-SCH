package test;

import engine.Cmd;
import model.entity.Entity;
import model.entity.Ghost;
import model.entity.Hero;
import model.entity.Orientation;
import model.plateau.Map;
import model.plateau.Square;
import model.plateau.Wall;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

class testGHOST extends Ghost {

	public testGHOST(Square position, int hp, int atk, Hero target) {
		super(position, hp, atk, target);
	}

	public void setNextPos(Square position){
		this.nextPos = position;
	}
}

public class deplacementMovable {
	private Map map;
	private Square pos;
	private Square posGHOST;
	private Square posd;
	private Square posg;
	private Square posb;
	private Square posh;
	private Square posMURd;

	private Hero hero;
	private testGHOST ghost;
	private Entity ent;

	@Before
	public void preparation(){
		map = EasyMock.createStrictMock(Map.class);
		ent = EasyMock.createStrictMock(Entity.class);
		pos = new Square(1,1,map);
		posGHOST = new Square(1,1,map);

		posd = new Square(2,1,map);
		posg = new Square(0,1,map);
		posb = new Square(1,2,map);
		posh = new Square(1,0,map);

		posMURd = new Wall(2,1,map);

		hero = new Hero(pos, 17, 17);
		ghost = new testGHOST(posGHOST, 17, 17, hero);

		//Diminution du cooldown
		for(int i = 0; i < 2; i++)
			hero.move();
		for(int i = 0; i < 5; i++)
			ghost.move();
	}

	@Test
	public void droite(){
		assertEquals(pos, hero.getPos());
		//Mise en place du deplacement
		hero.evolve(Cmd.RIGHT);

		//Partie GetNextPos
		EasyMock.expect(map.getSquare(posd.getPosX(),posd.getPosY())).andReturn(posd);

		//Scenario # # # # # # #
		EasyMock.replay(map);//#
		//					   #
		hero.move();//		   #
		//					   #
		EasyMock.verify(map);//#
		// # # # # # # # # # # #

		//Autres verif
		assertEquals(posd, hero.getPos());
		assertEquals(Orientation.EAST, hero.getOrientation());
	}

	@Test
	public void gauche(){
		assertEquals(pos, hero.getPos());
		//Mise en place du deplacement
		hero.evolve(Cmd.LEFT);

		//Partie GetNextPos
		EasyMock.expect(map.getSquare(posg.getPosX(),posg.getPosY())).andReturn(posg);

		//Scenario # # # # # # #
		EasyMock.replay(map);//#
		//					   #
		hero.move();//		   #
		//					   #
		EasyMock.verify(map);//#
		// # # # # # # # # # # #

		//Autres verif
		assertEquals(posg, hero.getPos());
		assertEquals(Orientation.WEST, hero.getOrientation());
	}

	@Test
	public void bas(){
		assertEquals(pos, hero.getPos());
		//Mise en place du deplacement
		hero.evolve(Cmd.DOWN);

		//Partie GetNextPos
		EasyMock.expect(map.getSquare(posb.getPosX(),posb.getPosY())).andReturn(posb);

		//Scenario # # # # # # #
		EasyMock.replay(map);//#
		//					   #
		hero.move();//		   #
		//					   #
		EasyMock.verify(map);//#
		// # # # # # # # # # # #

		//Autres verif
		assertEquals(posb, hero.getPos());
		assertEquals(Orientation.SOUTH, hero.getOrientation());
	}

	@Test
	public void haut(){
		assertEquals(pos, hero.getPos());
		//Mise en place du deplacement
		hero.evolve(Cmd.UP);

		//Partie GetNextPos
		EasyMock.expect(map.getSquare(posh.getPosX(),posh.getPosY())).andReturn(posh);

		//Scenario # # # # # # #
		EasyMock.replay(map);//#
		//					   #
		hero.move();//		   #
		//					   #
		EasyMock.verify(map);//#
		// # # # # # # # # # # #

		//Autres verif
		assertEquals(posh, hero.getPos());
		assertEquals(Orientation.NORTH, hero.getOrientation());
	}

	@Test
	public void mur(){
		assertEquals(pos, hero.getPos());
		//Mise en place du deplacement
		hero.evolve(Cmd.RIGHT);

		//Partie GetNextPos
		EasyMock.expect(map.getSquare(posMURd.getPosX(),posMURd.getPosY())).andReturn(posMURd);

		//Scenario # # # # # # #
		EasyMock.replay(map);//#
		//					   #
		hero.move();//		   #
		//					   #
		EasyMock.verify(map);//#
		// # # # # # # # # # # #

		//Autres verif
		assertEquals(pos, hero.getPos());
	}

	@Test
	public void prisParEntite(){
		assertEquals(pos, hero.getPos());
		//Mise en place du deplacement
		hero.evolve(Cmd.RIGHT);

		//Entite sur la prochaine case
		posd.setEntity(ent);

		//Partie GetNextPos
		EasyMock.expect(map.getSquare(posd.getPosX(),posd.getPosY())).andReturn(posd);

		//Scenario # # # # # # #
		EasyMock.replay(map);//#
		//					   #
		hero.move();//		   #
		//					   #
		EasyMock.verify(map);//#
		// # # # # # # # # # # #

		//Autres verif
		assertEquals(pos, hero.getPos());
	}

	@Test
	public void ghostMur(){
		assertEquals(posGHOST, ghost.getPos());
		//Mise en place du deplacement
		ghost.setNextPos(posMURd);


		//Scenario # # #
		ghost.move();//#
		// # # # # # # #

		//Autres verif
		assertEquals(posMURd, ghost.getPos());
	}
}
