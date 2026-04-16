package com.stackoverflow.service;

import com.stackoverflow.entity.Answer;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class AnswerFacade {
    @PersistenceContext(unitName = "StackOverflowPU")
    private EntityManager em;

    public void create(Answer answer) {
        em.persist(answer);
    }

    public void markAsAccepted(Long answerId, Long questionId) {
        em.createQuery("UPDATE Answer a SET a.isAccepted = false WHERE a.question.id = :questionId")
          .setParameter("questionId", questionId).executeUpdate();
        
        em.createQuery("UPDATE Answer a SET a.isAccepted = true WHERE a.id = :answerId")
          .setParameter("answerId", answerId).executeUpdate();
    }
}
