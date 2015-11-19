package com.example.codingchallenge.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.example.codingchallenge.App;
import com.example.codingchallenge.R;
import com.example.codingchallenge.model.ArticleModel;
import com.example.codingchallenge.ui.activity.MainActivity;
import com.example.codingchallenge.ui.adapter.RecyclerViewAdapter;
import com.example.codingchallenge.utils.Utility;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import dagger.Lazy;
import retrofit.RestAdapter;

public class ReviewFragment extends Fragment {

    public static final String TAG = ReviewFragment.class.getName();

    private static final String ARG_REVIEWED_JSON = "reviewArticles";

    private static final int LIST_VIEW = 1;
    private static final int GRID_VIEW = 2;

    @Inject
    RestAdapter mRestAdapter;

    @Inject
    Picasso picasso;

    @Inject
    Lazy<RecyclerViewAdapter> mRecyclerAdapter;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private GridLayoutManager mGridLayoutManager;

    private RecyclerViewAdapter mAdapter;
    private List<ArticleModel.Article> mArticles;

    public ReviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment ArticleFragment.
     */
    public static ReviewFragment newInstance(String json) {
        ReviewFragment fragment = new ReviewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_REVIEWED_JSON, json);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).getComponent().inject(this);

        if (getArguments() != null) {
            String json = getArguments().getString(ARG_REVIEWED_JSON);
            mArticles = Utility.fromJson(json);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        mAdapter = mRecyclerAdapter.get();
        mAdapter.setPicasso(picasso);
        mGridLayoutManager = new GridLayoutManager(getActivity(), LIST_VIEW);
        recyclerView.setLayoutManager(mGridLayoutManager);

        mAdapter.setData(mArticles);
        recyclerView.setAdapter(mAdapter);

        setLayout(LIST_VIEW);

        return view;
    }

    private void setLayout(int type) {
        if (type == GRID_VIEW) {
            mAdapter.setLayoutView(true);
        } else {
            mAdapter.setLayoutView(false);
        }
        mGridLayoutManager.setSpanCount(type);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_review, menu);
        MenuItem item = menu.findItem(R.id.action_switch);
        if (item != null) {
            SwitchCompat switchCompat = (SwitchCompat) item.getActionView().findViewById(R.id.action_switch);
            if (switchCompat != null) {
                switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            setLayout(GRID_VIEW);
                        } else {
                            setLayout(LIST_VIEW);
                        }
                    }
                });
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Get item selected and deal with it
        switch (item.getItemId()) {
            case android.R.id.home:
                getFragmentManager().popBackStackImmediate();
                return true;
        }
        return false;
    }

}
