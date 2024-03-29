package sealedshapes;

import static java.lang.Math.abs;

/**
 * Example sealed intarface.
 *
 * @author Pieter van den Hombergh {@code <pieter.van.den.hombergh@gmail.com>}
 */
sealed interface BasicShape
        extends Comparable<BasicShape>
        permits Triangle, Rectangle, Circle {

    /**
     * Compute area for all known subclasses.
     *
     * @return the area.
     */
    default double area() {
        return switch ( this ) {
            case Circle( var unused, double radius) ->
                radius * radius * Math.PI;
            case Rectangle( BasicPoint( var tlx, var tly), BasicPoint( var brx, var bry)) ->
                abs( tlx - brx ) * abs( tly - bry );
            case Triangle( BasicPoint a, BasicPoint b, BasicPoint c) -> {
                double lA = b.distance( c );
                double lB = c.distance( a );
                double lC = a.distance( b );
                double s = ( lA + lB + lC ) / 2D;
                yield Math.sqrt( s * ( s - lA ) * ( s - lB ) * ( s - lC ) );
            }
        };
    }

    /**
     * Based on the area, the interface can provide the implementation for
     * compareTo.
     *
     * @param other to compare to this.
     * @return the outcome
     */
    @Override
    public default int compareTo( BasicShape other ) {
        return Double.compare( this.area(), other.area() );
    }

}
