/* Fruit.java: Deals with collections of fruit, to be used compositionally
by actors which have fruit.
 */
//Jalen Lyle-Holmes 1122679 jlyleholmes@student.unimelb.edu.au


import bagel.Font;

public class Fruits {
    private int numFruit;
    private final Actor holder;
    private static final String FONT_DIRECTORY="res/";
    private static final int FONT_SIZE=24;
    private final Font font = new Font(FONT_DIRECTORY+"VeraMono.ttf",FONT_SIZE);

    public int getNumFruit() {
        return numFruit;
    }

    public void setNumFruit(int numFruit) {
        this.numFruit = numFruit;
    }

    public Fruits(int numFruit, Actor holder) {
        this.numFruit = numFruit;
        this.holder = holder;
    }

    /* Reduces fruit count by 1 */
    public void take() {

    }

    /* Increases fruit count by 1 */
    public void add() {

    }

    /** Draws the currect number of fruit at the
     * top left corner of the 'holder' actor.
     *
     */
    public void drawNumber() {
        font.drawString(Integer.toString(numFruit),
                holder.getTile().toPixels().x,holder.getTile().toPixels().y);

    }
}
