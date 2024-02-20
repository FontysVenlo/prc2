/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package sealedshapes;

import static sealedshapes.BasicPoint.bp;

/**
 * Examples of usage.
 * @author Pieter van den Hombergh {@code <pieter.van.den.hombergh@gmail.com>}
 */
public class Sealedshapes {

    /**
     * Demo program.
     * 
     * @param args not used
     */
    public static void main(String[] args) {
        BasicPoint a = new BasicPoint( 0, 0 );
        BasicPoint b = new BasicPoint( 0, 2 );
        BasicPoint d = new BasicPoint( 2, 2 );
        BasicPoint center = new BasicPoint( 1, 1 );
        double radius = 1D;

        Circle circle = new Circle( center, radius );
        Rectangle square = new Rectangle( a, d );
        Triangle triangle = new Triangle( a, b, d );
        int circleSquare = circle.compareTo( square );
        int squareTriangle = square.compareTo( triangle );
        int triangleCircle = triangle.compareTo( circle );

        System.out.println( "circleSquare = " + circleSquare );
        System.out.println( "squareTriangle = " + squareTriangle );
        System.out.println( "triangleCircle = " + triangleCircle );
        double areaCircle = circle.area();
        System.out.println( "area Circle = " + areaCircle );
        double areaSquare = square.area();
        System.out.println( "area Square = " + areaSquare );
        double areaTriangle = triangle.area();
        System.out.println( "area Triangle = " + areaTriangle );
        System.out.println( "Hello World!" );

        var s = new Circle( d, 2.0D );
        
        if (s instanceof Circle(var bp, var rad)){
            System.out.println( "diameter = " + rad*2 );
        }
    }
    private Sealedshapes(){}
}
