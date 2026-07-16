package fr.ada.java_blog.model;

public class Categorie {
    private Integer id;
    private String nom;
    private String description;

    public Categorie(Integer id, String nom, String description) {
        this.id = id;
        this.nom = nom;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

}