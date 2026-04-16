package com.stackoverflow.service;

import com.stackoverflow.entity.Vote;
import com.stackoverflow.entity.User;
import com.stackoverflow.entity.Question;
import com.stackoverflow.entity.Answer;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.NoResultException;

@Stateless
public class VoteFacade {
    @PersistenceContext(unitName = "StackOverflowPU")
    private EntityManager em;

    public void castVote(User user, Question question, Answer answer, short voteType) {
        try {
            String queryStr = "SELECT v FROM Vote v WHERE v.user.id = :userId ";
            if (question != null) {
                queryStr += "AND v.question.id = :entityId";
            } else {
                queryStr += "AND v.answer.id = :entityId";
            }
            
            Vote existing = em.createQuery(queryStr, Vote.class)
                              .setParameter("userId", user.getId())
                              .setParameter("entityId", question != null ? question.getId() : answer.getId())
                              .getSingleResult();
                              
            existing.setVoteType(voteType);
            em.merge(existing);
        } catch (NoResultException e) {
            Vote v = new Vote();
            v.setUser(user);
            v.setQuestion(question);
            v.setAnswer(answer);
            v.setVoteType(voteType);
            em.persist(v);
        }
    }
}
