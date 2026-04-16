package com.stackoverflow.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Lob
    @Column(name = "profile_picture")
    private byte[] profilePicture;

    @Enumerated(EnumType.STRING)
    @Column(name = "department", length = 50)
    private Department department;

    @Column(name = "is_admin", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isAdmin = false;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public byte[] getProfilePicture() { return profilePicture; }
    public void setProfilePicture(byte[] profilePicture) { this.profilePicture = profilePicture; }
    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }
    public Boolean getIsAdmin() { return isAdmin != null ? isAdmin : false; }
    public void setIsAdmin(Boolean isAdmin) { this.isAdmin = isAdmin; }
}
