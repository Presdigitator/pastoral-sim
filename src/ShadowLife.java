//ShadowLife.java: Simulation
//Jalen Lyle-Holmes 1122679 jlyleholmes@student.unimelb.edu.au
/*Referred to https://stackabuse.com/reading-and-writing-csvs-in-java/ about
 * loading and reading csv files with Java. */

import bagel.AbstractGame;
import bagel.Image;
import bagel.Input;
import bagel.util.Point;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ShadowLife extends AbstractGame {
    private static final Point backgroundTopLeft = new Point(0, 0);
    //location for top-left corner of background
    private static final int tileSize = 64; //size of tiles
    private static final int width = 1024; //width of game window
    private static final int height = 768; //height of game window
    private static final int xColumn = 1;
    private static final int yColumn = 2;
    private static final int typeColumn = 0;
    private static Image backgroundImage;   //image for background
    private long lastTick = 0; // when was the last tick
    /*
    The above three variables specify which column of the
  world CSV holds which info
  */
    private ArrayList<Actor> world = new ArrayList<Actor>();
    //List of actors in current game

    //Constructor
    public ShadowLife(int width, int height) {
        super(width, height);
        backgroundImage = new Image("res/images/background.png");
    }

    //Getters:

    public static int getTileSize() {
        return tileSize;
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public static void main(String[] args) {
        //initialise game
        ShadowLife game = new ShadowLife(width, height);
        //load the world
        try {
            //Try to load csv world file and create specified actors
            if (!(game.loadWorld())) {
                /*Name column in CSV contains something other than "Tree"
                or "Gatherer" */
                System.out.println("Actor name in csv not recognised.\n");
                return;
            }
        } catch (IOException e) {
            /* problem with csv file */
            System.out.println("Error reading file.\n");
            return;
        }
        //run the game
        game.run();


    }

    /* Load the world info and create actors.
     *Referred to https://stackabuse.com/reading-and-writing-csvs-in-java/ for
     *how to load and read csv files with Java. */
    public boolean loadWorld() throws IOException {
        BufferedReader csvReader;
        csvReader = new BufferedReader(new FileReader("res/worlds/test.csv"));
        String row;
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            int pixelX = Integer.parseInt(data[xColumn]);
            int pixelY = Integer.parseInt(data[yColumn]);
            int tileX = pixelX / ShadowLife.tileSize;
            int tileY = pixelY / ShadowLife.tileSize;
            Actor actor;
            if (data[typeColumn].equals("Tree")) {
                actor = new Tree(tileX, tileY);
            } else if (data[typeColumn].equals("Gatherer")) {
                actor = new Gatherer(tileX, tileY);
            } else {
                return false;
            }
            this.world.add(actor);
        }
        return true;
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
        int tickLength = 500; //length of tick in milliseconds
        if ((time - this.lastTick) >= tickLength) {
            for (Actor actor : world) actor.update();
            this.lastTick = time;
        }

        //draw actors
        for (Actor actor : world) actor.render();

    }
}
