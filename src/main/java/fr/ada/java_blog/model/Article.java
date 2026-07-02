package fr.ada.java_blog.model;

public class Article {

    private String titre;
    private String contenu;
    private boolean publie;

    public Article(String titre, String contenu) {
        this.titre = titre;
        this.contenu = contenu;
        this.publie = false;
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

    public void publier() {
        this.publie = true;
    }

    @Override
    public String toString() {
        return "Article{titre='" + titre + "', publie=" + publie + "}";
    }
}