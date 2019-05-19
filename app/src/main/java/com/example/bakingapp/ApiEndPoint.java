package com.example.bakingapp;

import com.example.bakingapp.model.RecipeCard;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiEndPoint {

    @GET("topher/2017/May/59121517_baking/baking.json")
    public Call<List<RecipeCard>> getAll();

}
