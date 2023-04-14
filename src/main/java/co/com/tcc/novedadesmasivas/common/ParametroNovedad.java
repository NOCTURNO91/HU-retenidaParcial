package co.com.tcc.novedadesmasivas.common;

/**
 *
 * @author be-smart
 */
public class ParametroNovedad
{
    private String canoIdInt;
    private String observaciones;
    private int idCeOpEjecuta;
    private int livaCausalNov;
    private String usuario;

    public String getCanoIdInt() {
        return canoIdInt;
    }

    public void setCanoIdInt(String canoIdInt) {
        this.canoIdInt = canoIdInt;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getIdCeOpEjecuta() {
        return idCeOpEjecuta;
    }

    public void setIdCeOpEjecuta(int idCeOpEjecuta) {
        this.idCeOpEjecuta = idCeOpEjecuta;
    }

    public int getLivaCausalNov() {
        return livaCausalNov;
    }

    public void setLivaCausalNov(int livaCausalNov) {
        this.livaCausalNov = livaCausalNov;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}