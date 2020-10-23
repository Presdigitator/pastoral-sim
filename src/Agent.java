/* Agent.java: Represents actors which can move and take actions.
 *
 *Jalen Lyle-Holmes 1122679 jlyleholmes@student.unimelb.edu.au */

import java.util.PriorityQueue;
import java.util.Random;

public abstract class Agent extends Actor {
    private Random rand = new Random();
    private boolean carrying;
    private boolean active;
    private TileCoordinates previousTile;
    private Direction direction;
    //
    private static final int POOL_TURNS_ONE = 1;
    private static final int POOL_TURNS_TWO=3;



    public Agent(TileCoordinates tile, World world, ActorType type) {
        super(tile, world, type);
        previousTile = null;
    }


    public void setType(ActorType type) {
        super.setType(type);
    }

    public boolean isCarrying() {
        return carrying;
    }

    public void setCarrying(boolean carrying) {
        this.carrying = carrying;
    }

    public boolean isActive() {
        return active;
    }

    /**
     * Set active attribute
     *
     * @param active boolean value to set active to
     */
    public void setActive(boolean active) {
        // If this.active already matches active, do nothing
        if (this.active == active) {
            return;
        }
        // Otherwise update active status
        else {
            // Set active status
            this.active = active;
        }
    }

    public TileCoordinates getPreviousTile() {
        return previousTile;
    }

    public void setPreviousTile(TileCoordinates previousTile) {
        this.previousTile = previousTile;
    }

    /**
     * Runs the agent's update algorithm
     * (movement etc)
     */
    @Override
    public void update() {
        // Move the agent if active
        if (active) {
            move();
        }
        // Get queue of the actors on the current tile
        PriorityQueue<Actor> actorsOnTile = getWorld().getActorsOntile(getTile());
        // React appropriately to actors this agent is standing on
        for (Actor actor : actorsOnTile) {
            actor.stoodOnBy(this);
        }

    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Rotates ninety-degrees clockwise the number
     * of times given by parameter
     *
     * @param times How many times to rotate
     */
    public void rotateClockwiseNinety(int times) {
        setDirection(getDirection().getClockwiseNinety(times));

    }

    /**
     * react to collision with a Tree
     *
     * @param tree the tree this agent is on
     */
    public abstract void collideWith(Tree tree);

    /**
     * react to collision with a Pile
     *
     * @param pile the Pile this agent is on
     */
    public abstract void collideWith(Pile pile);

    public abstract void collideWith(Gatherer gatherer);

    /**
     * Respond to collision with a BasicActor
     *
     * @param basicActor the basicActor this agent is on
     */
    public void collideWith(BasicActor basicActor) {
        // Respond differently depending on type
        switch (basicActor.getType()) {
            case FENCE:
                collideWithFence(basicActor);
                break;
            case SIGNUP:
            case SIGNDOWN:
            case SIGNLEFT:
            case SIGNRIGHT:
                collideWithSign(basicActor);
                break;
            case PAD:
                collideWithPad();
                break;
            case POOL:
                collideWithPool();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + basicActor.getType());
        }

    }

    /**
     * Collision with fence, stops agent.
     *
     * @param fence The fence collided with.
     */
    private void collideWithFence(BasicActor fence) {
        setActive(false);
        setTile(getPreviousTile());
    }

    /**
     * Collision with sign, changes direction of agent
     *
     * @param basicActor The sign collided with
     */
    private void collideWithSign(BasicActor basicActor) {
        setDirection(basicActor.getType().direction);
    }

    public abstract void collideWithPad();

    /**
     * Collision with pool. Splits agent into two.
     * Bam, Mitosized!
     */
    protected void collideWithPool() {
        mitosize(POOL_TURNS_ONE);
        mitosize(POOL_TURNS_TWO);

        selfDestruct();

    }

    public void mitosize(int turns) {
        Agent agent = mitosisCopy();
        agent.rotateClockwiseNinety(turns);
        agent.move();
        getWorld().addToBench(agent);

    }

    // Move one tile in the current direction
    private void move() {
        // Sets previousTile to current tile
        previousTile = getTile();
        // Calculate what tile to move to
        TileCoordinates next =
                new TileCoordinates(getTile().getX() + direction.getX(),
                        getTile().getY() + direction.getY());
        // Change current tile
        setTile(next);

    }

    /**
     * Stops agent at a fence appropriately
     */
    public void respondToFence() {

    }

    /**
     * Returns a copy of an agent of the same class
     *
     * @return Agent An agent of the same class
     */
    public abstract Agent mitosisCopy();

    private void selfDestruct() {
        setActive(false);
        getWorld().removeFromActors(this);
        getWorld().removeFromTile(this);
    }

}
