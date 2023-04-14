package co.com.tcc.novedadesmasivas.controller;

import co.com.tcc.novedadesmasivas.common.JsonReturn;
import co.com.tcc.novedadesmasivas.common.ParamsLista;
import co.com.tcc.novedadesmasivas.manager.IMgrListas;
import galileo.base.exception.MgrException;
import galileo.base.msg.Mensajes;
import galileo.base.prnacional.command.listas.CmdListas;
import galileo.base.prnacional.maestras.command.localizacion.CmdLocalizacion;
import galileo.base.prnacional.maestras.common.lista.ParametrosListaDos;
import galileo.base.value.Value;
import galileo.componentes.maestras.command.CmdListaValores;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping( value = "/listas",  produces = {"application/json;charset=UTF-8", "application/x-www-form-urlencoded;charset=UTF-8"})
public class ListasController
{
    @Autowired
    IMgrListas mgrListas;
    
    @ResponseBody
    @ResponseStatus( HttpStatus.OK )
    @RequestMapping( value="/obtenerLista", method=RequestMethod.POST )
    public JsonReturn obtenerLista( @RequestBody ParamsLista paramsLista )
    {
        JsonReturn jsonReturn = new JsonReturn();
        Value value;
        
        try
        {
            ParametrosListaDos parametrosListaDos = new ParametrosListaDos();
            
            parametrosListaDos.setTipoLista( paramsLista.getTipoLista() );
            parametrosListaDos.setUsuario( paramsLista.getUsuario() );
            parametrosListaDos.setExpansionUno( paramsLista.getExpansionUno() );
            parametrosListaDos.setExpansionDos( paramsLista.getExpansionDos() );
            parametrosListaDos.setExpansionTres( paramsLista.getExpansionTres() );
            parametrosListaDos.setExpansionCuatro( paramsLista.getExpansionCuatro() );
            
            CmdListas cmdListas = new CmdListas();
            value = cmdListas.obtenerListaDos( parametrosListaDos );
            
            if ( value.getMsg() == null )
            {
                ArrayList<Object> listReturn = new ArrayList<Object>();
                ArrayList<ParametrosListaDos> list = (ArrayList<ParametrosListaDos>) value.getList();
                
                int size = list.size();
                
                // Toca hacer este recorrido, por problemas con la presentación del BigDecimal en JavaScript.
                for ( int i = 0; i < size; i++ )
                {
                    paramsLista = new ParamsLista();
                    
                    paramsLista.setIdInt( list.get( i ).getIdInt().toPlainString() );
                    paramsLista.setDescripcion( list.get( i ).getDescripcion() );
                    
                    listReturn.add( paramsLista );
                }
                
                jsonReturn.setList( listReturn );
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
    @RequestMapping( value="/listarCentrosOperacion", method=RequestMethod.GET )
    public JsonReturn listarCentrosOperacion()
    {
        JsonReturn jsonReturn = new JsonReturn();
        Value value;
        
        try
        {
            CmdLocalizacion cmdLocalizacion = new CmdLocalizacion();
            value = cmdLocalizacion.listarCentrosOperacion();
            
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
    @RequestMapping( value="/obtenerListaStandard/{abreviatura}", method=RequestMethod.GET )
    public JsonReturn obtenerListaStandard( @PathVariable("abreviatura") String abreviatura )
    {
        JsonReturn jsonReturn = new JsonReturn();
        Value value;
        
        try
        {
            CmdListaValores cmsListaValores = new CmdListaValores();
            value = cmsListaValores.listar( abreviatura );
            
            if ( value.getMsg() == null ) {
                jsonReturn.setList( value.getList() );
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
    @RequestMapping( value="/consultar", method=RequestMethod.POST )
    public JsonReturn consultar( @RequestBody ParamsLista paramsLista )
    {
        JsonReturn jsonReturn = new JsonReturn();
        
        try
        {
            jsonReturn = mgrListas.consultar( paramsLista );
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
    @RequestMapping( value="/obtenerListaDos", method=RequestMethod.POST )
    public JsonReturn obtenerListaDos( @RequestBody ParametrosListaDos parametrosListaDos )
    {
        JsonReturn jsonReturn = new JsonReturn();
        
        try
        {
            jsonReturn = mgrListas.obtenerListaDos( parametrosListaDos );
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
}