package com.gustavogenovese.servermensajeria.core;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.gustavogenovese.servermensajeria.entidades.Usuario;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 *
 * @author gus
 */
@Service
@Transactional
public class ServicioNotificacionesPushImpl implements ServicioNotificacionesPush{

    private final static String SENDER = "347223524006";
    private final static String SERVER_KEY = "AIzaSyBig0bAZ83A4KDvQ8zMGClINLZGxbW_Wac";


    @Autowired
    private ServicioUsuarios servicioUsuarios;

    @Override
    public void notificarNuevoMensaje(String usuarioId, String mensajeId) {
        if (!StringUtils.hasText(usuarioId)){
            return;
        }

        Usuario usuario = servicioUsuarios.buscarUsuarioPorId(usuarioId);
        if (StringUtils.hasText(usuario.getToken() )){
            Sender sender = new Sender(SERVER_KEY);
            Message message = new Message.Builder()
                    .addData("tipoMensaje", "nuevoMensaje")
                    .addData("mensajeId", mensajeId)
                    .build();
            try{
                Result result = sender.send(message, usuario.getToken(), 5);
            }catch(IOException ex){
            }
        }
    }

}
