package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Article {

    @JsonProperty("title")
    private String title;

    @JsonProperty("publication_info")
    private PublicationInfo publicationInfo;

    @JsonProperty("snippet")
    private String abstractText;

    @JsonProperty("link")
    private String link;

    @JsonProperty("inline_links")
    private InlineLinks inlineLinks;


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PublicationInfo {
        @JsonProperty("authors")
        private List<Author> authors;

        @JsonProperty("summary")
        private String summary;

        public List<Author> getAuthors() { return authors; }
        public String getSummary() { return summary; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Author {
        @JsonProperty("name")
        private String name;
        public String getName() { return name; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class InlineLinks {
        @JsonProperty("cited_by")
        private CitedBy citedBy;

        public CitedBy getCitedBy() { return citedBy; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CitedBy {
        @JsonProperty("total")
        private Integer total;
        public Integer getTotal() { return total; }
    }

    public String getTitle() { return title; }

    public String getAuthors() {
        if (publicationInfo != null) {
            List<Author> authorsList = publicationInfo.getAuthors();
            if (authorsList != null && !authorsList.isEmpty()) {
                return authorsList.stream()
                        .map(Author::getName)
                        .filter(name -> name != null && !name.isBlank())
                        .collect(Collectors.joining(", "));
            }
            if (publicationInfo.getSummary() != null) {
                return publicationInfo.getSummary();
            }
        }
        return "Unknown";
    }

    public String getPublicationDate() {
        return (publicationInfo != null && publicationInfo.getSummary() != null)
                ? extractYear(publicationInfo.getSummary())
                : null;
    }

    public String getAbstractText() { return abstractText; }

    public String getLink() { return link; }

    public Integer getCitedBy() {
        return (inlineLinks != null && inlineLinks.getCitedBy() != null)
                ? inlineLinks.getCitedBy().getTotal()
                : 0;
    }

    private String extractYear(String text) {
        if (text == null) return null;
        var matcher = java.util.regex.Pattern.compile("(19|20)\\d{2}").matcher(text);
        return matcher.find() ? matcher.group() : null;
    }

    @Override
    public String toString() {
        return "Title: " + title +
                "\nAuthors: " + getAuthors() +
                "\nDate: " + getPublicationDate() +
                "\nCited By: " + getCitedBy() +
                "\nLink: " + link +
                "\nSnippet: " + (abstractText != null ? abstractText.substring(0, Math.min(abstractText.length(), 100)) + "..." : "N/A");
    }
}
