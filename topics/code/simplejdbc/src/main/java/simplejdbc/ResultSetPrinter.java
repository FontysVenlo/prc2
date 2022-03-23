package simplejdbc;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility to print tables from result set in ascii art.
 *
 * @author hom
 */
public class ResultSetPrinter {

    private final ResultSet rs;
    private ResultSetMetaData metaData = null;

    public ResultSetPrinter( ResultSet rs ) {
        this.rs = rs;
    }

    /**
     * Padd a string to a specific length.
     *
     * @param in string.
     * @param to length the result should have (minimally).
     * @param filler the fill char when padding.
     * @return padded string of at least to length.
     */
    public static String padd( String in, int to, char filler ) {
        if ( in.length() >= to ) {
            return in;
        }
        char[] padd = new char[ to - in.length() ];
        Arrays.fill( padd, filler );
        return new String( padd ) + in;
    }

    /**
     * Padd with spaces.
     *
     * @param in string
     * @param to new length
     * @return string padded to length.
     */
    public static String padd( String in, int to ) {
        return padd( in, to, ' ' );
    }

    /**
     * Print to this result set to out.
     *
     * @param out1
     * @throws SQLException
     */
    public void printTable(
            PrintStream out1 ) throws
            SQLException {
        ResultSetMetaData meta = getMetaData();
        printTableRule( out1 );
        printHeader( out1 );
        while ( rs.next() ) {
            out1.append( "| " );
            for ( int cols = 1; cols <= meta.getColumnCount(); cols++ ) {
                Object data = rs.getObject( cols );
                int padto = Math.max( meta.getColumnDisplaySize(
                        cols ), meta.getColumnLabel( cols ).length() );
                out1.append( padd( Objects.toString( data, "NULL" ),
                                   padto ) )
                        .append( " | " );
            }
            out1.append( System.lineSeparator() );
        }
        printTableRule( out1 );
    }

    /**
     * Prints a header for this result set printer.
     *
     * @param out1 to print/append to
     * @throws SQLException on db error
     */
    public void printHeader(
            PrintStream out1 ) throws
            SQLException {

        // note count starts for 1. Database ;-))
        ResultSetMetaData meta = getMetaData();
        StringBuilder nameRow = new StringBuilder( "| " );
        StringBuilder typeRow = new StringBuilder( "| " );
        for ( int cols = 1; cols <= meta.getColumnCount();
                cols++ ) {
            String colName = meta.getColumnName( cols );
            String colType = meta.getColumnTypeName( cols );
            int padto = Math.max( meta
                    .getColumnDisplaySize( cols ), meta
                                  .getColumnLabel( cols ).length() );
            nameRow.append( padd( colName, padto ) )
                    .append( " | " );
            typeRow.append( padd( colType, padto ) )
                    .append( " | " );
        }
        out1.append( nameRow )
                .append( System.lineSeparator() );
        out1.append( typeRow )
                .append( System.lineSeparator() );
        printTableRule( out1 );
    }

    /**
     * Prints a horizontal rule to the output
     *
     * @param out1 output
     * @throws SQLException when meta data is not available.
     */
    public void printTableRule(
            PrintStream out1 )
            throws SQLException {
        ResultSetMetaData meta = getMetaData();
        StringBuilder headerRule = new StringBuilder( "+" );
        for ( int cols = 1; cols <= meta.getColumnCount();
                cols++ ) {
            int padto = Math.max( meta
                    .getColumnDisplaySize( cols ), meta
                                  .getColumnLabel( cols ).length() );
            headerRule.append( padd( "", padto, '-' ) )
                    .append( "--+" );

        }
        out1.append( headerRule )
                .append( System.lineSeparator() );
    }

    /**
     * Get the properties of a connection.
     *
     * @param con
     */
    public static void getDBProperties( Connection con ) {
        try {
            DatabaseMetaData meta = con.getMetaData();
            String dbVendor = meta.getDatabaseProductName();
            int majorV = meta.getDriverMajorVersion();
            int minorV = meta.getDatabaseMinorVersion();
            System.out.print( "dbvendor = " + dbVendor );
            System.out.print( ",majorV = " + majorV );
            System.out.println( ",minorV = " + minorV );
        } catch ( SQLException ex ) {
            logger.log( Level.SEVERE, null, ex );
        }
    }

    private final static Logger logger
            = Logger.getLogger( PgJDBCUtils.class.getName() );

    private ResultSetMetaData getMetaData() throws
            SQLException {
        if ( metaData == null ) {
            metaData = rs.getMetaData();
        }
        return metaData;
    }
}
