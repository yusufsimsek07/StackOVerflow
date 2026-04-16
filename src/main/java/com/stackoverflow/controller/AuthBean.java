package com.stackoverflow.controller;

import com.stackoverflow.entity.User;
import com.stackoverflow.entity.Department;
import com.stackoverflow.service.UserFacade;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import java.security.MessageDigest;
import java.util.Base64;

@Named
@SessionScoped
public class AuthBean implements Serializable {
    @Inject
    private UserFacade userFacade;

    private User currentUser;
    private String username;
    private String password;
    private String email;
    private Department department;

    public Department[] getAvailableDepartments() {
        return Department.values();
    }

    public String login() {
        String hash = hashPassword(password);
        User user = userFacade.login(username, hash);
        if (user != null) {
            currentUser = user;
            return "index?faces-redirect=true";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Giriş başarısız!"));
        return null;
    }

    public String register() {
        User u = new User();
        u.setUsername(username);
        u.setEmail(email);
        u.setPasswordHash(hashPassword(password));
        u.setDepartment(department);
        if ("admin".equalsIgnoreCase(username)) {
            u.setIsAdmin(true);
        }
        try {
            userFacade.create(u);
            currentUser = u;
            return "index?faces-redirect=true";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Kayıt başarısız! Kullanıcı adı veya e-posta zaten kullanımda olabilir."));
            return null;
        }
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        currentUser = null;
        return "index?faces-redirect=true";
    }

    private String hashPassword(String pwd) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(pwd.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public boolean isLoggedIn() { return currentUser != null; }
    
    public User getCurrentUser() { return currentUser; }
    public void setCurrentUser(User currentUser) { this.currentUser = currentUser; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }
}
