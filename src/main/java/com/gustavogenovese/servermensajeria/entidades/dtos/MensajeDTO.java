package com.gustavogenovese.servermensajeria.entidades.dtos;

import java.io.Serializable;
import org.jdto.annotation.Source;
import org.jdto.mergers.DateFormatMerger;

/**
 *
 * @author gus
 */
public class MensajeDTO implements Serializable{

    private String id;

    @Source("remitente.usuario")
    private String de;

    @Source("destinatario.usuario")
    private String para;

    private String mensaje;

    @Source(value="fecha", merger=DateFormatMerger.class, mergerParam="dd/MM/yyyy HH:mm:ss")
    private String fecha;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDe() {
        return de;
    }

    public void setDe(String de) {
        this.de = de;
    }

    public String getPara() {
        return para;
    }

    public void setPara(String para) {
        this.para = para;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
