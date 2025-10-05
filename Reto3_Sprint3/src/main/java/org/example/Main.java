package org.example;

import org.example.controller.AuthorController;
import org.example.model.ScholarService;
import org.example.view.AuthorView;

public class Main {
    public static void main(String[] args) {
        ScholarService model = new ScholarService();
        AuthorView view = new AuthorView();
        AuthorController controller = new AuthorController(model, view);

        System.out.println("\n  === Scholar API Integration Demo ===");
        System.out.println("Fetching data for 2 researchers, 3 articles each...\n");

        controller.searchArticlesFor("Coronavirus");
        controller.searchArticlesFor("Einstein");

        System.out.println("\nProcess completed. Check database for articles saved.");
    }
}
