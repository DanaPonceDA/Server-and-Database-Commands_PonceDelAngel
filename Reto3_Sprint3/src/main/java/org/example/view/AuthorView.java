package org.example.view;

import java.util.Scanner;
import org.example.model.Article;

public class AuthorView {
    private final Scanner scanner = new Scanner(System.in);


    public void displayArticle(Article article) {
        System.out.println("\n--- Article Found ---");
        System.out.println(article.toString());
        System.out.println( article.getTitle());
        System.out.println("Authors: " + article.getAuthors());
        System.out.println("Year: " + article.getPublicationDate());
        System.out.println("Link: " + article.getLink());
        System.out.println("Cited by: " + article.getCitedBy());
        System.out.println("---------------------\n");
    }


    public void displayError(String message) {
        System.err.println("ERROR: " + message);
    }
}