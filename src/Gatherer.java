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

    /** Respond to collision with a BasicActor
     *
     * @param basicActor the basicActor this agent is on
     */
    @Override
    public void collideWith(BasicActor basicActor) {
        // Respond differently depending on type
        switch(basicActor.getType()) {
            case FENCE:
                collideWithFence(basicActor);
                break;
            case SIGNUP:
            case SIGNDOWN:
            case SIGNLEFT:
            case SIGNRIGHT:
                collideWithSign(basicActor);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + basicActor.getType());
        }

    }

    /**
     * Collision with fence, stops agent.
     * @param fence The fence collided with.
     */
    private void collideWithFence(BasicActor fence) {
        setActive(false);
        setTile(getPreviousTile());
    }

    /**
     * Collision with sign, changes direction of agent
     * @param basicActor The sign collided with
     */
    private void collideWithSign(BasicActor basicActor){
        setDirection(basicActor.getType().direction);
    }

    /**
     * Respond to collision with other agent
     * @param agent the other agent this agent is standing on
     */
    @Override
    public void collideWith(Agent agent) {

    }

    /**
     * Returns a new Gatherer
     *
     * @return Agent A new Gatherer
     */
    @Override
    public Agent copy() {
        //> implement
        return null;
    }


    /**
     *
     * @param agent The agent that is standing on this actor
     */
    @Override
    public void stoodOnBy(Agent agent) {

    }


}
