package co.com.tcc.novedadesmasivas.manager;

import co.com.tcc.novedadesmasivas.common.JsonReturn;
import co.com.tcc.novedadesmasivas.common.ParamsLista;
import co.com.tcc.novedadesmasivas.dao.IListasDAO;
import co.com.tcc.novedadesmasivas.util.UsuarioLogueado;
import galileo.base.conexion.DAOfactory;
import galileo.base.exception.DAOException;
import galileo.base.exception.FactoryDAOException;
import galileo.base.exception.MgrException;
import galileo.base.prnacional.maestras.common.lista.ParametrosListaDos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Be-Smart_2
 */
@Component
public class MgrListas implements IMgrListas
{
    @Autowired
    IListasDAO listasDAO;
    
    @Override
    public JsonReturn consultar( ParamsLista paramsLista ) throws MgrException
    {
        DAOfactory dao = null;
        JsonReturn jsonReturn = new JsonReturn();

        try
        {
            dao = DAOfactory.getDAOFactory();
            dao.beginConexion();
            
            jsonReturn = listasDAO.consultar( paramsLista, dao );
            dao.commit();
        }
        catch ( FactoryDAOException fdaoe )
        {
            if ( null != dao )
            {
                try {
                    dao.rollback();
                }
                catch (Throwable t) {
                    t.printStackTrace();
                }
            }
            
            throw new MgrException( fdaoe );
        }
        catch ( DAOException daoe )
        {
            if (null != dao)
            {
                try {
                    dao.rollback();
                }
                catch ( Throwable t ) {
                    t.printStackTrace();
                }
            }

            throw new MgrException( daoe );
        }
        finally
        {
            if ( null != dao )
            {
                try {
                    dao.closeConexion();
                }
                catch ( Throwable t )
                {
                    System.err.println( "La conexión no se pudo cerrar." );
                    t.printStackTrace();
                }
            }
        }

        return jsonReturn;
    }
    
    @Override
    public JsonReturn obtenerListaDos( ParametrosListaDos parametrosListaDos ) throws MgrException
    {
        DAOfactory dao = null;
        JsonReturn jsonReturn = new JsonReturn();

        try
        {
            dao = DAOfactory.getDAOFactory();
            dao.beginConexion();
            
            parametrosListaDos.setUsuario(UsuarioLogueado.getUsuario());
            jsonReturn = listasDAO.obtenerListaDos( parametrosListaDos, dao );
            dao.commit();
        }
        catch ( FactoryDAOException fdaoe )
        {
            if ( null != dao )
            {
                try {
                    dao.rollback();
                }
                catch (Throwable t) {
                    t.printStackTrace();
                }
            }
            
            throw new MgrException( fdaoe );
        }
        catch ( DAOException daoe )
        {
            if (null != dao)
            {
                try {
                    dao.rollback();
                }
                catch ( Throwable t ) {
                    t.printStackTrace();
                }
            }

            throw new MgrException( daoe );
        }
        finally
        {
            if ( null != dao )
            {
                try {
                    dao.closeConexion();
                }
                catch ( Throwable t )
                {
                    System.err.println( "La conexión no se pudo cerrar." );
                    t.printStackTrace();
                }
            }
        }

        return jsonReturn;
    }
}