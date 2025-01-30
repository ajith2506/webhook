package com.demo.webhook.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PushEvent {
    private String ref; // Branch or tag reference (e.g., "refs/heads/main")
    private String before; // SHA of the commit before the push
    private String after; // SHA of the commit after the push
    private List<Commit> commits; // List of commits in the push
    private Repository repository; // Repository details

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Commit {
        private String id; // Commit hash
        private String message; // Commit message
        private String url; // URL to the commit
        private Author author; // Author details

        @Getter
        @Setter
//        @AllArgsConstructor
//        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Author {

            private String name;  // Author name
            private String email; // Author email
            public Author() {
            }
            @JsonCreator
            public Author(@JsonProperty("name") String name, @JsonProperty("email") String email) {
                this.name = name;
                this.email = email;
            }

            @Override
            public String toString() {
                return "Author{" +
                        "name='" + name + '\'' +
                        ", email='" + email + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "Commit{" +
                    "id='" + id + '\'' +
                    ", message='" + message + '\'' +
                    ", url='" + url + '\'' +
                    ", author=" + author +
                    '}';
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Repository {
        private String name; // Repository name
        private String fullName; // Full repository name (e.g., "owner/repo")
        private String url; // Repository URL
        private String id;

        @Override
        public String toString() {
            return "Repository{" +
                    "name='" + name + '\'' +
                    ", fullName='" + fullName + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PushEvent{" +
                "ref='" + ref + '\'' +
                ", before='" + before + '\'' +
                ", after='" + after + '\'' +
                ", commits=" + commits +
                ", repository=" + repository +
                '}';
    }
}
