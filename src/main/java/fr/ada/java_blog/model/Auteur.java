package fr.ada.java_blog.model;

public class Auteur {
    private String nom;
    private String email;

    public Auteur(String nom, String email) {
        this.nom = nom;
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Auteur{nom='" + nom + "', email='" + email + "}";
    }
}
