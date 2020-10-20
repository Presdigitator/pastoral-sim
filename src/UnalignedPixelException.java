/**
 * An exception for if a pixel coordinate is not centred on a tile correctly.
 */
public class UnalignedPixelException extends Exception {

    public UnalignedPixelException(double pixelX, double pixelY) {
        super("error: Point \""+pixelX+","+pixelY+"\" is not a multiple of the tile size, cannot be aligned to a tile.");
    }
}
