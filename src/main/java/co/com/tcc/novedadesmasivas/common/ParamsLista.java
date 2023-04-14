package co.com.tcc.novedadesmasivas.common;

/**
 *
 * @author Be-Smart_2
 */
public class ParamsLista
{
    private String tipoLista;
    
    /** 
    * Atributos para presentación en cliente.
    */
    private String idInt;
    private String descripcion;
    
    private String tercIdInt;
    private int unneIdInt;
    private String usuario;
    
    /* Parámetros de expansión en caso de futuros requerimientos. */
    private String expansionUno;
    private String expansionDos;
    private String expansionTres;
    private String expansionCuatro;
    
    private String procIdInt;

    
    public String getTipoLista() {
        return tipoLista;
    }

    public void setTipoLista(String tipoLista) {
        this.tipoLista = tipoLista;
    }

    public String getIdInt() {
        return idInt;
    }

    public void setIdInt(String idInt) {
        this.idInt = idInt;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTercIdInt() {
        return tercIdInt;
    }

    public void setTercIdInt(String tercIdInt) {
        this.tercIdInt = tercIdInt;
    }

    public int getUnneIdInt() {
        return unneIdInt;
    }

    public void setUnneIdInt(int unneIdInt) {
        this.unneIdInt = unneIdInt;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getExpansionUno() {
        return expansionUno;
    }

    public void setExpansionUno(String expansionUno) {
        this.expansionUno = expansionUno;
    }

    public String getExpansionDos() {
        return expansionDos;
    }

    public void setExpansionDos(String expansionDos) {
        this.expansionDos = expansionDos;
    }

    public String getExpansionTres() {
        return expansionTres;
    }

    public void setExpansionTres(String expansionTres) {
        this.expansionTres = expansionTres;
    }

    public String getExpansionCuatro() {
        return expansionCuatro;
    }

    public void setExpansionCuatro(String expansionCuatro) {
        this.expansionCuatro = expansionCuatro;
    }
    
    public String getProcIdInt() {
        return procIdInt;
    }

    public void setProcIdInt(String procIdInt) {
        this.procIdInt = procIdInt;
    }
}