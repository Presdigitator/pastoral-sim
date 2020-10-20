//Tree.java: Represents Tree-type actors
//Jalen Lyle-Holmes 1122679 jlyleholmes@student.unimelb.edu.au

import bagel.Image;


public class Tree extends Actor {
    // Image for normal trees
    private static final Image treeImage = new Image(IMAGE_DIRECTORY + "tree.png");
    // Image for golden trees
    private static final Image goldenTreeImage = new Image(IMAGE_DIRECTORY + "gold-tree.png");
    // The variety of this tree, is it normal or golden?:
    TreeVarieties treeVariety;
    private Image image;
    // Fruits object for this tree's fruit store:
    private Fruits fruits;

    public Tree(TileCoordinates tile, String treeVariety) {
        super(tile);
        // Set variety of this tree according to passed string.
        this.treeVariety = TreeVarieties.valueOf(treeVariety);
        // Set image to image matching the tree variety
        this.image = this.treeVariety.image;
        // Set up the tree fruit appropriately for normal or golden tree
        if (this.treeVariety.equals(TreeVarieties.Tree)) {
            this.fruits = new Fruits(3, this);
        } else if (this.treeVariety.equals(TreeVarieties.GoldenTree)) {
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
     * Fruit picked from tree. If normal tree reduce store of fruit
     * */
    public void pickFruit() {
        if (treeVariety.equals(TreeVarieties.Tree)) {
            fruits.take();
        }
    }

    @Override
    public void render() {
        super.render();
        if (treeVariety.equals(TreeVarieties.Tree)) {
            fruits.drawNumber();
        }
    }

    /* An enum of the different tree varieties,  by their names
    * as used in world CSVs, with matching image.
     */
    private enum TreeVarieties {
        Tree(treeImage),
        GoldenTree(goldenTreeImage);


        Image image;

        TreeVarieties(Image image) {
            this.image = image;
        }
    }


}
