package com.example.codingchallenge.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.codingchallenge.R;
import com.example.codingchallenge.model.ArticleModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Picasso picasso;

    private boolean isGridView;
    private List<ArticleModel.Article> mArticles;

    public RecyclerViewAdapter() {
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_review, parent, false);
        return new ViewHolder(v);
    }

    public void setData(List<ArticleModel.Article> articles) {
        this.mArticles = articles;
    }

    public void setPicasso(Picasso picasso) {
        this.picasso = picasso;
    }

    public void setLayoutView(boolean viewType) {
        this.isGridView = viewType;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ArticleModel.Article article = mArticles.get(position);

        holder.articleImage.setImageBitmap(null);
        picasso.load(article.media.get(0).uri).into(holder.articleImage);

        holder.articleTitle.setText(article.title);

        if (!isGridView) {
            holder.articleTitle.setVisibility(View.VISIBLE);
        } else {
            holder.articleTitle.setVisibility(View.INVISIBLE);
        }

        if (article.isLiked) {
            holder.articleSelected.setVisibility(View.VISIBLE);
        } else {
            holder.articleSelected.setVisibility(View.INVISIBLE);
        }

        holder.itemView.setTag(article);
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView articleImage;
        public ImageView articleSelected;
        public TextView articleTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            articleSelected = (ImageView) itemView.findViewById(R.id.imageViewSelected);
            articleImage = (ImageView) itemView.findViewById(R.id.articleImage);
            articleTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
        }
    }
}
