package com.example.item_service.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Item {




    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "photo")
    private String photo;
private String useremail;
    private String type;

    @Column(name = "description", length = 1000)

    private String description;
    private String location; // lieu de perte
    private LocalDate date;  // date de perte

    @Enumerated(EnumType.STRING)
    private Status status; // LOST, FOUND, CLAIMED, RETURNED

   // private Long userId; // ID de l'utilisateur qui a déclaré


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }



    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }




}
