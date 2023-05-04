package co.com.tcc.novedadesmasivas.dao;

import co.com.tcc.novedadesmasivas.common.JsonReturn;
import co.com.tcc.novedadesmasivas.common.ParametroNovedad;
import co.com.tcc.novedadesmasivas.common.SeguimientoNovedad;
import co.com.tcc.novedadesmasivas.common.NovedadUnidadRemesa;
import co.com.tcc.novedadesmasivas.common.Remesa;
import co.com.tcc.novedadesmasivas.common.RemesaLog;
import co.com.tcc.novedadesmasivas.common.Util;
import galileo.base.conexion.DAOfactory;
import galileo.base.exception.DAOException;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author Be-Smart_2
 */
public interface INovedadesDAO
{
    public JsonReturn consultar( Remesa remesa, DAOfactory dao ) throws DAOException;
    public JsonReturn consultarExcel( Remesa remesa, boolean consultarUnidades, String nombreArchivo, DAOfactory dao ) throws DAOException;
    public JsonReturn consultarUnidadesRemesa( String remeIdInt, DAOfactory dao ) throws DAOException;
    public JsonReturn consultarTrazabilidadUnidades( String ipidIdInt, DAOfactory dao ) throws DAOException;
    public JsonReturn consultarDocumentosClienteRemesa( String remeIdInt, DAOfactory dao ) throws DAOException;
    public JsonReturn consultarFormatosImpresion( DAOfactory dao ) throws DAOException;
    public JsonReturn guardarLog( RemesaLog remesaLog, DAOfactory dao ) throws DAOException;
    public JsonReturn consultarNovedadesUnidades( NovedadUnidadRemesa novedadUnidad, DAOfactory dao ) throws DAOException;
    public JsonReturn grabarNovedad( Remesa remesa, DAOfactory dao ) throws DAOException;
    public JsonReturn ingresarSeguimiento( SeguimientoNovedad seguimiento, DAOfactory dao ) throws DAOException;
    public JsonReturn ejecutarNovedad( ParametroNovedad novedad, DAOfactory dao ) throws DAOException;
    public JsonReturn anularNovedad( ParametroNovedad novedad, DAOfactory dao ) throws DAOException;
    public JsonReturn consultarDiasNoHabiles( DAOfactory dao ) throws DAOException;
    public JsonReturn consultarPermisos( Map<String, String> params, DAOfactory dao ) throws DAOException;
    public JsonReturn consultarDiasHabiles(Util util,DAOfactory dao) throws DAOException;
    public JsonReturn obtenerDiaHabil(Date fecha, Integer dias, DAOfactory dao) throws DAOException;
    public JsonReturn validarEstadoGlobalRemesaIngresoNovedad(Remesa remesa, DAOfactory dao ) throws DAOException;
    public JsonReturn consultarIUPRemesa(String remesa, DAOfactory dao ) throws DAOException;
}