package co.com.tcc.novedadesmasivas.controller;

import co.com.tcc.novedadesmasivas.common.Amazon;
import co.com.tcc.novedadesmasivas.common.JsonReturn;
import co.com.tcc.novedadesmasivas.common.IngresoNovedad;
import co.com.tcc.novedadesmasivas.common.ParametroNovedad;
import co.com.tcc.novedadesmasivas.common.SeguimientoNovedad;
import co.com.tcc.novedadesmasivas.common.NovedadUnidadRemesa;
import co.com.tcc.novedadesmasivas.common.Remesa;
import co.com.tcc.novedadesmasivas.common.RemesaLog;
import co.com.tcc.novedadesmasivas.common.ParamsExport;
import co.com.tcc.novedadesmasivas.manager.IMgrNovedades;
import co.com.tcc.novedadesmasivas.common.Util;
import galileo.base.exception.MgrException;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Be-Smart_2
 */
@RestController
@RequestMapping( value = "/novedades",  produces = {"application/json;charset=UTF-8", "application/x-www-form-urlencoded;charset=UTF-8"})
public class NovedadesController
{
    @Autowired
    private IMgrNovedades mgrNovedades;
    
    @Autowired
    private ServletContext context;
    
    @ResponseBody
    @ResponseStatus( HttpStatus.OK )
    @RequestMapping( value="/consultar", method=RequestMethod.POST )
    public JsonReturn consultar( @RequestBody Remesa remesa )
    {
        JsonReturn jsonReturn = new JsonReturn();
        
        try
        {
            jsonReturn = mgrNovedades.consultar( remesa );
        }
        catch( MgrException mgrEx )
        {
            mgrEx.printStackTrace();
            
            jsonReturn.setCodigo( -1 )
                      .setMensaje( "Error enviando los datos." );
        }
        catch( Exception ex )
        {
            ex.printStackTrace();
            
            jsonReturn.setCodigo( -1 )
                      .setMensaje( "Error enviando los datos." );
        }
        
        return jsonReturn;
    }
    
    @ResponseBody
    @ResponseStatus( HttpStatus.OK )
    @RequestMapping( value="/consultarUnidadesRemesa/{remeIdInt}", method=RequestMethod.GET )
    public JsonReturn consultarUnidadesRemesa( @PathVariable("remeIdInt") String remeIdInt )
    {
        JsonReturn jsonReturn = new JsonReturn();
        
        try
        {
            jsonReturn = mgrNovedades.consultarUnidadesRemesa( remeIdInt );
        }
        catch( MgrException mgrEx )
        {
            mgrEx.printStackTrace();
            
            jsonReturn.setCodigo( -1 )
                      .setMensaje( "Error enviando los datos." );
        }
        catch( Exception ex )
        {
            ex.printStackTrace();
            
            jsonReturn.setCodigo( -1 )
                      .setMensaje( "Error enviando los datos." );
        }
        
        return jsonReturn;
    }
    
    @ResponseBody
    @ResponseStatus( HttpStatus.OK )
    @RequestMapping( value="/consultarTrazabilidadUnidades/{ipidIdInt}", method=RequestMethod.GET )
    public JsonReturn consultarTrazabilidadUnidades( @PathVariable("ipidIdInt") String ipidIdInt )
    {
        JsonReturn jsonReturn = new JsonReturn();
        
        try
        {
            jsonReturn = mgrNovedades.consultarTrazabilidadUnidades( ipidIdInt );
        }
        catch( MgrException mgrEx )
        {
            mgrEx.printStackTrace();
            
            jsonReturn.setCodigo( -1 )
                      .setMensaje( "Error enviando los datos." );
        }
        catch( Exception ex )
        {
            ex.printStackTrace();
            
            jsonReturn.setCodigo( -1 )
                      .setMensaje( "Error enviando los datos." );
        }
        
        return jsonReturn;
    }
    
    @ResponseBody
    @ResponseStatus( HttpStatus.OK )
    @RequestMapping( value="/consultarDocumentosClienteRemesa/{remeIdInt}", method=RequestMethod.GET )
    public JsonReturn consultarDocumentosClienteRemesa( @PathVariable("remeIdInt") String remeIdInt )
    {
        JsonReturn jsonReturn = new JsonReturn();
        
        try
        {
            jsonReturn = mgrNovedades.consultarDocumentosClienteRemesa( remeIdInt );
        }
        catch( MgrException mgrEx )
        {
            mgrEx.printStackTrace();
            
            jsonReturn.setCodigo( -1 )
                      .setMensaje( "Error enviando los datos." );
        }
        catch( Exception ex )
        {
            ex.printStackTrace();
            
            jsonReturn.setCodigo( -1 )
                      .setMensaje( "Error enviando los datos." );
        }
        
        return jsonReturn;
    }
    
    @ResponseBody
    @ResponseStatus( HttpStatus.OK )
    @RequestMapping( value="/consultarFormatosImpresion", method=RequestMethod.GET )
    public JsonReturn consultarFormatosImpresion()
    {
        JsonReturn jsonReturn = new JsonReturn();
        
        try
        {
            jsonReturn = mgrNovedades.consultarFormatosImpresion();
        }
        catch( MgrException mgrEx )
        {
            mgrEx.printStackTrace();
            
            jsonReturn.setCodigo( -1 )
                      .setMensaje( "Error enviando los datos." );
        }
        catch( Exception ex )
        {
            ex.printStackTrace();
            
            jsonReturn.setCodigo( -1 )
                      .setMensaje( "Error enviando los datos." );
        }
        
        return jsonReturn;
    }
    
    @ResponseBody
    @ResponseStatus( HttpStatus.OK )
    @RequestMapping( value="/obtenerCumplidoRemesa", params = "imagen", method=RequestMethod.GET )
    public JsonReturn obtenerCumplidoRemesa( @RequestParam("imagen") String imagen )
    {
        JsonReturn jsonReturn = new JsonReturn();
        
        Amazon cliente = new Amazon();
        String imagenCodificada = cliente.obtenerImagen( imagen );
        
        if ( imagenCodificada != null ) {
            jsonReturn.setObject( imagenCodificada );
        }
        
        return jsonReturn;
    }
    
    @ResponseBody
    @ResponseStatus( HttpStatus.OK )
    @RequestMapping( value="/exportarExcel", params = { "tipoExportacion", "sessionId" }, method=RequestMethod.POST )
    public JsonReturn exportarExcel( @RequestBody Remesa remesa,
                                     @RequestParam("tipoExportacion") String tipoExportacion,
                                     @RequestParam("sessionId") String sessionId )
    {
        JsonReturn jsonReturn = new JsonReturn();
        
        try
        {
            if ( ParamsExport.map == null ) {
                ParamsExport.map = new HashMap<String, Map<String, Object>>();
            }

            Map<String, Object> map = new HashMap<String, Object>();
            
            map.put( "remesa", remesa );
            map.put( "tipoExportacion", tipoExportacion );
            map.put( "estado", "preparado" );

            ParamsExport.map.put( sessionId, map );
        }
        catch( Exception ex )
        {
            ex.printStackTrace();
            
            jsonReturn.setCodigo( -1 )
                      .setMensaje( "Error enviando los datos." );
        }

        return jsonReturn;
    }
    
    @ResponseBody
    @ResponseStatus( HttpStatus.OK )
    @RequestMapping( value="/verificarExportacion", params = "sessionId", method=RequestMethod.GET )
    public JsonReturn verificarExportacion( @RequestParam("sessionId") final String sessionId )
    {
        JsonReturn jsonReturn = new JsonReturn();
        
        try
        {
            if ( ParamsExport.map != null )
            {
                final Map<String, Object> map = ParamsExport.map.get( sessionId );
                String estado = (String) map.get( "estado" );
    			
                System.err.println( "estado: " + estado );
                
                if ( estado != null )
                {
                    if ( estado.equals( "preparado" ) == true )
                    {
                        final String path = context.getRealPath( "" );
                        
                        map.put( "estado", "iniciado" );
                        ParamsExport.map.put( sessionId, map );
                        
                        try
                        {
                            Timer timer = new Timer();
                            TimerTask timerTask = new TimerTask()
                            {
                                @Override
                                public void run()
                                {
                                    try
                                    {
                                        Remesa remesa = (Remesa) map.get( "remesa" );
                                        String tipoExportacion = (String) map.get( "tipoExportacion" );

                                        JsonReturn jsonRet = mgrNovedades.exportarExcel( remesa, tipoExportacion, path );

                                        map.put( "estado", "finalizado" );
                                        map.put( "jsonReturn", jsonRet );

                                        ParamsExport.map.put( sessionId, map );
                                    }
                                    catch( MgrException mgrEx )
                                    {
                                        mgrEx.printStackTrace();

                                        map.put( "estado", "fallido" );
                                        ParamsExport.map.put( sessionId, map );
                                    }
                                    catch ( Exception ex )
                                    {
                                        ex.printStackTrace();

                                        map.put( "estado", "fallido" );
                                        ParamsExport.map.put( sessionId, map );
                                    }
                                }
                            };
                            
                            long tiempo = 10 * 1000; // 10 segundos
                            timer.schedule( timerTask, tiempo );
                        }
                        catch ( Exception ex )
                        {
                            ex.printStackTrace();

                            map.put( "estado", "fallido" );
                            ParamsExport.map.put( sessionId, map );
                        }
                    }
                    else if ( estado.equals( "finalizado" ) == true )
                    {
                        jsonReturn = (JsonReturn) map.get( "jsonReturn" );
                        ParamsExport.map.remove( sessionId );
                    }
                    else if ( estado.equals( "fallido" ) == true )
                    {
                        ParamsExport.map.remove( sessionId );
                        
                        jsonReturn.setCodigo( -1 )
                                  .setMensaje( "No se pudo generar el archivo." );
                    }
                }
            }
        }
        catch( Exception ex )
        {
            ex.printStackTrace();
            
            jsonReturn.setCodigo( -1 )
                      .setMensaje( "Error enviando los datos." );
        }
        
        return jsonReturn;
    }
    
    @ResponseBody
    @ResponseStatus( HttpStatus.OK )
    @RequestMapping( value="/descargarExcel/{nombreArchivo}", method=RequestMethod.GET )
    public JsonReturn descargarExcel( @PathVariable("nombreArchivo") String nombreArchivo, HttpServletResponse response )
    {
        JsonReturn jsonReturn = new JsonReturn();
        
        try
        {
            final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
            
            String path = context.getRealPath( "" );
            File file = new File( path + "/" + nombreArchivo + ".xlsx" );
            
            InputStream inputStream = new FileInputStream( file );
            ServletOutputStream outputStream = response.getOutputStream();

            response.setHeader( "Cache-Control","no-cache" ); //HTTP 1.1
            response.setHeader( "Pragma","no-cache" ); //HTTP 1.0
            response.setDateHeader( "Expires", 0 ); //prevents caching at the proxy server

            response.setHeader( "Content-Description", "File Transfer" );
            response.setContentType( "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" );
            response.setHeader( "Content-Disposition", "attachment; filename=\"Novedades_masivas.xlsx\"" );
            response.setHeader( "Content-Transfer-Encoding", "binary" );
            response.setContentLength( Integer.parseInt( "" + file.length() ) );

            // Write file contents to response.
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            
            while ( ( length = inputStream.read( buffer ) ) > 0 ) {
            	outputStream.write( buffer, 0, length );
            }

            outputStream.flush();
            outputStream.close();

            inputStream.close();
            
            if ( file.canWrite() == true ) {
                file.delete();
            }
        }
        catch( Exception ex )
        {
            ex.printStackTrace();
            
            jsonReturn.setCodigo( -1 )
                      .setMensaje( "Error enviando los datos." );
        }
        
        return jsonReturn;
    }
    
    @ResponseBody
    @ResponseStatus( HttpStatus.OK )
    @RequestMapping( value="/guardarLog", method=RequestMethod.POST )
    public JsonReturn guardarLog( @RequestBody RemesaLog[] arrRemesaLog )
    {
        JsonReturn jsonReturn = new JsonReturn();
        
        try
        {
            jsonReturn = mgrNovedades.guardarLog( arrRemesaLog );
        }
        catch( MgrException mgrEx )
        {
            mgrEx.printStackTrace();
            
            jsonReturn.setCodigo( -1 )
                      .setMensaje( "Error enviando los datos." );
        }
        catch( Exception ex )
        {
            ex.printStackTrace();
            
            jsonReturn.setCodigo( -1 )
                      .setMensaje( "Error enviando los datos." );
        }
        
        return jsonReturn;
    }
    
    @ResponseBody
    @ResponseStatus( HttpStatus.OK )
    @RequestMapping( value="/consultarNovedadesUnidades", method=RequestMethod.POST )
    public JsonReturn consultarNovedadesUnidades( @RequestBody NovedadUnidadRemesa novedadUnidad )
    {
        JsonReturn jsonReturn = new JsonReturn();
        
        try
        {
            jsonReturn = mgrNovedades.consultarNovedadesUnidades( novedadUnidad );
        }
        catch( MgrException mgrEx )
        {
            mgrEx.printStackTrace();
            
            jsonReturn.setCodigo( -1 )
                      .setMensaje( "Error enviando los datos." );
        }
        catch( Exception ex )
        {
            ex.printStackTrace();
            
            jsonReturn.setCodigo( -1 )
                      .setMensaje( "Error enviando los datos." );
        }
        
        return jsonReturn;
    }
    
    @ResponseBody
    @ResponseStatus( HttpStatus.OK )
    @RequestMapping( value="/obtenerImagenesNovedadUnidad", method=RequestMethod.POST )
    public JsonReturn obtenerImagenesNovedadUnidad( @RequestBody NovedadUnidadRemesa novedadUnidad )
    {
        JsonReturn jsonReturn = new JsonReturn();
        
        try
        {
            jsonReturn = mgrNovedades.obtenerImagenesNovedadUnidad( novedadUnidad );
        }
        catch( MgrException mgrEx )
        {
            mgrEx.printStackTrace();
            
            jsonReturn.setCodigo( -1 )
                      .setMensaje( "Error enviando los datos." );
        }
        catch( Exception ex )
        {
            ex.printStackTrace();
            
            jsonReturn.setCodigo( -1 )
                      .setMensaje( "Error enviando los datos." );
        }
        
        return jsonReturn;
    }
    
    @ResponseBody
    @ResponseStatus( HttpStatus.OK )
    @RequestMapping( value="/grabarNovedades", method=RequestMethod.POST )
    public JsonReturn grabarNovedades( @RequestBody IngresoNovedad ingresoNovedad )
    {
        JsonReturn jsonReturn = new JsonReturn();
        
        try
        {
            jsonReturn = mgrNovedades.grabarNovedades( ingresoNovedad );
        }
        catch( MgrException mgrEx )
        {
            mgrEx.printStackTrace();
            
            jsonReturn.setCodigo( -1 )
                      .setMensaje( "Error enviando los datos." );
        }
        catch( Exception ex )
        {
            ex.printStackTrace();
            
            jsonReturn.setCodigo( -1 )
                      .setMensaje( "Error enviando los datos." );
        }
        
        return jsonReturn;
    }
    
    @ResponseBody
    @ResponseStatus( HttpStatus.OK )
    @RequestMapping( value="/ingresarSeguimientosMasivos", method=RequestMethod.POST )
    public JsonReturn ingresarSeguimientosMasivos( @RequestBody SeguimientoNovedad[] arrSeguimientos )
    {
        JsonReturn jsonReturn = new JsonReturn();
        
        try
        {
            jsonReturn = mgrNovedades.ingresarSeguimientosMasivos( arrSeguimientos );
        }
        catch( MgrException mgrEx )
        {
            mgrEx.printStackTrace();
            
            jsonReturn.setCodigo( -1 )
                      .setMensaje( "Error enviando los datos." );
        }
        catch( Exception ex )
        {
            ex.printStackTrace();
            
            jsonReturn.setCodigo( -1 )
                      .setMensaje( "Error enviando los datos." );
        }
        
        return jsonReturn;
    }
    
    @ResponseBody
    @ResponseStatus( HttpStatus.OK )
    @RequestMapping( value="/ejecutarNovedad", method=RequestMethod.POST )
    public JsonReturn ejecutarNovedad( @RequestBody ParametroNovedad[] arrNovedades )
    {
        JsonReturn jsonReturn = new JsonReturn();
        
        try
        {
            jsonReturn = mgrNovedades.ejecutarNovedad( arrNovedades );
        }
        catch( MgrException mgrEx )
        {
            mgrEx.printStackTrace();
            
            jsonReturn.setCodigo( -1 )
                      .setMensaje( "Error enviando los datos." );
        }
        catch( Exception ex )
        {
            ex.printStackTrace();
            
            jsonReturn.setCodigo( -1 )
                      .setMensaje( "Error enviando los datos." );
        }
        
        return jsonReturn;
    }
    
    @ResponseBody
    @ResponseStatus( HttpStatus.OK )
    @RequestMapping( value="/anularNovedad", method=RequestMethod.POST )
    public JsonReturn anularNovedad( @RequestBody ParametroNovedad[] arrNovedades )
    {
        JsonReturn jsonReturn = new JsonReturn();
        
        try
        {
            jsonReturn = mgrNovedades.anularNovedad( arrNovedades );
        }
        catch( MgrException mgrEx )
        {
            mgrEx.printStackTrace();
            
            jsonReturn.setCodigo( -1 )
                      .setMensaje( "Error enviando los datos." );
        }
        catch( Exception ex )
        {
            ex.printStackTrace();
            
            jsonReturn.setCodigo( -1 )
                      .setMensaje( "Error enviando los datos." );
        }
        
        return jsonReturn;
    }
    
    @ResponseBody
    @ResponseStatus( HttpStatus.OK )
    @RequestMapping( value="/consultarDiasNoHabiles", method=RequestMethod.GET )
    public JsonReturn consultarDiasNoHabiles()
    {
        JsonReturn jsonReturn = new JsonReturn();
        
        try
        {
            jsonReturn = mgrNovedades.consultarDiasNoHabiles();
        }
        catch( MgrException mgrEx )
        {
            mgrEx.printStackTrace();
            
            jsonReturn.setCodigo( -1 )
                      .setMensaje( "Error enviando los datos." );
        }
        catch( Exception ex )
        {
            ex.printStackTrace();
            
            jsonReturn.setCodigo( -1 )
                      .setMensaje( "Error enviando los datos." );
        }
        
        return jsonReturn;
    }
    
    @ResponseBody
    @ResponseStatus( HttpStatus.OK )
    @RequestMapping( value="/consultarPermisos", method=RequestMethod.POST )
    public JsonReturn consultarPermisos( @RequestBody Map<String, String> params )
    {
        JsonReturn jsonReturn = new JsonReturn();
        
        try
        {
            jsonReturn = mgrNovedades.consultarPermisos( params );
        }
        catch( MgrException mgrEx )
        {
            mgrEx.printStackTrace();
            
            jsonReturn.setCodigo( -1 )
                      .setMensaje( "Error enviando los datos." );
        }
        catch( Exception ex )
        {
            ex.printStackTrace();
            
            jsonReturn.setCodigo( -1 )
                      .setMensaje( "Error enviando los datos." );
        }
        
        return jsonReturn;
    }
    
    
    @ResponseBody
    @ResponseStatus( HttpStatus.OK )
    @RequestMapping( value="/consultardiashabiles", method=RequestMethod.POST )
    public JsonReturn consultarDiasHabiles( @RequestBody Util util )
    {
        JsonReturn jsonReturn = new JsonReturn();
        
        try
        {
            jsonReturn = mgrNovedades.consultarDiasHabiles( util );
        }
        catch( MgrException mgrEx )
        {
            mgrEx.printStackTrace();
            
            jsonReturn.setCodigo( -1 )
                      .setMensaje( "Error enviando los datos." );
        }
        catch( Exception ex )
        {
            ex.printStackTrace();
            
            jsonReturn.setCodigo( -1 )
                      .setMensaje( "Error enviando los datos." );
        }
        
        return jsonReturn;
        
    }
    
    @ResponseBody
    @ResponseStatus( HttpStatus.OK )
    @RequestMapping( value="/validarestadoglobalremesa", method=RequestMethod.POST )
    public JsonReturn validarEstadoGlobalRemesaIngresoNovedad( @RequestBody Remesa remesa )
    {
        
        System.err.println("------------");
        JsonReturn jsonReturn = new JsonReturn();
        
        try
        {
            jsonReturn = mgrNovedades.validarEstadoGlobalRemesaIngresoNovedad(remesa );
        }
        catch( MgrException mgrEx )
        {
            mgrEx.printStackTrace();
            
            jsonReturn.setCodigo( -1 )
                      .setMensaje( "Error enviando los datos." );
        }
        catch( Exception ex )
        {
            ex.printStackTrace();
            
            jsonReturn.setCodigo( -1 )
                      .setMensaje( "Error enviando los datos." );
        }
        
        return jsonReturn;
        
    }
    
    @ResponseBody
    @ResponseStatus( HttpStatus.OK )
    @RequestMapping( value="/consultarIUPRemesa", method=RequestMethod.POST )
    public JsonReturn consultarIUPRemesa( @RequestBody Integer[] ListaRemesa){
        
        System.out.print(ListaRemesa[0]);
        JsonReturn jsonReturn = new JsonReturn();
            String _Respuesta = null;
            ArrayList<String> Lista = new ArrayList<String>();
            try
        {
            jsonReturn = mgrNovedades.consultarIUPRemesa( ListaRemesa );
        }
        catch( MgrException mgrEx )
        {
            mgrEx.printStackTrace();
            
            jsonReturn.setCodigo( -1 )
                      .setMensaje( "Error enviando los datos." );
        }
        catch( Exception ex )
        {
            ex.printStackTrace();
            
            jsonReturn.setCodigo( -1 )
                      .setMensaje( "Error enviando los datos." );
        }
            
            
            
            //jsonReturn.setCodigo(0).setMensaje("aqui envio algo").setList(Lista);
            return jsonReturn;
            
            
    }   
}