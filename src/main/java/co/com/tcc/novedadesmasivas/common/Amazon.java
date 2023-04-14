package co.com.tcc.novedadesmasivas.common;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.Base64;
import com.amazonaws.util.IOUtils;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Be-Smart_2
 */
public class Amazon
{
    private final String BUCKET_NAME = "s3movilidadtcc01";
    private final String ACCESS_KEY = "AKIAIUFPFOV57G6PWRUQ";
    private final String SECRET_KEY = "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";
    
    private AmazonS3 _cliente = null;
    
    public Amazon()
    {
        _setAmazonS3Client();
    }
    
    private void _setAmazonS3Client()
    {
        AWSCredentials credentials = null;
        
        try {
            credentials = new BasicAWSCredentials( ACCESS_KEY, SECRET_KEY );
        }
        catch (Exception e)
        {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location (~/.aws/credentials), and is in valid format.",
                    e);
        }

        _cliente = new AmazonS3Client( credentials );
        _cliente.setRegion( Region.getRegion( Regions.US_WEST_2 ) );
    }
    
    public String obtenerImagen( String archivo )
    {
        System.err.println( "archivo: " + archivo );
        
        if ( archivo == null || archivo.length() == 0 ) {
            return null;
        }
        
        try
        {
            S3Object object = _cliente.getObject( new GetObjectRequest( BUCKET_NAME, archivo ) );
            System.out.println( "Content-Type: " + object.getObjectMetadata().getContentType() );
            
            return codificarArchivo( object.getObjectContent() );
        }
        catch( IOException ioEx )
        {
            ioEx.printStackTrace();
        }
        catch( AmazonServiceException ase )
        {
            System.out.println("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        }
        catch( AmazonClientException ace )
        {
            System.out.println("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
        
        return null;
    }
    
    public String codificarArchivo( InputStream input ) throws IOException
    {
        byte[] bytes = IOUtils.toByteArray( input );

        String archivoCodificado = Base64.encodeAsString( bytes );
        input.close();
        
        return archivoCodificado;
    }
}