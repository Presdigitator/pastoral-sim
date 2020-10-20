import bagel.Image;

import java.util.HashMap;

public class Sign extends Actor {
    private static final Image upImage = new Image(IMAGE_DIRECTORY+"up.png");
    private static final Image downImage = new Image(IMAGE_DIRECTORY+"down.png");
    private static final Image leftImage = new Image(IMAGE_DIRECTORY+"left.png");
    private static final Image rightImage = new Image(IMAGE_DIRECTORY+"right.png");
    /* An enum giving the appropriate direction and image
     *for each possible
    *sign type input in the world CSV.
     */
    private enum SignVarieties {
        SignUp (upImage, Direction.UP),
        SignDown (downImage, Direction.DOWN),
        SignLeft (leftImage, Direction.LEFT),
        SignRight (rightImage, Direction.RIGHT);

        Image image;
        Direction direction;
        SignVarieties(Image image, Direction direction) {
            this.image=image;
            this.direction=direction;
        }

    }
    private Image image;
    private final Direction direction;

    // Name of this variety of sign (e.g. SignDown):
    private final SignVarieties signVariety;


    public Sign(TileCoordinates tile, String actorName) {
        super(tile);
        this.signVariety=SignVarieties.valueOf(actorName);
        this.direction= signVariety.direction;
        this.image=signVariety.image;
    }



    public Direction getDirection() {
        return direction;
    }

    @Override
    protected Image getImage() {
        return image;
    }
}
