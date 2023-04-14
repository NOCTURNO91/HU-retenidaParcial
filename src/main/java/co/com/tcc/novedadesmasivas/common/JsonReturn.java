package co.com.tcc.novedadesmasivas.common;

import java.util.List;

/**
 *
 * @author Be-Smart_2
 */
public class JsonReturn
{
    private int codigo;
    private String mensaje;
    
    private Object object;
    private List<?> list;
    
    public int getCodigo() {
        return codigo;
    }

    public JsonReturn setCodigo(int codigo)
    {
        this.codigo = codigo;
        return this;
    }

    public String getMensaje() {
        return mensaje;
    }

    public JsonReturn setMensaje(String mensaje)
    {
        this.mensaje = mensaje;
        return this;
    }

    public Object getObject() {
        return object;
    }

    public JsonReturn setObject(Object object)
    {
        this.object = object;
        return this;
    }

    public List<?> getList() {
        return list;
    }

    public JsonReturn setList(List<?> list)
    {
        this.list = list;
        return this;
    }
}
