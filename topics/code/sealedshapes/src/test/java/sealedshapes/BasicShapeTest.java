/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package sealedshapes;

//import static org.junit.jupiter.api.Assertions.*;
import static java.lang.Math.signum;
import static java.lang.Math.sqrt;
import static sealedshapes.BasicPoint.bp;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

/**
 *
 * @author Pieter van den Hombergh {@code <pieter.van.den.hombergh@gmail.com>}
 */
public class BasicShapeTest {

    @DisplayName("Comparing Rectangles")
    //@Disabled("think TDD")
    @Test
    public void testCompareRectangle() {
        Rectangle r1 = new Rectangle( bp( 0D, 0D ), bp( sqrt( 2.0 ), sqrt( 2 ) ) );
        Rectangle r2 = new Rectangle( bp( 0D, 0D ), bp( 2.0D, 2.0D ) );

        assertThat( r1 ).isLessThan( r2 );

//        fail( "method testCompare reached end. You know what to do." );
    }

    /**
     *
     */
    @DisplayName("Compare Rectangle and Triangle")
//@Disabled("think TDD")
    @Test
    public void testRetangleTriangle() {
        BasicPoint a = new BasicPoint( 0, 0 );
        BasicPoint b = new BasicPoint( 0, 2 );
        BasicPoint c = new BasicPoint( 2, 0 );
        BasicPoint d = new BasicPoint( 2, 2 );
        Rectangle r1 = new Rectangle( a, d );
        Triangle t1 = new Triangle( a, b, c );
        System.out.println( "t2 = " + t1 + " area " + t1.area() );
        Triangle t2 = new Triangle( b, c, a );
        System.out.println( "t2 = " + t2 + " area " + t2.area() );
        int compareTo1 = t1.compareTo( r1 );
        assertThat( signum( compareTo1 ) ).isEqualTo( -1 );
        assertThat( (BasicShape) t1 ).isLessThan( r1 );
    }

    //@Disabled("think TDD")
    @DisplayName("Circle smaller than square")
    @Test
    public void testCircleSmallerThanSquare() {
        BasicPoint center = bp( 1, 1 );
        BasicPoint topLeft = bp( 2, 0 );
        BasicPoint bottomRight = bp( 0, 2 );

        Circle circle = new Circle( center, 1D );
        Rectangle square = new Rectangle( topLeft, bottomRight );
        assertThat( (BasicShape) circle ).isLessThan( square );
//        fail( "method testCircleSmallerThanSquare reached end. You know what to do." );
    }
}
