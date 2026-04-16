package com.stackoverflow.service;

import com.stackoverflow.entity.Question;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class QuestionFacade {
    @PersistenceContext(unitName = "StackOverflowPU")
    private EntityManager em;

    public void create(Question question) {
        em.persist(question);
    }

    public void edit(Question question) {
        em.merge(question);
    }

    public Question find(Long id) {
        Question q = em.find(Question.class, id);
        if (q != null) {
            Number score = (Number) em.createQuery("SELECT COALESCE(SUM(v.voteType), 0) FROM Vote v WHERE v.question.id = :id")
                    .setParameter("id", id).getSingleResult();
            q.setScore(score != null ? score.intValue() : 0);
        }
        return q;
    }

    public List<Question> findAll() {
        return em.createQuery("SELECT q FROM Question q ORDER BY q.createdAt DESC", Question.class).getResultList();
    }

    public List<Question> findByDepartment(com.stackoverflow.entity.Department dept) {
        return em.createQuery("SELECT q FROM Question q WHERE q.department = :dept ORDER BY q.createdAt DESC", Question.class)
                 .setParameter("dept", dept)
                 .getResultList();
    }
    
    public void incrementViewCount(Long id) {
        em.createQuery("UPDATE Question q SET q.viewCount = COALESCE(q.viewCount, 0) + 1 WHERE q.id = :id")
          .setParameter("id", id).executeUpdate();
    }

    public void deleteQuestionWithDependencies(Long questionId) {
        Question q = em.find(Question.class, questionId);
        if (q != null) {
            // First destroy all votes linking to this question
            em.createQuery("DELETE FROM Vote v WHERE v.question.id = :qid")
              .setParameter("qid", questionId).executeUpdate();

            // Then destroy all votes linking to the answers of this question
            em.createQuery("DELETE FROM Vote v WHERE v.answer.id IN (SELECT a.id FROM Answer a WHERE a.question.id = :qid)")
              .setParameter("qid", questionId).executeUpdate();

            // JPA will handle removing answers via CascadeType.ALL, orphanRemoval=true
            // Finally remove the question
            em.remove(q);
        }
    }
}
