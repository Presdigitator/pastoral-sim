/* Thief.java: Represents the thief actor.
 *
 *Jalen Lyle-Holmes 1122679 jlyleholmes@student.unimelb.edu.au */

import bagel.util.Point;

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

    public Point toPixels() {
        return new Point(x*TILE_SIZE, y*TILE_SIZE);
    }

    /**
     *  Takes pixel coordinates and returns an appropriate TileCoordinates object
     * @param pixelX The pixel x-coordinate
     * @param pixelY The pixel y-coordinate
     * @return TileCoordinates A TileCoordinates object matching the pixel coordinates
     * @throws UnalignedPixelException
     */
    public static TileCoordinates fromPixels(double pixelX, double pixelY) throws UnalignedPixelException {
        // Check that pixel location is valid, i.e. a multiple of the tile size
        if ((pixelX%TILE_SIZE != 0) | (pixelY%TILE_SIZE != 0)) {
            throw new UnalignedPixelException(pixelX, pixelY);
        }
        // Return a TileCoordinates object with coordinates in tiles (rather than pixels)
        return new TileCoordinates((int)pixelX/TILE_SIZE, (int)pixelY/TILE_SIZE);
    }


}
