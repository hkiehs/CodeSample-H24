package com.example.codingchallenge.utils;

import com.example.codingchallenge.model.ArticleModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class Utility {

    public static String toJson(List<ArticleModel.Article> articles) {
        return new Gson().toJson(articles);
    }

    public static List<ArticleModel.Article> fromJson(String json) {
        return new Gson().fromJson(json, new TypeToken<List<ArticleModel.Article>>() {
        }.getType());
    }

}
