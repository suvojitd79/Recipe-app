package com.example.bakingapp;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.bakingapp.model.RecipeCard;
import com.example.bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

public class VideoTitleAdapter extends RecyclerView.Adapter<VideoTitleAdapter.VideoTitleViewHolder>{

    private final VideoTitleClick videoTitleClick;
    private List<Step> steps;

    public VideoTitleAdapter(VideoTitleClick videoTitleClick) {

        this.steps = new ArrayList<>();
        this.videoTitleClick = videoTitleClick;
    }

    public void setSteps(List<Step> recipeCards) {
        this.steps = recipeCards;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VideoTitleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.vid_title,viewGroup,false);

        return new VideoTitleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoTitleViewHolder viewHolder, int i) {

        viewHolder.setText(steps.get(i).getShortDescription());

    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    class VideoTitleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView vid_title;

        public VideoTitleViewHolder(@NonNull View itemView) {
            super(itemView);
            vid_title = itemView.findViewById(R.id.vid_title);
            vid_title.setOnClickListener(this);
        }

        public void setText(String text) {
            this.vid_title.setText(text);
        }

        @Override
        public void onClick(View v) {

            //cross-check
            if (!(videoTitleClick instanceof Details)) return;

            int id = v.getId();

            if (id == R.id.vid_title){

                videoTitleClick.showVideo(getAdapterPosition());
            }

        }


    }


}
