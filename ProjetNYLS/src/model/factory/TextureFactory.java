package model.factory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TextureFactory {
    private static Image texWall;
    private static TextureFactory instance = new TextureFactory();

    private TextureFactory(){
        try {
            texWall = ImageIO.read(new File("ressources/texture/texWall.png"));
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
}
