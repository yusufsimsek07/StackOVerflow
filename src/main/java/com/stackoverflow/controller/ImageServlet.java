package com.stackoverflow.controller;

import com.stackoverflow.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/image")
public class ImageServlet extends HttpServlet {
    @PersistenceContext(unitName = "StackOverflowPU")
    private EntityManager em;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userIdStr = request.getParameter("userId");
        if (userIdStr != null && !userIdStr.isEmpty()) {
            try {
                Long userId = Long.valueOf(userIdStr);
                User user = em.find(User.class, userId);
                if (user != null) {
                    if (user.getProfilePicture() != null) {
                        response.setContentType("image/jpeg");
                        response.getOutputStream().write(user.getProfilePicture());
                    } else {
                        response.sendRedirect("https://ui-avatars.com/api/?name=" + user.getUsername() + "&background=random");
                    }
                    return;
                }
            } catch (Exception e) {
                // Ignore exception and serve default below
            }
        }
        response.sendRedirect("https://ui-avatars.com/api/?name=User&background=random");
    }
}
