package com.gustavogenovese.servermensajeria.core;

import com.gustavogenovese.servermensajeria.entidades.Usuario;

/**
 *
 * @author gus
 */
public interface ServicioUsuarios {
    Usuario buscarUsuarioPorId(String id);
    Usuario buscarUsuarioPorUsuario(String usuario);

    boolean agregarUsuario(String usuario, String password);
    String autenticarUsuario(String usuario, String password);
}
