/* BasicActor.java: Represents actors which have no
 * functionality or attributes beyond their image.
 */
//Jalen Lyle-Holmes 1122679 jlyleholmes@student.unimelb.edu.au

import bagel.Image;


public class BasicActor extends Actor {


    private final Image image;

    public BasicActor(TileCoordinates tile, World world, ActorType type) {
        super(tile, world, type);
        /* Depending on what variety of actor has been specified,
        create an actor with the appropriate image and variety identifier.
         */
        try {
            this.setType(type);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        // Set image appropriately
        this.image = this.getType().image;

    }

    @Override
    public Image getImage() {
        return image;
    }

    /**
     * Call collision method for agent standing on this actor
     * (Visitor Pattern)
     * @param agent The agent that is standing on this actor
     */
    @Override
    public void stoodOnBy(Agent agent) {
        agent.collideWith(this);
    }

}
