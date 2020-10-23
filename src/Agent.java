/** Represents actors which can move and take actions.
 *
 *@author Jalen Lyle-Holmes 1122679 jlyleholmes@student.unimelb.edu.au */

import java.util.PriorityQueue;
import java.util.Random;

public abstract class Agent extends Actor {
    /* Number of times to rotate 90 degrees clockwise
     *when on mitosis pool:
     */
    private static final int POOL_TURNS_ONE = 1;
    private static final int POOL_TURNS_TWO = 3;
    private Random rand = new Random();
    private boolean carrying;
    private boolean active;
    private TileCoordinates previousTile;
    private Direction direction;


    /**
     * Constructor for Agent
     */
    public Agent(TileCoordinates tile, World world, ActorType type) {
        super(tile, world, type);
        previousTile = null;
    }


    /**
     * Calls Actor's setType
     *
     * @param type New value for type
     */
    public void setType(ActorType type) {
        super.setType(type);
    }

    /**
     * Gets carrying
     *
     * @return value of carrying
     */
    public boolean isCarrying() {
        return carrying;
    }

    /**
     * Sets carrying
     *
     * @param carrying New value for carrying
     */
    public void setCarrying(boolean carrying) {
        this.carrying = carrying;
    }

    /**
     * Gets active
     *
     * @return value of active
     */
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

    /**
     * Gets previousTile
     *
     * @return value of previousTile
     */
    public TileCoordinates getPreviousTile() {
        return previousTile;
    }

    /**
     * Sets previousTile
     *
     * @param previousTile New value for previousTile
     */
    public void setPreviousTile(TileCoordinates previousTile) {
        this.previousTile = previousTile;
    }

    /**
     * Gets direction
     *
     * @return value of direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Sets direction
     *
     * @param direction New value for direction
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
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
     * React to collision with a Pile
     *
     * @param pile the Pile this agent is on
     */
    public abstract void collideWith(Pile pile);

    /**
     * React to collision with gatherer
     *
     * @param gatherer The gatherer this agent is on
     */
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

    /*
     * Collision with fence, stops agent.
     *
     */
    private void collideWithFence(BasicActor fence) {
        setActive(false);
        setTile(getPreviousTile());
    }

    /*
     * Collision with sign, changes direction of agent
     *
     */
    private void collideWithSign(BasicActor basicActor) {
        setDirection(basicActor.getType().direction);
    }

    public abstract void collideWithPad();

    /*
     * Mitosis effect. Make two copies and send them in opposite directions
     * before self-destructing
     */
    private void collideWithPool() {
        mitosize(POOL_TURNS_ONE);
        mitosize(POOL_TURNS_TWO);
        selfDestruct();

    }

    /*
     * Make a copy of the agent that turns the number of times
     * specified by turns, and moves.
     */
    private void mitosize(int turns) {
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

    // "Destroys" this Agent.
    private void selfDestruct() {
        setActive(false);
        getWorld().removeFromActors(this);
        getWorld().removeFromTile(this);
    }

}
