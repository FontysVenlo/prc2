package cabbage;

import java.util.Comparator;
import java.util.Objects;

/**
 *
 * @author insert your name here
 */
public class Cabbage implements Comparable<Cabbage> {
    // add the odour enum.

    public enum Odour {
        // enums are comparable and compare using their natural order. 
        // the enum later in the list has a higher value in the comparison.
        NONE, THRESHOLD, WEAK, DISTINCT, STRONG, VERY_STRONG, INTOLERABLE;
    }

    final String name;
    final int weight;
    final double volume;
    final Odour odour;

    public Cabbage( String name, int weight, double volume, Odour odour ) {
        this.name = name;
        this.weight = weight;
        this.volume = volume;
        this.odour = odour;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public Odour getOdour() {
        return odour;
    }

    public double getVolume() {
        return volume;
    }

    // by weight
    @Override
    public int compareTo( Cabbage other ) {
        return Double.compare( this.getWeight(), other.getWeight() );
    }

    static Comparator<Cabbage> byStinkyness() {

        return Comparator.comparing( Cabbage::getOdour );
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode( this.name );
        hash = 67 * hash + this.weight;
        hash
                = 67 * hash + (int) ( Double.doubleToLongBits( this.volume ) ^ ( Double.doubleToLongBits( this.volume ) >>> 32 ) );
        hash = 67 * hash + Objects.hashCode( this.odour );
        return hash;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final Cabbage other = (Cabbage) obj;
        if ( this.weight != other.weight ) {
            return false;
        }
        if ( Double.doubleToLongBits( this.volume ) != Double.doubleToLongBits( other.volume ) ) {
            return false;
        }
        if ( !Objects.equals( this.name, other.name ) ) {
            return false;
        }
        return this.odour == other.odour;
    }

    
    
}
