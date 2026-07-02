package fr.ada.java_blog.model;

import java.time.LocalDateTime;

public class ArticleBdd {

    private int id;
    private String titre;
    private String contenu;
    private boolean publie;
    private LocalDateTime date;

    public ArticleBdd(int id, String titre, String contenu, boolean publie, LocalDateTime date) {
        this.id = id;
        this.titre = titre;
        this.contenu = contenu;
        this.publie = publie;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getContenu() {
        return contenu;
    }

    public boolean isPublie() {
        return publie;
    }

    public LocalDateTime getDate() {
        return date;
    }
}