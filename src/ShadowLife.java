//ShadowLife.java: Simulation
//Jalen Lyle-Holmes 1122679 jlyleholmes@student.unimelb.edu.au
/*Referred to https://stackabuse.com/reading-and-writing-csvs-in-java/ about
 * loading and reading csv files with Java. */

import bagel.AbstractGame;
import bagel.Image;
import bagel.Input;
import bagel.util.Point;

public class ShadowLife extends AbstractGame {
    // Number of numeric arguments in command line:
    private static final int NUM_NUMERIC_ARGS = 2;
    // Total number of command line arguments:
    private static final int NUM_CL_ARGS = 3;
    private static final Point BACKGROUND_TOP_LEFT = new Point(0, 0);
    private static Image backgroundImage;
    // Length of tick in milliseconds:
    private final int TICK_RATE;
    // Maximum nnumber of ticks before ending game:
    private final int MAX_TICKS;
    private final World world;
    private int ticksElapsed = 0;
    // When was the last tick (in milliseconds since UNIX epoch):
    private long lastTick = 0;


    //Constructor
    public ShadowLife(int tickRate, int maxTicks, String worldFile) {
        super();
        TICK_RATE = tickRate;
        MAX_TICKS = maxTicks;
        world = new World(this, worldFile);
        backgroundImage = new Image(Actor.IMAGE_DIRECTORY + "background.png");
    }

    /**
     * The main method which sets things up and calls the game.run method
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        /*
        Get command-line arguments, if valid.
         */
        boolean wellFormed;
        wellFormed = true;
        if (args.length != NUM_CL_ARGS) {
            wellFormed = false;
        } else {
            for (int i = 0; i < NUM_NUMERIC_ARGS; i++) {
                if (!isPositiveInt(args[i])) {
                    wellFormed = false;
                }
            }
        }
        /*
         * If invalid, print error message
         */
        if (!wellFormed) {
            System.out.println("usage: ShadowLife <tick rate> <max ticks> <world file>\n");
            System.exit(-1);
        } else {
            /*
             * Otherwise arguments are valid, so we can initialise game.
             */
            ShadowLife game = new ShadowLife(
                    Integer.parseInt(args[0]), Integer.parseInt(args[1]), args[2]);
            // Run the game
            game.run();

        }


    }

    // Check if positive int.
    private static boolean isPositiveInt(String s) {
        try {
            return Integer.parseInt(s) >= 0;
        } catch (NumberFormatException ex) {
            return false;
        }
    }


    /*Update method runs many times per second,
    running logic and rendering simulation elements. */
    @Override
    protected void update(Input input) {
        // If no more active agents, halt
        if(!world.anyActive()) {
            halt();
        }
        // If it's time for another tick,  update actors
        long time = System.currentTimeMillis();
        if ((time - this.lastTick) >= TICK_RATE) {
            tick();
        }

        //draw background and actors
        backgroundImage.drawFromTopLeft(
                BACKGROUND_TOP_LEFT.x, BACKGROUND_TOP_LEFT.y);
        world.renderAll();


    }

    /*
    * Do everything that happens each tick, inc updating actors
     */
    private void tick() {
        ticksElapsed++;
        this.lastTick = System.currentTimeMillis();
        // If more than MAX_TICKS have passed, time out
        if (ticksElapsed > MAX_TICKS) {
            System.out.println("Timed out");
            System.exit(-1);
        }
        // Otherwise loop through gatherers then thieves and run update algorithms
        world.updateAgents(Actor.ActorType.GATHERER);
        world.updateAgents(Actor.ActorType.THIEF);
        // Add newly created 'mitosized' agents to the allActors, and activeAgents
        world.offTheBench();
        // Remove inactive agents from activeAgents list
        world.clearInactive();

    }

    private void halt() {
        // Print elapsed ticks
        System.out.println(ticksElapsed+" ticks");
        // Print info for relevant actors (stockpiles and hoards)
        world.haltPrint();
        System.exit(0);

    }


}
