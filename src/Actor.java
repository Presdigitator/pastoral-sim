//Actor.java: Represents actors within the game, who may or may not move
//Jalen Lyle-Holmes 1122679 jlyleholmes@student.unimelb.edu.au

import bagel.*;
import bagel.util.Point;

public abstract class Actor {
    private static final String DIRECTORY="res";
    private int tileX;
    private int tileY;
                //these give location in terms of tiles (not pixels)
    protected static final int xDimension = 0;
    protected static final int yDimension = 1;
                //which index (in directions) gives x vs. y value
    private static final int screenZoneBuffer=1;
                /*how far offscreen will we continue
                  *calling render method for an actor (in number of tiles)*/

    public Actor(int tileX, int tileY) {
        this.tileX=tileX;
        this.tileY=tileY;
    }

    public int getTileX() {
        return tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public static String getDirectory() {
        return directory;
    }

    public void setTileX(int tileX) {
        this.tileX = tileX;
    }

    public void setTileY(int tileY) {
        this.tileY = tileY;
    }

    //used by ShadowLife.update() to update this actor
    public abstract void update();

    //renders this actor on screen (if applicable)
    public void render() {
        //checks if within visible window
        if (isInWindow(this.tileX, this.tileY)) {
            //Convert tile location to pixel location and draw image
            Point centre = this.tileToPixel();
            this.getImage().drawFromTopLeft(centre.x,centre.y);
        }
    }

    //returns image of the actor
    protected abstract Image getImage();

    //returns coordinates of pixel location for the actor's current tile
    protected final Point tileToPixel(){
        double pixelX = (this.tileX)*ShadowLife.getTileSize();
        double pixelY = (this.tileY)*ShadowLife.getTileSize();
        return new Point(pixelX, pixelY);
    }

    //checks if given tile is within visible window  (with buffer to be safe)
    private boolean isInWindow(int tileX, int tileY) {
        boolean isIn = true;
        if ((tileX<(-screenZoneBuffer) )|| (tileY<(-screenZoneBuffer)) ||
                (tileX>(ShadowLife.getWidth()/ShadowLife.getTileSize())+
                        screenZoneBuffer) ||
               (tileY>(ShadowLife.getHeight()/ShadowLife.getTileSize()+
                       screenZoneBuffer))) {
            isIn=false;
        }
        return isIn;
    }
}
