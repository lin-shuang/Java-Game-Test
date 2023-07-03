package com.game;

import java.awt.event.KeyEvent;
import com.input.Controls;

public class Game {

    public double time;
    public Controls controller;

    public Game(){
        controller = new Controls();
    }

    public void tick(boolean[] key){
        time++;
        boolean moveF = key[KeyEvent.VK_W];
        boolean moveB = key[KeyEvent.VK_S];
        boolean strafeR = key[KeyEvent.VK_D];
        boolean strafeL = key[KeyEvent.VK_A];
        boolean ascend = key[KeyEvent.VK_SPACE];
        boolean descend = key[KeyEvent.VK_SHIFT];

        controller.tick(moveF, moveB, strafeR, strafeL, ascend, descend);
    }
}
