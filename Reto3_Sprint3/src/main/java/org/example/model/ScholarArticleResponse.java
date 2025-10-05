package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Collections;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ScholarArticleResponse {

    @JsonProperty("organic_results")
    private List<Article> articles;

    public List<Article> getArticles() {
        return articles != null ? articles : Collections.emptyList();
    }
}