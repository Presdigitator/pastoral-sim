/* Thief.java: Represents the thief actor.
 *
 *Jalen Lyle-Holmes 1122679 jlyleholmes@student.unimelb.edu.au */

import bagel.Image;

public class Thief extends Agent {
    public static final Image thiefImage = new Image(IMAGE_DIRECTORY + "thief.png");
    private boolean consuming;

    public Thief(TileCoordinates tile, String actorName) {
        super(tile);
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

    @Override
    protected Image getImage() {
        return thiefImage;
    }

    /**
     * Updates this actor according to the thief algorithm.
     * To be called on a tick.
     */
    @Override
    public void update() {
        super.update();
    }




}


