package com.example.bakingapp;

import android.animation.Animator;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.bakingapp.model.RecipeCard;
import com.example.bakingapp.model.Step;
import com.google.gson.Gson;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements RecipeClick {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.flashView)
    LinearLayout flashView;

    @BindView(R.id.data)
    LinearLayout data;

    @BindView(R.id.shareApp)
    FloatingActionButton shareApp;

    private String BASE_URL;
    private final String TAG = "d99";

    private final int CONST = 200;

    public static final String BASE_IMAGE_URL = "https://images.pexels.com/photos/2113556/pexels-photo-2113556.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940";

    private Adapter adapter;

    private List<RecipeCard> recipeCards;

    private String STEP;

    private String INGREDIENT;

    private String CURR = "curr";


    private int current = -1; //default


    //shared-preferences constant
    public static final String SP = "sharedData";
    public static final String SP_KEY = "sharedDataKey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        BASE_URL = getString(R.string.base_url);
        STEP = getString(R.string.step);
        INGREDIENT = getString(R.string.ingredient);

        int span = (int) getDP() / CONST;

        double margin = ((getDP() - span * CONST) * Resources.getSystem().getDisplayMetrics().density) / span;

        //Log.d(TAG, "" + getDP());


        adapter = new Adapter(margin, this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, span));
        recyclerView.setAdapter(adapter);

        makeNetworkCall();


        flashView.setAlpha(1f);
        flashView.setVisibility(View.VISIBLE);
        flashView.animate()
                .alpha(0.01f)
                .setDuration(4000)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {


                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        flashView.setVisibility(View.GONE);
                        data.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }

                });


        shareApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                share("Check out the new Recipe app!");

            }

        });

        if (savedInstanceState!=null)
            current = savedInstanceState.getInt(CURR);

        Log.d(TAG, current+"");

        SharedPreferences sharedPreferences = getSharedPreferences(SP,MODE_PRIVATE);
      //  Toast.makeText(this,sharedPreferences.getString(SP_KEY,"no data yet!"),Toast.LENGTH_SHORT).show();


    }

    private double getDP() {

        return Math.ceil(Resources.getSystem().getDisplayMetrics().widthPixels / Resources.getSystem().getDisplayMetrics().density);

    }


    private void makeNetworkCall() {

        Retrofit retrofit =
                new Retrofit.Builder().
                        baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();


        ApiEndPoint apiEndPoint
                = retrofit.create(ApiEndPoint.class);


        Call<List<RecipeCard>> call
                = apiEndPoint.getAll();


        call.enqueue(new Callback<List<RecipeCard>>() {
            @Override
            public void onResponse(Call<List<RecipeCard>> call, Response<List<RecipeCard>> response) {

                if (response.isSuccessful()) {
                    recipeCards = response.body();
                    adapter.setRecipeCards(recipeCards);
                }
            }

            @Override
            public void onFailure(Call<List<RecipeCard>> call, Throwable t) {

                Log.d(TAG, t + "");

            }

        });

    }


    @Override
    protected void onPostResume() {
        super.onPostResume();

    }


    @Override
    public void showDetails(int position) {


        //save the data
        current = position; //update
        saveData(current);

        // Toast.makeText(this,String.valueOf(position),Toast.LENGTH_SHORT).show();


        //Toast.makeText(this,String.valueOf(position),Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Details.class);
        intent.putExtra(STEP, Parcels.wrap(recipeCards.get(position).getSteps()));
        intent.putExtra(INGREDIENT, Parcels.wrap(recipeCards.get(position).getIngredients()));
        startActivity(intent);

    }


    @Override
    public void share(int position) {

        share(recipeCards.get(position).getName());

    }


    private void share(String msg) {


        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        startActivity(intent);

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(CURR,current);

        //save the data
        saveData(current);
    }

    private void saveData(int position) {

        if (position == -1) return;

        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences(SP, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String data = gson.toJson(recipeCards.get(position).getIngredients());
        editor.putString(SP_KEY, data);
        editor.apply();


        //update the app-widget

        Intent intent = new Intent(this,RecipeWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(getApplication())
                    .getAppWidgetIds(new ComponentName(getApplication(),RecipeWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        sendBroadcast(intent);

    }


}
