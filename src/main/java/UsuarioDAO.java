package cenaflix.dao;

import cenaflix.model.Usuario;
import cenaflix.util.JPAUtil;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class UsuarioDAO {
    
    private EntityManager em;
    
    public UsuarioDAO() {
        em = JPAUtil.getEntityManager();
    }
    
    public void salvar(Usuario usuario) {
        try {
            em.getTransaction().begin();
            if (usuario.getId() == null) {
                em.persist(usuario);
            } else {
                em.merge(usuario);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
    }
    
    public Usuario buscarPorId(Long id) {
        return em.find(Usuario.class, id);
    }
    
    public Usuario buscarPorLogin(String login) {
        try {
            TypedQuery<Usuario> query = em.createQuery(
                "SELECT u FROM Usuario u WHERE u.login = :login", Usuario.class);
            query.setParameter("login", login);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public Usuario autenticar(String login, String senha) {
        try {
            TypedQuery<Usuario> query = em.createQuery(
                "SELECT u FROM Usuario u WHERE u.login = :login AND u.senha = :senha", 
                Usuario.class);
            query.setParameter("login", login);
            query.setParameter("senha", senha);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public List<Usuario> listarTodos() {
        TypedQuery<Usuario> query = em.createQuery(
            "SELECT u FROM Usuario u ORDER BY u.nome", Usuario.class);
        return query.getResultList();
    }
    
    public void excluir(Long id) {
        try {
            em.getTransaction().begin();
            Usuario usuario = em.find(Usuario.class, id);
            if (usuario != null) {
                em.remove(usuario);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
    }
    
    public void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}