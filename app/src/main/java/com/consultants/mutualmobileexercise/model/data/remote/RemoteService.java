package com.consultants.mutualmobileexercise.model.data.remote;

import com.consultants.mutualmobileexercise.model.recipe.RecipeResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RemoteService {

    @GET("search")
    Observable<RecipeResponse> getRecipes(
            @Query("q") String query
            , @Query("from") String from
            , @Query("to") String to
            , @Query("app_id") String app_id
            , @Query("app_key") String app_key);
}
