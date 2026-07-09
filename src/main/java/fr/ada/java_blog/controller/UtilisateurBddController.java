package fr.ada.java_blog.controller;

import fr.ada.java_blog.model.UtilisateurBdd;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
public class UtilisateurBddController {
    private final JdbcTemplate jdbcTemplate;

    public UtilisateurBddController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/utilisateurs")
    public List<UtilisateurBdd> utilisateurs() {
        return jdbcTemplate.query(
                """
                        SELECT id, pseudo, mail, mdp
                        FROM users
                        ORDER BY id
                        """,
                (rs, rowNum) -> {
                    return new UtilisateurBdd(
                            rs.getInt("id"),
                            rs.getString("pseudo"),
                            rs.getString("mail"),
                            rs.getString("mdp"));
                });
    }

    @GetMapping("/utilisateurs/{id}")
    public UtilisateurBdd utilisateurById(@PathVariable int id) {
        return jdbcTemplate.queryForObject(
                """
                        SELECT id, pseudo, mail, mdp
                        FROM users
                        WHERE id = ?
                        """,
                (rs, rowNum) -> new UtilisateurBdd(
                        rs.getInt("id"),
                        rs.getString("pseudo"),
                        rs.getString("mail"),
                        rs.getString("mdp")),
                id);
    }

    @PostMapping("/utilisateurs")
    public UtilisateurBdd addUtilisateur(@RequestBody UtilisateurBdd addUtilisateur) {
        jdbcTemplate.update(
                """
                        INSERT INTO users
                        (pseudo, mail, mdp)
                        VALUES
                        (?, ?, ?)
                        """,
                addUtilisateur.getPseudo(),
                addUtilisateur.getMail(),
                addUtilisateur.getMdp());
        return addUtilisateur;
    }

    @PutMapping("/utilisateurs/{id}")
    public UtilisateurBdd updateUtilisateur(@PathVariable int id, @RequestBody UtilisateurBdd updateUtilisateur) {
        jdbcTemplate.update(
                """
                        UPDATE users
                        SET pseudo = ?, mail = ?, mdp = ?
                        WHERE id = ?
                        """,
                updateUtilisateur.getPseudo(),
                updateUtilisateur.getMail(),
                updateUtilisateur.getMdp(),
                id);
        return updateUtilisateur;
    }

    @PatchMapping("/utilisateurs/{id}")
    public UtilisateurBdd patchUtilisateur(@PathVariable int id, @RequestBody UtilisateurBdd patchUtilisateur) {
        jdbcTemplate.update(
                """
                        UPDATE users
                        SET pseudo = COALESCE(?, pseudo),
                            mail = COALESCE(?, mail),
                            mdp = COALESCE(?, mdp)
                        WHERE id = ?
                        """,
                patchUtilisateur.getPseudo(),
                patchUtilisateur.getMail(),
                patchUtilisateur.getMdp(),
                id);
        return patchUtilisateur;
    }

    @DeleteMapping("/utilisateurs/{id}")
    public void deleteUtilisateur(@PathVariable int id) {
        jdbcTemplate.update(
                """
                        DELETE FROM users
                        WHERE id = ?
                        """, id);
    }
}