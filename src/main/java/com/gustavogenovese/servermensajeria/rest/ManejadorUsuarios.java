package com.gustavogenovese.servermensajeria.rest;

import com.gustavogenovese.servermensajeria.core.ServicioUsuarios;
import com.gustavogenovese.servermensajeria.core.Sesiones;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author gus
 */
@Component
@Path("/usuarios")
public class ManejadorUsuarios {

    @Autowired
    private ServicioUsuarios servicioUsuarios;

    /**
     * Registrar nuevo usuario
     *
     * @param usuario
     * @param password
     * @return El exito o no de la operacion
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/registrarusuario")
    public Response registrarUsuario(@QueryParam("usuario") String usuario,
                                     @QueryParam("password") String password) {
        boolean resultado = servicioUsuarios.agregarUsuario(usuario, password);
        if (resultado) {
            return Response.status(Response.Status.CREATED).entity(true).build();
        } else {
            return Response.status(Response.Status.CONFLICT).entity(false).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/login")
    public Response autenticarUsuario(@QueryParam("usuario") String usuario,
                                      @QueryParam("password") String password) {
        String id = servicioUsuarios.autenticarUsuario(usuario, password);

        if (id != null) {
            String token = Sesiones.getInstance().nuevaSesion(id);
            return Response.status(Response.Status.OK).entity(token).build();
        }
        return Response.status(Response.Status.FORBIDDEN).entity("").build();
    }
}
