//Tree.java: Represents Tree-type actors
//Jalen Lyle-Holmes 1122679 jlyleholmes@student.unimelb.edu.au

import bagel.Image;


public class Tree extends Actor {

    private Image image;
    // Fruits object for this tree's fruit store:
    private Fruits fruits;

    public Tree(TileCoordinates tile, World world, ActorType type) {
        super(tile,world, type);
        // Set type of this tree according to passed type
        setType(type);
        // Set image to image matching the tree variety
        this.image = type.image;
        // Set up the tree fruit appropriately for normal or golden tree
        if (getType().equals(ActorType.TREE)) {
            this.fruits = new Fruits(3, this);
        } else if (getType().equals(ActorType.GOLDENTREE)) {
            this.fruits = null;
        }

    }

    /** Tree update method does nothing, as tree does not move.
     *
     */
    @Override
    public void update() {

    }

    //returns tree image
    @Override
    public Image getImage() {
        return image;
    }

    /**
     * Calls collision method for agent standing on this Tree
     * (Part of Visitor Pattern)
     * @param agent The agent that is standing on this actor
     */
    @Override
    public void stoodOnBy(Agent agent) {
        agent.collideWith(this);
    }

    /**
     * Fruit picked from tree. If normal tree reduce store of fruit
     * */
    public void pickFruit(Agent agent) {
        // If normal tree, 'pick' a fruit
        if (getType().equals(ActorType.TREE)) {
            fruits.giveTo(agent);
        }
        // If golden tree, we just set agent to carrying
        else {
            agent.setCarrying(true);
        }
    }

    /**
     *  Returns whether has any fruit
     * @return boolean True if has at least one fruit,
     * or is golden tree
     */
    public boolean hasFruit() {
        // If golden tree, always has fruit so return true
        if(getType().equals(ActorType.GOLDENTREE)){
            return true;
        }
        // Otherwise check
        else {
            return fruits.hasFruit();
        }
    }

    /**
     * Draws the tree onscreen, along with fruit count.
     */
    @Override
    public void render() {
        super.render();
        if (getType().equals(ActorType.TREE)) {
            fruits.drawNumber();
        }
    }

   }
