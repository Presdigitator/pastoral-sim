/* Direction.java: Stores a direction in the xy plane as a 2-d unit vector.
 *
 *Jalen Lyle-Holmes 1122679 jlyleholmes@student.unimelb.edu.au */

import java.util.Random;

public enum Direction {

    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    // Array and number  of all values, used in getRandomDirection()
    private static final Direction[] VALUES;
    private static final int SIZE;
    static {
        VALUES = values();
        SIZE = VALUES.length;
    }

    // x component of matching unit vector:
    private final int x;
    // Y component of matching unit vector:
    private final int y;

    private final Random RAND = new Random();

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Changes the direction by rotating it right
     * the number of times specified by the times
     * parameter.
     *
     * @param times The number of times to rotate
     */
    public void rotateRight(int times) {

    }

    /**
     * Returns a random up,down,left or right Direction
     * Credit: Referred to this Stackoverflow question about ways
     * to get random values of an enum:
     * https://stackoverflow.com/questions/1972392/pick-a-random-value-from-an-enum
     *
     * @return Direction A random direction
     */
    public Direction randomDirection() {
        return VALUES[RAND.nextInt(SIZE)];


    }

}