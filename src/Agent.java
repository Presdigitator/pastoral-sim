/* Agent.java: Represents actors which can move and take actions.
 *
 *Jalen Lyle-Holmes 1122679 jlyleholmes@student.unimelb.edu.au */

import java.util.Random;

public abstract class Agent extends Actor {
    private Random rand = new Random();
    private boolean carrying;
    private boolean active;
    private TileCoordinates previousTile;
    private Direction direction;

    public Agent(TileCoordinates tile) {
        super(tile);
        previousTile = null;
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

    public void setActive(boolean active) {
        this.active = active;
    }

    public TileCoordinates getPreviousTile() {
        return previousTile;
    }

    public void setPreviousTile(TileCoordinates previousTile) {
        this.previousTile = previousTile;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /** Updates the actor when called (e.g. moves)
     *
     */
    @Override
    public void update() {
        super.update();
    }

    /**
     * Splits agent into two on a mitosis pool
     */
    protected void mitosize() {

    }

    /** Stops agent at a fence appropriately
    */
    public void respondToFence() {

    }

    /** Returns a copy of an agent of the same class
     *
     * @return Agent An agent of the same class
     */
    public abstract Agent copy();

}
