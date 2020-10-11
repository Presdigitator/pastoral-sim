//ShadowLife.java: Simulation
//Jalen Lyle-Holmes 1122679 jlyleholmes@student.unimelb.edu.au
/*Referred to https://stackabuse.com/reading-and-writing-csvs-in-java/ about
 * loading and reading csv files with Java. */

import bagel.AbstractGame;
import bagel.Image;
import bagel.Input;
import bagel.util.Point;

public class ShadowLife extends AbstractGame {
    private static final Point backgroundTopLeft = new Point(0, 0);
    //location for top-left corner of background
    // Number of numeric arguments in command line:
    private static final int NUM_NUMERIC_ARGS=2;
    // NUmber of command line arguments:
    private static final int NUM_CL_ARGS=3;
    //length of tick in milliseconds
    private final int TICK_RATE;
    private final int MAX_TICKS;
    private static Image backgroundImage;   //image for background
    private long lastTick = 0; // when was the last tick
    /*
    The above three variables specify which column of the
  world CSV holds which info
  */
    private World world;
    //List of actors in current game

    //Constructor
    public ShadowLife(int tickRate, int maxTicks, String worldFile) {
        super();
        TICK_RATE=tickRate;
        MAX_TICKS=maxTicks;
        world = new World(this, worldFile);
        backgroundImage = new Image("res/images/background.png");
    }

    //Getters:





    /*
    Credit: Integer parsing exception handling structure from
    https://stackoverflow.com/questions/12558206/how-can-i-check-if-a-value-is-of-type-integer
     */
    public static void main(String[] args) {
        /*
        Get command-line arguments, if valid.
         */
        boolean wellFormed;
        wellFormed = true;
        if (args.length != NUM_CL_ARGS) {
            wellFormed=false;
        }
        else {
            for (int i = 0; i < NUM_NUMERIC_ARGS; i++) {
                if(!isPositiveInt(args[i])) {
                    wellFormed=false;
                }
            }
        }
        if (!wellFormed) {
            System.out.println("usage: ShadowLife <tick rate> <max ticks> <world file>\n");
            System.exit(-1);
        }
        else {
            System.out.printf("1: %d, 2: %d, 3: %s", Integer.parseInt(args[0]), Integer.parseInt(args[1]), (args[2]) );
            /*
            //initialise game
            ShadowLife game = new ShadowLife(
                    args[0], args[1], args[2]);
            //run the game
            game.run();
            */

        }




    }

    private static boolean isPositiveInt(String s) {
        try {
            if(Integer.parseInt(s)<0) {
                return false;
            }
            else return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }


    /*Update method runs many times per second,
    running logic and rendering simulation elements. */
    @Override
    protected void update(Input input) {
        //draw background
        backgroundImage.drawFromTopLeft(
                backgroundTopLeft.x, backgroundTopLeft.y);

        //Update actors every tick
        long time = System.currentTimeMillis();
        if ((time - this.lastTick) >= TICK_RATE) {
            for (Actor actor : world.getActors()) actor.update();
            this.lastTick = time;
        }

        //draw actors
        for (Actor actor : world.getActors()) actor.render();

    }


}
