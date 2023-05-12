package co.com.tcc.novedadesmasivas.dao;

import co.com.tcc.novedadesmasivas.common.FormatoImpresion;
import co.com.tcc.novedadesmasivas.common.JsonReturn;
import co.com.tcc.novedadesmasivas.common.ParametroNovedad;
import co.com.tcc.novedadesmasivas.common.SeguimientoNovedad;
import co.com.tcc.novedadesmasivas.common.DocumentoCliente;
import co.com.tcc.novedadesmasivas.common.NovedadUnidadRemesa;
import co.com.tcc.novedadesmasivas.common.Remesa;
import co.com.tcc.novedadesmasivas.common.RemesaLog;
import co.com.tcc.novedadesmasivas.common.TrazaUnidadRemesa;
import co.com.tcc.novedadesmasivas.common.UnidadRemesa;
import co.com.tcc.novedadesmasivas.common.Util;
import galileo.base.conexion.DAOfactory;
import galileo.base.exception.DAOException;
import galileo.base.msg.Mensajes;
import galileo.base.oracle.StructAdapter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import org.springframework.stereotype.Component;

/**
 *
 * @author Be-Smart_2
 */
@Component
public class NovedadesDAO implements INovedadesDAO {

    @Override
    public JsonReturn consultar(Remesa remesa, DAOfactory dao) throws DAOException {
        JsonReturn jsonReturn = new JsonReturn();
        StructAdapter structAdapter = new StructAdapter();
        OracleCallableStatement comd = null;

        ResultSet rs = null;

        try {
            Connection con = dao.getConexion();
            Mensajes msg = new Mensajes();

            comd = (oracle.jdbc.OracleCallableStatement) con.prepareCall("{ call WRAPPER_NOVEDADES_API.CONSULTAR_NOV_MASIVAS_PRC( ?, ?, ?, ? ) }");

            comd.setObject(1, remesa, oracle.jdbc.OracleTypes.STRUCT);
            comd.setString(2, remesa.getIdUnidadesNegocio());
            comd.registerOutParameter(3, oracle.jdbc.OracleTypes.CURSOR);
            comd.registerOutParameter(4, oracle.jdbc.OracleTypes.STRUCT, "OBJ_MENSAJES_T");
            // comd.registerOutParameter( 5, oracle.jdbc.OracleTypes.VARCHAR );

            comd.execute();

            // System.err.println( comd.getString(5) );
            // Se le pasan los datos del objeto de retorno de mensajes
            msg.setStruct(structAdapter.Struct(con, comd.getObject(4)));

            if (msg.getMens_codigo_sistema() != 0) {
                System.err.println(msg.getMens_codigo_sistema() + ": " + msg.getMens_descrip_sistema());

                jsonReturn.setCodigo(-1)
                        .setMensaje(msg.getMens_descrip_usuario());
            } else {
                rs = (ResultSet) comd.getObject(3);
                ArrayList<Remesa> list = new ArrayList<Remesa>();

                int pos = 0;
                int numUnidades;
                int totalUnidades = 0;
                int totalRegistros = -1;

                BigDecimal idInt;
                Map<String, Integer> hashReturn = new HashMap<String, Integer>();

                if (rs != null) {

                    while (rs.next() == true) {

                        remesa = new Remesa();

                        numUnidades = rs.getInt("UNIDADES_REMESA");
                        totalUnidades += numUnidades;

                        remesa.setPos(pos);

                        idInt = rs.getBigDecimal("CANO_ID_INT");
                        remesa.setCanoIdInt(idInt == null ? "0" : idInt.toPlainString());

                        idInt = rs.getBigDecimal("ID_REMESA");
                        remesa.setRemeIdInt(idInt == null ? "0" : idInt.toPlainString());

                        remesa.setNumRemesa(rs.getString("NUMERO_REMESA"));
                        remesa.setUnidadNegocio(rs.getString("UNIDAD_NEGOCIO"));
                        remesa.setFechaRemesa(rs.getString("FECHA"));
                        remesa.setUnidadesRemesa(numUnidades);
                        remesa.setEstadoRemesa(rs.getString("ESTADO_REMESA"));
                        remesa.setEsonIdInt(rs.getInt("ESON_ID_INT"));
                        remesa.setTipoTransporte(rs.getString("TIPO_TRANSPORTE"));
                        remesa.setTipoServicioOperativo(rs.getString("TIPO_SERV_OPERATIVO"));
                        remesa.setCeOpOrigen(rs.getString("CEOP_ORIGEN"));
                        remesa.setCiudadOrigen(rs.getString("CIUDAD_ORIGEN"));
                        remesa.setIdentificacionRemitente(rs.getString("IDENTIFICACION_REMITENTE"));
                        remesa.setNombreRemitente(rs.getString("REMITENTE"));
                        remesa.setSedeRemitente(rs.getString("SEDE_REMITENTE"));
                        remesa.setDireccionRemitente(rs.getString("DIRECCION_REMITENTE"));
                        remesa.setTelefonoRemitente(rs.getString("TELEFONO_REMITENTE"));
                        remesa.setCeOpDestino(rs.getString("CEOP_DESTINO"));
                        remesa.setCiudadDestino(rs.getString("CIUDAD_DESTINO"));
                        remesa.setIdentificacionDestinatario(rs.getString("IDENTIFICACION_DESTINATARIO"));
                        remesa.setNombreDestinatario(rs.getString("NOMBRE_DESTINATARIO"));
                        remesa.setSedeDestinatario(rs.getString("SEDE_DESTINATARIO"));
                        remesa.setDireccionDestinatario(rs.getString("DIRECCION_DESTINATARIO"));
                        remesa.setTelefonoDestinatario(rs.getString("TELEFONO_DESTINATARIO"));
                        remesa.setPesoReal(rs.getString("PESO_REAL"));
                        remesa.setPesoVolumen(rs.getString("PESO_VOLUMEN"));
                        remesa.setVehiculo(rs.getString("MOVIL"));
                        remesa.setRuta(rs.getString("RUTA"));
                        remesa.setViaje(rs.getString("VIAJE"));
                        remesa.setPosicion(rs.getString("POSICION"));
                        remesa.setObservaciones(rs.getString("OBSERVACIONES"));
                        remesa.setTieneNovedad(rs.getInt("COD_NOVEDAD") > 0);
                        remesa.setFechaRemesaDos(rs.getString("FECHA_REM_2"));
                        
                        if( rs.getInt("UNNE_ID_INT") == 2 ){
                            remesa.setNovedadesRezagos(rs.getInt("NOVEDADES_REZAGOS_REMESA"));
                        }else{
                            remesa.setNovedadesRezagos(0);
                        };
                        remesa.setSeguimientosUltimNovedad(rs.getInt("SEGUIM_ULT_NOV"));
                        
                        remesa.setCumplido(rs.getString("CUMPLIDO"));
                        remesa.setCuenta(rs.getString("CUENTA"));
                        remesa.setUsuario(rs.getString("USUARIO"));
                        remesa.setTieneNovedadUnidad(rs.getInt("NOVEDAD_UND") > 0);
                        remesa.setFormaPago(rs.getString("FORMA_PAGO"));
                        remesa.setConsecutivoNovedad(rs.getString("CONSECUTIVO_NOVEDAD"));
                        remesa.setViajeLocal(rs.getString("ORSE_VIAJE"));
                        remesa.setRutaLocal(rs.getString("RUTA_ROUTE_DESCRIPTION"));
                        remesa.setNumeroRemesaSolucion(rs.getString("CARS_NUMERO_REMESA"));
                        remesa.setTipoDeSolucion(rs.getString("CASE_TIPO_SOLUCION"));
                        remesa.setFechaProgramacionLocal(rs.getString("FECHA_PROGRAMACION_LOCAL"));
                        remesa.setFechaProgramacionNacional(rs.getString("FECHA_PROGRAMACION_NAL"));
                        remesa.setVan(rs.getString("VAN"));
                        remesa.setCodEstadoNovedad(rs.getString("COD_ESTADO_NOVEDAD"));
                        remesa.setIdEstadoNovedad(rs.getInt("ID_ESTADO_NOVEDAD"));
                        remesa.setEstadoNovedad(rs.getString("ESTADO_NOVEDAD"));
                        remesa.setProcIdInt(rs.getInt("ID_PROCESO_REMESA"));
                        remesa.setDescProceso(rs.getString("PROCESO"));
                        //remesa.setDescTipoUnidad( rs.getString( "TIPO_UNIDAD" ) );
                        remesa.setCountProcesosAsoc(rs.getString("COUNT_PROCESOS_ASOC"));
                        // 
                        //remesa.setGrupoNovedad( rs.getString( "GRUPO_NOV_PRINCIPAL" ) );
                        //remesa.setFechaNovedad( rs.getString( "FECHA_NOVEDAD" ) );
                        //remesa.setNumeroSeguimientos( rs.getInt( "NRO_SEGUIMIENTOS" ) );
                        //remesa.setNovedadComplemento( rs.getString( "NOVEDAD_COMPLEMENTO" ) );
                        //remesa.setNombreCeOpReporta( rs.getString( "NOMBRE_CEOP_PLANTEA" ) );
                        //remesa.setCanalPlantea( rs.getString( "CANAL_PLANTEA" ) );
                        remesa.setUsuarioPlantea(rs.getString("USUARIO_PLANTEAMIENTO"));
                        //remesa.setObservacionesNovedad( rs.getString( "OBSERVACIONES_NOVEDAD" ) );
                        remesa.setNombreProceso(rs.getString("PROCESO"));
                        //remesa.setDescPrioridad( rs.getString( "PRIORIDAD" ) );
                        //remesa.setImputabilidad( rs.getString( "IMPUTABILIDAD" ) );
                        //remesa.setSolucionNovedad( rs.getString( "SOLUCION_NOVEDAD" ) );
                        //remesa.setCeOpSoluciona( rs.getString( "CEOP_SOLUCIONA" ) );
                        //remesa.setFechaFinSolucion( rs.getString( "FECHA_SOLUCION" ) );
                        //remesa.setUsuarioSoluciona( rs.getString( "USAURIO_SOLUCIONA" ) );
                        //remesa.setClienteSoluciona( rs.getString( "CLIENTE_SOLUCIONA" ) );
                        //remesa.setDescTipoSolucion( rs.getString( "DESC_TIPO_SOLUCION" ) );
                        //remesa.setCanalSoluciona( rs.getString( "CANAL_SOLUCIONA" ) );
                        //remesa.setTipoRemesaSolucion( rs.getString( "TIPO_REMESA_SOLUCION" ) );
                        //remesa.setUltimoSeguimiento( rs.getString( "ULTIMO_SEGUIMIENTO" ) );
                        //remesa.setCeOpUltimoSeguimiento( rs.getString( "CEOP_ULTIMO_SEGUIMIENTO" ) );
                        //remesa.setFechaUltimoSeguimiento( rs.getString( "FE_ULTIMO_SEGUIMIENTO" ) );
                        //remesa.setNumRemesaSolucion( rs.getString( "NRO_REMESA_SOLUCION" ) );
                        //remesa.setCanoObservEjecucion( rs.getString( "CANO_OBVS_EJECUCION" ) );
                        //remesa.setNombreCeOpEjecuta( rs.getString( "CEOP_EJECUTA" ) );
                        //remesa.setFechaEjecucion( rs.getString( "FECHA_EJECUCION" ) );
                        //remesa.setUsuarioEjecucion( rs.getString( "USUARIO_EJECUCION" ) );
                        //remesa.setDescCausalNov( rs.getString( "CAUSAL_NOV" ) );
                        //remesa.setFechaFinReofrecimiento( rs.getString( "FECHA_REOFRECIMIENTO" ) );
                        //remesa.setCierrePorProcedimiento( rs.getString( "CIERRE_POR_PROCEDIMIENTO" ) );
                        remesa.setTipoCobertura(rs.getString("TIPO_COBERTURA"));
                        remesa.setUnidadesNovedad(rs.getInt("UNIDADES_NOVEDAD"));
                        remesa.setMovilLocal(rs.getString("MOVIL"));//POSICION
                        remesa.setIdTipoServicioEspecial(rs.getInt("ID_TIPO_SERVICIO_ESPECIAL"));
                        remesa.setCodigoNovedadComplemento(rs.getInt("CODIGO_NOVEDAD_COMPLEMENTO"));
                        //System.err.println("--DATOS INFORMACI¬o->" + rs.getString( "POSICION" ));
                        remesa.setDescLicencia(rs.getString("LICENCIA"));

                        //remesa.setReex(rs.getString("REEXPEDIDA"));
                        if (totalRegistros == -1) {
                            totalRegistros = rs.getInt("TOTAL_REGISTROS");
                        }

                        list.add(remesa);
                        pos++;
                    }
                }

                hashReturn.put("totalUnidades", totalUnidades);
                hashReturn.put("totalRegistros", totalRegistros);

                jsonReturn.setList(list)
                        .setObject(hashReturn);
            }

            return jsonReturn;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException(ex);
        } catch (Throwable te) {
            te.printStackTrace();
            throw new DAOException(te);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            if (comd != null) {
                try {
                    comd.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public JsonReturn consultarExcel(Remesa remesa, boolean consultarUnidades, String nombreArchivo, DAOfactory dao) throws DAOException {
        JsonReturn jsonReturn = new JsonReturn();
        StructAdapter structAdapter = new StructAdapter();
        OracleCallableStatement comd = null;

        ResultSet rs = null;

        try {
            Connection con = dao.getConexion();
            Mensajes msg = new Mensajes();

            comd = (oracle.jdbc.OracleCallableStatement) con.prepareCall("{ call WRAPPER_NOVEDADES_API.CONSULTAR_NOV_MASIVAS_PRC( ?, ?, ?, ? ) }");

            comd.setObject(1, remesa, oracle.jdbc.OracleTypes.STRUCT);
            comd.setString(2, remesa.getIdUnidadesNegocio());
            comd.registerOutParameter(3, oracle.jdbc.OracleTypes.CURSOR);
            comd.registerOutParameter(4, oracle.jdbc.OracleTypes.STRUCT, "OBJ_MENSAJES_T");

            comd.execute();

            // Se le pasan los datos del objeto de retorno de mensajes
            msg.setStruct(structAdapter.Struct(con, comd.getObject(4)));

            if (msg.getMens_codigo_sistema() != 0) {
                System.err.println(msg.getMens_codigo_sistema() + ": " + msg.getMens_descrip_sistema());

                jsonReturn.setCodigo(-1)
                        .setMensaje(msg.getMens_descrip_usuario());
            } else {
                rs = (ResultSet) comd.getObject(3);

                int numRegs = 0;
                String datos;

                while (rs.next() == true) {
                    numRegs++;

                    datos = _validarDato(rs.getString("NUMERO_REMESA")) + "¬"
                            + rs.getInt("UNIDADES_REMESA") + "¬"
                            + _validarDato(rs.getString("UNIDAD_NEGOCIO")) + "¬"
                            + _validarDato(rs.getString("LICENCIA")) + "¬"
                            + _validarDato(rs.getString("FECHA")) + "¬"
                            + _validarDato(rs.getString("ESTADO_REMESA")) + "¬"
                            + _validarDato(rs.getString("PROCESO")) + "¬"
                            + _validarDato(rs.getString("CONSECUTIVO_NOVEDAD")) + "¬"
                            + _validarDato(rs.getString("ESTADO_NOVEDAD")) + "¬"
                            + _validarDato(rs.getString("FORMA_PAGO")) + "¬"
                            + _validarDato(rs.getString("CUENTA")) + "¬"
                            + _validarDato(rs.getString("TIPO_TRANSPORTE")) + "¬"
                            + _validarDato(rs.getString("TIPO_SERV_OPERATIVO")) + "¬"
                            + _validarDato(rs.getString("CEOP_ORIGEN")) + "¬"
                            + _validarDato(rs.getString("CIUDAD_ORIGEN")) + "¬"
                            + _validarDato(rs.getString("IDENTIFICACION_REMITENTE")) + "¬"
                            + _validarDato(rs.getString("REMITENTE")) + "¬"
                            + _validarDato(rs.getString("SEDE_REMITENTE")) + "¬"
                            + _validarDato(rs.getString("DIRECCION_REMITENTE")) + "¬"
                            + _validarDato(rs.getString("TELEFONO_REMITENTE")) + "¬"
                            + _validarDato(rs.getString("CEOP_DESTINO")) + "¬"
                            + _validarDato(rs.getString("CIUDAD_DESTINO")) + "¬"
                            + _validarDato(rs.getString("TIPO_COBERTURA")) + "¬"
                            + _validarDato(rs.getString("IDENTIFICACION_DESTINATARIO")) + "¬"
                            + _validarDato(rs.getString("NOMBRE_DESTINATARIO")) + "¬"
                            + _validarDato(rs.getString("SEDE_DESTINATARIO")) + "¬"
                            + _validarDato(rs.getString("DIRECCION_DESTINATARIO")) + "¬"
                            + _validarDato(rs.getString("TELEFONO_DESTINATARIO")) + "¬"
                            + //_validarDato( rs.getString( "TIPO_UNIDAD" ) ) + "¬" +
                            _validarDato(rs.getString("PESO_REAL")) + "¬"
                            + _validarDato(rs.getString("PESO_VOLUMEN")) + "¬"
                            + _validarDato(rs.getString("OBSERVACIONES")) + "¬"
                            + _validarDato(rs.getString("USUARIO")) + "¬"
                            + _validarDato(rs.getString("VIAJE")) + "¬"
                            + _validarDato(rs.getString("VAN")) + "¬"
                            + _validarDato(rs.getString("FECHA_PROGRAMACION_NAL")) + "¬"
                            + _validarDato(rs.getString("MOVIL")) + "¬"
                            + _validarDato(rs.getString("ORSE_VIAJE")) + "¬"
                            + _validarDato(rs.getString("RUTA_ROUTE_DESCRIPTION")) + "¬"
                            + _validarDato(rs.getString("FECHA_PROGRAMACION_LOCAL"));

                    if (consultarUnidades == true) {
                        jsonReturn = _consultarUnidadesRemesaExcel(rs.getBigDecimal("ID_REMESA"), datos, nombreArchivo, dao);

                        if (jsonReturn.getCodigo() != 0) {
                            break;
                        }
                    } else {
                        datos += "\n";
                        _escribirArchivo(nombreArchivo, datos);
                    }
                }

                jsonReturn.setObject(numRegs > 0);
            }

            return jsonReturn;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException(ex);
        } catch (Throwable te) {
            te.printStackTrace();
            throw new DAOException(te);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            if (comd != null) {
                try {
                    comd.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private JsonReturn _consultarUnidadesRemesaExcel(BigDecimal remeIdInt, String datosRemesa, String nombreArchivo, DAOfactory dao) throws DAOException {
        JsonReturn jsonReturn = new JsonReturn();
        StructAdapter structAdapter = new StructAdapter();
        OracleCallableStatement comd = null;

        ResultSet rs = null;

        try {
            Connection con = dao.getConexion();
            Mensajes msg = new Mensajes();

            comd = (oracle.jdbc.OracleCallableStatement) con.prepareCall("{ call WRAPPER_IMPR_ETIQUETAS_API.CONSULTA_UNDS_REMESA_PRC( ?, ?, ? ) }");

            comd.setBigDecimal(1, remeIdInt);
            comd.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
            comd.registerOutParameter(3, oracle.jdbc.OracleTypes.STRUCT, "OBJ_MENSAJES_T");

            comd.execute();

            // Se le pasan los datos del objeto de retorno de mensajes
            msg.setStruct(structAdapter.Struct(con, comd.getObject(3)));

            if (msg.getMens_codigo_sistema() != 0) {
                System.err.println(msg.getMens_codigo_sistema() + ": " + msg.getMens_descrip_sistema());

                jsonReturn.setCodigo(-1)
                        .setMensaje(msg.getMens_descrip_usuario());
            } else {
                rs = (ResultSet) comd.getObject(2);
                String datos;

                while (rs.next() == true) {
                    datos = datosRemesa + "¬"
                            + _validarDato(rs.getString("CODIGO_UNIDAD")) + "¬"
                            + _validarDato(rs.getString("TIPO_UNIDAD")) + "¬"
                            + _validarDato(rs.getString("CLASE_EMPAQUE")) + "¬"
                            + _validarDato(rs.getString("ESTADO_ACTUAL_UNIDAD")) + "¬"
                            + _validarDato(rs.getString("TIPO_UBICAICON_ACTUAL")) + "¬"
                            + _validarDato(rs.getString("COD_BARRAS_UB_ACTUAL")) + "¬"
                            + _validarDato(rs.getString("UBICACION_ACTUAL")) + "¬"
                            + _validarDato(rs.getString("CEOP_UB_ACTUAL")) + "¬"
                            + _validarDato(rs.getString("MOVIL_ENTREGA")) + "¬"
                            + _validarDato(rs.getString("CERTIFICACION")) + "¬"
                            + _validarDato("" + rs.getDouble("P_REAL")) + "¬"
                            + _validarDato("" + rs.getDouble("P_VOL")) + "¬"
                            + _validarDato("" + rs.getDouble("ALTO")) + "¬"
                            + _validarDato("" + rs.getDouble("ANCHO")) + "¬"
                            + _validarDato("" + rs.getDouble("LARGO")) + "\n";

                    _escribirArchivo(nombreArchivo, datos);
                }
            }

            return jsonReturn;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException(ex);
        } catch (Throwable te) {
            te.printStackTrace();
            throw new DAOException(te);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            if (comd != null) {
                try {
                    comd.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void _escribirArchivo(String nombreArchivo, String content) throws IOException {
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;

        try {
            File file = new File(nombreArchivo);

            fileWriter = new FileWriter(file, true);
            bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(content);
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
            throw ioEx;
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }

                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException ioExc) {
                ioExc.printStackTrace();
            }
        }
    }

    private String _validarDato(String dato) {
        if (dato == null) {
            return " ";
        }

        return dato.replace("\n", " ").replace("\r", " ");
    }

    @Override
    public JsonReturn consultarUnidadesRemesa(String remeIdInt, DAOfactory dao) throws DAOException {
        JsonReturn jsonReturn = new JsonReturn();
        StructAdapter structAdapter = new StructAdapter();
        OracleCallableStatement comd = null;

        ResultSet rs = null;

        try {
            Connection con = dao.getConexion();
            Mensajes msg = new Mensajes();

            comd = (oracle.jdbc.OracleCallableStatement) con.prepareCall("{ call WRAPPER_IMPR_ETIQUETAS_API.CONSULTA_UNDS_REMESA_PRC( ?, ?, ? ) }");

            comd.setBigDecimal(1, new BigDecimal(remeIdInt));
            comd.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
            comd.registerOutParameter(3, oracle.jdbc.OracleTypes.STRUCT, "OBJ_MENSAJES_T");

            comd.execute();

            // Se le pasan los datos del objeto de retorno de mensajes
            msg.setStruct(structAdapter.Struct(con, comd.getObject(3)));

            if (msg.getMens_codigo_sistema() != 0) {
                System.err.println(msg.getMens_codigo_sistema() + ": " + msg.getMens_descrip_sistema());

                jsonReturn.setCodigo(-1)
                        .setMensaje(msg.getMens_descrip_usuario());
            } else {
                rs = (ResultSet) comd.getObject(2);

                BigDecimal idIup;
                UnidadRemesa unidadRemesa;
                ArrayList<UnidadRemesa> list = new ArrayList<UnidadRemesa>();

                while (rs.next() == true) {
                    unidadRemesa = new UnidadRemesa();

                    idIup = rs.getBigDecimal("ID_IUP");
                    unidadRemesa.setIdIup(idIup == null ? "" : idIup.toPlainString());

                    unidadRemesa.setCodigoUnidad(rs.getString("CODIGO_UNIDAD"));
                    unidadRemesa.setTipoUnidad(rs.getString("TIPO_UNIDAD"));
                    unidadRemesa.setClaseEmpaque(rs.getString("CLASE_EMPAQUE"));
                    unidadRemesa.setEstadoUnidad(rs.getString("ESTADO_ACTUAL_UNIDAD"));
                    unidadRemesa.setTipoUbicacion(rs.getString("TIPO_UBICAICON_ACTUAL"));
                    unidadRemesa.setCodBarrasUbicacion(rs.getString("COD_BARRAS_UB_ACTUAL"));
                    unidadRemesa.setUbicacion(rs.getString("UBICACION_ACTUAL"));
                    unidadRemesa.setCeOp(rs.getString("CEOP_UB_ACTUAL"));
                    unidadRemesa.setTieneNovedad(rs.getInt("CON_NOVEDAD") > 0);
                    unidadRemesa.setMovilEntrega(rs.getString("MOVIL_ENTREGA"));
                    unidadRemesa.setCertificacion(rs.getString("CERTIFICACION"));
                    unidadRemesa.setPesoReal(rs.getDouble("P_REAL"));
                    unidadRemesa.setPesoVolumen(rs.getDouble("P_VOL"));
                    unidadRemesa.setAlto(rs.getDouble("ALTO"));
                    unidadRemesa.setAncho(rs.getDouble("ANCHO"));
                    unidadRemesa.setLargo(rs.getDouble("LARGO"));

                    list.add(unidadRemesa);
                }

                jsonReturn.setList(list);
            }

            return jsonReturn;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException(ex);
        } catch (Throwable te) {
            te.printStackTrace();
            throw new DAOException(te);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            if (comd != null) {
                try {
                    comd.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public JsonReturn consultarTrazabilidadUnidades(String ipidIdInt, DAOfactory dao) throws DAOException {
        JsonReturn jsonReturn = new JsonReturn();
        StructAdapter structAdapter = new StructAdapter();
        OracleCallableStatement comd = null;

        ResultSet rs = null;

        try {
            Connection con = dao.getConexion();
            Mensajes msg = new Mensajes();

            comd = (oracle.jdbc.OracleCallableStatement) con.prepareCall("{ call WRAPPER_IMPR_ETIQUETAS_API.CONSULTA_TRAZA_UNDS_REMESA_PRC( ?, ?, ? ) }");

            comd.setBigDecimal(1, new BigDecimal(ipidIdInt));
            comd.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
            comd.registerOutParameter(3, oracle.jdbc.OracleTypes.STRUCT, "OBJ_MENSAJES_T");

            comd.execute();

            // Se le pasan los datos del objeto de retorno de mensajes
            msg.setStruct(structAdapter.Struct(con, comd.getObject(3)));

            if (msg.getMens_codigo_sistema() != 0) {
                System.err.println(msg.getMens_codigo_sistema() + ": " + msg.getMens_descrip_sistema());

                jsonReturn.setCodigo(-1)
                        .setMensaje(msg.getMens_descrip_usuario());
            } else {
                rs = (ResultSet) comd.getObject(2);

                TrazaUnidadRemesa trazaUnidadRemesa;
                ArrayList<TrazaUnidadRemesa> list = new ArrayList<TrazaUnidadRemesa>();

                while (rs.next() == true) {
                    trazaUnidadRemesa = new TrazaUnidadRemesa();

                    trazaUnidadRemesa.setFechaTraza(rs.getString("FE_TRAZA"));
                    trazaUnidadRemesa.setProceso(rs.getString("PROCESO"));
                    trazaUnidadRemesa.setCiudadProceso(rs.getString("CIUDAD_PROCESO"));
                    trazaUnidadRemesa.setTipoUbicacion(rs.getString("TIPO_UBICACION"));
                    trazaUnidadRemesa.setCodBarrasUbicacion(rs.getString("COD_BAR_UBICACION"));
                    trazaUnidadRemesa.setDescUbicacion(rs.getString("DESC_UBICACION"));
                    trazaUnidadRemesa.setUsuario(rs.getString("USUARIO"));
                    trazaUnidadRemesa.setEstadoTraza(rs.getString("ESTADO_TRAZA_UND"));
                    trazaUnidadRemesa.setObservaciones(rs.getString("OBSERVACIONES_TRAZA"));

                    list.add(trazaUnidadRemesa);
                }

                jsonReturn.setList(list);
            }

            return jsonReturn;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException(ex);
        } catch (Throwable te) {
            te.printStackTrace();
            throw new DAOException(te);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            if (comd != null) {
                try {
                    comd.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public JsonReturn consultarDocumentosClienteRemesa(String remeIdInt, DAOfactory dao) throws DAOException {
        JsonReturn jsonReturn = new JsonReturn();
        StructAdapter structAdapter = new StructAdapter();
        OracleCallableStatement comd = null;

        ResultSet rs = null;

        try {
            Connection con = dao.getConexion();
            Mensajes msg = new Mensajes();

            comd = (oracle.jdbc.OracleCallableStatement) con.prepareCall("{ call WRAPPER_IMPR_ETIQUETAS_API.CONSULTA_DOCS_REF_REMESA_PRC( ?, ?, ? ) }");

            comd.setBigDecimal(1, new BigDecimal(remeIdInt));
            comd.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
            comd.registerOutParameter(3, oracle.jdbc.OracleTypes.STRUCT, "OBJ_MENSAJES_T");

            comd.execute();

            // Se le pasan los datos del objeto de retorno de mensajes
            msg.setStruct(structAdapter.Struct(con, comd.getObject(3)));

            if (msg.getMens_codigo_sistema() != 0) {
                System.err.println(msg.getMens_codigo_sistema() + ": " + msg.getMens_descrip_sistema());

                jsonReturn.setCodigo(-1)
                        .setMensaje(msg.getMens_descrip_usuario());
            } else {
                rs = (ResultSet) comd.getObject(2);

                DocumentoCliente documentoCliente;
                ArrayList<DocumentoCliente> list = new ArrayList<DocumentoCliente>();

                while (rs.next() == true) {
                    documentoCliente = new DocumentoCliente();

                    documentoCliente.setTipoDoc(rs.getString("TIPO_DOC"));
                    documentoCliente.setFecha(rs.getString("FE_DOC"));
                    documentoCliente.setNumero(rs.getString("NUM_DOC"));

                    list.add(documentoCliente);
                }

                jsonReturn.setList(list);
            }

            return jsonReturn;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException(ex);
        } catch (Throwable te) {
            te.printStackTrace();
            throw new DAOException(te);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            if (comd != null) {
                try {
                    comd.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public JsonReturn consultarFormatosImpresion(DAOfactory dao) throws DAOException {
        JsonReturn jsonReturn = new JsonReturn();
        StructAdapter structAdapter = new StructAdapter();
        OracleCallableStatement comd = null;

        ResultSet rs = null;

        try {
            Connection con = dao.getConexion();
            Mensajes msg = new Mensajes();

            comd = (oracle.jdbc.OracleCallableStatement) con.prepareCall("{ call WRAPPER_IMPR_ETIQUETAS_API.LISTAR_FORMATOS_IMPR_PRC( ?, ? ) }");

            comd.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
            comd.registerOutParameter(2, oracle.jdbc.OracleTypes.STRUCT, "OBJ_MENSAJES_T");

            comd.execute();

            // Se le pasan los datos del objeto de retorno de mensajes
            msg.setStruct(structAdapter.Struct(con, comd.getObject(2)));

            if (msg.getMens_codigo_sistema() != 0) {
                System.err.println(msg.getMens_codigo_sistema() + ": " + msg.getMens_descrip_sistema());

                jsonReturn.setCodigo(-1)
                        .setMensaje(msg.getMens_descrip_usuario());
            } else {
                rs = (ResultSet) comd.getObject(1);

                FormatoImpresion formatoImpresion;
                ArrayList<FormatoImpresion> list = new ArrayList<FormatoImpresion>();

                while (rs.next() == true) {
                    formatoImpresion = new FormatoImpresion();

                    formatoImpresion.setUnneIdInt(rs.getInt("UNNE_ID_INT"));
                    formatoImpresion.setUnneDescripcion(rs.getString("UNNE_DESCRIPCION"));
                    formatoImpresion.setRotuIdInt(rs.getInt("ROTU_ID_INT"));
                    formatoImpresion.setRotuDescripcion(rs.getString("ROTU_DESCRIPCION"));
                    formatoImpresion.setRotiIdInt(rs.getInt("ROTI_ID_INT"));
                    formatoImpresion.setTiunIdInt(rs.getInt("TIUN_ID_INT"));
                    formatoImpresion.setTiunDescripcion(rs.getString("TIUN_DESCRIPCION"));

                    list.add(formatoImpresion);
                }

                jsonReturn.setList(list);
            }

            return jsonReturn;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException(ex);
        } catch (Throwable te) {
            te.printStackTrace();
            throw new DAOException(te);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            if (comd != null) {
                try {
                    comd.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public JsonReturn guardarLog(RemesaLog remesaLog, DAOfactory dao) throws DAOException {
        JsonReturn jsonReturn = new JsonReturn();
        StructAdapter structAdapter = new StructAdapter();
        OracleCallableStatement comd = null;

        try {
            Connection con = dao.getConexion();
            Mensajes msg = new Mensajes();

            comd = (oracle.jdbc.OracleCallableStatement) con.prepareCall("{ call WRAPPER_IMPR_ETIQUETAS_API.LOG_IMPRESION_PRC( ?, ?, ?, ? ) }");

            comd.setBigDecimal(1, new BigDecimal(remesaLog.getRemeIdInt()));
            comd.setLong(2, remesaLog.getLocaIdInt());
            comd.setString(3, remesaLog.getUsuario());
            comd.registerOutParameter(4, oracle.jdbc.OracleTypes.STRUCT, "OBJ_MENSAJES_T");

            comd.execute();

            // Se le pasan los datos del objeto de retorno de mensajes
            msg.setStruct(structAdapter.Struct(con, comd.getObject(4)));

            if (msg.getMens_codigo_usuario() != 0) {
                System.err.println(msg.getMens_codigo_sistema() + ": " + msg.getMens_descrip_sistema());

                jsonReturn.setCodigo(-1)
                        .setMensaje(msg.getMens_descrip_usuario());
            }

            return jsonReturn;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException(ex);
        } catch (Throwable te) {
            te.printStackTrace();
            throw new DAOException(te);
        } finally {
            if (comd != null) {
                try {
                    comd.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public JsonReturn consultarNovedadesUnidades(NovedadUnidadRemesa novedadUnidad, DAOfactory dao) throws DAOException {
        JsonReturn jsonReturn = new JsonReturn();
        StructAdapter structAdapter = new StructAdapter();
        OracleCallableStatement comd = null;

        ResultSet rs = null;

        try {
            Connection con = dao.getConexion();
            Mensajes msg = new Mensajes();

            comd = (oracle.jdbc.OracleCallableStatement) con.prepareCall("{ call WRAPPER_NOVEDADES_API.CONSULTA_NOVEDADES_UNIDADES( ?, ?, ? ) }");

            comd.setObject(1, novedadUnidad);
            comd.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
            comd.registerOutParameter(3, oracle.jdbc.OracleTypes.STRUCT, "OBJ_MENSAJES_T");

            comd.execute();

            // Se le pasan los datos del objeto de retorno de mensajes
            msg.setStruct(structAdapter.Struct(con, comd.getObject(3)));

            if (msg.getMens_codigo_sistema() != 0) {
                System.err.println(msg.getMens_codigo_sistema() + ": " + msg.getMens_descrip_sistema());

                jsonReturn.setCodigo(-1)
                        .setMensaje(msg.getMens_descrip_usuario());
            } else {
                rs = (ResultSet) comd.getObject(2);

                String foto;
                ArrayList<NovedadUnidadRemesa> list = new ArrayList<NovedadUnidadRemesa>();

                while (rs.next() == true) {
                    novedadUnidad = new NovedadUnidadRemesa();

                    novedadUnidad.setCodigoUnidad(rs.getString("COD_UNIDAD"));
                    novedadUnidad.setCeOpPlantea(rs.getString("CEOP_PLANTEA"));
                    novedadUnidad.setFechaNovedad(rs.getString("FE_NOVEDAD"));
                    novedadUnidad.setCodNovedad(rs.getLong("COD_NOVEDAD"));
                    novedadUnidad.setNovedad(rs.getString("NOVEDAD"));
                    novedadUnidad.setComentarios(rs.getString("COMENTARIOS"));
                    novedadUnidad.setProceso(rs.getString("PROCESO"));
                    novedadUnidad.setUsuario(rs.getString("USUARIO"));

                    foto = rs.getString("FOTO");

                    if (foto == null) {
                        foto = "";
                    }

                    novedadUnidad.setFoto(foto.trim());

                    list.add(novedadUnidad);
                }

                jsonReturn.setList(list);
            }

            return jsonReturn;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException(ex);
        } catch (Throwable te) {
            te.printStackTrace();
            throw new DAOException(te);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            if (comd != null) {
                try {
                    comd.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public JsonReturn grabarNovedad(Remesa remesa, DAOfactory dao) throws DAOException {
        JsonReturn jsonReturn = new JsonReturn();
        StructAdapter structAdapter = new StructAdapter();
        OracleCallableStatement comd = null;

        try {
            Connection con = dao.getConexion();
            Mensajes msg = new Mensajes();

            Date fechaNovedad = Util.parseDate(remesa.getFechaNovedad(), "dd/MM/yyyy HH:mm");
            Date fechaRemesa = Util.parseDate(remesa.getFechaRemesa());

            comd = (oracle.jdbc.OracleCallableStatement) con.prepareCall(
                    "{ call WRAPPER_DMRUTA_WM_API.GENERAR_NOVEDAD_V3_PRC( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) }"
            );

            comd.setLong(1, Long.parseLong(remesa.getIdCiudadOrigen()));
            comd.setInt(2, 0);
            comd.setTimestamp(3, fechaNovedad == null ? null : new java.sql.Timestamp(fechaNovedad.getTime()));
            comd.setString(4, remesa.getCodCausaNovedad());
            comd.setString(5, remesa.getObservaciones());
            comd.setString(6, remesa.getNumRemesa());
            comd.setDate(7, fechaRemesa == null ? null : new java.sql.Date(fechaRemesa.getTime()));
            comd.setString(8, remesa.getUsuario());
            comd.setBigDecimal(9, new BigDecimal(remesa.getRemeIdInt() == null ? "0" : remesa.getRemeIdInt()));
            comd.setString(10, null); // i_imagenes
            comd.setString(11, "1"); // I_CANAL_INGRESO
            comd.setString(12, "" + remesa.getProcIdInt());
            comd.setInt(13, remesa.getUnidadesRemesa());
            comd.setString(14, remesa.getCodigoBarras());
            comd.setNull(15, OracleTypes.NULL);
            comd.setNull(16, OracleTypes.NULL);
            comd.setInt(17, remesa.getCodigoJustificacionNovedad()); 
            comd.registerOutParameter(18, oracle.jdbc.OracleTypes.STRUCT, "OBJ_MENSAJES_T");
            
            comd.execute();

            // Se le pasan los datos del objeto de retorno de mensajes
            msg.setStruct(structAdapter.Struct(con, comd.getObject(18)));

            if (msg.getMens_codigo_usuario() == 0) {
                jsonReturn.setObject(msg.getMens_descrip_sistema());
            } else {
                System.err.println(msg.getMens_codigo_sistema() + ": " + msg.getMens_descrip_sistema());
                jsonReturn.setCodigo(-1);
            }

            jsonReturn.setMensaje(msg.getMens_descrip_usuario());

            return jsonReturn;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException(ex);
        } catch (Throwable te) {
            te.printStackTrace();
            throw new DAOException(te);
        } finally {
            if (comd != null) {
                try {
                    comd.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public JsonReturn ingresarSeguimiento(SeguimientoNovedad seguimiento, DAOfactory dao) throws DAOException {
        JsonReturn jsonReturn = new JsonReturn();
        StructAdapter structAdapter = new StructAdapter();
        OracleCallableStatement comd = null;

        try {
            Connection con = dao.getConexion();
            Mensajes msg = new Mensajes();

            comd = (oracle.jdbc.OracleCallableStatement) con.prepareCall("{ call WRAPPER_NOVEDADES_API.INSERTAR_SEG_NOVEDAD_PRC( ?, ? ) }");

            comd.setObject(1, seguimiento);
            comd.registerOutParameter(2, oracle.jdbc.OracleTypes.STRUCT, "OBJ_MENSAJES_T");

            comd.execute();

            // Se le pasan los datos del objeto de retorno de mensajes
            msg.setStruct(structAdapter.Struct(con, comd.getObject(2)));

            if (msg.getMens_codigo_sistema() != 0) {
                System.err.println(msg.getMens_codigo_sistema() + ": " + msg.getMens_descrip_sistema());

                jsonReturn.setCodigo(-1)
                        .setMensaje(msg.getMens_descrip_usuario());
            }

            return jsonReturn;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException(ex);
        } catch (Throwable te) {
            te.printStackTrace();
            throw new DAOException(te);
        } finally {
            if (comd != null) {
                try {
                    comd.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public JsonReturn ejecutarNovedad(ParametroNovedad novedad, DAOfactory dao) throws DAOException {
        JsonReturn jsonReturn = new JsonReturn();
        StructAdapter structAdapter = new StructAdapter();
        OracleCallableStatement comd = null;

        try {
            Connection con = dao.getConexion();
            Mensajes msg = new Mensajes();

            comd = (oracle.jdbc.OracleCallableStatement) con.prepareCall("{ call WRAPPER_NOVEDADES_API.EJECUTAR_NOVEDAD_PRC( ?, ?, ?, ?, ?, ? ) }");

            comd.setBigDecimal(1, new BigDecimal(novedad.getCanoIdInt()));
            comd.setString(2, novedad.getObservaciones());
            comd.setString(3, novedad.getUsuario());
            comd.setInt(4, novedad.getIdCeOpEjecuta());
            comd.setInt(5, novedad.getLivaCausalNov());
            comd.registerOutParameter(6, oracle.jdbc.OracleTypes.STRUCT, "OBJ_MENSAJES_T");

            comd.execute();

            // Se le pasan los datos del objeto de retorno de mensajes
            msg.setStruct(structAdapter.Struct(con, comd.getObject(6)));

            if (msg.getMens_codigo_sistema() != 0) {
                System.err.println(msg.getMens_codigo_sistema() + ": " + msg.getMens_descrip_sistema());

                jsonReturn.setCodigo(-1)
                        .setMensaje(msg.getMens_descrip_usuario());
            }

            return jsonReturn;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException(ex);
        } catch (Throwable te) {
            te.printStackTrace();
            throw new DAOException(te);
        } finally {
            if (comd != null) {
                try {
                    comd.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public JsonReturn anularNovedad(ParametroNovedad novedad, DAOfactory dao) throws DAOException {
        JsonReturn jsonReturn = new JsonReturn();
        StructAdapter structAdapter = new StructAdapter();
        OracleCallableStatement comd = null;

        try {
            Connection con = dao.getConexion();
            Mensajes msg = new Mensajes();

            comd = (oracle.jdbc.OracleCallableStatement) con.prepareCall("{ call WRAPPER_NOVEDADES_API.ANULAR_NOVEDAD_PRC( ?, ?, ?, ?, ? ) }");

            comd.setBigDecimal(1, new BigDecimal(novedad.getCanoIdInt()));
            comd.setString(2, novedad.getObservaciones());
            comd.setString(3, novedad.getUsuario());
            comd.setInt(4, novedad.getIdCeOpEjecuta());
            comd.registerOutParameter(5, oracle.jdbc.OracleTypes.STRUCT, "OBJ_MENSAJES_T");

            comd.execute();

            // Se le pasan los datos del objeto de retorno de mensajes
            msg.setStruct(structAdapter.Struct(con, comd.getObject(5)));

            if (msg.getMens_codigo_sistema() != 0) {
                System.err.println(msg.getMens_codigo_sistema() + ": " + msg.getMens_descrip_sistema());

                jsonReturn.setCodigo(-1)
                        .setMensaje(msg.getMens_descrip_usuario());
            }

            return jsonReturn;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException(ex);
        } catch (Throwable te) {
            te.printStackTrace();
            throw new DAOException(te);
        } finally {
            if (comd != null) {
                try {
                    comd.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public JsonReturn consultarDiasNoHabiles(DAOfactory dao) throws DAOException {
        JsonReturn jsonReturn = new JsonReturn();
        StructAdapter structAdapter = new StructAdapter();
        OracleCallableStatement comd = null;

        ResultSet rs = null;

        try {
            Connection con = dao.getConexion();
            Mensajes msg = new Mensajes();

            comd = (oracle.jdbc.OracleCallableStatement) con.prepareCall("{ call WRAPPER_NOVEDADES_API.OBTENER_DIAS_NO_HABILES( ?, ? ) }");

            comd.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
            comd.registerOutParameter(2, oracle.jdbc.OracleTypes.STRUCT, "OBJ_MENSAJES_T");

            comd.execute();

            // Se le pasan los datos del objeto de retorno de mensajes
            msg.setStruct(structAdapter.Struct(con, comd.getObject(2)));

            if (msg.getMens_codigo_sistema() != 0) {
                System.err.println(msg.getMens_codigo_sistema() + ": " + msg.getMens_descrip_sistema());

                jsonReturn.setCodigo(-1)
                        .setMensaje(msg.getMens_descrip_usuario());
            } else {
                rs = (ResultSet) comd.getObject(1);
                List<String> list = new ArrayList<String>();

                while (rs.next() == true) {
                    list.add(rs.getString("DINH_FECHA"));
                }

                jsonReturn.setList(list);
            }

            return jsonReturn;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException(ex);
        } catch (Throwable te) {
            te.printStackTrace();
            throw new DAOException(te);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            if (comd != null) {
                try {
                    comd.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public JsonReturn consultarPermisos(Map<String, String> params, DAOfactory dao) throws DAOException {
        JsonReturn jsonReturn = new JsonReturn();
        StructAdapter structAdapter = new StructAdapter();
        OracleCallableStatement comd = null;

        ResultSet rs = null;

        try {
            Connection con = dao.getConexion();
            Mensajes msg = new Mensajes();

            comd = (oracle.jdbc.OracleCallableStatement) con.prepareCall("{ call WRAPPER_WLS_SECURITY.CONS_RESTRICCION_COMP_PRC( ?, ?, ?, ? ) }");

            comd.setString(1, params.get("usuario"));
            comd.setString(2, params.get("aplicacion"));
            comd.registerOutParameter(3, oracle.jdbc.OracleTypes.CURSOR);
            comd.registerOutParameter(4, oracle.jdbc.OracleTypes.STRUCT, "OBJ_MENSAJES_T");

            comd.execute();

            // Se le pasan los datos del objeto de retorno de mensajes
            msg.setStruct(structAdapter.Struct(con, comd.getObject(4)));

            if (msg.getMens_codigo_usuario() != 0) {
                System.err.println(msg.getMens_codigo_usuario() + ": " + msg.getMens_descrip_usuario());

                jsonReturn.setCodigo(-1)
                        .setMensaje(msg.getMens_descrip_usuario());
            } else {
                rs = (ResultSet) comd.getObject(3);
                List<String> list = new ArrayList<String>();

                while (rs.next() == true) {
                    list.add(rs.getString("COMPONENTNAME"));
                }

                jsonReturn.setList(list);
            }

            return jsonReturn;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException(ex);
        } catch (Throwable te) {
            te.printStackTrace();
            throw new DAOException(te);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            if (comd != null) {
                try {
                    comd.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public JsonReturn consultarDiasHabiles(Util _util, DAOfactory dao) throws DAOException {
        Connection con = dao.getConexion();
        Statement stmt = null;
        JsonReturn jsonReturn = new JsonReturn();

        try {

            String query;

            // retorna los dias anteriores no habiles a la fecha solicitada, 
            query = "SELECT DINH_FECHA AS DIAS FROM REPOSITORIO_PROD.MAE_DIAS_NO_HABILES DINH WHERE DINH.DINH_FECHA >= :RANGO AND DINH.DINH_FECHA<=TRUNC(SYSDATE) "
                    .replace(":RANGO", "TRUNC(sysdate)-" + _util.getRangoDiasFechaReal() + "");

            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            List<Util> listReturn = new ArrayList<Util>();
            while (rs.next()) {

                Util util = new Util();

                util.setDiasHabiles((String) rs.getString("DIAS"));

                listReturn.add(util);

            }
            jsonReturn.setCodigo(0).setList(listReturn);

        } catch (SQLException ex) {
            ex.printStackTrace();
            jsonReturn.setCodigo(-1)
                    .setMensaje("Error en la consulta");
            throw new DAOException(ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                }
            }
        }

        return jsonReturn;

    }

    @Override
    public JsonReturn obtenerDiaHabil(Date fecha, Integer dias, DAOfactory dao) throws DAOException {

        Connection con = dao.getConexion();
        PreparedStatement stmt = null;

        JsonReturn jsonReturn = new JsonReturn();

        try {

            String query;

            // retorna los dias anteriores no habiles a la fecha solicitada, 
            query = "select FNC_GET_FECHA_HABIL(?,?) from dual";

            stmt = con.prepareStatement(query);

            java.sql.Date sDate = new java.sql.Date(fecha.getTime());

            stmt.setDate(1, sDate);
            stmt.setInt(2, dias);

            ResultSet rs = stmt.executeQuery(query);

            List<Util> listReturn = new ArrayList<Util>();

            while (rs.next()) {

                Util util = new Util();

                util.setDiasHabiles((String) rs.getString(1));

                listReturn.add(util);

            }
            jsonReturn.setCodigo(0).setList(listReturn);

        } catch (SQLException ex) {
            ex.printStackTrace();
            jsonReturn.setCodigo(-1)
                    .setMensaje("Error en la consulta");
            throw new DAOException(ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                }
            }
        }

        return jsonReturn;

    }

    @Override
    public JsonReturn validarEstadoGlobalRemesaIngresoNovedad(Remesa remesa, DAOfactory dao) throws DAOException {

        Connection con = dao.getConexion();
        Statement stmt = null;
        JsonReturn jsonReturn = new JsonReturn();

        try {

            String query;

            // retorna los dias anteriores no habiles a la fecha solicitada, 
            query = ""
                    + "SELECT "
                    + "             REME.REME_ID_INT AS ID_REMESA "
                    + "            , REME.REME_NUMERO_REMESA AS NUMERO_REMESA "
                    + "            , ESTA_NOV.LIVA_DESCRIPCION  AS ESTADO_NOVEDAD "
                    + "            , ESTA_NOV.LIVA_VALOR "
                    + "    FROM  REM_REMESAS REME "
                    + "            LEFT JOIN MAE_UNIDAD_NEGOCIOS UNEG ON (REME.UNNE_ID_INT = UNEG.UNNE_ID_INT AND REME.REME_ESTADO= 'A' AND REME.REME_INACTIVACION IS NULL ) "
                    + "            LEFT JOIN REM_REMESA_EXT REEX ON (REME.REME_ID_INT=REEX.REME_ID_INT AND REEX.REEX_ESTADO='A') "
                    + "            LEFT JOIN MAE_ESTADO_OBJETO_NEGOCIO ESON ON (REME.ESON_ID_INT = ESON.ESON_ID_INT ) "
                    + "            LEFT JOIN MAE_ESTADOS ESTA ON (ESTA.ESTA_ID_INT = ESON.ESTA_ID_INT AND ESTA.ESTA_ID_INT NOT IN (TO_NUMBER(UTILI_WRAPPER_SERVICIOS_UTI.FNC_PARAMETROS_SISTEMA('ESON_REM_ANU_SAS'))))        LEFT JOIN CASO_NOVEDADES CANO ON (REME.REME_ID_INT  = CANO.REME_ID_INT AND CANO.CANO_ESTADO = 'A') "
                    + "            LEFT JOIN MAE_LISTA_VALORES ESTA_NOV ON (CANO.CANO_COD_ESTADO = ESTA_NOV.LIVA_VALOR AND ESTA_NOV.LIST_ID_INT  = TO_NUMBER(UTILI_WRAPPER_SERVICIOS_UTI.FNC_PARAMETROS_SISTEMA('LIST_EST_NVD_LGD'))) "
                    + "    WHERE "
                    + "     REME.REME_NUMERO_REMESA IN  (select regexp_substr('"+remesa.getNumRemesa()+"' ,'[^,]+', 1, level) from dual connect by regexp_substr('"+remesa.getNumRemesa()+"', '[^,]+', 1, level) is not null ) "
                    + "    AND ESTA_NOV.LIVA_VALOR NOT IN (6)";

            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            List<Util> listReturn = new ArrayList<Util>();
            while (rs.next()) {

                Util util = new Util();

                util.setDiasHabiles((String) rs.getString("ESTADO_NOVEDAD"));

                listReturn.add(util);

            }
            jsonReturn.setCodigo(0).setList(listReturn);

        } catch (SQLException ex) {
            ex.printStackTrace();
            jsonReturn.setCodigo(-1)
                    .setMensaje("Error en la consulta");
            throw new DAOException(ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                }
            }
        }

        return jsonReturn;

    }

    public JsonReturn consultarIUPRemesa(String remesas, DAOfactory dao) throws DAOException {
         JsonReturn jsonReturn = new JsonReturn();
        StructAdapter structAdapter = new StructAdapter();
        OracleCallableStatement comd = null;

        ResultSet rs = null;

        try {
            Connection con = dao.getConexion();
            Mensajes msg = new Mensajes();

            comd = (oracle.jdbc.OracleCallableStatement) con.prepareCall("{ call WRAPPER_NOVEDADES_API.CONS_UNIDADES_REMESA_PRC( ?, ?, ? ) }");
           // Integer[] RemesasArray  =remesas;
           // Array  inputArray = con.createArrayOf("NUMBER", RemesasArray);
            
            comd.setString(1, remesas);
            comd.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR); //salida
            comd.registerOutParameter(3, oracle.jdbc.OracleTypes.STRUCT, "OBJ_MENSAJES_T");//salida
            // comd.registerOutParameter( 5, oracle.jdbc.OracleTypes.VARCHAR );

            comd.execute();

            // System.err.println( comd.getString(5) );
            // Se le pasan los datos del objeto de retorno de mensajes
            msg.setStruct(structAdapter.Struct(con, comd.getObject(3)));

            if (msg.getMens_codigo_sistema() != 0) {
                System.err.println(msg.getMens_codigo_sistema() + ": " + msg.getMens_descrip_sistema());

                jsonReturn.setCodigo(-1)
                        .setMensaje(msg.getMens_descrip_usuario());
            } else {
                rs = (ResultSet) comd.getObject(2);
                ArrayList<Remesa> list = new ArrayList<Remesa>();


                BigDecimal idInt;
                Map<String, Integer> hashReturn = new HashMap<String, Integer>();

                if (rs != null) {

                    while (rs.next() == true) {

                      Remesa  remesa = new Remesa();

                      String remeIdInt = rs.getString("REME_ID_INT");
                        remesa.setRemeIdInt(remeIdInt);
                        
                      String codigoBarras = rs.getString("CODIGO_BARRAS");
                        remesa.setCodigoBarras(codigoBarras);
               

                        list.add(remesa);
                     
                    }
                }


                jsonReturn.setList(list)
                        ;
            }

            return jsonReturn;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException(ex);
        } catch (Throwable te) {
            te.printStackTrace();
            throw new DAOException(te);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            if (comd != null) {
                try {
                    comd.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

}
