import bagel.Image;

import java.util.Random;

public class Gatherer extends Agent {
    private static final Image gathererImage = new Image(IMAGE_DIRECTORY + "gatherer.png");
    private final Random rand = new Random();
    private Direction direction;


    public Gatherer(TileCoordinates tile, String actorName) {

        super(tile);
        super.setDirection(Direction.LEFT);
        super.setCarrying(false);
        super.setActive(true);
    }

    /* Grabs fruit from tree. */
    private void grabFruit(Tree tree) {

    }

    /* Dumps fruit into pile */
    private void dump(Pile pile) {

    }

    /**
     * Moves gatherer and responds to environment appropriately
     */
    @Override
    public void update() {


    }

    /** Returns a new Gatherer
     *
     * @return Agent A new Gatherer
     */
    @Override
    public Agent copy() {
        //> implement
        return null;
    }

    @Override
    protected Image getImage() {
        return gathererImage;
    }


}
