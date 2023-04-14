package co.com.tcc.novedadesmasivas.dao;

import co.com.tcc.novedadesmasivas.common.JsonReturn;
import co.com.tcc.novedadesmasivas.common.ParamsLista;
import co.com.tcc.novedadesmasivas.util.JsonTransformer;
import galileo.base.conexion.DAOfactory;
import galileo.base.exception.DAOException;
import galileo.base.msg.Mensajes;
import galileo.base.oracle.StructAdapter;
import galileo.base.prnacional.maestras.common.lista.ParametrosListaDos;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import oracle.jdbc.OracleCallableStatement;
import org.springframework.stereotype.Component;

/**
 *
 * @author Be-Smart_2
 */
@Component
public class ListasDAO implements IListasDAO
{
    @Override
    public JsonReturn consultar( ParamsLista paramsLista, DAOfactory dao ) throws DAOException
    {
        JsonReturn jsonReturn = new JsonReturn();
        OracleCallableStatement comd = null;
        
        ResultSet rs = null;

        try
        {
            Connection con = dao.getConexion();

            if ( paramsLista.getTipoLista().equals( "TIPO_UNIDADES" ) == true ) {
                jsonReturn = _consultarTipoUnidades( con );
            }
            
            else if ( paramsLista.getTipoLista().equals( "TIPO_DOCUMENTO" ) == true ) {
                jsonReturn = _consultarTipoDocumento( con );
            }
            
            else if ( paramsLista.getTipoLista().equals( "CUENTAS_CLIENTE" ) == true ) {
                jsonReturn = _consultarCuentasCliente( paramsLista, con );
            }
            
            else if ( paramsLista.getTipoLista().equals( "SEDES_CLIENTE" ) == true ) {
                jsonReturn = _consultarSedesCliente( paramsLista.getTercIdInt(), con );
            }
            
            else if ( paramsLista.getTipoLista().equals( "ESTADOS_UNIDAD" ) == true ) {
                jsonReturn = _consultarEstadosUnidad( con );
            }
            
            else if ( paramsLista.getTipoLista().equals( "CEOP_USUARIO" ) == true ) {
                jsonReturn = _consultarCeOpUsuario( paramsLista.getUsuario(), con );
            }
            
            else if ( paramsLista.getTipoLista().equals( "UNNE_USUARIO" ) == true ) {
                jsonReturn = _consultarUnneUsuario( paramsLista.getUsuario(), con );
            }

            return jsonReturn;
        }
        catch ( DAOException daoEx )
        {
            daoEx.printStackTrace();
            throw daoEx;
        }
        finally
        {
            if ( rs != null )
            {
                try {
                    rs.close();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            
            if ( comd != null )
            {
                try {
                    comd.close();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    @Override
    public JsonReturn obtenerListaDos( ParametrosListaDos parametrosListaDos, DAOfactory dao ) throws DAOException
    {
        JsonReturn jsonReturn = new JsonReturn();
        StructAdapter structAdapter = new StructAdapter();
        OracleCallableStatement comd = null;
            
        ResultSet rs = null;

        try
        {
            Connection con = dao.getConexion();
            Mensajes msg = new Mensajes();
            comd = (oracle.jdbc.OracleCallableStatement) con.prepareCall( "{ call UTIL_LISTAS2.obtenerListaDos_prc( ?, ?, ? ) }" );

            System.out.println("PARAMETROS: "+JsonTransformer.toJson(parametrosListaDos));
            comd.setObject( 1, parametrosListaDos );
            comd.registerOutParameter( 2, oracle.jdbc.OracleTypes.CURSOR );
            comd.registerOutParameter( 3, oracle.jdbc.OracleTypes.STRUCT, "OBJ_MENSAJES_T" );

            comd.execute();
            
            // Se le pasan los datos del objeto de retorno de mensajes
            msg.setStruct( structAdapter.Struct( con, comd.getObject(3) ) );

            if ( msg.getMens_codigo_sistema() != 0 )
            {
                System.err.println( msg.getMens_codigo_sistema() + ": " + msg.getMens_descrip_sistema() );
                
                jsonReturn.setCodigo( -1 )
                          .setMensaje( msg.getMens_descrip_usuario() );
            }
            else
            {
                rs = (ResultSet) comd.getObject(2);
                
                ParamsLista paramsLista;
                ArrayList<ParamsLista> list = new ArrayList<ParamsLista>();
                
                BigDecimal idInt;
                
                while ( rs.next() == true )
                {
                    paramsLista = new ParamsLista();
                    idInt = rs.getBigDecimal( "ID_INT" );
                    
                    paramsLista.setIdInt( idInt == null ? "" : idInt.toPlainString() );
                    paramsLista.setDescripcion( rs.getString( "DESCRIPCION" ) );
                    
                    try {
                        paramsLista.setExpansionUno(rs.getString( "EXPANSION_UNO" ) );
                         paramsLista.setProcIdInt(rs.getString( "PROC_ID_INT" ) );
                    } catch(Exception e) {
                        paramsLista.setExpansionUno("0");
                        System.err.println(e.getMessage());
                    }
                    
                    list.add( paramsLista );
                }
                
                jsonReturn.setList( list );
            }

            return jsonReturn;
        }
        catch ( SQLException ex )
        {
            ex.printStackTrace();
            throw new DAOException(ex);
        }
        catch ( Throwable te )
        {
            te.printStackTrace();
            throw new DAOException(te);
        }
        finally
        {
            if ( rs != null )
            {
                try {
                    rs.close();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            
            if ( comd != null )
            {
                try {
                    comd.close();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    private JsonReturn _consultarTipoUnidades( Connection con ) throws DAOException
    {
        JsonReturn jsonReturn = new JsonReturn();
        OracleCallableStatement comd = null;
        
        ResultSet rs = null;

        try
        {
            comd = (oracle.jdbc.OracleCallableStatement) con.prepareCall( "{ call TIPO_UNIDADES_API.listar_prc( ?, ?, ? ) }" );

            comd.registerOutParameter( 1, oracle.jdbc.OracleTypes.CURSOR );
            comd.registerOutParameter( 2, oracle.jdbc.OracleTypes.NUMBER );
            comd.registerOutParameter( 3, oracle.jdbc.OracleTypes.VARCHAR );

            comd.execute();
            
            int codError = comd.getInt(2);
            String msjError = comd.getString(3);

            if ( msjError == null ) {
                msjError = "";
            }
            
            msjError = msjError.trim();

            if ( msjError.length() > 0 )
            {
                System.err.println( codError + ": " + msjError );
                
                jsonReturn.setCodigo( -1 )
                          .setMensaje( "Error desconocido obteniendo la lista." );
            }
            else
            {
                rs = (ResultSet) comd.getObject( 1 );
                
                ParamsLista paramsLista;
                ArrayList<ParamsLista> list = new ArrayList<ParamsLista>();
                
                while ( rs.next() == true )
                {
                    paramsLista = new ParamsLista();
                    
                    paramsLista.setIdInt( rs.getString( "TIUN_ID_INT" ) );
                    paramsLista.setDescripcion( rs.getString( "TIUN_DESCRIPCION" ) );
                    
                    list.add( paramsLista );
                }
                
                jsonReturn.setList( list );
            }

            return jsonReturn;
        }
        catch ( SQLException ex )
        {
            ex.printStackTrace();
            throw new DAOException(ex);
        }
        catch ( Throwable te )
        {
            te.printStackTrace();
            throw new DAOException(te);
        }
        finally
        {
            if ( rs != null )
            {
                try {
                    rs.close();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            
            if ( comd != null )
            {
                try {
                    comd.close();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    private JsonReturn _consultarTipoDocumento( Connection con ) throws DAOException
    {
        JsonReturn jsonReturn = new JsonReturn();
        OracleCallableStatement comd = null;
        
        ResultSet rs = null;

        try
        {
            comd = (oracle.jdbc.OracleCallableStatement) con.prepareCall( "{ call TIPO_DOCUMENTO_API.listar_prc( ?, ?, ? ) }" );

            comd.registerOutParameter( 1, oracle.jdbc.OracleTypes.CURSOR );
            comd.registerOutParameter( 2, oracle.jdbc.OracleTypes.NUMBER );
            comd.registerOutParameter( 3, oracle.jdbc.OracleTypes.VARCHAR );

            comd.execute();
            
            int codError = comd.getInt(2);
            String msjError = comd.getString(3);

            if ( msjError == null ) {
                msjError = "";
            }
            
            msjError = msjError.trim();

            if ( msjError.length() > 0 )
            {
                System.err.println( codError + ": " + msjError );
                
                jsonReturn.setCodigo( -1 )
                          .setMensaje( "Error desconocido obteniendo la lista." );
            }
            else
            {
                rs = (ResultSet) comd.getObject( 1 );
                
                ParamsLista paramsLista;
                ArrayList<ParamsLista> list = new ArrayList<ParamsLista>();
                
                while ( rs.next() == true )
                {
                    paramsLista = new ParamsLista();
                    
                    paramsLista.setIdInt( rs.getString( "TIDC_ID_INT" ) );
                    paramsLista.setDescripcion( rs.getString( "TIDC_DESCRIPCION" ) );
                    
                    list.add( paramsLista );
                }
                
                jsonReturn.setList( list );
            }

            return jsonReturn;
        }
        catch ( SQLException ex )
        {
            ex.printStackTrace();
            throw new DAOException(ex);
        }
        catch ( Throwable te )
        {
            te.printStackTrace();
            throw new DAOException(te);
        }
        finally
        {
            if ( rs != null )
            {
                try {
                    rs.close();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            
            if ( comd != null )
            {
                try {
                    comd.close();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    private JsonReturn _consultarCuentasCliente( ParamsLista paramsLista, Connection con ) throws DAOException
    {
        JsonReturn jsonReturn = new JsonReturn();
        OracleCallableStatement comd = null;
        
        ResultSet rs = null;

        try
        {
            StructAdapter structAdapter = new StructAdapter();
            Mensajes msg = new Mensajes();

            comd = (oracle.jdbc.OracleCallableStatement) con.prepareCall( "{ call CONSULTAS_CLIENTES_API.CONSULTAR_TERCERO_CUENTAS_PRC( ?, ?, ?, ? ) }" );
            
            comd.setBigDecimal( 1, new BigDecimal( paramsLista.getTercIdInt() ) );
            comd.setInt( 2, paramsLista.getUnneIdInt() );
            comd.registerOutParameter( 3, oracle.jdbc.OracleTypes.CURSOR );
            comd.registerOutParameter( 4, oracle.jdbc.OracleTypes.STRUCT, "OBJ_MENSAJES_T" );

            comd.execute();

            // Se le pasan los datos del objeto de retorno de mensajes
            msg.setStruct( structAdapter.Struct( con, comd.getObject(4) ) );

            if ( msg.getMens_codigo_sistema() != 0 )
            {
                System.err.println( msg.getMens_codigo_sistema() + ": " + msg.getMens_descrip_sistema() );
                
                jsonReturn.setCodigo( -1 )
                          .setMensaje( msg.getMens_descrip_usuario() );
            }
            else
            {
                rs = (ResultSet) comd.getObject(3);
                ArrayList<ParamsLista> list = new ArrayList<ParamsLista>();
                
                while ( rs.next() == true )
                {
                    paramsLista = new ParamsLista();
                    
                    paramsLista.setIdInt( rs.getString( "CUEN_ID_INT" ) );
                    paramsLista.setDescripcion( rs.getString( "CUEN_DESCRIPCION" ) );
                    
                    list.add( paramsLista );
                }
                
                jsonReturn.setList( list );
            }

            return jsonReturn;
        }
        catch ( SQLException ex )
        {
            ex.printStackTrace();
            throw new DAOException(ex);
        }
        catch ( Throwable te )
        {
            te.printStackTrace();
            throw new DAOException(te);
        }
        finally
        {
            if ( rs != null )
            {
                try {
                    rs.close();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            
            if ( comd != null )
            {
                try {
                    comd.close();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    private JsonReturn _consultarSedesCliente( String tercIdInt, Connection con ) throws DAOException
    {
        JsonReturn jsonReturn = new JsonReturn();
        OracleCallableStatement comd = null;
        
        ResultSet rs = null;

        try
        {
            comd = (oracle.jdbc.OracleCallableStatement) con.prepareCall( "{ call SEDES_API.consultar_prc( ?, ?, ?, ? ) }" );

            comd.setBigDecimal( 1, new BigDecimal( tercIdInt ) );
            comd.registerOutParameter( 2, oracle.jdbc.OracleTypes.CURSOR );
            comd.registerOutParameter( 3, oracle.jdbc.OracleTypes.NUMBER );
            comd.registerOutParameter( 4, oracle.jdbc.OracleTypes.VARCHAR );

            comd.execute();
            
            int codError = comd.getInt(3);
            String msjError = comd.getString(4);

            if ( msjError == null ) {
                msjError = "";
            }
            
            msjError = msjError.trim();

            if ( msjError.length() > 0 )
            {
                System.err.println( codError + ": " + msjError );
                
                jsonReturn.setCodigo( -1 )
                          .setMensaje( "Error desconocido obteniendo la lista." );
            }
            else
            {
                rs = (ResultSet) comd.getObject( 2 );
                
                BigDecimal sedeIdInt;
                ParamsLista paramsLista;
                ArrayList<ParamsLista> list = new ArrayList<ParamsLista>();
                
                while ( rs.next() == true )
                {
                    paramsLista = new ParamsLista();
                    sedeIdInt = rs.getBigDecimal( "SEDE_ID_INT" );
                    
                    paramsLista.setIdInt( sedeIdInt == null ? "" : sedeIdInt.toPlainString() );
                    paramsLista.setDescripcion( rs.getString( "SEDE_NOMBRE" ) );
                    
                    list.add( paramsLista );
                }
                
                jsonReturn.setList( list );
            }

            return jsonReturn;
        }
        catch ( SQLException ex )
        {
            ex.printStackTrace();
            throw new DAOException(ex);
        }
        catch ( Throwable te )
        {
            te.printStackTrace();
            throw new DAOException(te);
        }
        finally
        {
            if ( rs != null )
            {
                try {
                    rs.close();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            
            if ( comd != null )
            {
                try {
                    comd.close();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    private JsonReturn _consultarEstadosUnidad( Connection con ) throws DAOException
    {
        JsonReturn jsonReturn = new JsonReturn();
        OracleCallableStatement comd = null;
        
        ResultSet rs = null;

        try
        {
            comd = (oracle.jdbc.OracleCallableStatement) con.prepareCall( "{ call ESTADOS_API.listarxAbreviatura_prc( ?, ?, ?, ? ) }" );

            comd.setString( 1, "IUP-IUD" );
            comd.registerOutParameter( 2, oracle.jdbc.OracleTypes.CURSOR );
            comd.registerOutParameter( 3, oracle.jdbc.OracleTypes.NUMBER );
            comd.registerOutParameter( 4, oracle.jdbc.OracleTypes.VARCHAR );

            comd.execute();
            
            int codError = comd.getInt(3);
            String msjError = comd.getString(4);

            if ( msjError == null ) {
                msjError = "";
            }
            
            msjError = msjError.trim();

            if ( msjError.length() > 0 )
            {
                System.err.println( codError + ": " + msjError );
                
                jsonReturn.setCodigo( -1 )
                          .setMensaje( "Error desconocido obteniendo la lista." );
            }
            else
            {
                rs = (ResultSet) comd.getObject(2);
                
                ParamsLista paramsLista;
                ArrayList<ParamsLista> list = new ArrayList<ParamsLista>();
                
                while ( rs.next() == true )
                {
                    paramsLista = new ParamsLista();
                    
                    paramsLista.setIdInt( rs.getString( "ESTA_ID_INT" ) );
                    paramsLista.setDescripcion( rs.getString( "ESTA_DESCRIPCION" ) );
                    
                    list.add( paramsLista );
                }
                
                jsonReturn.setList( list );
            }

            return jsonReturn;
        }
        catch ( SQLException ex )
        {
            ex.printStackTrace();
            throw new DAOException(ex);
        }
        catch ( Throwable te )
        {
            te.printStackTrace();
            throw new DAOException(te);
        }
        finally
        {
            if ( rs != null )
            {
                try {
                    rs.close();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            
            if ( comd != null )
            {
                try {
                    comd.close();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    // Obtiene los CeOps asociados al usuario.
    private JsonReturn _consultarCeOpUsuario( String usuario, Connection con ) throws DAOException
    {
        JsonReturn jsonReturn = new JsonReturn();
        OracleCallableStatement comd = null;
        
        ResultSet rs = null;

        try
        {
            StructAdapter structAdapter = new StructAdapter();
            Mensajes msg = new Mensajes();
            
            comd = (oracle.jdbc.OracleCallableStatement) con.prepareCall( "{ call WRAPPER_WLS_SECURITY.CONSULTAR_CEOP_PRC( ?, ?, ? ) }" );

            comd.setString( 1, usuario );
            comd.registerOutParameter( 2, oracle.jdbc.OracleTypes.CURSOR );
            comd.registerOutParameter( 3, oracle.jdbc.OracleTypes.STRUCT, "OBJ_MENSAJES_T" );

            comd.execute();

            // Se le pasan los datos del objeto de retorno de mensajes
            msg.setStruct( structAdapter.Struct( con, comd.getObject(3) ) );

            if ( msg.getMens_codigo_sistema() != 0 )
            {
                System.err.println( msg.getMens_codigo_sistema() + ": " + msg.getMens_descrip_sistema() );
                
                jsonReturn.setCodigo( -1 )
                          .setMensaje( msg.getMens_descrip_usuario() );
            }
            else
            {
                rs = (ResultSet) comd.getObject(2);
                
                ParamsLista paramsLista;
                ArrayList<ParamsLista> list = new ArrayList<ParamsLista>();
                
                while ( rs.next() == true )
                {
                    paramsLista = new ParamsLista();
                    
                    paramsLista.setIdInt( rs.getString( "ID_INT" ) );
                    paramsLista.setDescripcion( rs.getString( "DESCRIPCION" ) );
                    
                    list.add( paramsLista );
                }
                
                jsonReturn.setList( list );
            }

            return jsonReturn;
        }
        catch ( SQLException ex )
        {
            ex.printStackTrace();
            throw new DAOException(ex);
        }
        catch ( Throwable te )
        {
            te.printStackTrace();
            throw new DAOException(te);
        }
        finally
        {
            if ( rs != null )
            {
                try {
                    rs.close();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            
            if ( comd != null )
            {
                try {
                    comd.close();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    // Obtiene las unidades de negocio asociadas al usuario.
    private JsonReturn _consultarUnneUsuario( String usuario, Connection con ) throws DAOException
    {
        JsonReturn jsonReturn = new JsonReturn();
        OracleCallableStatement comd = null;
        
        ResultSet rs = null;

        try
        {
            StructAdapter structAdapter = new StructAdapter();
            Mensajes msg = new Mensajes();
            
            comd = (oracle.jdbc.OracleCallableStatement) con.prepareCall( "{ call WRAPPER_WLS_SECURITY.CONSULTAR_UEN_PRC( ?, ?, ? ) }" );

            comd.setString( 1, usuario );
            comd.registerOutParameter( 2, oracle.jdbc.OracleTypes.CURSOR );
            comd.registerOutParameter( 3, oracle.jdbc.OracleTypes.STRUCT, "OBJ_MENSAJES_T" );

            comd.execute();

            // Se le pasan los datos del objeto de retorno de mensajes
            msg.setStruct( structAdapter.Struct( con, comd.getObject(3) ) );

            if ( msg.getMens_codigo_sistema() != 0 )
            {
                System.err.println( msg.getMens_codigo_sistema() + ": " + msg.getMens_descrip_sistema() );
                
                jsonReturn.setCodigo( -1 )
                          .setMensaje( msg.getMens_descrip_usuario() );
            }
            else
            {
                rs = (ResultSet) comd.getObject(2);
                
                ParamsLista paramsLista;
                ArrayList<ParamsLista> list = new ArrayList<ParamsLista>();
                
                while ( rs.next() == true )
                {
                    paramsLista = new ParamsLista();
                    
                    paramsLista.setIdInt( rs.getString( "UNNE_ID_INT" ) );
                    paramsLista.setDescripcion( rs.getString( "UNNE_DESCRIPCION" ) );
                    
                    list.add( paramsLista );
                }
                
                jsonReturn.setList( list );
            }

            return jsonReturn;
        }
        catch ( SQLException ex )
        {
            ex.printStackTrace();
            throw new DAOException(ex);
        }
        catch ( Throwable te )
        {
            te.printStackTrace();
            throw new DAOException(te);
        }
        finally
        {
            if ( rs != null )
            {
                try {
                    rs.close();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            
            if ( comd != null )
            {
                try {
                    comd.close();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}