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

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    private final RecipeClick recipeClick;
    private List<RecipeCard> recipeCards;
    private final int margin;

    public Adapter(double margin,RecipeClick recipeClick) {
        this.margin = (int) margin/2;
        this.recipeCards = new ArrayList<>();
        this.recipeClick = recipeClick;
    }

    public void setRecipeCards(List<RecipeCard> recipeCards) {
        this.recipeCards = recipeCards;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.block,viewGroup,false);


        if (v.getLayoutParams() instanceof ViewGroup.LayoutParams){

                ViewGroup.MarginLayoutParams
                  p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
                p.setMargins(margin,20,margin,20);

        }

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.setText(recipeCards.get(i).getName());

    }

    @Override
    public int getItemCount() {
        return recipeCards.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textView;
        private ConstraintLayout constraintLayout;
        private FrameLayout showDetails;
        private FloatingActionButton share;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.title);
            showDetails = itemView.findViewById(R.id.showDetails);
            showDetails.setOnClickListener(this);
            share = itemView.findViewById(R.id.shareButton);
            share.setOnClickListener(this);
        }

        public void setText(String text) {
            this.textView.setText(text);
        }

        @Override
        public void onClick(View v) {

                //cross-check
            if (!(recipeClick instanceof MainActivity)) return;

            int id = v.getId();

            if (id == R.id.showDetails){

                    recipeClick.showDetails(getAdapterPosition());
            }

            else if (id == R.id.shareButton) {

                    recipeClick.share(getAdapterPosition());

            }


        }


    }

}
