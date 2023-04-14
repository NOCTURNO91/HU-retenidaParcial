package co.com.tcc.novedadesmasivas.common;

import galileo.base.value.Value;
import galileo.componentes.maestras.dto.ClienteCriterios;

/**
 *
 * @author Be-Smart_2
 */
public class ValueClientes extends Value
{
    private ClienteCriterios clienteCriterios;

    public ClienteCriterios getClienteCriterios() {
        return clienteCriterios;
    }

    public void setClienteCriterios(ClienteCriterios clienteCriterios) {
        this.clienteCriterios = clienteCriterios;
    }
}