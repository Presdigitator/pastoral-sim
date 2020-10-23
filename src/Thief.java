/* Thief.java: Represents the thief actor.
 *
 *Jalen Lyle-Holmes 1122679 jlyleholmes@student.unimelb.edu.au */


public class Thief extends Agent {

    //
    private static final int PILE_TURNS = 1;
    private static final int GATHERER_TURNS = 3;
    private boolean consuming;
    private boolean gathererEncountered = false;

    public Thief(TileCoordinates tile, World world, ActorType type) {
        super(tile, world, type);
        super.setType(ActorType.THIEF);
        super.setDirection(Direction.UP);
        super.setCarrying(false);
        this.consuming = false;
        super.setActive(true);

    }

    public boolean isConsuming() {
        return consuming;
    }


    public void setConsuming(boolean consuming) {
        this.consuming = consuming;
    }

    /* Hoards in the specified hoard */
    private void hoard(Pile hoard) {

    }

    /**
     * Returns a 'copy' of this class, i.e. a thief
     *
     * @return Agent A thief
     */
    @Override
    public Agent mitosisCopy() {
        return new Thief(getTile(), getWorld(), getType());
    }


    /**
     * Calls collision method for agent standing on this actor.
     * (Visitor Pattern)
     *
     * @param agent The agent that is standing on this actor
     */
    @Override
    public void stoodOnBy(Agent agent) {

    }

    /**
     * Updates this actor according to the thief algorithm.
     * To be called on a tick.
     */
    @Override
    public void update() {
        // Track that has not collided with any gatherers yet this tick
        gathererEncountered = false;
        super.update();
    }

    /**
     * Respond to collision with Tree.
     *
     * @param tree the tree this agent is on
     */
    @Override
    public void collideWith(Tree tree) {
        if ((!isCarrying()) && (tree.hasFruit()))
            tree.pickFruit(this);
    }


    /**
     * Respond to collision with Pile
     *
     * @param pile the Pile this agent is on
     */
    @Override
    public void collideWith(Pile pile) {
        switch (pile.getType()) {
            case HOARD:
                collideWithHoard(pile);
                break;
            case STOCKPILE:
                collideWithStockPile(pile);
                break;
        }

    }

    @Override
    public void collideWithPad() {
        setConsuming(true);
    }

    /**
     * Respond to collision with a gatherer
     *
     * @param gatherer the gatherer this agent is standing on
     */
    public void collideWith(Gatherer gatherer) {
        if (!gathererEncountered) {
            rotateClockwiseNinety(GATHERER_TURNS);
            gathererEncountered = true;
        }
    }

    private void collideWithHoard(Pile hoard) {
        if (isConsuming()) {
            setConsuming(false);
            if (!isCarrying()) {
                if (hoard.hasFruit()) {
                    hoard.pickFruit(this);
                } else {
                    rotateClockwiseNinety(PILE_TURNS);
                }
            }
        } else if (isCarrying()) {
            hoard.getFruitFrom(this);
            rotateClockwiseNinety(PILE_TURNS);
        }

    }

    private void collideWithStockPile(Pile stockpile) {
        if (!isCarrying()) {
            if (stockpile.hasFruit()) {
                stockpile.pickFruit(this);
                setConsuming(false);
                rotateClockwiseNinety(PILE_TURNS);
            }
        } else {
            rotateClockwiseNinety(PILE_TURNS);
        }

    }


}


