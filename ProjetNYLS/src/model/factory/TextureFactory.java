package model.factory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TextureFactory {
    private static Image texWall;
    private static Image texHero;
    private static TextureFactory instance = new TextureFactory();

    private TextureFactory(){
        try {
            texWall = ImageIO.read(new File("texture/texWall.png"));
            texHero = ImageIO.read(new File("texture/texHero.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Image getTexWall(){
        return texWall;
    }

    public static TextureFactory getInstance(){
        return instance;
    }

    public Image getTexHero() {
        return texHero;
    }
}
