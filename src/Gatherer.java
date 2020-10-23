/** Represents the Gatherers which move around the map and respond to things.
 *
 */

import java.util.Random;

public class Gatherer extends Agent {
    /*
     *The number of clockwise 90 degree turns to make when standing on
     * different actors:
     */
    private static final int TREE_TURNS = 2;
    private static final int PILE_TURNS = 2;
    private final Random rand = new Random();


    /**
     * Constructor
     */
    public Gatherer(TileCoordinates tile, World world, ActorType type) {
        super(tile, world, type);
        super.setType(ActorType.GATHERER);
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
        super.update();
    }

    /**
     * React to collision with a tree
     *
     * @param tree the tree this agent is on
     */
    public void collideWith(Tree tree) {
        if ((!isCarrying()) && (tree.hasFruit())) {
            tree.pickFruit(this);
            rotateClockwiseNinety(TREE_TURNS);
        }
    }

    /**
     * Respond to collision with a Pile.
     *
     * @param pile the Pile this agent is on
     */
    @Override
    public void collideWith(Pile pile) {
        // If carrying, give fruit to pile
        if (isCarrying()) {
            pile.getFruitFrom(this);
        }
        // Rotate 180 degrees
        rotateClockwiseNinety(PILE_TURNS);
    }

    /**
     * Respond to collision with a pad. (Does nothing)
     */
    @Override
    public void collideWithPad() {

    }

    /**
     * Respond to collision with another gatherer (Does nothing)
     *
     * @param gatherer The gatherer this agent is on
     */
    @Override
    public void collideWith(Gatherer gatherer) {

    }

    /**
     * Returns a new Gatherer
     *
     * @return Agent A new Gatherer
     */
    @Override
    public Agent mitosisCopy() {
        return new Gatherer(getTile(), getWorld(), getType());
    }


    /**
     * When stood on by Agent, call the agent's
     * method for processing the collision.
     * (Visitor Pattern)
     *
     * @param agent The agent that is standing on this actor
     */
    @Override
    public void stoodOnBy(Agent agent) {
        agent.collideWith(this);
    }


}
