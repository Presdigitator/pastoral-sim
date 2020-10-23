/* Pile.java: Represents actors which are used to store fruit.
 */
//Jalen Lyle-Holmes 1122679 jlyleholmes@student.unimelb.edu.au

import bagel.Image;

public class Pile extends Actor {
    private final Fruits fruits;
    private Image image;

    /**
     * Depending on what variety of pile has been specified,
     * create an pile with the appropriate image and variety identifier.
     *
     * @param tile The tile on which to create the pile
     * @param type The type of pile to create
     */
    public Pile(TileCoordinates tile, World world, ActorType type) {
        super(tile, world, type);
        setType(type);
        this.fruits = new Fruits(0, this);
        this.image = type.image;
    }


    @Override
    public Image getImage() {
        return image;
    }

    /**
     * Renders the pile onscreen, with number of fruits
     */
    @Override
    public void render() {
        super.render();
        fruits.drawNumber();
    }


    /**
     * Prints number of fruits, called when simulation halts
     */
    @Override
    public void haltPrint() {
        System.out.println(fruits.getNumFruit());
    }

    /**
     * Calls collision method for agent standing on this Pile.
     * (Visitor Pattern)
     * @param agent The agent that is standing on this actor
     */
    @Override
    public void stoodOnBy(Agent agent) {
        agent.collideWith(this);
    }

    /**
     * Take one fruit from passed agent.
     * @param agent The agent giving the fruit.
     */
    public void getFruitFrom(Agent agent) {
        fruits.getFrom(agent);
    }

    /**
     *  Returns whether has any fruit
     * @return boolean True if has at least one fruit,
     *
     */
    public boolean hasFruit() {
            return fruits.hasFruit();
    }

    /**
     * Fruit picked from pile.
     * @param agent The agent picking
     * */
    public void pickFruit(Agent agent) {
        // 'pick' a fruit
            fruits.giveTo(agent);
    }
}
