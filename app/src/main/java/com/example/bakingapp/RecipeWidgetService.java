package com.example.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.bakingapp.model.Ingredient;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class RecipeWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {


        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SP,MODE_PRIVATE);
        String data = sharedPreferences.getString(MainActivity.SP_KEY,"");

        if (data.equals("")) return null;

        Gson gson = new Gson();
        Ingredient[] ingredients = gson.fromJson(data,Ingredient[].class);

       return new RecipeWidgetFactory(getApplicationContext(),new ArrayList<Ingredient>(Arrays.asList(ingredients)));
    }
}

class RecipeWidgetFactory implements RemoteViewsService.RemoteViewsFactory {


    private ArrayList<Ingredient> ingredients = new ArrayList<>();
    private Context context;

    public RecipeWidgetFactory(Context context, ArrayList<Ingredient> ingredients) {
        this.context = context;
        this.ingredients = ingredients;
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        remoteViews.setTextViewText(R.id.quantity, ingredients.get(position).getQuantity() + "");
        remoteViews.setTextViewText(R.id.measure, ingredients.get(position).getMeasure());
        remoteViews.setTextViewText(R.id.ingredients, ingredients.get(position).getIngredient());
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
