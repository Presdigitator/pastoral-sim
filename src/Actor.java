//Actor.java: Represents actors within the game, who may or may not move
//Jalen Lyle-Holmes 1122679 jlyleholmes@student.unimelb.edu.au

import bagel.Image;
import bagel.Window;
import bagel.util.Point;

public abstract class Actor {

    public static final String IMAGE_DIRECTORY = "res/images/";
    public static final Class<?>[] argClasses = {TileCoordinates.class, String.class};
    /* How far offscreen will we continue
     *calling render method for an actor (in number of tiles):
     */
    private static final int screenZoneBuffer = 1;
    private TileCoordinates tile;
    private World world;


    public Actor(TileCoordinates tile) {
        this.tile = tile;
    }


    public TileCoordinates getTile() {
        return tile;
    }


    public void setTile(TileCoordinates tile) {
        this.tile = tile;
    }

    /** This method renders the actor onscreen, if within bounds of screen
     *
     */
    public void render() {
        //checks if within visible window
        if (isInWindow(this.tile)) {
            //Convert tile location to pixel location and draw image
            Point centre = this.tile.toPixels();
            this.getImage().drawFromTopLeft(centre.x, centre.y);
        }
    }

    /*This method checks if given tile is within visible window
     * (with buffer to be safe)
     *
     */
    private boolean isInWindow(TileCoordinates tile) {
        boolean isIn = true;
        if ((tile.getX() < (-screenZoneBuffer)) || (tile.getY() < (-screenZoneBuffer)) ||
                (tile.getX() > (Window.getWidth() / TileCoordinates.TILE_SIZE) +
                        screenZoneBuffer) ||
                (tile.getY() > (Window.getHeight() / TileCoordinates.TILE_SIZE +
                        screenZoneBuffer))) {
            isIn = false;
        }
        return isIn;
    }

    /** Responds appropriately to agent standing on this actor
     * @param agent The agent standing on this actor
      */
    public void stoodOn(Agent agent) {

    }

    /** Used by ShadowLife.update() to update this actor
     *
     */
    public void update() {
    }

    /** Returns the image of the actor.
     *
     * @return Image The image of the actor
     */
    protected abstract Image getImage();
}
