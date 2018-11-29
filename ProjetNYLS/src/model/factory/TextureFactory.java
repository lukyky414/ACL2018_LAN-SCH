package model.factory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TextureFactory {
    private static BufferedImage texWall;
    private static BufferedImage texHero;
    private static BufferedImage texGoblin;
    private static BufferedImage texGhost;
    private static BufferedImage texChest;
    private static BufferedImage texPortal;
    private static BufferedImage texHealingPotion;
    private static TextureFactory instance = new TextureFactory();

    private TextureFactory(){
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            texWall = ImageIO.read(classLoader.getResourceAsStream("texture/texWall.png"));
            texHero = ImageIO.read(classLoader.getResourceAsStream("texture/texHero.png"));
            texGoblin = ImageIO.read(classLoader.getResourceAsStream("texture/texGoblin.png"));
            texGhost = ImageIO.read(classLoader.getResourceAsStream("texture/texGhost.png"));
            texChest = ImageIO.read(classLoader.getResourceAsStream("texture/texChest.png"));
            texPortal = ImageIO.read(classLoader.getResourceAsStream("texture/texPortal.png"));
            texHealingPotion = ImageIO.read(classLoader.getResourceAsStream("texture/texHealingPotion.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage getTexGoblin() {
        return texGoblin;
    }

    public static BufferedImage getTexGhost() {
        return texGhost;
    }

    public static BufferedImage getTexChest() {
        return texChest;
    }

    public static BufferedImage getTexPortal() {
        return texPortal;
    }

    public static BufferedImage getTexHealingPotion() {
        return texHealingPotion;
    }

    public BufferedImage getTexWall(){
        return texWall;
    }

    public static TextureFactory getInstance(){
        return instance;
    }

    public BufferedImage getTexHero() {
        return texHero;
    }
}
