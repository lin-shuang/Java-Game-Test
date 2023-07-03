package com.graphics;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

public class Texture {
    public static Render floor = loadBitmap("./src/res/textures/sand.png");
    public static Render ceiling = loadBitmap("./src/res/textures/water.png");

    public static Render loadBitmap(String fileName){
        try{
            BufferedImage img = ImageIO.read(new FileInputStream(fileName));
            int width = img.getWidth();
            int height = img.getHeight();
            Render render = new Render(width, height);
            img.getRGB(0, 0, width, height, render.pixels, 0, width);
            return render;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
