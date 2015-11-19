package com.example.codingchallenge.service;

import com.example.codingchallenge.model.ArticleModel;

import retrofit.http.GET;
import rx.Observable;

public interface ApiService {
    @GET("/api/v1/articles?appDomain=1&limit=10")
    Observable<ArticleModel> getArticles();
}