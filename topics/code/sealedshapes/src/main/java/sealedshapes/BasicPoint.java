/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package sealedshapes;

/**
 *
 * @author Pieter van den Hombergh {@code <pieter.van.den.hombergh@gmail.com>}
 */
public record BasicPoint(double x, double y) {

    /**
     * Distance between this BasicPoint and the other BasicPoint. The computation uses Pythagoras' formula to do the computation.
     * @param other point
     * @return length of the shortest straight line through this and the other point.
     */
    public double distance(BasicPoint other) {
        double xLen = Math.abs( x - other.x );
        double yLen = Math.abs( y - other.y );
        return Math.hypot( xLen, yLen );
    }
    
    /**
     * Factory method to allow shorthand {@code bp(1,0D,2.0D)} to create a point. import static.
     * @param x coordinate
     * @param y coordinate
     * @return  the record
     */
    public static BasicPoint bp(double x, double y){
        return new BasicPoint(x, y );
    }
}
