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
    private static TextureFactory instance = new TextureFactory();

    private TextureFactory(){
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            texWall = ImageIO.read(new File(classLoader.getResource("texture/texWall.png").getFile()));
            texHero = ImageIO.read(new File(classLoader.getResource("texture/texHero.png").getFile()));
            texGoblin = ImageIO.read(new File(classLoader.getResource("texture/texGoblin.png").getFile()));
            texGhost = ImageIO.read(new File(classLoader.getResource("texture/texGhost.png").getFile()));
            texChest = ImageIO.read(new File(classLoader.getResource("texture/texChest.png").getFile()));
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
