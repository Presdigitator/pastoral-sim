import bagel.*;
import java.util.Random;

public class Gatherer extends Actor {
    private static final Image image=new Image("res/images/gatherer.png");
        //image for all gatherers
    private int[] direction = new int[2];
        //direction vector for current direction
    private Random rand = new Random(); //for generating randomness
    private int ticks=0; //counts number of ticks so far

    public Gatherer(int tileX, int tileY) {
        super(tileX, tileY);
    }

    //Moves gatherer and changes direction every five ticks
    @Override
    public void update() {
        //If is a 'fifth' tick, change direction
        if(0 == this.ticks % 5) {
            this.direction = randomDirection();
        }
        //move one tile in current direction
        this.tileX+=this.direction[Actor.xDimension];
        this.tileY+=this.direction[Actor.yDimension];

        this.ticks++;

    }

    @Override
    protected Image getImage() {
        return Gatherer.image;
    }

    //returns a random up,down,left,right direction as a 'vector'
    private int[] randomDirection() {
        int[] direction = {0,0};
        //choose x or y dimension
        int axis=rand.nextInt(2);
        //choose positive or negative direction
        int magnitude=rand.nextInt(2);
        if (magnitude==0){
            magnitude=-1;
        }
        direction[axis]=magnitude;
        return direction;


    }
}
