package co.com.tcc.novedadesmasivas.controller;

import co.com.tcc.novedadesmasivas.common.JsonReturn;
import galileo.base.msg.Mensajes;
import galileo.componentes.maestras.command.CmdUnidadNegocios;
import galileo.base.value.IValue;
import java.util.ArrayList;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Be-Smart_2
 */
@RestController
@RequestMapping( value = "/unidadesnegocio",  produces = {"application/json;charset=UTF-8", "application/x-www-form-urlencoded;charset=UTF-8"})
public class UnidadesNegocioController
{
    @ResponseBody
    @ResponseStatus( HttpStatus.OK )
    @RequestMapping( value="/obtenerUnidades", method=RequestMethod.GET )
    public JsonReturn obtenerUnidades()
    {
        JsonReturn jsonReturn = new JsonReturn();
        IValue value;
        
        try
        {
            CmdUnidadNegocios cmdUnidadNegocio = new CmdUnidadNegocios();
            value = cmdUnidadNegocio.listarSeguro( null, null, null );
            
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
}