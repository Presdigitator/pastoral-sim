import bagel.*;
import java.util.Random;

public class Gatherer extends Actor {
    //2-Dimensional game. Attribute is number of dimensions
    private static final int DIMENSIONS=2;
    private static final Image image=new Image(DIRECTORY+"/images/gatherer.png");
        //image for all gatherers
    private int[] direction = new int[DIMENSIONS];
        //direction vector for current direction
    private Random rand = new Random(); //for generating randomness
    private int ticks=0; //counts number of ticks so far
    // Turns every fifth tick
    private static final int turnFrequency=5;

    public Gatherer(int tileX, int tileY) {
        super(tileX, tileY);
    }

    //Moves gatherer and changes direction every five ticks
    @Override
    public void update() {
        //If is a 'fifth' tick, change direction
        if(0 == this.ticks % turnFrequency) {
            this.direction = randomDirection();
        }
        //move one tile in current direction
        setTileX(getTileX()+direction[Actor.xDimension]);
        setTileY(getTileY()+direction[Actor.yDimension]);

        this.ticks++;

    }

    @Override
    protected Image getImage() {
        return Gatherer.image;
    }

    //returns a random up,down,left,right direction as a 'vector'
    private int[] randomDirection() {
        int[] direction = {0,0};
        //choose x or y dimension. The 2 is
        int axis=rand.nextInt(DIMENSIONS);
        /* Shoose positive or negative direction.
        The 2 is for either positive or negative
         */
        int magnitude=rand.nextInt(2);
        if (magnitude==0){
            magnitude=-1;
        }
        direction[axis]=magnitude;
        return direction;


    }
}
