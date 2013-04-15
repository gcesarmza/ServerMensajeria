package com.gustavogenovese.servermensajeria.core;

import com.gustavogenovese.servermensajeria.entidades.Usuario;
import java.util.UUID;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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
public class ServicioUsuariosImpl implements ServicioUsuarios{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean agregarUsuario(String usuario, String password) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Usuario.class);
        criteria.add(Restrictions.eq("usuario", usuario));
        criteria.setMaxResults(1);
        if (criteria.uniqueResult() != null){
            return false;
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setId(UUID.randomUUID().toString());
        nuevoUsuario.setUsuario(usuario);
        nuevoUsuario.setPassword(password);

        sessionFactory.getCurrentSession().save(nuevoUsuario);
        return true;
    }

    @Override
    public String autenticarUsuario(String usuario, String password) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Usuario.class);
        criteria.add(Restrictions.eq("usuario", usuario));
        criteria.add(Restrictions.eq("password", password));
        criteria.setMaxResults(1);
        Usuario u = (Usuario)criteria.uniqueResult();
        if (u == null){
            return null;
        }
        return u.getId();
    }

    @Override
    public Usuario buscarUsuarioPorId(String id) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Usuario.class);
        criteria.add(Restrictions.idEq(id));
        criteria.setMaxResults(1);
        return (Usuario)criteria.uniqueResult();
    }

    @Override
    public Usuario buscarUsuarioPorUsuario(String usuario) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Usuario.class);
        criteria.add(Restrictions.eq("usuario", usuario));
        criteria.setMaxResults(1);
        return (Usuario)criteria.uniqueResult();
    }

    @Override
    public boolean registrarTokenPush(String idUsuario, String token) {
        if (!StringUtils.hasText(idUsuario) || !StringUtils.hasText(token)){
            return false;
        }
        Usuario usuario = buscarUsuarioPorId(idUsuario);
        if (usuario == null){
            return false;
        }
        usuario.setToken(token);
        sessionFactory.getCurrentSession().saveOrUpdate(usuario);
        return true;
    }

}
