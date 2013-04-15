package com.gustavogenovese.servermensajeria.core;

/**
 *
 * @author gus
 */
public interface ServicioNotificacionesPush {

    void notificarNuevoMensaje(String usuarioId, String mensajeId);
}
