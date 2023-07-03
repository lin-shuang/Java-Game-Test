package com.graphics;

import com.game.Game;

public class Render3D extends Render{

    public Render3D(int width, int height) {
        super(width, height);
    }

    //render floor and ceiling
    public void floor(Game game){

        double rotation = game.time / 100.0;
        double cosine = Math.cos(rotation);
        double sine = Math.sin(rotation);
        double positionCeiling = 50;
        double positionFloor = 10;

        for(int y=0; y<height; y++){

            double yCeiling = (y + -height / 2.0) / height;
            double z = positionFloor / yCeiling;
            if(yCeiling < 0){
                z = positionCeiling / -yCeiling;
            };

            for(int x=0; x<width; x++){
                double xDepth = (x - width / 2.0) / height;
                xDepth *= z;
                double xx = xDepth*cosine + z*sine;
                double yy = z*cosine - xDepth*sine;
                int xPix = (int) xx;
                int yPix = (int) yy;
                pixels[x+y*width] = ((xPix & 15)*16) | ((yPix & 15)*16) << 8;
            }
        }
    }
}
