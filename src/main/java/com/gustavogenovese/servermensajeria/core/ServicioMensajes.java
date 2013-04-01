package com.gustavogenovese.servermensajeria.core;

import com.gustavogenovese.servermensajeria.entidades.dtos.MensajeDTO;
import java.util.List;

/**
 *
 * @author gus
 */
public interface ServicioMensajes {

    boolean enviarMensaje(String remitenteId, String destinatarioNombre, String mensaje);
    List<MensajeDTO> listarMensajesPara(String destinatarioId);
    List<MensajeDTO> listarMensajesDe(String remitenteId);
}
