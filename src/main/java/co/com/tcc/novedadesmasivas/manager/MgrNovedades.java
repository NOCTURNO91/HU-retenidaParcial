package co.com.tcc.novedadesmasivas.manager;

import co.com.tcc.novedadesmasivas.common.Amazon;
import co.com.tcc.novedadesmasivas.common.JsonReturn;
import co.com.tcc.novedadesmasivas.common.IngresoNovedad;
import co.com.tcc.novedadesmasivas.common.ParametroNovedad;
import co.com.tcc.novedadesmasivas.common.SeguimientoNovedad;
import co.com.tcc.novedadesmasivas.common.NovedadUnidadRemesa;
import co.com.tcc.novedadesmasivas.common.Remesa;
import co.com.tcc.novedadesmasivas.common.RemesaLog;
import co.com.tcc.novedadesmasivas.common.Util;
import co.com.tcc.novedadesmasivas.dao.INovedadesDAO;
import galileo.base.conexion.DAOfactory;
import galileo.base.exception.DAOException;
import galileo.base.exception.FactoryDAOException;
import galileo.base.exception.MgrException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Be-Smart_2
 */
@Component
public class MgrNovedades implements IMgrNovedades {
    
    private static final Logger logger = Logger.getLogger(MgrNovedades.class.getName());

    @Autowired
    private INovedadesDAO novedadesDAO;

    @Override
    public JsonReturn consultar(Remesa remesa) throws MgrException {
        DAOfactory dao = null;
        JsonReturn jsonReturn = new JsonReturn();

        try {
            dao = DAOfactory.getDAOFactory();
            dao.beginConexion();

            jsonReturn = novedadesDAO.consultar(remesa, dao);
            dao.commit();
        } catch (FactoryDAOException fdaoe) {
            if (null != dao) {
                try {
                    dao.rollback();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            throw new MgrException(fdaoe);
        } catch (DAOException daoe) {
            if (null != dao) {
                try {
                    dao.rollback();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            throw new MgrException(daoe);
        } finally {
            if (null != dao) {
                try {
                    dao.closeConexion();
                } catch (Throwable t) {
                    System.err.println("La conexión no se pudo cerrar.");
                    t.printStackTrace();
                }
            }
        }

        return jsonReturn;
    }

    @Override
    public JsonReturn consultarUnidadesRemesa(String remeIdInt) throws MgrException {
        DAOfactory dao = null;
        JsonReturn jsonReturn = new JsonReturn();

        try {
            dao = DAOfactory.getDAOFactory();
            dao.beginConexion();

            jsonReturn = novedadesDAO.consultarUnidadesRemesa(remeIdInt, dao);
            dao.commit();
        } catch (FactoryDAOException fdaoe) {
            if (null != dao) {
                try {
                    dao.rollback();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            throw new MgrException(fdaoe);
        } catch (DAOException daoe) {
            if (null != dao) {
                try {
                    dao.rollback();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            throw new MgrException(daoe);
        } finally {
            if (null != dao) {
                try {
                    dao.closeConexion();
                } catch (Throwable t) {
                    System.err.println("La conexión no se pudo cerrar.");
                    t.printStackTrace();
                }
            }
        }

        return jsonReturn;
    }

    @Override
    public JsonReturn consultarTrazabilidadUnidades(String ipidIdInt) throws MgrException {
        DAOfactory dao = null;
        JsonReturn jsonReturn = new JsonReturn();

        try {
            dao = DAOfactory.getDAOFactory();
            dao.beginConexion();

            jsonReturn = novedadesDAO.consultarTrazabilidadUnidades(ipidIdInt, dao);
            dao.commit();
        } catch (FactoryDAOException fdaoe) {
            if (null != dao) {
                try {
                    dao.rollback();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            throw new MgrException(fdaoe);
        } catch (DAOException daoe) {
            if (null != dao) {
                try {
                    dao.rollback();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            throw new MgrException(daoe);
        } finally {
            if (null != dao) {
                try {
                    dao.closeConexion();
                } catch (Throwable t) {
                    System.err.println("La conexión no se pudo cerrar.");
                    t.printStackTrace();
                }
            }
        }

        return jsonReturn;
    }

    @Override
    public JsonReturn consultarDocumentosClienteRemesa(String remeIdInt) throws MgrException {
        DAOfactory dao = null;
        JsonReturn jsonReturn = new JsonReturn();

        try {
            dao = DAOfactory.getDAOFactory();
            dao.beginConexion();

            jsonReturn = novedadesDAO.consultarDocumentosClienteRemesa(remeIdInt, dao);
            dao.commit();
        } catch (FactoryDAOException fdaoe) {
            if (null != dao) {
                try {
                    dao.rollback();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            throw new MgrException(fdaoe);
        } catch (DAOException daoe) {
            if (null != dao) {
                try {
                    dao.rollback();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            throw new MgrException(daoe);
        } finally {
            if (null != dao) {
                try {
                    dao.closeConexion();
                } catch (Throwable t) {
                    System.err.println("La conexión no se pudo cerrar.");
                    t.printStackTrace();
                }
            }
        }

        return jsonReturn;
    }

    @Override
    public JsonReturn consultarFormatosImpresion() throws MgrException {
        DAOfactory dao = null;
        JsonReturn jsonReturn = new JsonReturn();

        try {
            dao = DAOfactory.getDAOFactory();
            dao.beginConexion();

            jsonReturn = novedadesDAO.consultarFormatosImpresion(dao);
            dao.commit();
        } catch (FactoryDAOException fdaoe) {
            if (null != dao) {
                try {
                    dao.rollback();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            throw new MgrException(fdaoe);
        } catch (DAOException daoe) {
            if (null != dao) {
                try {
                    dao.rollback();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            throw new MgrException(daoe);
        } finally {
            if (null != dao) {
                try {
                    dao.closeConexion();
                } catch (Throwable t) {
                    System.err.println("La conexión no se pudo cerrar.");
                    t.printStackTrace();
                }
            }
        }

        return jsonReturn;
    }

    @Override
    public JsonReturn exportarExcel(Remesa remesa, String tipoExportacion, String path) throws MgrException {
        DAOfactory dao = null;
        JsonReturn jsonReturn = new JsonReturn();

        try {
            dao = DAOfactory.getDAOFactory();
            dao.beginConexion();

            // R: REMESAS
            // U: UNIDADES
            String nombreArchivo = "Novedades_masivas" + (new Date()).getTime();

            SXSSFWorkbook wb = new SXSSFWorkbook(1000);

            CreationHelper createHelper = wb.getCreationHelper();
            Sheet sheet = wb.createSheet("Hoja 1");

            boolean consultarUnidades = tipoExportacion.equals("U");

            // sheet.setDefaultColumnWidth(  );
            // Create a row and put some cells in it. Rows are 0 based.
            Row row = sheet.createRow(0);

            row.createCell(0).setCellValue(createHelper.createRichTextString("Número remesa"));
            row.createCell(1).setCellValue(createHelper.createRichTextString("Unidades"));
            row.createCell(2).setCellValue(createHelper.createRichTextString("Unidad Negocio"));
            row.createCell(3).setCellValue( createHelper.createRichTextString( "Licencia" ) );
            row.createCell(4).setCellValue(createHelper.createRichTextString("Fecha"));
            row.createCell(5).setCellValue(createHelper.createRichTextString("Estado remesa"));
            row.createCell(6).setCellValue(createHelper.createRichTextString("Proceso remesa"));
            row.createCell(7).setCellValue(createHelper.createRichTextString("Consecutivo novedad"));
            row.createCell(8).setCellValue(createHelper.createRichTextString("Estado novedad"));
            row.createCell(9).setCellValue(createHelper.createRichTextString("Forma pago"));
            row.createCell(10).setCellValue(createHelper.createRichTextString("Cuenta"));
            row.createCell(11).setCellValue(createHelper.createRichTextString("Tipo transporte"));
            row.createCell(12).setCellValue(createHelper.createRichTextString("Tipo servicio"));
            row.createCell(13).setCellValue(createHelper.createRichTextString("Ceop Origen"));
            row.createCell(14).setCellValue(createHelper.createRichTextString("Ciudad Origen"));
            row.createCell(15).setCellValue(createHelper.createRichTextString("Identificación remitente"));
            row.createCell(16).setCellValue(createHelper.createRichTextString("Nombre remitente"));
            row.createCell(17).setCellValue(createHelper.createRichTextString("Sede Remitente"));
            row.createCell(18).setCellValue(createHelper.createRichTextString("Dirección Remitente"));
            row.createCell(19).setCellValue(createHelper.createRichTextString("Teléfono Remitente"));
            row.createCell(20).setCellValue(createHelper.createRichTextString("CeOp destino"));
            row.createCell(21).setCellValue(createHelper.createRichTextString("Ciudad destino"));
            row.createCell(22).setCellValue(createHelper.createRichTextString("Tipo cobertura"));
            row.createCell(23).setCellValue(createHelper.createRichTextString("Identificación destinatario"));
            row.createCell(24).setCellValue(createHelper.createRichTextString("Nombre destinatario"));
            row.createCell(25).setCellValue(createHelper.createRichTextString("Sede Destinatario"));
            row.createCell(26).setCellValue(createHelper.createRichTextString("Dirección destinatario"));
            row.createCell(27).setCellValue(createHelper.createRichTextString("Teléfono destinatario"));
            //row.createCell(26).setCellValue( createHelper.createRichTextString( "Tipo unidad" ) );
            row.createCell(28).setCellValue(createHelper.createRichTextString("Peso real"));
            row.createCell(29).setCellValue(createHelper.createRichTextString("Peso volumen"));
            row.createCell(30).setCellValue(createHelper.createRichTextString("Observaciones"));
            row.createCell(31).setCellValue(createHelper.createRichTextString("Usuario"));
            row.createCell(32).setCellValue(createHelper.createRichTextString("Viaje nacional"));
            row.createCell(33).setCellValue(createHelper.createRichTextString("Van"));
            row.createCell(34).setCellValue(createHelper.createRichTextString("Fecha programación nacional"));
            row.createCell(35).setCellValue(createHelper.createRichTextString("Móvil local"));
            row.createCell(36).setCellValue(createHelper.createRichTextString("Viaje"));
            row.createCell(37).setCellValue(createHelper.createRichTextString("Ruta"));
            row.createCell(38).setCellValue(createHelper.createRichTextString("Fecha programación local"));

            if (consultarUnidades == true) {
                row.createCell(39).setCellValue(createHelper.createRichTextString("Código de unidad"));
                row.createCell(40).setCellValue(createHelper.createRichTextString("Tipo de unidad"));
                row.createCell(41).setCellValue(createHelper.createRichTextString("Clase de empaque"));
                row.createCell(42).setCellValue(createHelper.createRichTextString("Estado de la unidad"));
                row.createCell(43).setCellValue(createHelper.createRichTextString("Tipo de ubicación"));
                row.createCell(44).setCellValue(createHelper.createRichTextString("Código ubicación actual"));
                row.createCell(45).setCellValue(createHelper.createRichTextString("Ubicación actual"));
                row.createCell(46).setCellValue(createHelper.createRichTextString("Regional ubicación"));
                row.createCell(47).setCellValue(createHelper.createRichTextString("Móvil entrega"));
                row.createCell(48).setCellValue(createHelper.createRichTextString("Certificación"));
                row.createCell(49).setCellValue(createHelper.createRichTextString("Peso real"));
                row.createCell(50).setCellValue(createHelper.createRichTextString("Peso volumen"));
                row.createCell(51).setCellValue(createHelper.createRichTextString("Alto"));
                row.createCell(52).setCellValue(createHelper.createRichTextString("Ancho"));
                row.createCell(53).setCellValue(createHelper.createRichTextString("Largo"));
            }

            // int i, size;
            int numPag = 1;
            int numRow = 1;

            boolean hayRegistros = true;

            remesa.setNumPag(numPag);
            remesa.setNumRegs(1000);

            while (hayRegistros == true) {

                jsonReturn = novedadesDAO.consultarExcel(remesa, consultarUnidades, path + "/" + nombreArchivo + ".txt", dao);

                if (jsonReturn.getCodigo() == 0) {
                    hayRegistros = (Boolean) jsonReturn.getObject();

                    numPag++;
                    remesa.setNumPag(numPag);
                } else {
                    hayRegistros = false;
                }
            }

            File file = new File(path + "/" + nombreArchivo + ".txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            hayRegistros = true;
            String line;

            String[] arrDatos;

            while (hayRegistros == true) {
                line = bufferedReader.readLine();

                if (line == null) {
                    hayRegistros = false;
                } else {
                    arrDatos = line.split("¬");
                    // System.err.println( "arrDatos.length: " + arrDatos.length );

                    _crearCelda(createHelper, sheet, numRow, arrDatos, consultarUnidades);
                    numRow++;
                }
            }

            // Always close files.
            bufferedReader.close();

            FileOutputStream fileOut = new FileOutputStream(path + "/" + nombreArchivo + ".xlsx");
            wb.write(fileOut);

            fileOut.close();
            wb.dispose();

            jsonReturn.setList(null).setObject(nombreArchivo);
            dao.commit();

            if (file.canWrite() == true) {
                file.delete();
            }
        } catch (FactoryDAOException fdaoe) {
            if (null != dao) {
                try {
                    dao.rollback();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            throw new MgrException(fdaoe);
        } catch (DAOException daoe) {
            if (null != dao) {
                try {
                    dao.rollback();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            throw new MgrException(daoe);
        } catch (Exception ex) {
            if (null != dao) {
                try {
                    dao.rollback();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            throw new MgrException(ex);
        } finally {
            if (null != dao) {
                try {
                    dao.closeConexion();
                } catch (Throwable t) {
                    System.err.println("La conexión no se pudo cerrar.");
                    t.printStackTrace();
                }
            }
        }

        return jsonReturn;
    }

    private void _crearCelda(CreationHelper createHelper, Sheet sheet, int numRow, String[] arrDatos, boolean consultarUnidades) {
        int i;
        Row row = sheet.createRow(numRow);

        for (i = 0; i <= 38; i++) {
            row.createCell(i).setCellValue(createHelper.createRichTextString(arrDatos.length >= (i + 1) ? arrDatos[i] : ""));
        }

        if (consultarUnidades == true) {
            for (i = 39; i <= 53; i++) {
                row.createCell(i).setCellValue(createHelper.createRichTextString(arrDatos.length >= (i + 1) ? arrDatos[i] : ""));
            }
        }
    }

    @Override
    public JsonReturn guardarLog(RemesaLog[] arrRemesaLog) throws MgrException {
        DAOfactory dao = null;
        JsonReturn jsonReturn = new JsonReturn();

        try {
            dao = DAOfactory.getDAOFactory();
            dao.beginConexion();

            for (int i = 0; i < arrRemesaLog.length; i++) {
                jsonReturn = novedadesDAO.guardarLog(arrRemesaLog[i], dao);

                if (jsonReturn.getCodigo() != 0) {
                    break;
                }
            }

            if (jsonReturn.getCodigo() == 0) {
                dao.commit();
            } else {
                dao.rollback();
            }
        } catch (FactoryDAOException fdaoe) {
            if (null != dao) {
                try {
                    dao.rollback();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            throw new MgrException(fdaoe);
        } catch (DAOException daoe) {
            if (null != dao) {
                try {
                    dao.rollback();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            throw new MgrException(daoe);
        } catch (Exception ex) {
            if (null != dao) {
                try {
                    dao.rollback();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            throw new MgrException(ex);
        } finally {
            if (null != dao) {
                try {
                    dao.closeConexion();
                } catch (Throwable t) {
                    System.err.println("La conexión no se pudo cerrar.");
                    t.printStackTrace();
                }
            }
        }

        return jsonReturn;
    }

    @Override
    public JsonReturn consultarNovedadesUnidades(NovedadUnidadRemesa novedadUnidad) throws MgrException {
        DAOfactory dao = null;
        JsonReturn jsonReturn = new JsonReturn();

        try {
            dao = DAOfactory.getDAOFactory();
            dao.beginConexion();

            jsonReturn = novedadesDAO.consultarNovedadesUnidades(novedadUnidad, dao);
            dao.commit();
        } catch (FactoryDAOException fdaoe) {
            if (null != dao) {
                try {
                    dao.rollback();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            throw new MgrException(fdaoe);
        } catch (DAOException daoe) {
            if (null != dao) {
                try {
                    dao.rollback();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            throw new MgrException(daoe);
        } finally {
            if (null != dao) {
                try {
                    dao.closeConexion();
                } catch (Throwable t) {
                    System.err.println("La conexión no se pudo cerrar.");
                    t.printStackTrace();
                }
            }
        }

        return jsonReturn;
    }

    @Override
    public JsonReturn obtenerImagenesNovedadUnidad(NovedadUnidadRemesa novedadUnidad) throws MgrException {
        JsonReturn jsonReturn = new JsonReturn();

        try {
            String imagenes = novedadUnidad.getFoto();
            String[] arrListaImagenes = imagenes.split("\\|");
            List<Map> listReturn = new ArrayList<Map>();

            if (arrListaImagenes != null && arrListaImagenes.length > 0) {
                int numImagen = 1;
                Amazon amazon = new Amazon();
                String imgCodificada;

                String[] arrImagenesUnidad;
                Map<String, String> imagenUnidad;

                for (int i = 0; i < arrListaImagenes.length; i++) {
                    arrImagenesUnidad = arrListaImagenes[i].split("&");

                    if (arrImagenesUnidad != null && arrImagenesUnidad.length > 0) {
                        imagenUnidad = new HashMap<String, String>();
                        imgCodificada = amazon.obtenerImagen(arrImagenesUnidad[0]);

                        imagenUnidad.put("url", "data:image/jpeg;base64," + imgCodificada);
                        imagenUnidad.put("caption", "Imágenes de novedad para la unidad " + novedadUnidad.getCodigoUnidad() + " (" + numImagen + ")");

                        if (arrImagenesUnidad.length > 1) {
                            imagenUnidad.put("imageObservations", arrImagenesUnidad[1]);
                        } else {
                            imagenUnidad.put("imageObservations", novedadUnidad.getComentarios());
                        }

                        listReturn.add(imagenUnidad);
                        numImagen++;
                    }
                }
            }

            jsonReturn.setList(listReturn);
        } catch (Exception ex) {
            throw new MgrException(ex);
        }

        return jsonReturn;
    }

    @Override
    public JsonReturn grabarNovedades(IngresoNovedad ingresoNovedad) throws MgrException {
        DAOfactory dao = null;
        JsonReturn jsonReturn = new JsonReturn();

        try {
            dao = DAOfactory.getDAOFactory();
            dao.beginConexion();

            Calendar cal = Calendar.getInstance();

            int hora = cal.get(Calendar.HOUR_OF_DAY);
            int min = cal.get(Calendar.MINUTE);

            String strHora = (hora < 10) ? ("0" + hora) : ("" + hora);
            String strMin = (min < 10) ? ("0" + min) : ("" + min);

            String canoIdInt;

            Remesa remesa;
            Remesa[] arrRemesas = ingresoNovedad.getArrRemesas();

            SeguimientoNovedad seguimiento;
            SeguimientoNovedad[] arrSeguimientos = ingresoNovedad.getArrSeguimientos();

            ParametroNovedad novedad;
            ParametroNovedad[] arrNovedades = ingresoNovedad.getArrNovedades();

            for (int i = 0; i < arrRemesas.length; i++) {
                remesa = arrRemesas[i];

                remesa.setFechaNovedad(remesa.getFechaNovedad() + " " + strHora + ":" + strMin);
                jsonReturn = novedadesDAO.grabarNovedad(remesa, dao);

                if (jsonReturn.getCodigo() == 0) {
                    if (arrSeguimientos.length > 0) {
                        canoIdInt = (String) jsonReturn.getObject();

                        seguimiento = arrSeguimientos[i];
                        seguimiento.setCanoIdInt(canoIdInt);

                        jsonReturn = novedadesDAO.ingresarSeguimiento(seguimiento, dao);

                        if (jsonReturn.getCodigo() == 0) {
                            if (arrNovedades.length > 0) {
                                novedad = arrNovedades[i];
                                novedad.setCanoIdInt(canoIdInt);

                                jsonReturn = novedadesDAO.ejecutarNovedad(novedad, dao);

                                if (jsonReturn.getCodigo() != 0) {
                                    break;
                                }
                            }
                        } else {
                            break;
                        }
                    }
                } else {
                    break;
                }
            }

            if (jsonReturn.getCodigo() == 0) {
                dao.commit();
            } else {
                dao.rollback();
            }
        } catch (FactoryDAOException fdaoe) {
            if (null != dao) {
                try {
                    dao.rollback();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            throw new MgrException(fdaoe);
        } catch (DAOException daoe) {
            if (null != dao) {
                try {
                    dao.rollback();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            throw new MgrException(daoe);
        } finally {
            if (null != dao) {
                try {
                    dao.closeConexion();
                } catch (Throwable t) {
                    System.err.println("La conexión no se pudo cerrar.");
                    t.printStackTrace();
                }
            }
        }

        return jsonReturn;
    }

    @Override
    public JsonReturn ingresarSeguimientosMasivos(SeguimientoNovedad[] arrSeguimientos) throws MgrException {
        DAOfactory dao = null;
        JsonReturn jsonReturn = new JsonReturn();

        try {
            dao = DAOfactory.getDAOFactory();
            dao.beginConexion();

            for (SeguimientoNovedad seguimiento : arrSeguimientos) {
                jsonReturn = novedadesDAO.ingresarSeguimiento(seguimiento, dao);

                if (jsonReturn.getCodigo() != 0) {
                    break;
                }
            }

            if (jsonReturn.getCodigo() == 0) {
                dao.commit();
            } else {
                dao.rollback();
            }
        } catch (FactoryDAOException fdaoe) {
            if (null != dao) {
                try {
                    dao.rollback();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            throw new MgrException(fdaoe);
        } catch (DAOException daoe) {
            if (null != dao) {
                try {
                    dao.rollback();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            throw new MgrException(daoe);
        } finally {
            if (null != dao) {
                try {
                    dao.closeConexion();
                } catch (Throwable t) {
                    System.err.println("La conexión no se pudo cerrar.");
                    t.printStackTrace();
                }
            }
        }

        return jsonReturn;
    }

    @Override
    public JsonReturn ejecutarNovedad(ParametroNovedad[] arrNovedades) throws MgrException {
        DAOfactory dao = null;
        JsonReturn jsonReturn = new JsonReturn();

        try {
            dao = DAOfactory.getDAOFactory();
            dao.beginConexion();

            for (ParametroNovedad novedad : arrNovedades) {
                jsonReturn = novedadesDAO.ejecutarNovedad(novedad, dao);

                if (jsonReturn.getCodigo() != 0) {
                    break;
                }
            }

            if (jsonReturn.getCodigo() == 0) {
                dao.commit();
            } else {
                dao.rollback();
            }
        } catch (FactoryDAOException fdaoe) {
            if (null != dao) {
                try {
                    dao.rollback();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            throw new MgrException(fdaoe);
        } catch (DAOException daoe) {
            if (null != dao) {
                try {
                    dao.rollback();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            throw new MgrException(daoe);
        } finally {
            if (null != dao) {
                try {
                    dao.closeConexion();
                } catch (Throwable t) {
                    System.err.println("La conexión no se pudo cerrar.");
                    t.printStackTrace();
                }
            }
        }

        return jsonReturn;
    }

    @Override
    public JsonReturn anularNovedad(ParametroNovedad[] arrNovedades) throws MgrException {
        DAOfactory dao = null;
        JsonReturn jsonReturn = new JsonReturn();

        try {
            dao = DAOfactory.getDAOFactory();
            dao.beginConexion();

            for (ParametroNovedad novedad : arrNovedades) {
                jsonReturn = novedadesDAO.anularNovedad(novedad, dao);

                if (jsonReturn.getCodigo() != 0) {
                    break;
                }
            }

            if (jsonReturn.getCodigo() == 0) {
                dao.commit();
            } else {
                dao.rollback();
            }
        } catch (FactoryDAOException fdaoe) {
            if (null != dao) {
                try {
                    dao.rollback();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            throw new MgrException(fdaoe);
        } catch (DAOException daoe) {
            if (null != dao) {
                try {
                    dao.rollback();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            throw new MgrException(daoe);
        } finally {
            if (null != dao) {
                try {
                    dao.closeConexion();
                } catch (Throwable t) {
                    System.err.println("La conexión no se pudo cerrar.");
                    t.printStackTrace();
                }
            }
        }

        return jsonReturn;
    }

    @Override
    public JsonReturn consultarDiasNoHabiles() throws MgrException {
        DAOfactory dao = null;
        JsonReturn jsonReturn = new JsonReturn();

        try {
            dao = DAOfactory.getDAOFactory();
            dao.beginConexion();

            jsonReturn = novedadesDAO.consultarDiasNoHabiles(dao);
            dao.commit();
        } catch (FactoryDAOException fdaoe) {
            if (null != dao) {
                try {
                    dao.rollback();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            throw new MgrException(fdaoe);
        } catch (DAOException daoe) {
            if (null != dao) {
                try {
                    dao.rollback();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            throw new MgrException(daoe);
        } finally {
            if (null != dao) {
                try {
                    dao.closeConexion();
                } catch (Throwable t) {
                    System.err.println("La conexión no se pudo cerrar.");
                    t.printStackTrace();
                }
            }
        }

        return jsonReturn;
    }

    @Override
    public JsonReturn consultarPermisos(Map<String, String> params) throws MgrException {
        DAOfactory dao = null;
        JsonReturn jsonReturn = new JsonReturn();

        try {
            dao = DAOfactory.getDAOFactory();
            dao.beginConexion();

            jsonReturn = novedadesDAO.consultarPermisos(params, dao);
            dao.commit();
        } catch (FactoryDAOException fdaoe) {
            if (null != dao) {
                try {
                    dao.rollback();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            throw new MgrException(fdaoe);
        } catch (DAOException daoe) {
            if (null != dao) {
                try {
                    dao.rollback();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            throw new MgrException(daoe);
        } finally {
            if (null != dao) {
                try {
                    dao.closeConexion();
                } catch (Throwable t) {
                    System.err.println("La conexión no se pudo cerrar.");
                    t.printStackTrace();
                }
            }
        }

        return jsonReturn;
    }

    @Override
    public JsonReturn consultarDiasHabiles(Util util) throws MgrException {
        
        DAOfactory dao = null;
        JsonReturn jsonReturn = new JsonReturn();

        try {
            dao = DAOfactory.getDAOFactory();
            dao.beginConexion();

            jsonReturn = novedadesDAO.consultarDiasHabiles(util, dao);
            dao.commit();
        } catch (FactoryDAOException fdaoe) {
            if (null != dao) {
                try {
                    dao.rollback();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            throw new MgrException(fdaoe);
        } catch (DAOException daoe) {
            if (null != dao) {
                try {
                    dao.rollback();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            throw new MgrException(daoe);
        } finally {
            if (null != dao) {
                try {
                    dao.closeConexion();
                } catch (Throwable t) {
                    System.err.println("La conexión no se pudo cerrar.");
                    t.printStackTrace();
                }
            }
        }

        return jsonReturn;
        
    }
    
    @Override
    public JsonReturn obtenerDiaHabil(Date fecha, Integer dias) throws MgrException {
        

        DAOfactory dao = null;
        JsonReturn jsonReturn = new JsonReturn();

        try {
            dao = DAOfactory.getDAOFactory();
            dao.beginConexion();

            jsonReturn = novedadesDAO.obtenerDiaHabil(fecha, dias, dao);
            dao.commit();
            
        } catch (FactoryDAOException fdaoe) {
            
            throw new MgrException(fdaoe);
            
        } catch (DAOException daoe) {

            throw new MgrException(daoe);
            
        } finally {
            
            if (null != dao) {
                try {
                    dao.closeConexion();
                } catch (Throwable t) {
                    System.err.println("La conexión no se pudo cerrar.");
                    t.printStackTrace();
                }
            }
        }

        return jsonReturn;
        
    }


    @Override
    public JsonReturn validarEstadoGlobalRemesaIngresoNovedad(Remesa remesa) throws MgrException {
        DAOfactory dao = null;
        JsonReturn jsonReturn = new JsonReturn();

        try {
            dao = DAOfactory.getDAOFactory();
            dao.beginConexion();

            jsonReturn = novedadesDAO.validarEstadoGlobalRemesaIngresoNovedad(remesa, dao);
            dao.commit();
        } catch (FactoryDAOException fdaoe) {
            if (null != dao) {
                try {
                    dao.rollback();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            throw new MgrException(fdaoe);
        } catch (DAOException daoe) {
            if (null != dao) {
                try {
                    dao.rollback();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            throw new MgrException(daoe);
        } finally {
            if (null != dao) {
                try {
                    dao.closeConexion();
                } catch (Throwable t) {
                    System.err.println("La conexión no se pudo cerrar.");
                    t.printStackTrace();
                }
            }
        }

        return jsonReturn;
    }

    public JsonReturn consultarIUPRemesa( Integer[] remesa) throws MgrException {
        for(int element:remesa){
            System.out.print(element);
        }
        
       DAOfactory dao = null;
        JsonReturn jsonReturn = new JsonReturn();

        try {
            dao = DAOfactory.getDAOFactory();
            dao.beginConexion();

            jsonReturn = novedadesDAO.consultarIUPRemesa(remesa, dao);
            dao.commit();
        } catch (FactoryDAOException fdaoe) {
            if (null != dao) {
                try {
                    dao.rollback();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            throw new MgrException(fdaoe);
        } catch (DAOException daoe) {
            if (null != dao) {
                try {
                    dao.rollback();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

            throw new MgrException(daoe);
        } finally {
            if (null != dao) {
                try {
                    dao.closeConexion();
                } catch (Throwable t) {
                    System.err.println("La conexión no se pudo cerrar.");
                    t.printStackTrace();
                }
            }
        }

        return jsonReturn;
    }


}
