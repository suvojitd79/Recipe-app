package com.example.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.bakingapp.fragment.Fragment1;
import com.example.bakingapp.model.Ingredient;
import com.example.bakingapp.model.Step;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Details extends AppCompatActivity implements VideoTitleClick{


    private String STEP;
    private String VID;
    private List<Step> steps;
    private List<Ingredient> ingredients;
    private Fragment1 fragment1;
    private String CURRENT;
    private String INGREDIENT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        fragment1 = new Fragment1();
        STEP = getString(R.string.step);
        VID = getString(R.string.vid);
        CURRENT = getString(R.string.current);
        INGREDIENT = getString(R.string.ingredient);


        if (getIntent().getParcelableExtra(STEP)!=null) {

            steps = Parcels.unwrap(getIntent().getParcelableExtra(STEP));
            ingredients = Parcels.unwrap(getIntent().getParcelableExtra(INGREDIENT));

                //once you get the data then call it
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frag1,fragment1)
                    .commit();


        }

    }

    public List<Step> getSteps(){

        return steps;

    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }


    @Override
    public void showVideo(int position) {


        Intent intent = new Intent(this,Video.class);
        intent.putExtra(VID, Parcels.wrap(steps));
        intent.putExtra(CURRENT,position);
        startActivity(intent);

    }


}
