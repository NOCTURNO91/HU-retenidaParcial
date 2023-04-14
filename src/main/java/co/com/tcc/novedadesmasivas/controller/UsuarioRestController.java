package co.com.tcc.novedadesmasivas.controller;

import co.com.tcc.novedadesmasivas.util.UsuarioLogueado;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioRestController {

    private static final String API = "api/usuario/";

    @RequestMapping(value = API + "roles", method = RequestMethod.GET)
    public List<Object> listarRoles() {

        return UsuarioLogueado.listarRoles();
    }

}
