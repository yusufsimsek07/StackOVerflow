package com.stackoverflow.controller;

import com.stackoverflow.entity.Question;
import com.stackoverflow.entity.Answer;
import com.stackoverflow.entity.Department;
import com.stackoverflow.service.QuestionFacade;
import com.stackoverflow.service.AnswerFacade;
import com.stackoverflow.service.VoteFacade;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;

@Named
@ViewScoped
public class QuestionBean implements Serializable {
    @Inject
    private QuestionFacade questionFacade;
    
    @Inject
    private AnswerFacade answerFacade;
    
    @Inject
    private VoteFacade voteFacade;
    
    @Inject
    private AuthBean authBean;

    private Long questionId;
    private Question question;
    private String newAnswerContent;
    
    private String newQuestionTitle;
    private String newQuestionContent;
    private Department newQuestionDepartment;

    public void loadQuestion() {
        if (questionId != null) {
            question = questionFacade.find(questionId);
            if (question != null) {
                questionFacade.incrementViewCount(questionId);
            }
        }
    }

    public String submitQuestion() {
        if (!authBean.isLoggedIn()) return "login?faces-redirect=true";
        
        Question q = new Question();
        q.setTitle(newQuestionTitle);
        q.setContent(newQuestionContent);
        q.setDepartment(newQuestionDepartment);
        q.setUser(authBean.getCurrentUser());
        
        questionFacade.create(q);
        return "index?faces-redirect=true";
    }

    public void submitAnswer() {
        if (!authBean.isLoggedIn()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Lütfen giriş yapın"));
            return;
        }
        
        Answer a = new Answer();
        a.setContent(newAnswerContent);
        a.setQuestion(question);
        a.setUser(authBean.getCurrentUser());
        a.setIsAccepted(false);
        answerFacade.create(a);
        
        newAnswerContent = "";
        loadQuestion(); 
    }
    
    public void upvoteQuestion() {
        if (!authBean.isLoggedIn()) return;
        voteFacade.castVote(authBean.getCurrentUser(), question, null, (short)1);
        loadQuestion();
    }
    
    public void downvoteQuestion() {
        if (!authBean.isLoggedIn()) return;
        voteFacade.castVote(authBean.getCurrentUser(), question, null, (short)-1);
        loadQuestion();
    }

    public boolean isCanEdit() {
        if (!authBean.isLoggedIn() || question == null || question.getUser() == null) return false;
        com.stackoverflow.entity.User currentUser = authBean.getCurrentUser();
        return currentUser.getIsAdmin() || currentUser.getId().equals(question.getUser().getId());
    }

    public String deleteQuestion() {
        if (!isCanEdit()) return "index?faces-redirect=true";
        questionFacade.deleteQuestionWithDependencies(questionId);
        return "index?faces-redirect=true";
    }

    public Long getQuestionId() { return questionId; }
    public void setQuestionId(Long questionId) { this.questionId = questionId; }
    public Question getQuestion() { return question; }
    public void setQuestion(Question question) { this.question = question; }
    public String getNewAnswerContent() { return newAnswerContent; }
    public void setNewAnswerContent(String newAnswerContent) { this.newAnswerContent = newAnswerContent; }
    public String getNewQuestionTitle() { return newQuestionTitle; }
    public void setNewQuestionTitle(String newQuestionTitle) { this.newQuestionTitle = newQuestionTitle; }
    public String getNewQuestionContent() { return newQuestionContent; }
    public void setNewQuestionContent(String newQuestionContent) { this.newQuestionContent = newQuestionContent; }
    public Department getNewQuestionDepartment() { return newQuestionDepartment; }
    public void setNewQuestionDepartment(Department newQuestionDepartment) { this.newQuestionDepartment = newQuestionDepartment; }
}
