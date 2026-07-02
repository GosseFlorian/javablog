package fr.ada.java_blog.model;

public class Demo {
    public static void main(String[] args) {

        Article a1 = new Article("Mon premier article", "Bonjour le blog !");
        Article a2 = new Article("La machine de Turing", "Un texte sur le calcul.");

        System.out.println(a1);
        System.out.println(a2);

        a1.publier();

        System.out.println("Après publication :");
        System.out.println(a1);
        System.out.println(a2);

        System.out.println("Titre de a2 : " + a2.getTitre());
    }
}