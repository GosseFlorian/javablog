package fr.ada.java_blog.controller;

import fr.ada.java_blog.model.Auteur;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuteurController {
    private List<Auteur> auteurs = ListAuteur();

    private List<Auteur> ListAuteur() {
        Auteur a1 = new Auteur("Flo", "flo@email.com");
        Auteur a2 = new Auteur("Olivier", "oliv@email.com");
        return List.of(a1, a2);
    }

    @GetMapping("/auteurs")
    public List<Auteur> Lister() {
        return auteurs;
    }

    @GetMapping("/auteurs/count")
    public int NombreAuteurs() {
        return auteurs.size();
    }

    @GetMapping("/auteurs/{id}")
    public Auteur getAuteurById(@PathVariable int id) {
        return auteurs.get(id);
    }

}
