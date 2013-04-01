package com.gustavogenovese.servermensajeria.core;

import com.gustavogenovese.servermensajeria.entidades.Mensaje;
import com.gustavogenovese.servermensajeria.entidades.Usuario;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author gus
 */
@Service
@Transactional
public class ServicioMensajesImpl implements ServicioMensajes{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean enviarMensaje(Usuario remitente, Usuario destinatario, String mensaje) {
        Mensaje m = new Mensaje();
        m.setId(UUID.randomUUID().toString());
        m.setRemitente(remitente);
        m.setDestinatario(destinatario);
        m.setMensaje(mensaje);
        m.setFecha(new Date());

        sessionFactory.getCurrentSession().save(m);
        return true;
    }

    @Override
    public List<Mensaje> listarMensajesPara(Usuario destinatario) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Mensaje.class);
        criteria.add(Restrictions.eq("destinatario", destinatario));
        criteria.addOrder(Order.desc("fecha"));
        return criteria.list();
    }

    @Override
    public List<Mensaje> listarMensajesDe(Usuario remitente) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Mensaje.class);
        criteria.add(Restrictions.eq("remitente", remitente));
        criteria.addOrder(Order.desc("fecha"));
        return criteria.list();
    }

}
