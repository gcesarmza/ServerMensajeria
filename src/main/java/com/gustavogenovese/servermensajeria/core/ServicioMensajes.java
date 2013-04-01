package com.gustavogenovese.servermensajeria.core;

import com.gustavogenovese.servermensajeria.entidades.Mensaje;
import com.gustavogenovese.servermensajeria.entidades.Usuario;
import java.util.List;

/**
 *
 * @author gus
 */
public interface ServicioMensajes {

    boolean enviarMensaje(Usuario remitente, Usuario destinatario, String mensaje);
    List<Mensaje> listarMensajesPara(Usuario destinatario);
    List<Mensaje> listarMensajesDe(Usuario remitente);
}
