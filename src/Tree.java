//Tree.java: Represents Tree-type actors
//Jalen Lyle-Holmes 1122679 jlyleholmes@student.unimelb.edu.au

import bagel.*;


public class Tree extends Actor {
    private static Image image=new Image(DIRECTORY+"/images/tree.png");
                                        //image for all trees

    public Tree(int tileX, int tileY) {
        super(tileX, tileY);
    }

    //Tree update method does nothing, as tree does not move
    @Override
    public void update() {

    }

    //returns tree image
    @Override
    public Image getImage() {
        return Tree.image;
    }

}
