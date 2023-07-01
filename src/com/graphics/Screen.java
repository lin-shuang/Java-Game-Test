package com.graphics;

import java.util.Random;
import com.game.Game;

public class Screen extends Render{

    private Render test;
    private Render3D render;

    public Screen(int width, int height) {
        super(width, height);

        Random random = new Random();
        test = new Render(256, 256);
        render = new Render3D(width, height);

        for(int i = 0; i < 256*256; i++){
            test.pixels[i] = random.nextInt() * (random.nextInt(5)/4);
        }
    }

    public void render(Game game) {

        for(int i = 0; i < width*height; i++){
            pixels[i] = 0;
        }

        for(int i = 0; i < 100; i++){
            //test render
            //int animate = (int) (Math.sin((System.currentTimeMillis()+(i*1)) % 2000.0 / 2000 * Math.PI * 2) * 200);
            //int animate2 = (int) (Math.cos((System.currentTimeMillis()+(i*1)) % 2000.0 / 2000 * Math.PI * 2) * 200);
            //int animate = (int) (Math.sin((game.time + i * 2) % 1000.0 / 100) *100);
            //int animate2 = (int) (Math.cos((game.time + i * 2) % 1000.0 / 100) *100);
            //draw(test, (width-256)/2+animate, (height-256)/2+animate2); //put in middle (x-256)/2
        
            //3D render
            render.floor();
            draw(render,0 ,0);
        }
    }
}
