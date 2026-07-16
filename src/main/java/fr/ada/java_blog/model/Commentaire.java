package fr.ada.java_blog.model;

import java.time.LocalDateTime;

public class Commentaire {
    private Integer id;
    private String contenu;
    private LocalDateTime date;

    public Commentaire(Integer id, String contenu, LocalDateTime date) {
        this.id = id;
        this.contenu = contenu;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public String getContenu() {
        return contenu;
    }

    public LocalDateTime getDate() {
        return date;
    }

}