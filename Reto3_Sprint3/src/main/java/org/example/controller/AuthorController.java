package org.example.controller;

import org.example.model.Article;
import org.example.model.ScholarService;
import org.example.view.AuthorView;
import java.util.List;

public class AuthorController {
    private final ScholarService model;
    private final AuthorView view;

    public AuthorController(ScholarService model, AuthorView view) {
        this.model = model;
        this.view = view;
    }

    public void searchArticlesFor(String authorName) {
        try {
            List<Article> results = model.searchArticles(authorName);

            if (!results.isEmpty()) {
                results.forEach(view::displayArticle);
            } else {
                view.displayError("No articles found for: " + authorName);
            }

        } catch (Exception e) {
            view.displayError("Error searching for " + authorName + ": " + e.getMessage());
        }
    }

}
