package fr.ada.java_blog.model;

public class UtilisateurBdd {
    private Integer id;
    private String pseudo;
    private String mail;
    private String mdp;

    public UtilisateurBdd(Integer id, String pseudo, String mail, String mdp) {
        this.id = id;
        this.pseudo = pseudo;
        this.mail = mail;
        this.mdp = mdp;
    }

    public Integer getId() {
        return id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getMail() {
        return mail;
    }

    public String getMdp() {
        return mdp;
    }
}
