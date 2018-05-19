package com.example.android.sportsnewsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public class SportsNewsAdapter extends RecyclerView.Adapter<SportNewsHolder> {

    private ArrayList<SportNewsModel> newsList;
    private Context context;

    SportsNewsAdapter(Context context, ArrayList<SportNewsModel> itemModels) {
        this.context = context;
        this.newsList = itemModels;
    }

    @NonNull
    @Override
    public SportNewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(context).inflate(R.layout.layout_of_an_element, parent, false);
        return new SportNewsHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull final SportNewsHolder holder, final int position) {

        holder.articleAuthor.setText(newsList.get(position).getAuthorName());
//        holder.articleDate.setText(newsList.get(position).getDateOfCreate());
        holder.articleTitle.setText(newsList.get(position).getArticleTitle());
        holder.articleSection.setText(newsList.get(position).getSectionName());

        String articleDate = newsList.get(position).getDateOfCreate();
        String formatedData =articleDate.replace("T", " ").replace("Z", "");
        holder.articleDate.setText(formatedData);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = newsList.get(position).getWebUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void clearAllData() {
        newsList.clear();
        notifyDataSetChanged();
    }

    public void addAllData(List<SportNewsModel> news) {
        newsList.clear();
        newsList.addAll(news);
        notifyDataSetChanged();
    }
}