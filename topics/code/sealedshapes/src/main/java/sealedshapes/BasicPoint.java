package sealedshapes;

/**
 * Helper class to define points and helper functions.
 *
 * @author Pieter van den Hombergh {@code <pieter.van.den.hombergh@gmail.com>}
 *
 * @param x coordinate of point
 * @param y coordinate of point
 */
public record BasicPoint( double x, double y) {

    /**
     * Distance between this BasicPoint and the other BasicPoint. The
     * computation uses Pythagoras' formula to do the computation.
     *
     * @param other point
     * @return length of the shortest straight line through this and the other
     * point.
     */
    public double distance( BasicPoint other ) {
        double xLen = Math.abs( x - other.x );
        double yLen = Math.abs( y - other.y );
        return Math.hypot( xLen, yLen );
    }

    /**
     * Factory method to allow shorthand {@code bp(1,0D,2.0D)} to create a
     * point. import static to have the most benefits.
     *
     * @param x coordinate
     * @param y coordinate
     * @return the record
     */
    public static BasicPoint bp( double x, double y ) {
        return new BasicPoint( x, y );
    }
}
