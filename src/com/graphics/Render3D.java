package com.graphics;

import com.game.Game;

public class Render3D extends Render{

    //render distance
    public double[] zBuffer;
    public double renderDistance = 5000.00;
    
    public Render3D(int width, int height) {
        super(width, height);
        zBuffer = new double[width*height];
    }

    //render floor and ceiling
    public void floor(Game game){

        double rotation = game.controller.rotation;
        double cosine = Math.cos(rotation);
        double sine = Math.sin(rotation);
        double move = game.controller.z;
        double strafe = game.controller.x;
        double up = game.controller.y;
        double positionCeiling = 50.0;
        double positionFloor = 10.0;

        for(int y=0; y<height; y++){

            double yCeiling = (y + -height / 2.0) / height;
            double z = (positionFloor + up) / yCeiling;
            if(yCeiling < 0){
                z = (positionCeiling - up) / -yCeiling;
            };

            if(z <= (renderDistance)){
                for(int x=0; x<width; x++){
                    double xDepth = (x - width / 2.0) / height;
                    xDepth *= z;
                    double xx = xDepth*cosine + z*sine;
                    double yy = z*cosine - xDepth*sine;
                    int xPix = (int) (xx + strafe);
                    int yPix = (int) (yy + move);
                    zBuffer[x+y*width] = z;
                    pixels[x+y*width] = Texture.ceiling.pixels[(xPix & 7) + (yPix & 7) * 16];
                }
            }
        }
    }

    //render distance
    public void renderDistanceShader(){
        for(int i=0; i<width*height; i++){
            int brightness = (int) ((renderDistance) / zBuffer[i]);

            //brightness limiter
            if(brightness < 0){
                brightness = 0;
            }
            else if(brightness > 255){
                brightness = 255;
            }

            //color setter
            int r = ((pixels[i] >> 16) & 0xff) * brightness >>> 8;
            int g = ((pixels[i] >> 8) & 0xff) * brightness >>> 8;
            int b = ((pixels[i]) & 0xff) * brightness >>> 8;
            pixels[i] = r << 16 | g << 8 | b;
        }
    }
}
