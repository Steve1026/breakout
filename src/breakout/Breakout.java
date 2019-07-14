package breakout;

import acm.graphics.*;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Breakout extends GraphicsProgram
{
    public static final int APPLICATION_WIDTH = 404;
    public static final int APPLICATION_HEIGHT = 600;
    private static final long serialVersionUID = 997;
    private static final int WIDTH = APPLICATION_WIDTH;
    private static final int HEIGHT = APPLICATION_HEIGHT;
    private static final int PADDLE_WIDTH = 60;
    private static final int PADDLE_HEIGHT = 10;
    private static final int PADDLE_Y_OFFSET = 30;
    private static final int NBRICKS_PER_ROW = 10;
    private static final int NBRICK_ROWS = 10;
    private static final int BRICK_SEP = 4;
    private static final int BRICK_WIDTH =
            (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;
    private static final int BRICK_HEIGHT = 8;
    private static final int BALL_RADIUS = 10;
    private static final int BRICK_Y_OFFSET = 70;
    private static final int NTURNS = 3;
    private int turn;
    private GObject collider;
    private GObject gobj;  // The object being dragged
    private GPoint last;  // The last mouse position
    private GLabel label;
    private GOval ball; // Creates the ball
    private GRect paddle;
    private double vx, vy; // Velocity variables for ball
    private RandomGenerator rgen = RandomGenerator.getInstance();

    public void run()
    {
        label = new GLabel("");
        label.setFont("Courier New-36");

        turn = 1;
        int brickcount = 100;

        vy = 3.0;
        vx = rgen.nextDouble(1.0, 3.0);
        if (rgen.nextBoolean(0.5)) vx = -vx;  //need to figure out how this works!

        setupBoard();
        createPaddle();
        placeBall();
        addMouseListeners();

        do
        {
            moveBall();
            checkForCollision();
            getCollidingObject();
            if (collider == paddle)
            {
                //vx = -vx;
                vy = -vy;
            } else if (collider != null)
            {
                vy = -vy;
                remove(collider);
                brickcount--;
            } else if (brickcount == 0)
            {
                //label = "You Win!";
                label.setLabel("You Win!");
                label.setColor(Color.RED);
                add(label, (getWidth() - label.getWidth()) / 2, getHeight() / 2);
                remove(ball);
                break;
            } else
                pause(25);
        } while ((ball.getX() < WIDTH) || (ball.getY() < HEIGHT));
    }

    private void createPaddle()
    {

        double x = (getWidth() - PADDLE_WIDTH) / 2;
        double y = getHeight() - PADDLE_Y_OFFSET;

        paddle = new GRect(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);

        paddle.setFilled(true);
        add(paddle);
    }

    private void setupBoard()
    {
        // rows for loop
        for (int i = 0; i < NBRICK_ROWS; i++)
        {
            GRect brick;
            int x = BRICK_SEP;
            int y = BRICK_Y_OFFSET + (i * (BRICK_SEP + BRICK_HEIGHT));

            // bricks per row for loop
            for (int j = 0; j < NBRICKS_PER_ROW; j++)
            {
                brick = new GRect(x + (j * (BRICK_WIDTH + BRICK_SEP)), y, BRICK_WIDTH, BRICK_HEIGHT);

                brick.setFilled(true);

                if (i <= 1)
                {
                    brick.setColor(Color.RED);
                } else if (i <= 3)
                {
                    brick.setColor(Color.ORANGE);
                } else if (i <= 5)
                {
                    brick.setColor(Color.YELLOW);
                } else if (i <= 7)
                {
                    brick.setColor(Color.GREEN);
                } else
                    brick.setColor(Color.CYAN);
                add(brick);
            }
        }
    }

    public void mousePressed(MouseEvent e)
    {
        last = new GPoint(e.getPoint());
        gobj = getElementAt(last);
    }

    public void mouseDragged(MouseEvent e)
    {
        if (gobj == paddle)
        {
            gobj.move(e.getX() - last.getX(), 0);
            last = new GPoint(e.getPoint());
        }
    }

    private void placeBall()
    {

        double x = (getWidth() - BALL_RADIUS) / 2;
        double y = (getHeight() - BALL_RADIUS) / 2;

        ball = new GOval(x, y, BALL_RADIUS, BALL_RADIUS);
        ball.setFilled(true);
        ball.setColor(Color.BLACK);
        add(ball);
    }

    private void moveBall()
    {

        ball.move(vx, vy);
    }

    private void checkForCollision()
    {
        if ((ball.getX() + BALL_RADIUS) > getWidth() || ball.getX() < 0)
        {
            vx = -vx;
        } else if (ball.getY() < 0)
        {
            /*|| (ball.getY() + BALL_RADIUS) > getHeight()) {*/
            vy = -vy;
        } else if (turn == NTURNS)
        {
            pause(50);
            label.setLabel("GAME OVER!");
            label.setColor(Color.RED);
            add(label, (getWidth() - label.getWidth()) / 2, getHeight() / 2);
            remove(ball);
        } else if (ball.getY() > getHeight())
        {
            pause(50);
            turn++;
            label.setLabel("Round = " + turn);
            label.setColor(Color.GREEN);
            add(label, (getWidth() - label.getWidth()) / 2, getHeight() / 2);

            //placeball();
            pause(5000);
            placeBall();
        }
    }

    private GObject getCollidingObject()
    {

        collider = getElementAt(ball.getX(), ball.getY());

        if (collider != null)
        {
            return collider;
        }

        collider = getElementAt(ball.getX() + BALL_RADIUS, ball.getY());

        if (collider != null)
        {
            return collider;
        }

        collider = getElementAt(ball.getX(), ball.getY() + BALL_RADIUS);

        if (collider != null)
        {
            return collider;
        }

        collider = getElementAt(ball.getX() + BALL_RADIUS, ball.getY() + BALL_RADIUS);

        if (collider != null)
        {
            return collider;
        }
        return collider;
    }
}
