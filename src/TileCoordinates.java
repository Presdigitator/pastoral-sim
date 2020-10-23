/* Thief.java: Represents the thief actor.
 *
 *Jalen Lyle-Holmes 1122679 jlyleholmes@student.unimelb.edu.au */

import bagel.util.Point;

import java.util.Objects;

public class TileCoordinates {
    int x;
    int y;
    /* Size of tiles in pixels */
    public static final int TILE_SIZE = 64;

    TileCoordinates(int x, int y){
        this.x=x;
        this.y=y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Returns tile location in pixel coordinates.
     * @return Point The pixel coordinates of the tile
     */
    public Point toPixels() {
        return new Point(x*TILE_SIZE, y*TILE_SIZE);
    }

    /**
     *  Takes pixel coordinates and returns an appropriate TileCoordinates object
     * @param pixelX The pixel x-coordinate
     * @param pixelY The pixel y-coordinate
     * @return TileCoordinates A TileCoordinates object matching the pixel coordinates
     */
    public static TileCoordinates fromPixels(double pixelX, double pixelY) throws UnalignedPixelException {
        // Check that pixel location is valid, i.e. a multiple of the tile size
        if ((pixelX%TILE_SIZE != 0) | (pixelY%TILE_SIZE != 0)) {
            throw new UnalignedPixelException(pixelX, pixelY);
        }
        // Return a TileCoordinates object with coordinates in tiles (rather than pixels)
        return new TileCoordinates((int)pixelX/TILE_SIZE, (int)pixelY/TILE_SIZE);
    }

    /**
     *  Gives equality if x and y values are equal
     * @param o Object to be compared to
     * @return boolean Return true if x and y are equal,
     * false if not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TileCoordinates that = (TileCoordinates) o;
        return x == that.x &&
                y == that.y;
    }

    /**
     * Override hashcode to use x and y values
     * @return int Hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
