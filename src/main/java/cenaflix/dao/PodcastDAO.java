package cenaflix.dao;

import cenaflix.model.Podcast;
import cenaflix.util.JPAUtil;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class PodcastDAO {
    
    private EntityManager em;
    
    public PodcastDAO() {
        em = JPAUtil.getEntityManager();
    }
    
    public void inserir(Podcast podcast) {
        try {
            em.getTransaction().begin();
            em.persist(podcast);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
    }
    
    public void atualizar(Podcast podcast) {
        try {
            em.getTransaction().begin();
            em.merge(podcast);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
    }
    
    public Podcast buscarPorId(Long id) {
        return em.find(Podcast.class, id);
    }
    
    public List<Podcast> listarTodos() {
        TypedQuery<Podcast> query = em.createQuery(
            "SELECT p FROM Podcast p ORDER BY p.produtor, p.numeroEpisodio", 
            Podcast.class);
        return query.getResultList();
    }
    
    public List<Podcast> filtrarPorProdutor(String produtor) {
        TypedQuery<Podcast> query = em.createQuery(
            "SELECT p FROM Podcast p WHERE LOWER(p.produtor) LIKE LOWER(:produtor) " +
            "ORDER BY p.produtor, p.numeroEpisodio", Podcast.class);
        query.setParameter("produtor", "%" + produtor + "%");
        return query.getResultList();
    }
    
    public void excluir(Long id) {
        try {
            em.getTransaction().begin();
            Podcast podcast = em.find(Podcast.class, id);
            if (podcast != null) {
                em.remove(podcast);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
    }
    
    public Long contar() {
        TypedQuery<Long> query = em.createQuery(
            "SELECT COUNT(p) FROM Podcast p", Long.class);
        return query.getSingleResult();
    }
    
    public void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}