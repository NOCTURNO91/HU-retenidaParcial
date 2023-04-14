package co.com.tcc.novedadesmasivas.common;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author Be-Smart_2
 */
public class Util
{
    
    private String diasHabiles;
    private String fechaHabil;
    private int rangoDiasFechaReal;

    public String getDiasHabiles() {
        return diasHabiles;
    }

    public void setDiasHabiles(String diasHabiles) {
        this.diasHabiles = diasHabiles;
    }

    public String getFechaHabil() {
        return fechaHabil;
    }

    public void setFechaHabil(String fechaHabil) {
        this.fechaHabil = fechaHabil;
    }

    public int getRangoDiasFechaReal() {
        return rangoDiasFechaReal;
    }

    public void setRangoDiasFechaReal(int rangoDiasFechaReal) {
        this.rangoDiasFechaReal = rangoDiasFechaReal;
    }
    
    public static double redondear( double val, int decimales )
    {
        if ( decimales < 0 ) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = new BigDecimal( val );
        bd = bd.setScale( decimales, RoundingMode.HALF_UP );
        
        return bd.doubleValue();
    }
    
    public static Date parseDate( String str )
    {
        return parseDate( str, "dd/MM/yyyy" );
    }
    
    public static Date parseDate( String str, String patron )
    {
        try
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat( patron );
            Date fecha = ( str == null ) ? null : dateFormat.parse( str );
            
            return fecha;
        }
        catch( Exception ex )
        {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static String formatDate( Date fecha )
    {
        return formatDate( fecha, "dd/MM/yyyy" );
    }
    
    public static String formatDate( Date fecha, String patron )
    {
        try
        {
            if ( fecha == null ) {
                return null;
            }
            
            SimpleDateFormat dateFormat = new SimpleDateFormat( patron );
            return dateFormat.format( fecha );
        }
        catch( Exception ex )
        {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static String formatNum( Object num )
    {
        // return formatNum( num, "#,##0.00" );
        return formatNum( num, "#,##0" );
    }
    
    public static String formatNum( Object num, String patron )
    {
        try
        {
            DecimalFormatSymbols decimalSymbols = new DecimalFormatSymbols( new Locale( "en", "US" ) );
            
            DecimalFormat decimalFormat = new DecimalFormat( patron, decimalSymbols );
            return decimalFormat.format( num );
        }
        catch( Exception ex )
        {
            ex.printStackTrace();
            return "" + num;
        }
    }
    
    public static String getSha1Digest( String texto )
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance( "SHA1" );
            byte[] messageDigest = md.digest( texto.getBytes( "UTF-8" ) );
            BigInteger number = new BigInteger( 1, messageDigest );

            return number.toString(16);
        }
        catch( NoSuchAlgorithmException nsaEx )
        {
            nsaEx.printStackTrace();
        }
        catch( UnsupportedEncodingException ueEx )
        {
            ueEx.printStackTrace();
        }

        return null;
    }
}