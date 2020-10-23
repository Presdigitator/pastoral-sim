import bagel.Image;

import java.util.Random;

public class Gatherer extends Agent {
    private final Random rand = new Random();
    /*
     *The number of clockwise 90 degree turns to make when standing on
     * different actors:
     */
    private static final int TREE_TURNS=2;
    private static final int PILE_TURNS=2;


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

    public void collideWith(Tree tree) {
        if((!isCarrying()) && (tree.hasFruit())) {
            tree.pickFruit(this);
            rotateClockwiseNinety(TREE_TURNS);
        }
    }

    /**
     * Respond to collision with a Pile.
     * @param pile the Pile this agent is on
     */
    @Override
    public void collideWith(Pile pile) {
        // If carrying, give fruit to pile
        if(isCarrying()) {
            pile.getFruitFrom(this);
        }
        // Rotate 180 degrees
        rotateClockwiseNinety(PILE_TURNS);
    }

    @Override
    public void collideWithPad() {

    }

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
     *
     * @param agent The agent that is standing on this actor
     */
    @Override
    public void stoodOnBy(Agent agent) {
        agent.collideWith(this);
    }



}
