package co.com.tcc.novedadesmasivas.manager;

import co.com.tcc.novedadesmasivas.common.JsonReturn;
import co.com.tcc.novedadesmasivas.common.ParamsLista;
import galileo.base.exception.MgrException;
import galileo.base.prnacional.maestras.common.lista.ParametrosListaDos;

/**
 *
 * @author Be-Smart_2
 */
public interface IMgrListas
{
    public JsonReturn consultar( ParamsLista paramsLista ) throws MgrException;
    public JsonReturn obtenerListaDos( ParametrosListaDos parametrosListaDos ) throws MgrException;
}