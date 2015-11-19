package com.example.codingchallenge.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.codingchallenge.App;
import com.example.codingchallenge.R;
import com.example.codingchallenge.manager.SharedPreferenceManager;
import com.example.codingchallenge.model.ArticleModel;
import com.example.codingchallenge.service.ApiService;
import com.example.codingchallenge.ui.fragment.ArticleFragment;
import com.example.codingchallenge.utils.Utility;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.RestAdapter;
import rx.Subscriber;
import rx.android.observables.AndroidObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends BaseActivity {

    private static final String BASE_URL = "https://api-mobile.home24.com";

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Inject
    RestAdapter mRestAdapter;

    private boolean isFragmentLoaded = false;
    private List<ArticleModel.Article> mArticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((App) getApplication()).getComponent().inject(this);

        // only executes for the first time in app life-time
        if (isAppLoadFirstTime()) {
            startActivity(new Intent(this, StartScreenActivity.class));
            SharedPreferenceManager.updateInstallStatus(this, false);
            finish();
        }

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        setupRestAdapter();
        ApiService service = mRestAdapter.create(ApiService.class);

        AndroidObservable.bindActivity(this, service.getArticles())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArticleModel>() {
                    @Override
                    public final void onCompleted() {
                        Timber.d("Operation completed");
                    }

                    @Override
                    public final void onError(Throwable e) {
                        Timber.e(e.getMessage());
                    }

                    @Override
                    public final void onNext(ArticleModel articleModel) {
                        mArticles = articleModel.getEmbedded().getArticles();
                        loadArticleFragment(mArticles);
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadArticleFragment(mArticles);
    }

    private void loadArticleFragment(List<ArticleModel.Article> articles) {
        if (!isFragmentLoaded && articles != null) {
            String json = Utility.toJson(articles);
            replaceFragment(R.id.container, ArticleFragment.newInstance(json), ArticleFragment.TAG);
            isFragmentLoaded = true;
        }
    }

    private boolean isAppLoadFirstTime() {
        return SharedPreferenceManager.isItFirstInstall(this);
    }

    private void setupRestAdapter() {
        mRestAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .build();
    }
}
