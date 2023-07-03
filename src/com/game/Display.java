package com.game;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.swing.JFrame;
import com.graphics.Screen;
import com.input.Controls;
import com.input.InputHandler;

public class Display extends Canvas implements Runnable{

   public static final int WIDTH = 1280;
   public static final int HEIGHT = 600;
   public static final String title = "Untitled 0.01";

   private Thread thread;
   private Screen screen;
   private BufferedImage img;
   private Boolean running = false;
   private int[] pixels;
   private int fps;
   private Game game;
   private InputHandler input;
   private int newX, oldX, newY, oldY = 0;

   public void start() {
    if (running) return;

    else{
        running = true;
        thread = new Thread(this);
        thread.start();
        System.out.println("Game started...");
    }
   }

    public Display(){
        Dimension size = new Dimension(WIDTH, HEIGHT);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);

        screen = new Screen(WIDTH, HEIGHT);
        game = new Game();
        img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
   
        input = new InputHandler();
        addKeyListener(input);
        addFocusListener(input);
        addMouseListener(input);
        addMouseMotionListener(input);
    }

   public void run(){
    this.requestFocus();

    int frames = 0;
    double unprocessedSeconds = 0;
    long prevTime = System.nanoTime();
    double secondsPerTick = 1 / 60.0;
    int tickCount = 0;
    boolean ticked = false;
    long currTime;
    long passedTime;

    while (running){
        currTime = System.nanoTime();
        passedTime = currTime - prevTime;
        prevTime = currTime;
        unprocessedSeconds += passedTime / 1000000000.0;

        while(unprocessedSeconds > secondsPerTick){
            tick();
            unprocessedSeconds -= secondsPerTick;
            ticked = true;
            tickCount++;

            if(tickCount % 60 == 0){
                //debug System.out.println(frames + "FPS");
                prevTime += 1000;
                fps = frames;
                frames = 0;
            }
        };

        if(ticked){ //remove will increase fps, but why keep?
            render();
            frames++;
        }
        render();
        frames++;

        //mouse movement catcher
        newX = InputHandler.mouseX;
        if(newX > oldX){
            //debug System.out.println("Right");
            Controls.rotateR = true;
        } 
        if(newX < oldX){
            //debug System.out.println("Left");
            Controls.rotateL = true;

        } 
        if(newX == oldX){
            //debug System.out.println("Still");
            Controls.rotateR = false;
            Controls.rotateL = false;


        } 
        oldX = newX;
        //debug System.out.println("X: " + InputHandler.mouseX + "Y: " + InputHandler.mouseY);
    }
   }
   
   private void tick(){
    game.tick(input.key);
   }

   private void render(){
    BufferStrategy bs = this.getBufferStrategy();
    if(bs == null){
        createBufferStrategy(3);
        return;
    }

    screen.render(game);

    for(int i = 0; i < WIDTH*HEIGHT; i++){
        pixels[i] = screen.pixels[i];
    }

    Graphics g = bs.getDrawGraphics();
    g.drawImage(img, 0, 0, WIDTH, HEIGHT, null);
    g.setColor(Color.GREEN);
    g.drawString(fps + " FPS", 0, 10);
    g.dispose();
    bs.show();
   }

   private void stop(){
    if (!running) return;

    else{
        running = false;
        try{
            thread.join();
        } catch (Exception e){
            e.printStackTrace();
            System.exit(0);
        }
    }
   }

    public static void main(String[] args) throws Exception{
        BufferedImage cursor = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor cursorBlank = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0, 0), "cursorBlank");
        Display game = new Display();
        JFrame frame = new JFrame();
        frame.add(game);
        frame.setTitle(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setCursor(cursorBlank);
        frame.pack();

        System.out.println("Frame created...");

        game.start();
    }
}
