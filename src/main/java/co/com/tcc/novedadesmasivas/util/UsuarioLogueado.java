package co.com.tcc.novedadesmasivas.util;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;
import weblogic.security.Security;

import weblogic.security.spi.WLSGroup;
import weblogic.security.spi.WLSUser;

public class UsuarioLogueado {

    public static String getUsuario() {
        Subject subject = Security.getCurrentSubject();
        for (Principal principal : subject.getPrincipals()) {
            if ((principal instanceof WLSUser)) {
                return ((WLSUser) principal).getName();
            }
        }
        return null;

    }

    public static boolean hasRol(String rol) {
        String rolWbl;

        Subject subject = Security.getCurrentSubject();
        for (Principal principal : subject.getPrincipals()) {
            if (principal instanceof WLSGroup) {
                rolWbl = (((WLSGroup) principal).getName());
                if (rolWbl.equals(rol)) {
                    return true;
                }
            }

        }
        return false;
    }

    public static List<Object> listarRoles() {

        List<Object> roles = new ArrayList<Object>();

        String rolWbl;
        Subject subject = Security.getCurrentSubject();
        for (Principal principal : subject.getPrincipals()) {
            if (principal instanceof WLSGroup) {
                rolWbl = (((WLSGroup) principal).getName());
                roles.add(rolWbl);
            }

        }

        return roles;
    }

}
