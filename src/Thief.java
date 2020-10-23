/* Thief.java: Represents the thief actor.
 *
 *Jalen Lyle-Holmes 1122679 jlyleholmes@student.unimelb.edu.au */


public class Thief extends Agent {

    private boolean consuming;

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

    /* Steals from the specified pile */
    private void steal(Pile pile) {

    }

    /* Hoards in the specified hoard */
    private void hoard(Pile hoard) {

    }

    /**
     *  Returns a 'copy' of this class, i.e. a thief
     * @return Agent A thief
     */
    @Override
    public Agent copy() {
        return null;
    }


    /**
     * Calls collision method for agent standing on this actor.
     * (Visitor Pattern)
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
        super.update();
    }

    /**
     * Respond to collision with Tree.
     * @param tree the tree this agent is on
     */
    @Override
    public void collideWith(Tree tree) {

    }

    /**
     * Respond to collision with Pile
     * @param pile the Pile this agent is on
     */
    @Override
    public void collideWith(Pile pile) {

    }


    /**
     * Respond to collision with BasicActor
     * @param basicActor the basicActor this agent is on
     */
    @Override
    public void collideWith(BasicActor basicActor) {

    }

    /**
     * Respond to collision with another agent
     * @param agent the other agent this agent is standing on
     */
    @Override
    public void collideWith(Agent agent) {

    }


}


