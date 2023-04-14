package co.com.tcc.novedadesmasivas.common;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.Date;

/**
 *
 * @author be-smart
 */
public class SeguimientoNovedad implements SQLData, Serializable
{
    private static final long serialVersionUID = 1L;
    
    private String caseIdInt;
    private String canoIdInt;
    private int caseCodCiudadPlantea;
    private int caseCodCiudadSoluciona;
    private int caseCodigoNovedad;
    private int caseCodigoSeguimiento;
    private int caseCiudadDigita;
    private String direIdInt;
    private String caseFechaSeguimiento;
    private String caseFrioTcc;
    private String caseFrioCliente;
    private String caseSeguimiento;
    private String caseFrioVaSeguimiento;
    private String caseFrioAplicaSeguimiento;
    private String caseFechaSolucion;
    private String caseObservaciones;
    private String caseAnulado;
    private String caseFechaAnulado;
    private String caseEsSolucion;
    private String caseAreaParticipe;
    private String caseFrioParticipe;
    private String caseInformado;
    private int caseTipoSolucion;
    private String caseNuevaDireccion;
    private String caseFechaReofrecimiento;
    private String caseHoraInicioReofrecimiento;
    private String caseHoraFinalReofrecimiento;
    private String caseReofrecimientoInformado;
    private String caseSegInconsistente;
    private String caseInconsistencia;
    private String caseUsuario;
    private int ticoIdInt;
    private String caseContactoCliente;
    private String caseEstado;
    private String caseTipoSolucionProd;
    private String caseObsvAnulaSol;
    private String caseTipoSeguimiento;
    private int caseCanalSolucion;
    private String observacionesAnulacion;
    private String caseUsuarioAnula;
    
    private String descCiudadPlantea;
    private String descTipoMedioContacto;
    private String descTipoSolucion;
    private String descCiudadSoluciona;
    private String descTipoSolProc;
    private String descTipoSeguimiento;

    public String getCaseIdInt() {
        return caseIdInt;
    }

    public void setCaseIdInt(String caseIdInt) {
        this.caseIdInt = caseIdInt;
    }

    public String getCanoIdInt() {
        return canoIdInt;
    }

    public void setCanoIdInt(String canoIdInt) {
        this.canoIdInt = canoIdInt;
    }

    public int getCaseCodCiudadPlantea() {
        return caseCodCiudadPlantea;
    }

    public void setCaseCodCiudadPlantea(int caseCodCiudadPlantea) {
        this.caseCodCiudadPlantea = caseCodCiudadPlantea;
    }

    public int getCaseCodCiudadSoluciona() {
        return caseCodCiudadSoluciona;
    }

    public void setCaseCodCiudadSoluciona(int caseCodCiudadSoluciona) {
        this.caseCodCiudadSoluciona = caseCodCiudadSoluciona;
    }

    public int getCaseCodigoNovedad() {
        return caseCodigoNovedad;
    }

    public void setCaseCodigoNovedad(int caseCodigoNovedad) {
        this.caseCodigoNovedad = caseCodigoNovedad;
    }

    public int getCaseCodigoSeguimiento() {
        return caseCodigoSeguimiento;
    }

    public void setCaseCodigoSeguimiento(int caseCodigoSeguimiento) {
        this.caseCodigoSeguimiento = caseCodigoSeguimiento;
    }

    public int getCaseCiudadDigita() {
        return caseCiudadDigita;
    }

    public void setCaseCiudadDigita(int caseCiudadDigita) {
        this.caseCiudadDigita = caseCiudadDigita;
    }

    public String getDireIdInt() {
        return direIdInt;
    }

    public void setDireIdInt(String direIdInt) {
        this.direIdInt = direIdInt;
    }

    public String getCaseFechaSeguimiento() {
        return caseFechaSeguimiento;
    }

    public void setCaseFechaSeguimiento(String caseFechaSeguimiento) {
        this.caseFechaSeguimiento = caseFechaSeguimiento;
    }

    public String getCaseFrioTcc() {
        return caseFrioTcc;
    }

    public void setCaseFrioTcc(String caseFrioTcc) {
        this.caseFrioTcc = caseFrioTcc;
    }

    public String getCaseFrioCliente() {
        return caseFrioCliente;
    }

    public void setCaseFrioCliente(String caseFrioCliente) {
        this.caseFrioCliente = caseFrioCliente;
    }

    public String getCaseSeguimiento() {
        return caseSeguimiento;
    }

    public void setCaseSeguimiento(String caseSeguimiento) {
        this.caseSeguimiento = caseSeguimiento;
    }

    public String getCaseFrioVaSeguimiento() {
        return caseFrioVaSeguimiento;
    }

    public void setCaseFrioVaSeguimiento(String caseFrioVaSeguimiento) {
        this.caseFrioVaSeguimiento = caseFrioVaSeguimiento;
    }

    public String getCaseFrioAplicaSeguimiento() {
        return caseFrioAplicaSeguimiento;
    }

    public void setCaseFrioAplicaSeguimiento(String caseFrioAplicaSeguimiento) {
        this.caseFrioAplicaSeguimiento = caseFrioAplicaSeguimiento;
    }

    public String getCaseFechaSolucion() {
        return caseFechaSolucion;
    }

    public void setCaseFechaSolucion(String caseFechaSolucion) {
        this.caseFechaSolucion = caseFechaSolucion;
    }

    public String getCaseObservaciones() {
        return caseObservaciones;
    }

    public void setCaseObservaciones(String caseObservaciones) {
        this.caseObservaciones = caseObservaciones;
    }

    public String getCaseAnulado() {
        return caseAnulado;
    }

    public void setCaseAnulado(String caseAnulado) {
        this.caseAnulado = caseAnulado;
    }

    public String getCaseFechaAnulado() {
        return caseFechaAnulado;
    }

    public void setCaseFechaAnulado(String caseFechaAnulado) {
        this.caseFechaAnulado = caseFechaAnulado;
    }

    public String getCaseEsSolucion() {
        return caseEsSolucion;
    }

    public void setCaseEsSolucion(String caseEsSolucion) {
        this.caseEsSolucion = caseEsSolucion;
    }

    public String getCaseAreaParticipe() {
        return caseAreaParticipe;
    }

    public void setCaseAreaParticipe(String caseAreaParticipe) {
        this.caseAreaParticipe = caseAreaParticipe;
    }

    public String getCaseFrioParticipe() {
        return caseFrioParticipe;
    }

    public void setCaseFrioParticipe(String caseFrioParticipe) {
        this.caseFrioParticipe = caseFrioParticipe;
    }

    public String getCaseInformado() {
        return caseInformado;
    }

    public void setCaseInformado(String caseInformado) {
        this.caseInformado = caseInformado;
    }

    public int getCaseTipoSolucion() {
        return caseTipoSolucion;
    }

    public void setCaseTipoSolucion(int caseTipoSolucion) {
        this.caseTipoSolucion = caseTipoSolucion;
    }

    public String getCaseNuevaDireccion() {
        return caseNuevaDireccion;
    }

    public void setCaseNuevaDireccion(String caseNuevaDireccion) {
        this.caseNuevaDireccion = caseNuevaDireccion;
    }

    public String getCaseFechaReofrecimiento() {
        return caseFechaReofrecimiento;
    }

    public void setCaseFechaReofrecimiento(String caseFechaReofrecimiento) {
        this.caseFechaReofrecimiento = caseFechaReofrecimiento;
    }

    public String getCaseHoraInicioReofrecimiento() {
        return caseHoraInicioReofrecimiento;
    }

    public void setCaseHoraInicioReofrecimiento(String caseHoraInicioReofrecimiento) {
        this.caseHoraInicioReofrecimiento = caseHoraInicioReofrecimiento;
    }

    public String getCaseHoraFinalReofrecimiento() {
        return caseHoraFinalReofrecimiento;
    }

    public void setCaseHoraFinalReofrecimiento(String caseHoraFinalReofrecimiento) {
        this.caseHoraFinalReofrecimiento = caseHoraFinalReofrecimiento;
    }

    public String getCaseReofrecimientoInformado() {
        return caseReofrecimientoInformado;
    }

    public void setCaseReofrecimientoInformado(String caseReofrecimientoInformado) {
        this.caseReofrecimientoInformado = caseReofrecimientoInformado;
    }

    public String getCaseSegInconsistente() {
        return caseSegInconsistente;
    }

    public void setCaseSegInconsistente(String caseSegInconsistente) {
        this.caseSegInconsistente = caseSegInconsistente;
    }

    public String getCaseInconsistencia() {
        return caseInconsistencia;
    }

    public void setCaseInconsistencia(String caseInconsistencia) {
        this.caseInconsistencia = caseInconsistencia;
    }

    public String getCaseUsuario() {
        return caseUsuario;
    }

    public void setCaseUsuario(String caseUsuario) {
        this.caseUsuario = caseUsuario;
    }

    public int getTicoIdInt() {
        return ticoIdInt;
    }

    public void setTicoIdInt(int ticoIdInt) {
        this.ticoIdInt = ticoIdInt;
    }

    public String getCaseContactoCliente() {
        return caseContactoCliente;
    }

    public void setCaseContactoCliente(String caseContactoCliente) {
        this.caseContactoCliente = caseContactoCliente;
    }

    public String getCaseEstado() {
        return caseEstado;
    }

    public void setCaseEstado(String caseEstado) {
        this.caseEstado = caseEstado;
    }

    public String getCaseTipoSolucionProd() {
        return caseTipoSolucionProd;
    }

    public void setCaseTipoSolucionProd(String caseTipoSolucionProd) {
        this.caseTipoSolucionProd = caseTipoSolucionProd;
    }

    public String getCaseObsvAnulaSol() {
        return caseObsvAnulaSol;
    }

    public void setCaseObsvAnulaSol(String caseObsvAnulaSol) {
        this.caseObsvAnulaSol = caseObsvAnulaSol;
    }

    public String getCaseTipoSeguimiento() {
        return caseTipoSeguimiento;
    }

    public void setCaseTipoSeguimiento(String caseTipoSeguimiento) {
        this.caseTipoSeguimiento = caseTipoSeguimiento;
    }

    public int getCaseCanalSolucion() {
        return caseCanalSolucion;
    }

    public void setCaseCanalSolucion(int caseCanalSolucion) {
        this.caseCanalSolucion = caseCanalSolucion;
    }

    public String getObservacionesAnulacion() {
        return observacionesAnulacion;
    }

    public void setObservacionesAnulacion(String observacionesAnulacion) {
        this.observacionesAnulacion = observacionesAnulacion;
    }

    public String getCaseUsuarioAnula() {
        return caseUsuarioAnula;
    }

    public void setCaseUsuarioAnula(String caseUsuarioAnula) {
        this.caseUsuarioAnula = caseUsuarioAnula;
    }

    public String getDescCiudadPlantea() {
        return descCiudadPlantea;
    }

    public void setDescCiudadPlantea(String descCiudadPlantea) {
        this.descCiudadPlantea = descCiudadPlantea;
    }

    public String getDescTipoMedioContacto() {
        return descTipoMedioContacto;
    }

    public void setDescTipoMedioContacto(String descTipoMedioContacto) {
        this.descTipoMedioContacto = descTipoMedioContacto;
    }

    public String getDescTipoSolucion() {
        return descTipoSolucion;
    }

    public void setDescTipoSolucion(String descTipoSolucion) {
        this.descTipoSolucion = descTipoSolucion;
    }

    public String getDescCiudadSoluciona() {
        return descCiudadSoluciona;
    }

    public void setDescCiudadSoluciona(String descCiudadSoluciona) {
        this.descCiudadSoluciona = descCiudadSoluciona;
    }

    public String getDescTipoSolProc() {
        return descTipoSolProc;
    }

    public void setDescTipoSolProc(String descTipoSolProc) {
        this.descTipoSolProc = descTipoSolProc;
    }

    public String getDescTipoSeguimiento() {
        return descTipoSeguimiento;
    }

    public void setDescTipoSeguimiento(String descTipoSeguimiento) {
        this.descTipoSeguimiento = descTipoSeguimiento;
    }

    
    
    
    public String getSqlType() {
        return sqlType;
    }

    public void setSqlType(String sqlType) {
        this.sqlType = sqlType;
    }
    
    
    
    private String sqlType = "OBJ_CASO_SEG_T";
    
    public String getSQLTypeName() throws SQLException {
        return sqlType;
    }

    public void readSQL(SQLInput stream, String typeName) throws SQLException {
        sqlType = typeName;
    }

    public void writeSQL(SQLOutput stream) throws SQLException
    {
        Date fecha;
        
        stream.writeBigDecimal( new BigDecimal( caseIdInt == null ? "0" : caseIdInt ) );
        stream.writeBigDecimal( new BigDecimal( canoIdInt == null ? "0" : canoIdInt ) );
        stream.writeInt( caseCodCiudadPlantea );
        stream.writeInt( caseCodCiudadSoluciona );
        stream.writeInt( caseCodigoNovedad );
        stream.writeInt( caseCodigoSeguimiento );
        stream.writeInt( caseCiudadDigita );
        stream.writeBigDecimal( new BigDecimal( direIdInt == null ? "0" : direIdInt ) );
        
        fecha = Util.parseDate( caseFechaSeguimiento, "dd/MM/yyyy hh:mm a" );
        stream.writeTimestamp( fecha == null ? null : new java.sql.Timestamp( fecha.getTime() ) );
        
        stream.writeString( caseFrioTcc );
        stream.writeString( caseFrioCliente );
        stream.writeString( caseSeguimiento );
        stream.writeString( caseFrioVaSeguimiento );
        stream.writeString( caseFrioAplicaSeguimiento );
        
        fecha = Util.parseDate( caseFechaSolucion );
        stream.writeTimestamp( fecha == null ? null : new java.sql.Timestamp( fecha.getTime() ) );
        
        stream.writeString( caseObservaciones );
        stream.writeString( caseAnulado );
        
        fecha = Util.parseDate( caseFechaAnulado );
        stream.writeTimestamp( fecha == null ? null : new java.sql.Timestamp( fecha.getTime() ) );
        
        stream.writeString( caseEsSolucion );
        stream.writeString( caseAreaParticipe );
        stream.writeString( caseFrioParticipe );
        stream.writeString( caseInformado );
        stream.writeInt( caseTipoSolucion );
        stream.writeString( caseNuevaDireccion );
        
        fecha = Util.parseDate( caseFechaReofrecimiento );
        stream.writeTimestamp( fecha == null ? null : new java.sql.Timestamp( fecha.getTime() ) );
        
        fecha = Util.parseDate( caseHoraInicioReofrecimiento, "dd/MM/yyyy HH:mm" );
        stream.writeTimestamp( fecha == null ? null : new java.sql.Timestamp( fecha.getTime() ) );
        
        fecha = Util.parseDate( caseHoraFinalReofrecimiento, "dd/MM/yyyy HH:mm" );
        stream.writeTimestamp( fecha == null ? null : new java.sql.Timestamp( fecha.getTime() ) );
        
        stream.writeString( caseReofrecimientoInformado );
        stream.writeString( caseSegInconsistente );
        stream.writeString( caseInconsistencia );
        stream.writeString( caseUsuario );
        stream.writeInt( ticoIdInt );
        stream.writeString( caseContactoCliente );
        stream.writeString( caseTipoSolucionProd );
        stream.writeString( caseTipoSeguimiento );
        stream.writeInt( caseCanalSolucion );
    }
    
    /* Método para imprimir los atributos del objeto con los valores correspondientes
     * que le estén llegando desde el cliente */
    public String printR()
    {
        String result = "";

        // determine fields declared in this class only (no fields of superclass)
        Field[] fields = this.getClass().getDeclaredFields();

        //print field names paired with their values
        for ( Field field : fields  )
        {
            try {
                result += field.getName() + " : " + field.get( this ) + "\n";
            }
            catch ( IllegalAccessException ex ) {
                System.out.println(ex);
            }
        }

        return result;
    }
}