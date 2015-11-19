package com.example.codingchallenge.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.codingchallenge.App;
import com.example.codingchallenge.R;
import com.example.codingchallenge.model.ArticleModel;
import com.example.codingchallenge.ui.activity.BaseActivity;
import com.example.codingchallenge.ui.activity.MainActivity;
import com.example.codingchallenge.utils.Utility;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ArticleFragment extends Fragment {

    public static final String TAG = ArticleFragment.class.getName();

    private static final String ARG_ARTICLE_JSON = "articlesJson";
    private static final int MAX_COUNT = 10;

    @Bind(R.id.parent)
    RelativeLayout mParent;

    @Bind(R.id.contentFooter)
    RelativeLayout mContentFooter;

    @Bind(R.id.textViewLikeCount)
    TextView tvLikeCount;

    @Bind(R.id.buttonReview)
    Button buttonReview;

    @Inject
    Picasso picasso;

    private ImageView articleImage;
    private Button buttonLike;
    private Button buttonDislike;
    private TextView tvStatus;

    private int articleCounter = 0;
    private int likeCounter = 0;
    private int maxLimit = 10;

    private ArticleModel.Article currentArticle;
    private List<ArticleModel.Article> mArticles;

    public ArticleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment ArticleFragment.
     */
    public static ArticleFragment newInstance(String articleJson) {
        ArticleFragment fragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ARTICLE_JSON, articleJson);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).getComponent().inject(this);

        if (getArguments() != null) {
            String json = getArguments().getString(ARG_ARTICLE_JSON);
            mArticles = Utility.fromJson(json);

            int size = mArticles.size();
            if (size <= MAX_COUNT) {
                maxLimit = size;
            } else {
                maxLimit = MAX_COUNT;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article, container, false);
        ButterKnife.bind(this, view);

        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);

        // inflate card view layout
        inflateCardView(inflater);

        // load article
        loadNewArticle();
        updateCounter();

        return view;
    }

    private void inflateCardView(LayoutInflater inflater) {
        View cardView = inflater.inflate(R.layout.cardview_article, null);

        articleImage = (ImageView) cardView.findViewById(R.id.articleImage);
        buttonDislike = (Button) cardView.findViewById(R.id.buttonDislike);
        buttonLike = (Button) cardView.findViewById(R.id.buttonLike);
        tvStatus = (TextView) cardView.findViewById(R.id.textViewStatus);

        buttonLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLikePressed();
            }
        });

        buttonDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDisLikePressed();
            }
        });

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, cardView.getId());
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mContentFooter.setLayoutParams(params);

        mParent.addView(cardView);
    }

    @OnClick(R.id.buttonReview)
    public void onReviewPressed() {
        String json = Utility.toJson(mArticles);
        ((BaseActivity) getActivity()).replaceFragment(R.id.container, ReviewFragment.newInstance(json), ReviewFragment.TAG);
    }

    private void onLikePressed() {
        // add article for the favourite list
        currentArticle.isLiked = true;

        // update the like counter
        likeCounter++;

        loadNewArticle();
        updateCounter();

        for (ArticleModel.Article article : mArticles) {
            Log.d(TAG, article.isLiked + "");
        }
    }

    private void updateCounter() {
        tvLikeCount.setText(likeCounter + "/" + maxLimit);
    }


    private void onDisLikePressed() {
        loadNewArticle();
    }

    private void loadNewArticle() {
        if (articleCounter < maxLimit) {
            currentArticle = mArticles.get(articleCounter);
            picasso.load(currentArticle.media.get(0).uri).into(articleImage);
            articleCounter++;
        } else {
            tvStatus.setVisibility(View.VISIBLE);
            articleImage.setVisibility(View.INVISIBLE);
            buttonLike.setVisibility(View.INVISIBLE);
            buttonDislike.setVisibility(View.INVISIBLE);
            buttonReview.setVisibility(View.VISIBLE);
        }
    }
}