package co.com.tcc.novedadesmasivas.controller;

import co.com.tcc.novedadesmasivas.common.JsonReturn;
import co.com.tcc.novedadesmasivas.common.Util;
import co.com.tcc.novedadesmasivas.manager.IMgrNovedades;
import galileo.base.exception.MgrException;
import galileo.base.msg.Mensajes;
import galileo.base.prnacional.command.listas.CmdListas;
import galileo.base.prnacional.maestras.command.planeacionviaje.CmdPlaneacionViaje;
import galileo.base.prnacional.maestras.command.usuarioservlet.CmdUsuarioServlet;
import galileo.base.value.IValue;
import java.sql.Date;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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


@RestController
@RequestMapping( value = "/util",  produces = {"application/json;charset=UTF-8", "application/x-www-form-urlencoded;charset=UTF-8"})
public class UtilController {
    
    private static final Logger logger = Logger.getLogger(UtilController.class.getName());

    
    @Autowired
    private IMgrNovedades mgrNovedades;
    
    @ResponseBody
    @ResponseStatus( HttpStatus.OK )
    @RequestMapping( value="/obtenerParametrosSistema", method=RequestMethod.POST )
    public JsonReturn obtenerParametrosSistema( @RequestBody String[] nombreParametros )
    {
        JsonReturn jsonReturn = new JsonReturn();
        IValue value;
        
        try
        {
            CmdListas cmdListas = new CmdListas();
            value = cmdListas.obtenerParametrosSistema( nombreParametros );
            
            if ( value.getMsg() == null ) {
                jsonReturn.setObject( value.getObject() );
            }
            else
            {
                Mensajes msg = (Mensajes) value.getMsg();
                jsonReturn.setCodigo( -1 )
                          .setMensaje( msg.getMens_descrip_usuario() );
                
                System.err.println( msg.getMens_descrip_sistema() );
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
    @RequestMapping( value="/traerUsuario", method=RequestMethod.GET )
    public JsonReturn traerUsuario()
    {
        JsonReturn jsonReturn = new JsonReturn();
        IValue value;
        
        try
        {
            CmdUsuarioServlet cmdUsuarioServlet = new CmdUsuarioServlet();
            value = cmdUsuarioServlet.usuarioServlet();
            
            if ( value.getMsg() == null ) {
                jsonReturn.setList( (ArrayList<Object>) value.getList() );
            }
            else
            {
                Mensajes msg = (Mensajes) value.getMsg();
                jsonReturn.setCodigo( -1 )
                          .setMensaje( msg.getMens_descrip_usuario() );
                
                System.err.println( msg.getMens_descrip_sistema() );
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
    @RequestMapping( value="/obtenerFechaActual/{fechaHora}", method=RequestMethod.GET )
    public JsonReturn obtenerFechaActual( @PathVariable("fechaHora") boolean fechaHora )
    {
        JsonReturn jsonReturn = new JsonReturn();
        IValue value;
        
        try
        {
            CmdPlaneacionViaje cmdPlaneacionViaje = new CmdPlaneacionViaje();
            value = cmdPlaneacionViaje.obtenerFechaActual( fechaHora );
            
            if ( value.getMsg() == null )
            {
                jsonReturn.setObject(
                    Util.formatDate( (Date) value.getObject(), "dd/MM/yyyy HH:mm" )
                );
            }
            else
            {
                Mensajes msg = (Mensajes) value.getMsg();
                jsonReturn.setCodigo( -1 )
                          .setMensaje( msg.getMens_descrip_usuario() );
                
                System.err.println( msg.getMens_descrip_sistema() );
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
    @RequestMapping( value="/diahabil", params = {"fecha", "dias"}, method=RequestMethod.GET )
    public JsonReturn obtenerDiaHabil( @RequestParam("fecha") Date fecha, @RequestParam("dias") Integer dias ) {
        
        JsonReturn jsonReturn = new JsonReturn();
        
        try {
            
            jsonReturn = mgrNovedades.obtenerDiaHabil(fecha, dias);
            
        } catch (MgrException ex) {
            
            jsonReturn.setCodigo( -1 ).setMensaje( "Error calculando la fecha haábil." );
            logger.log(Level.SEVERE, "Error calculando la fecha haábil", ex);
            
        }
        
        return jsonReturn;
    }
    
}