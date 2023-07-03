package com.input;

public class Controls {
    public double x, y, z, rotation, x1, y1, z1, rotation1;

    public void tick(boolean moveF, boolean moveB, boolean strafeR, boolean strafeL, boolean rotateR, boolean rotateL){
        double rotationSpeed = 1.0;
        double walkSpeed = 1.0;
        double xMove = 0;
        double zMove = 0;

        if(moveF){
            zMove++;
        }
        if(moveB){
            zMove--;
        }
        if(strafeR){
            xMove++;
        }
        if(strafeL){
            xMove--;
        }
        if(rotateR){
            rotation1 += rotationSpeed;
        }
        if(rotateL){
           rotation1 -= rotationSpeed;
        }
        
        x1 += (xMove*Math.cos(rotation) + zMove*Math.sin(rotation)) * walkSpeed;
        z1 += (zMove*Math.cos(rotation) - xMove*Math.sin(rotation)) * walkSpeed;
        x += x1;
        z += z1;
        x1 *= 0.1;
        z1 *= 0.1;
        rotation += rotation1;
        rotation *= 0.05;
    }
}
