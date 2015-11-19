package com.example.codingchallenge.model;

import java.util.List;

public class ArticleModel {
    private Embedded _embedded;

    public Embedded getEmbedded() {
        return _embedded;
    }

    public class Medium {
        public String uri;
    }

    public class Article {
        //        public object description;
//        public object prevPrice;
//        public object manufacturePrice;
//        public object assemblyService;
//        public object availability;
//        public object url;
//        public object energyClass;
        public List<Medium> media;
        public String sku;
        public String title;
        public boolean isLiked;
    }

    public class Embedded {
        private List<Article> articles;

        public List<Article> getArticles() {
            return articles;
        }
    }
}
