package com.graphics;

import com.game.Game;

public class Render3D extends Render{

    public Render3D(int width, int height) {
        super(width, height);
    }

    //render floor and ceiling
    public void floor(Game game){
        for(int y=0; y<height; y++){

            double yCeiling = (y - height / 2.0) / height;
            if(yCeiling < 0){
                yCeiling = -yCeiling;
            }
            double z = 8.0 / yCeiling;

            for(int x=0; x<width; x++){
                double xDepth = (x - width / 2.0) / height;
                xDepth *= z;
                double xx = (xDepth + game.time);
                double yy = (z + game.time);
                int xPix = (int) xx;
                int yPix = (int) yy;
                pixels[x+y*width] = ((xPix & 15)*16) | ((yPix & 15)*16) << 8;
                //debug System.out.println(game.time);
            }
        }
    }
}
