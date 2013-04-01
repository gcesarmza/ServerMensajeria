package com.gustavogenovese.servermensajeria.core;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author gus
 */
public class Sesiones {

    private static Sesiones instancia = null;

    private Map<String, String> tokens;

    private Sesiones(){
        tokens = new HashMap<String,String>();
    }

    public static synchronized Sesiones getInstance(){
        if (instancia == null){
            instancia = new Sesiones();
        }
        return instancia;
    }

    public String nuevaSesion(String usuarioId){
        String token = UUID.randomUUID().toString();
        tokens.put(token, usuarioId);
        return token;
    }

    public String getUsuarioId(String token){
        return tokens.get(token);
    }
}
