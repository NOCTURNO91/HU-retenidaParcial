package co.com.tcc.novedadesmasivas.dao;

import co.com.tcc.novedadesmasivas.common.JsonReturn;
import co.com.tcc.novedadesmasivas.common.ParamsLista;
import galileo.base.conexion.DAOfactory;
import galileo.base.exception.DAOException;
import galileo.base.prnacional.maestras.common.lista.ParametrosListaDos;

/**
 *
 * @author Be-Smart_2
 */
public interface IListasDAO
{
    public JsonReturn consultar( ParamsLista paramsLista, DAOfactory dao ) throws DAOException;
    public JsonReturn obtenerListaDos( ParametrosListaDos parametrosListaDos, DAOfactory dao ) throws DAOException;
}