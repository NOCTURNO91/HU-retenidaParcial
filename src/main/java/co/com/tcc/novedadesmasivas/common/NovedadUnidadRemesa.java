package co.com.tcc.novedadesmasivas.common;

import java.io.Serializable;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

/**
 *
 * @author Be-Smart_2
 */
public class NovedadUnidadRemesa implements SQLData, Serializable
{
    private static final long serialVersionUID = 1L;
    
    private String numRemesa;
    private String codigoUnidad;
    private String fechaNovedad;
    private long codNovedad;
    private String novedad;
    private String comentarios;
    private String ceOpPlantea;
    private String proceso;
    private String usuario;
    private String foto;

    public String getNumRemesa() {
        return numRemesa;
    }

    public void setNumRemesa(String numRemesa) {
        this.numRemesa = numRemesa;
    }
    
    public String getCodigoUnidad() {
        return codigoUnidad;
    }

    public void setCodigoUnidad(String codigoUnidad) {
        this.codigoUnidad = codigoUnidad;
    }

    public String getFechaNovedad() {
        return fechaNovedad;
    }

    public void setFechaNovedad(String fechaNovedad) {
        this.fechaNovedad = fechaNovedad;
    }

    public long getCodNovedad() {
        return codNovedad;
    }

    public void setCodNovedad(long codNovedad) {
        this.codNovedad = codNovedad;
    }

    public String getNovedad() {
        return novedad;
    }

    public void setNovedad(String novedad) {
        this.novedad = novedad;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getCeOpPlantea() {
        return ceOpPlantea;
    }

    public void setCeOpPlantea(String ceOpPlantea) {
        this.ceOpPlantea = ceOpPlantea;
    }

    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
    
    
    private String sqlType = "OBJ_CONS_NOVEDADES_T";
    
    public String getSQLTypeName() throws SQLException {
        return sqlType;
    }

    public void readSQL(SQLInput stream, String typeName) throws SQLException {
        sqlType = typeName;
    }

    public void writeSQL(SQLOutput stream) throws SQLException
    {
        stream.writeString( numRemesa );
        stream.writeInt( 0 ); // unneIdInt
        stream.writeInt( 0 ); // idTipoTransporte
        stream.writeTimestamp( null ); // fechaInicioRemesa
        stream.writeTimestamp( null ); // fechaFinRemesa
        stream.writeInt( 0 ); // idEstadoRemesa
        stream.writeLong( 0 ); // idCeOpOrigen
        stream.writeLong( 0 ); // idCeOpDestino
        stream.writeLong( 0 ); // idCiudadOrigen
        stream.writeLong( 0 ); // idCiudadDestino
        stream.writeInt( 0 ); // idTipoServicio
        stream.writeLong( 0 ); // idRemitente
        stream.writeLong( 0 ); // idDestinatario
        stream.writeLong( 0 ); // idCuentaRmesa
        stream.writeLong( 0 ); // idCuentaDestinatario
        stream.writeLong( 0 ); // idSedeRemitente
        stream.writeLong( 0 ); // idSedeDestinatario
        stream.writeInt( 0 ); // idTipoUnidad
        stream.writeString( codigoUnidad );
        stream.writeLong( codNovedad );
        stream.writeInt( 0 ); // idTipoNovedad
        stream.writeLong( 0 ); // idCeOpSoluciona
        stream.writeLong( 0 ); // idCeOpReporta
        stream.writeInt( 0 ); // idEstadoNovedad
        stream.writeInt( 0 ); // idPrioridadNovedad
        stream.writeTimestamp( null ); // fechaInicioNovedad
        stream.writeTimestamp( null ); // fechaFinNovedad
        stream.writeTimestamp( null ); // fechaInicioReofre
        stream.writeTimestamp( null ); // fechaFinReofre
        stream.writeTimestamp( null ); // fechaInicioSolucion
        stream.writeTimestamp( null ); // fechaFinSolucion
        stream.writeInt( 0 ); // idProcesoOperativo
        stream.writeInt( 1 ); // numPag
        stream.writeInt( 1000 ); // numRegs
    }
}