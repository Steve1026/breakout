package breakout;
/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {
	
	private static final long serialVersionUID = 997;

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 404;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static final int NTURNS = 3;

/* Method: run() */
/** Runs the Breakout program. */
	public void run() {
		
		initGame();
		//createPaddle();
		addMouseListeners();
		playGame();
		
		double x = (getWidth() - PADDLE_WIDTH) / 2;
		
		double y = getHeight() - PADDLE_Y_OFFSET;
		
		GRect paddle = new GRect(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
		
		paddle.setFilled(true);
		
		add(paddle);
		
	}
	private void initGame() {
		
		// rows for loop
		for (int i=0; i<NBRICK_ROWS; i++) {

            int x = BRICK_SEP;
            int y = BRICK_Y_OFFSET + ( i * (BRICK_SEP + BRICK_HEIGHT));
            
            // bricks per row for loop
            for (int j=0; j<NBRICKS_PER_ROW; j++) {  
               
                GRect brick = new GRect(x + (j * (BRICK_WIDTH + BRICK_SEP)), y, BRICK_WIDTH, BRICK_HEIGHT);
                
                //pause(500);

                brick.setFilled(true);
                
                if (i<=1) {
                	brick.setColor(Color.RED);
                }
                else if (i<=3) {
                	brick.setColor(Color.ORANGE);
                }
                else if (i<=5) {
                	brick.setColor(Color.YELLOW);
                }
                else if (i<=7) {
                	brick.setColor(Color.GREEN);
                }
                else
                	brick.setColor(Color.BLUE);
            
                add(brick);
            }
		}
	}
	//public void createPaddle() {
	
	
	public void mousePressed(MouseEvent e) {
		lastX = e.getX();
		lastY = e.getY();
		paddle = getElementAt(lastX, lastY);
	}
	public void mouseDragged(MouseEvent e) {
		if (paddle != null) {
			paddle.move(e.getX() - lastX, e.getY() - lastY);
			lastX = e.getX();
			lastY = e.getY();
		}
		
		
	}
	private void playGame() {
		
	}
	
	private double lastY;
	private double lastX;
	private GRect paddle;
	

}
