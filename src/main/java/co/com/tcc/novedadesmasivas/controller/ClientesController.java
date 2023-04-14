package co.com.tcc.novedadesmasivas.controller;

import co.com.tcc.novedadesmasivas.common.ConsultaTerceros;
import co.com.tcc.novedadesmasivas.common.CuentasTercero;
import co.com.tcc.novedadesmasivas.common.DireccionTercero;
import co.com.tcc.novedadesmasivas.common.TerceroMedioContacto;
import co.com.tcc.novedadesmasivas.common.ValueClientes;
import co.com.tcc.novedadesmasivas.common.JsonReturn;
import galileo.base.msg.Mensajes;
import galileo.base.value.Value;
import galileo.componentes.maestras.command.CmdClientes;
import galileo.componentes.maestras.dto.CuentasCliente;
import galileo.componentes.maestras.dto.DireccionesCliente;
import galileo.componentes.maestras.dto.UtilConsultaTerceros;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
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
@RequestMapping( value = "/clientes",  produces = {"application/json;charset=UTF-8", "application/x-www-form-urlencoded;charset=UTF-8"})
public class ClientesController
{
    @ResponseBody
    @ResponseStatus( HttpStatus.OK )
    @RequestMapping( value="/consultarXCriterios", method=RequestMethod.POST )
    public JsonReturn consultarXCriterios( @RequestBody ValueClientes valueClientes )
    {
        JsonReturn jsonReturn = new JsonReturn();
        
        try
        {
            CmdClientes cmdClientes = new CmdClientes();
            valueClientes.setObject( valueClientes.getClienteCriterios() );
            
            Value value = cmdClientes.consultarXCriterios( valueClientes );
            
            if ( value.getMsg() == null )
            {
                int j, sizeList;
                int size = value.getList().size();
                
                BigDecimal idInt;
                
                List<UtilConsultaTerceros> listConsultaTerc = value.getList();
                List<DireccionesCliente> listDirCliente;
                List<CuentasCliente> listCuentasCliente;
                
                ConsultaTerceros consultaTerceros;
                List<ConsultaTerceros> listConsultaTercReturn = new ArrayList<ConsultaTerceros>();
                
                DireccionTercero direccionTercero;
                List<DireccionTercero> listDirTercero;
                
                CuentasTercero cuentasTercero;
                List<CuentasTercero> listCuentasTercero;
                
                // Se debe volver a recorrer los registros, pues JavaScript no interpreta bien los BigDecimal,
                // así que deben devolverse como un String.
                for ( int i = 0; i < size; i++ )
                {
                    consultaTerceros = new ConsultaTerceros();
                    
                    idInt = listConsultaTerc.get(i).getCote_id_int();
                    consultaTerceros.setCoteIdInt( idInt == null ? "" : idInt.toPlainString() );
                    
                    idInt = listConsultaTerc.get(i).getCoti_consecutivo();
                    consultaTerceros.setCotiConsecutivo( idInt == null ? "" : idInt.toPlainString() );
                    
                    idInt = listConsultaTerc.get(i).getTerc_id_int();
                    consultaTerceros.setTercIdInt( idInt == null ? "" : idInt.toPlainString() );
                    
                    consultaTerceros.setCotiTipoIdentificacion( listConsultaTerc.get(i).getCoti_tipo_identificacion() );
                    consultaTerceros.setCotiNumeroIdentificacion( listConsultaTerc.get(i).getCoti_numero_identificacion() );
                    consultaTerceros.setCotiPrimerNombre( listConsultaTerc.get(i).getCoti_primer_nombre() );
                    consultaTerceros.setCotiSegundoNombre( listConsultaTerc.get(i).getCoti_segundo_nombre() );
                    consultaTerceros.setCotiPrimerApellido( listConsultaTerc.get(i).getCoti_primer_apellido() );
                    consultaTerceros.setCotiSegundoApellido( listConsultaTerc.get(i).getCoti_segundo_apellido() );
                    consultaTerceros.setCotiRazonSocial( listConsultaTerc.get(i).getCoti_razon_social() );
                    consultaTerceros.setCotiNombreComercial( listConsultaTerc.get(i).getCoti_nombre_comercial() );
                    consultaTerceros.setCotiDireccion( listConsultaTerc.get(i).getCoti_direccion() );
                    consultaTerceros.setCotiBarrio( listConsultaTerc.get(i).getCoti_barrio() );
                    consultaTerceros.setCotiTelefono( listConsultaTerc.get(i).getCoti_telefono() );
                    consultaTerceros.setCotiCiudad( listConsultaTerc.get(i).getCoti_ciudad() );
                    consultaTerceros.setCotiUsuario( listConsultaTerc.get(i).getCoti_usuario() );
                    
                    idInt = listConsultaTerc.get(i).getCoti_id_registro();
                    consultaTerceros.setCotiIdRegistro( idInt == null ? "" : idInt.toPlainString() );
                    
                    consultaTerceros.setTotalRegistros( listConsultaTerc.get(i).getTotal_registros() );
                    consultaTerceros.setCotiNaturaleza( listConsultaTerc.get(i).getCoti_naturaleza() );
                    
                    idInt = listConsultaTerc.get(i).getCiud_id_int();
                    consultaTerceros.setCiudIdInt( idInt == null ? "" : idInt.toPlainString() );
                    
                    idInt = listConsultaTerc.get(i).getTero_id_int();
                    consultaTerceros.setTeroIdInt( idInt == null ? "" : idInt.toPlainString() );
                    
                    consultaTerceros.setNombreCliente(
                        consultaTerceros.getCotiNumeroIdentificacion() + " - " + listConsultaTerc.get(i).getNombre_cliente()
                    );
                    
                    consultaTerceros.setCotiTipoTercero( listConsultaTerc.get(i).getCoti_tipo_tercero() );
                    consultaTerceros.setCotiSegmentoCliente( listConsultaTerc.get(i).getCoti_segmento_cliente() );
                    
                    idInt = listConsultaTerc.get(i).getTedi_id_int();
                    consultaTerceros.setTediIdInt( idInt == null ? "" : idInt.toPlainString() );
                    
                    idInt = listConsultaTerc.get(i).getTemc_id_int();
                    consultaTerceros.setTemcIdInt( idInt == null ? "" : idInt.toPlainString() );
                    
                    consultaTerceros.setTemcValor( listConsultaTerc.get(i).getTemc_valor() );
                    
                    listDirCliente = listConsultaTerc.get(i).getListaDirecciones();
                    sizeList = listDirCliente == null ? 0 : listDirCliente.size();
                    
                    listDirTercero = new ArrayList<DireccionTercero>();
                    
                    for ( j = 0; j < sizeList; j++ )
                    {
                        direccionTercero = new DireccionTercero();
                        
                        idInt = listDirCliente.get(j).getTedi_id_int();
                        direccionTercero.setTediIdInt( idInt == null ? "" : idInt.toPlainString() );
                        
                        direccionTercero.setTediDescripcion( listDirCliente.get(j).getTedi_descripcion() );
                        direccionTercero.setTediDireccionNoEstandar( listDirCliente.get(j).getTedi_direccion_no_estandar() );
                        direccionTercero.setTediDireccionEstandar( listDirCliente.get(j).getTedi_direccion_estandar() );
                        
                        idInt = listDirCliente.get(j).getDire_id_int();
                        direccionTercero.setDireIdInt( idInt == null ? "" : idInt.toPlainString() );
                        
                        direccionTercero.setDireDescripcion( listDirCliente.get(j).getDire_descripcion() );
                        
                        idInt = listDirCliente.get(j).getBarr_id_int();
                        direccionTercero.setBarrIdInt( idInt == null ? "" : idInt.toPlainString() );
                        
                        direccionTercero.setBarrNombre( listDirCliente.get(j).getBarr_nombre() );
                        
                        idInt = listDirCliente.get(j).getLoca_id_int();
                        direccionTercero.setLocaIdInt( idInt == null ? 0 : idInt.longValue() );
                        
                        direccionTercero.setLocaDescripcion( listDirCliente.get(j).getLoca_descripcion() );
                        direccionTercero.setLocaAbreviatura( listDirCliente.get(j).getLoca_abreviatura() );
                        
                        listDirTercero.add( direccionTercero );
                    }
                    
                    listCuentasCliente = listConsultaTerc.get(i).getListaCuentasCliente();
                    sizeList = listCuentasCliente == null ? 0 : listCuentasCliente.size();
                    
                    listCuentasTercero = new ArrayList<CuentasTercero>();
                    
                    for ( j = 0; j < sizeList; j++ )
                    {
                        cuentasTercero = new CuentasTercero();
                        
                        idInt = listCuentasCliente.get(j).getCuen_id_int();
                        cuentasTercero.setCuenIdInt( idInt == null ? "" : idInt.toPlainString() );
                        
                        idInt = listCuentasCliente.get(j).getTerc_id_int();
                        cuentasTercero.setTercIdInt( idInt == null ? "" : idInt.toPlainString() );
                        
                        cuentasTercero.setCuenDescripcion( listCuentasCliente.get(j).getCuen_descripcion() );
                        cuentasTercero.setCuenAbreviatura( listCuentasCliente.get(j).getCuen_abreviatura() );
                        cuentasTercero.setCuenCodigo( listCuentasCliente.get(j).getCuen_codigo() );
                        cuentasTercero.setDsCuentaConcatenada( listCuentasCliente.get(j).getDscuentaconcatenada() );
                        
                        listCuentasTercero.add( cuentasTercero );
                    }
                    
                    consultaTerceros.setListaDirecciones( listDirTercero );
                    consultaTerceros.setListaMediosContacto( _getListMediosContacto( listConsultaTerc.get(i).getListaMediosContacto() ) );
                    consultaTerceros.setListaCuentasCliente( listCuentasTercero );
                    consultaTerceros.setListaEmail( _getListMediosContacto( listConsultaTerc.get(i).getListaemail() ) );
                    consultaTerceros.setListaMediosCelulares( _getListMediosContacto( listConsultaTerc.get(i).getListaMediosCelulares() ) );
                    consultaTerceros.setListaMediosTodos( _getListMediosContacto( listConsultaTerc.get(i).getListaMediostodos() ) );
                    
                    listConsultaTercReturn.add( consultaTerceros );
                }
                
                jsonReturn.setList( listConsultaTercReturn ).setObject( value.getSize() );
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
    
    private List<TerceroMedioContacto> _getListMediosContacto( List<galileo.componentes.maestras.dto.TerceroMedioContacto> listTercMedioContacto )
    {
        int size = listTercMedioContacto == null ? 0 : listTercMedioContacto.size();
        
        TerceroMedioContacto tercMedioContacto;
        List<TerceroMedioContacto> listTercMedioContReturn = new ArrayList<TerceroMedioContacto>();

        BigDecimal idInt;
        
        for ( int i = 0; i < size; i++ )
        {
            tercMedioContacto = new TerceroMedioContacto();
            
            idInt = listTercMedioContacto.get(i).getTemc_id_int();
            tercMedioContacto.setTemcIdInt( idInt == null ? "" : idInt.toPlainString() );
            
            idInt = listTercMedioContacto.get(i).getTerc_id_int();
            tercMedioContacto.setTercIdInt( idInt == null ? "" : idInt.toPlainString() );
            
            idInt = listTercMedioContacto.get(i).getTico_id_int();
            tercMedioContacto.setTicoIdInt( idInt == null ? "" : idInt.toPlainString() );
            
            tercMedioContacto.setTemcValor( listTercMedioContacto.get(i).getTemc_valor() );
            tercMedioContacto.setTicoAbreviatura( listTercMedioContacto.get(i).getTico_abreviatura() );
            tercMedioContacto.setTicoDescripcion( listTercMedioContacto.get(i).getTico_descripcion() );

            listTercMedioContReturn.add( tercMedioContacto );
        }
        
        return listTercMedioContReturn;
    }
}