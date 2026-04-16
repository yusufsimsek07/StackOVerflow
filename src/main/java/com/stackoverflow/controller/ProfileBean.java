package com.stackoverflow.controller;

import com.stackoverflow.entity.User;
import com.stackoverflow.service.UserFacade;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.Part;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.InputStream;
import java.io.Serializable;

@Named
@ViewScoped
public class ProfileBean implements Serializable {
    
    @Inject
    private UserFacade userFacade;
    
    @Inject
    private AuthBean authBean;

    private Part uploadedFile;

    public void uploadProfilePicture() {
        if (uploadedFile != null && authBean.isLoggedIn()) {
            try (InputStream input = uploadedFile.getInputStream()) {
                byte[] bytes = input.readAllBytes();
                User user = authBean.getCurrentUser();
                user.setProfilePicture(bytes);
                userFacade.update(user);
                
                // Keep session user updated
                authBean.setCurrentUser(userFacade.findByUsername(user.getUsername()));
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Profil fotoğrafınız güncellendi."));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Fotoğraf yüklenemedi."));
            }
        }
    }

    public Part getUploadedFile() { return uploadedFile; }
    public void setUploadedFile(Part uploadedFile) { this.uploadedFile = uploadedFile; }
}
