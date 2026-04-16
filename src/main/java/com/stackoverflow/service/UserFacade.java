package com.stackoverflow.service;

import com.stackoverflow.entity.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.NoResultException;

@Stateless
public class UserFacade {
    @PersistenceContext(unitName = "StackOverflowPU")
    private EntityManager em;

    public void create(User user) {
        em.persist(user);
    }
    
    public void update(User user) {
        em.merge(user);
    }

    public User findByUsername(String username) {
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                     .setParameter("username", username)
                     .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public User login(String username, String passwordHash) {
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.passwordHash = :hash", User.class)
                     .setParameter("username", username)
                     .setParameter("hash", passwordHash)
                     .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
