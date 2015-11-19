package com.example.codingchallenge;

import android.content.Context;

import com.example.codingchallenge.service.ApiService;
import com.example.codingchallenge.ui.adapter.RecyclerViewAdapter;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

@Module
public class ApplicationModule {

    private static final String BASE_URL = "https://api-mobile.home24.com";

    private final App app;

    public ApplicationModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.app;
    }

    @Provides
    @Singleton
    RestAdapter provideRestAdapter() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .build();

        return restAdapter;
    }

    @Provides
    @Singleton
    Picasso providePicasso() {
        Downloader downloader = new OkHttpDownloader(app, Integer.MAX_VALUE);
        Picasso.Builder builder = new Picasso.Builder(app);
        builder.downloader(downloader);

        return builder.build();
    }

    @Provides
    @Singleton
    RecyclerViewAdapter provideRecyclerViewAdapter() {
        return new RecyclerViewAdapter();
    }

}
