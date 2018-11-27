package test;

import model.entity.Ghost;
import model.entity.Goblin;
import model.entity.Hero;
import model.plateau.Map;
import model.plateau.Square;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EntityTest {

    Map m;
    Square caseHero;
    Square caseMonstre;
    Square caseFantome;
    Hero hero;
    Ghost fantome;
    Goblin monstre;

    @Before
    public void creation(){
        m = EasyMock.createStrictMock(Map.class);
        caseHero = new Square(0, 1, m);
        caseMonstre = new Square(1, 1, m);
        caseFantome = new Square(0, 0, m);
        hero  = new Hero(caseHero, 5, 3);
        fantome = new Ghost(caseFantome, 5, 2, hero, 1);
        monstre = new Goblin(caseMonstre, 5, 1, hero, 1);
    }

    @Test
    public void getPos() throws Exception {

        assertEquals(caseHero, hero.getPos());
        assertEquals(caseMonstre, monstre.getPos());
        assertEquals(caseFantome, fantome.getPos());
    }

    @Test
    public void getHp() throws Exception {

        assertEquals(5, hero.getHp());
        assertEquals(5, fantome.getHp());
        assertEquals(5, monstre.getHp());

    }

    @Test
    public void getHpMax() throws Exception {

        assertEquals(5, hero.getHpMax());
        assertEquals(5, monstre.getHpMax());
        assertEquals(5, fantome.getHpMax());
    }

    @Test
    public void getAttack() throws Exception {

        assertEquals(3, hero.getAttack());
        assertEquals(2, fantome.getAttack());
        assertEquals(1, monstre.getAttack());
    }

    @Test
    public void isDead() throws Exception {
        hero.setHp(0);
        assertTrue(hero.isDead());
    }

    @Test
    public void diminuerHp() throws Exception {

        hero.diminuerHp(monstre.getAttack());
        assertEquals(4, hero.getHp());
    }

    @Test
    public void attack() throws Exception {
        EasyMock.expect(m.getSquare(1,1)).andReturn(caseMonstre);
        EasyMock.expect(m.getSquare(-1,1)).andReturn(null);
        EasyMock.expect(m.getSquare(0,2)).andReturn(null);
        EasyMock.expect(m.getSquare(0,0)).andReturn(caseFantome);


        EasyMock.replay(m);

        hero.attack();

        EasyMock.verify(m);

        assertEquals(monstre.getHp(), 2);
        assertEquals(fantome.getHp(), 2);
    }

    @Test
    public void canAttack() throws Exception {

        boolean can = hero.canAttack();
        assertTrue(can);
    }

}