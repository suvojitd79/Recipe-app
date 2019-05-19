package com.example.bakingapp.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakingapp.Details;
import com.example.bakingapp.R;
import com.example.bakingapp.VideoTitleAdapter;
import com.example.bakingapp.VideoTitleClick;
import com.example.bakingapp.model.Ingredient;

import java.util.List;

public class Fragment1 extends Fragment implements View.OnClickListener {


    RecyclerView recyclerView;
    private VideoTitleAdapter videoTitleAdapter;
    private Context context;
    private TextView ingredients;

    public Fragment1() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;

        if (!(context instanceof VideoTitleClick))
            throw new RuntimeException("please implement VideoTitleClick in " + context.getClass());
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Details details = (Details) context;

        View view = inflater.inflate(R.layout.details_frag1, container, false);
        recyclerView = view.findViewById(R.id.detailsRecyclerView);
        ingredients = view.findViewById(R.id.ingredients);

        ingredients.setOnClickListener(this);


        videoTitleAdapter = new VideoTitleAdapter((VideoTitleClick) context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(videoTitleAdapter);
        if (details.getSteps() != null)
            videoTitleAdapter.setSteps(details.getSteps());
        return view;
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.ingredients) {

            StringBuilder stringBuilder = new StringBuilder();
            int i = 1;

            for (Ingredient ingredient:((Details) context).getIngredients()){

                  stringBuilder.append("STEP "+ i+" :)  " + ingredient.getQuantity()+","+ingredient.getMeasure()+","+ingredient.getIngredient()+"\n");
                  i++;
            }


            final AlertDialog.Builder
                    builder = new AlertDialog.Builder(context);
            builder.setTitle("Recipe Ingredients(quantity,measure,ingredient)");
            builder.setMessage(stringBuilder);
            builder.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();

                }
            });
            builder.show();
        }


    }


}
