/* BasicActor.java: Represents actors which have no
* functionality or attributes beyond their image.
*/
//Jalen Lyle-Holmes 1122679 jlyleholmes@student.unimelb.edu.au

import bagel.Image;


public class BasicActor extends Actor {

    // Images for all the different actors which are created as BasicActor
    private static final Image PAD_IMAGE=
            new Image(IMAGE_DIRECTORY+"pad.png");
    private static final Image FENCE_IMAGE =
            new Image(IMAGE_DIRECTORY+"fence.png");
    private static final Image POOL_IMAGE =
            new Image(IMAGE_DIRECTORY+"pool.png");
    private final Image image;
    /* Enum of the names of the different varieties
    *of actor that use
     *the BasicActor class, with
    *matching images.
     */
    private enum basicActorVarieties {
        Pad(PAD_IMAGE),
        Fence(FENCE_IMAGE),
        Pool(POOL_IMAGE);

        Image image;

        basicActorVarieties(Image image) {
            this.image = image;
        }

    }

    // The variety of this actor (e.g. Pool)
    private basicActorVarieties basicActorVariety;

    public BasicActor(TileCoordinates tile, String actorVariety) {
        super(tile);
        /* Depending on what variety of actor has been specified,
        create an actor with the appropriate image and variety identifier.
         */
        try {
            this.basicActorVariety=basicActorVarieties.valueOf(actorVariety);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        // Set image appropriately
        this.image=this.basicActorVariety.image;

    }

    @Override
    protected Image getImage() {
        return image;
    }
}
