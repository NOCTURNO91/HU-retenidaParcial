package co.com.tcc.novedadesmasivas.common;

/**
 *
 * @author be-smart
 */
public class IngresoNovedad
{
    private Remesa[] arrRemesas;
    private SeguimientoNovedad[] arrSeguimientos;
    private ParametroNovedad[] arrNovedades;

    public Remesa[] getArrRemesas() {
        return arrRemesas;
    }

    public void setArrRemesas(Remesa[] arrRemesas) {
        this.arrRemesas = arrRemesas;
    }

    public SeguimientoNovedad[] getArrSeguimientos() {
        return arrSeguimientos;
    }

    public void setArrSeguimientos(SeguimientoNovedad[] arrSeguimientos) {
        this.arrSeguimientos = arrSeguimientos;
    }

    public ParametroNovedad[] getArrNovedades() {
        return arrNovedades;
    }

    public void setArrNovedades(ParametroNovedad[] arrNovedades) {
        this.arrNovedades = arrNovedades;
    }
}