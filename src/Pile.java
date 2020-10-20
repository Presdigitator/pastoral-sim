/* Pile.java: Represents actors which are used to store fruit.
 */
//Jalen Lyle-Holmes 1122679 jlyleholmes@student.unimelb.edu.au

import bagel.Image;

public class Pile extends Actor {

    private static final Image stockpileImage =
            new Image(IMAGE_DIRECTORY + "cherries.png");
    private static final Image hoardImage =
            new Image(IMAGE_DIRECTORY + "hoard.png");
    private final Fruits fruits;
    private Image image;
    private PileVarieties pileVariety;

    /**
     * Depending on what variety of pile has been specified,
     * create an pile with the appropriate image and variety identifier.
     *
     * @param tile        The tile on which to create the pile
     * @param pileVariety The name of the variety of pile to create
     */
    public Pile(TileCoordinates tile, String pileVariety) {
        super(tile);
        this.pileVariety = PileVarieties.valueOf(pileVariety);
        this.fruits = new Fruits(0, this);
        this.image = this.pileVariety.image;
    }

    public PileVarieties getPileVariety() {
        return pileVariety;
    }

    @Override
    protected Image getImage() {
        return image;
    }

    @Override
    public void render() {
        super.render();
        fruits.drawNumber();
    }

    /* Enum of the different varieties of pile which this class
     * can represent, with matching image.
     */
    private enum PileVarieties {
        Stockpile(stockpileImage),
        Hoard(hoardImage);

        Image image;

        PileVarieties(Image image) {
            this.image = image;
        }

    }


}
