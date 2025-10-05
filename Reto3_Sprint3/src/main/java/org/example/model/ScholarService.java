package org.example.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ScholarService {
    private static final String API_BASE_URL = "https://serpapi.com/search.json";
    private static final String API_KEY = "d670cbc4504aa95395edb6654c01cb6a16ce723cf94db086409336102435942a";

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final DatabaseManager dbManager = new DatabaseManager();

    public List<Article> searchArticles(String query) {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String fullUrl = API_BASE_URL + "?engine=google_scholar&q=" + encodedQuery + "&api_key=" + API_KEY;

        System.out.println("\nSearching articles for: " + query);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(fullUrl);

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode != 200) {
                    System.err.println("API returned status code: " + statusCode);
                    return Collections.emptyList();
                }

                String jsonResponse = EntityUtils.toString(response.getEntity());
                ScholarArticleResponse apiResponse =
                        objectMapper.readValue(jsonResponse, ScholarArticleResponse.class);

                List<Article> articles = apiResponse.getArticles();

                if (articles.size() > 3) {
                    articles = articles.subList(0, 3);
                }

                for (Article article : articles) {
                    saveArticleToDB(article);
                }

                System.out.println(" Saved " + articles.size() + " articles for researcher: " + query);
                return articles;
            }

        } catch (IOException e) {
            System.err.println(" Network/IO Error: " + e.getMessage());
            return Collections.emptyList();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    private void saveArticleToDB(Article article) {
        String sql = "INSERT INTO articles (title, authors, publication_date, abstract, link, keywords, cited_by) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE cited_by=VALUES(cited_by), authors=VALUES(authors)";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, article.getTitle());
            stmt.setString(2, article.getAuthors());
            stmt.setString(3, article.getPublicationDate());
            stmt.setString(4, article.getAbstractText());
            stmt.setString(5, article.getLink());
            stmt.setString(6, "");
            stmt.setInt(7, Optional.ofNullable(article.getCitedBy()).orElse(0));

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("DB ERROR saving article '" + article.getTitle() + "': " + e.getMessage());
        }
    }
}