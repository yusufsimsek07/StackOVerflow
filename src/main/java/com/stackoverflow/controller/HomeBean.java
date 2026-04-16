package com.stackoverflow.controller;

import com.stackoverflow.entity.Question;
import com.stackoverflow.entity.Department;
import com.stackoverflow.service.QuestionFacade;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class HomeBean {
    @Inject
    private QuestionFacade questionFacade;

    private List<Question> questions;
    private String pageTitle = "Tüm Sorular";

    @PostConstruct
    public void init() {
        String deptParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("dept");
        if (deptParam != null && !deptParam.isEmpty()) {
            try {
                Department dept = Department.valueOf(deptParam);
                questions = questionFacade.findByDepartment(dept);
                pageTitle = dept.getLabel() + " Departmanı Soruları";
            } catch (IllegalArgumentException e) {
                questions = questionFacade.findAll();
            }
        } else {
            questions = questionFacade.findAll();
        }
    }

    public List<Question> getQuestions() { return questions; }
    public String getPageTitle() { return pageTitle; }
}
