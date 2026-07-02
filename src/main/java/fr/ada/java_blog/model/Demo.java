package fr.ada.java_blog.model;

public class Demo {
    public static void main(String[] args) {

        Auteur e1 = new Auteur("Florian", "flo@mail.com");
        Auteur e2 = new Auteur("Olivier", "oliv@mail.com");

        System.out.println(e1);
        System.out.println(e2);

        Article a1 = new Article("Mon premier titre", "Bonjour le blog");
        Article a2 = new Article("La machine marche", "Un texte encore");

        System.out.println(a1);
        System.out.println(a2);

        a1.publier();

        System.out.println("Après publication :");
        System.out.println(a1);
        System.out.println(a2);

        System.out.println("titre de a2: " + a2.getTitre());
    }
}
