package com.rayan.jobportal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.rayan.jobportal.entity.UserType;
import java.util.Date;

@Entity
@Table(name = "users")
public class User {


    public User(int userID, String email, boolean isActive, String password, Date registerionDate, UserType userTypeId) {
        this.userID = userID;
        this.email = email;
        this.isActive = isActive;
        this.password = password;
        this.registerionDate = registerionDate;
        this.userTypeId = userTypeId;
    }

    public User() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userID;

    @Column(unique = true)
    private String email;

    private boolean isActive;

    @NotEmpty
    private String password;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date registerionDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userTypeId",referencedColumnName = "userTypeId")
    private UserType userTypeId;


    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", email='" + email + '\'' +
                ", isActive=" + isActive +
                ", password='" + password + '\'' +
                ", registerionDate=" + registerionDate +
                ", userTypeId=" + userTypeId +
                '}';
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegisterionDate() {
        return registerionDate;
    }

    public void setRegisterionDate(Date registerionDate) {
        this.registerionDate = registerionDate;
    }

    public UserType getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(UserType userTypeId) {
        this.userTypeId = userTypeId;
    }


}
