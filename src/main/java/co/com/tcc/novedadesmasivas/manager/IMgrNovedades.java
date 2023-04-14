package co.com.tcc.novedadesmasivas.manager;

import co.com.tcc.novedadesmasivas.common.JsonReturn;
import co.com.tcc.novedadesmasivas.common.IngresoNovedad;
import co.com.tcc.novedadesmasivas.common.ParametroNovedad;
import co.com.tcc.novedadesmasivas.common.SeguimientoNovedad;
import co.com.tcc.novedadesmasivas.common.NovedadUnidadRemesa;
import co.com.tcc.novedadesmasivas.common.Remesa;
import co.com.tcc.novedadesmasivas.common.RemesaLog;
import co.com.tcc.novedadesmasivas.common.Util;
import galileo.base.exception.MgrException;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author Be-Smart_2
 */
public interface IMgrNovedades
{
    public JsonReturn consultar( Remesa remesa ) throws MgrException;
    public JsonReturn consultarUnidadesRemesa( String remeIdInt ) throws MgrException;
    public JsonReturn consultarTrazabilidadUnidades( String ipidIdInt ) throws MgrException;
    public JsonReturn consultarDocumentosClienteRemesa( String remeIdInt ) throws MgrException;
    public JsonReturn consultarFormatosImpresion() throws MgrException;
    public JsonReturn exportarExcel( Remesa remesa, String tipoExportacion, String path ) throws MgrException;
    public JsonReturn guardarLog( RemesaLog[] arrRemesaLog ) throws MgrException;
    public JsonReturn consultarNovedadesUnidades( NovedadUnidadRemesa novedadUnidad ) throws MgrException;
    public JsonReturn obtenerImagenesNovedadUnidad( NovedadUnidadRemesa novedadUnidad ) throws MgrException;
    public JsonReturn grabarNovedades( IngresoNovedad ingresoNovedad ) throws MgrException;
    public JsonReturn ingresarSeguimientosMasivos( SeguimientoNovedad[] arrSeguimientos ) throws MgrException;
    public JsonReturn ejecutarNovedad( ParametroNovedad[] arrNovedades ) throws MgrException;
    public JsonReturn anularNovedad( ParametroNovedad[] arrNovedades ) throws MgrException;
    public JsonReturn consultarDiasNoHabiles() throws MgrException;
    public JsonReturn consultarPermisos( Map<String, String> params ) throws MgrException;
    public JsonReturn consultarDiasHabiles(Util util) throws MgrException;
    public JsonReturn obtenerDiaHabil(Date fecha, Integer dias) throws MgrException;
    public JsonReturn validarEstadoGlobalRemesaIngresoNovedad(Remesa remesa) throws MgrException;
    public JsonReturn consultarIUPRemesa( Remesa[] remesa ) throws MgrException;
}