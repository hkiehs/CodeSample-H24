package com.example.codingchallenge;

import com.example.codingchallenge.ui.activity.MainActivity;
import com.example.codingchallenge.ui.fragment.ArticleFragment;
import com.example.codingchallenge.ui.fragment.ReviewFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    // Field injections of any dependencies of the VideoApplication
    void inject(App application);

    void inject(MainActivity activity);
    void inject(ArticleFragment articleFragment);
    void inject(ReviewFragment reviewFragment);
}