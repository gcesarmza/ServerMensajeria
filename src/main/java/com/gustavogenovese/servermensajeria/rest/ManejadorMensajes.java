package com.gustavogenovese.servermensajeria.rest;

import com.gustavogenovese.servermensajeria.core.ServicioMensajes;
import com.gustavogenovese.servermensajeria.core.ServicioUsuarios;
import com.gustavogenovese.servermensajeria.core.Sesiones;
import com.gustavogenovese.servermensajeria.entidades.Mensaje;
import com.gustavogenovese.servermensajeria.entidades.Usuario;
import com.gustavogenovese.servermensajeria.entidades.dtos.MensajeDTO;
import java.util.Collections;
import java.util.List;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jdto.DTOBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author gus
 */
@Component
@Path("/mensajes")
public class ManejadorMensajes {

    @Autowired
    private ServicioUsuarios servicioUsuarios;

    @Autowired
    private ServicioMensajes servicioMensajes;

    @Autowired
    private DTOBinder binder;

    @POST
    @Path("/listamensajes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listaMensajes(@QueryParam("sesion") String sesion){
        String usuarioId = Sesiones.getInstance().getUsuarioId(sesion);
        if (usuarioId == null){
            return Response.status(Response.Status.UNAUTHORIZED).entity(Collections.emptyList()).build();
        }

        Usuario u = servicioUsuarios.buscarUsuarioPorId(usuarioId);
        List<Mensaje> mensajes = servicioMensajes.listarMensajesPara(u);
        List<MensajeDTO> mensajesSerializados = binder.bindFromBusinessObjectList(MensajeDTO.class, mensajes);
        return Response.status(Response.Status.OK).entity(mensajesSerializados).build();
    }

    @POST
    @Path("/listamensajesenviados")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listaMensajesEnviados(@QueryParam("sesion") String sesion){
        String usuarioId = Sesiones.getInstance().getUsuarioId(sesion);
        if (usuarioId == null){
            return Response.status(Response.Status.UNAUTHORIZED).entity(Collections.emptyList()).build();
        }

        Usuario u = servicioUsuarios.buscarUsuarioPorId(usuarioId);
        List<Mensaje> mensajes = servicioMensajes.listarMensajesDe(u);
        List<MensajeDTO> mensajesSerializados = binder.bindFromBusinessObjectList(MensajeDTO.class, mensajes);
        return Response.status(Response.Status.OK).entity(mensajesSerializados).build();
    }

    @POST
    @Path("/nuevomensaje")
    @Produces(MediaType.APPLICATION_JSON)
    public Response enviarMensaje(@QueryParam("sesion") String sesion,
                                 @QueryParam("destinatario") String destinatario,
                                 @QueryParam("mensaje") String mensaje){
        String usuarioId = Sesiones.getInstance().getUsuarioId(sesion);
        if (usuarioId == null){
            return Response.status(Response.Status.UNAUTHORIZED).entity(false).build();
        }
        Usuario remitente = servicioUsuarios.buscarUsuarioPorId(usuarioId);
        Usuario dest = servicioUsuarios.buscarUsuarioPorUsuario(destinatario);
        if (dest == null){
            return Response.status(Response.Status.UNAUTHORIZED).entity(false).build();
        }

        boolean resultado = servicioMensajes.enviarMensaje(remitente, dest, mensaje);
        if (resultado){
            return Response.status(Response.Status.CREATED).entity(true).build();
        }else{
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(true).build();
        }
    }
}
