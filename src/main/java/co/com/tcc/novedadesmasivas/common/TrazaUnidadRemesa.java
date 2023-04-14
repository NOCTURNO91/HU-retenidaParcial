package co.com.tcc.novedadesmasivas.common;

/**
 *
 * @author Be-Smart_2
 */
public class TrazaUnidadRemesa
{
    private String fechaTraza;
    private String proceso;
    private String ciudadProceso;
    private String tipoUbicacion;
    private String codBarrasUbicacion;
    private String descUbicacion;
    private String usuario;
    private String estadoTraza;
    private String observaciones;

    public String getFechaTraza() {
        return fechaTraza;
    }

    public void setFechaTraza(String fechaTraza) {
        this.fechaTraza = fechaTraza;
    }

    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public String getCiudadProceso() {
        return ciudadProceso;
    }

    public void setCiudadProceso(String ciudadProceso) {
        this.ciudadProceso = ciudadProceso;
    }

    public String getTipoUbicacion() {
        return tipoUbicacion;
    }

    public void setTipoUbicacion(String tipoUbicacion) {
        this.tipoUbicacion = tipoUbicacion;
    }

    public String getCodBarrasUbicacion() {
        return codBarrasUbicacion;
    }

    public void setCodBarrasUbicacion(String codBarrasUbicacion) {
        this.codBarrasUbicacion = codBarrasUbicacion;
    }

    public String getDescUbicacion() {
        return descUbicacion;
    }

    public void setDescUbicacion(String descUbicacion) {
        this.descUbicacion = descUbicacion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEstadoTraza() {
        return estadoTraza;
    }

    public void setEstadoTraza(String estadoTraza) {
        this.estadoTraza = estadoTraza;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}