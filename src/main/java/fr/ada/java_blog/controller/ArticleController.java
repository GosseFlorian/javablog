package fr.ada.java_blog.controller;

import fr.ada.java_blog.model.Article;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class ArticleController {
    private List<Article> articles = ListArticle();

    private List<Article> ListArticle() {
        Article a1 = new Article("Mon premier article", "Bonjour toto");
        Article a2 = new Article("Ma machine de guerre", "Text de fou");
        Article a3 = new Article("3eme article", "Pas d'inspi");
        a1.publier();
        return List.of(a1, a2, a3);
    }

    @GetMapping("/articles")
    public List<Article> Lister() {
        return articles;
    }

    @GetMapping("/articles/count")
    public int NombreArticles() {
        return articles.size();
    }

    @GetMapping("/articles/{id}")
    public Article getArticleById(@PathVariable int id) {
        return articles.get(id);
    }

}
