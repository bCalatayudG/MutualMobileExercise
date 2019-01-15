package com.consultants.mutualmobileexercise.model.data.remote;

import android.content.Context;

import com.consultants.mutualmobileexercise.model.recipe.RecipeResponse;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteDataSource {

    private static final String APP_ID = "d124eaae";
    private static final String APP_KEY = "ebb324b2741ebbe104340040cccf8c09";

    private String BASE_URL;

    public RemoteDataSource(String BASE_URL, Context context) {
        this.BASE_URL = BASE_URL;
    }

    private Retrofit create(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public Observable<RecipeResponse> getResponse(String query, String from, String to){
        Retrofit retrofit = create();
        RemoteService remoteService = retrofit.create(RemoteService.class);
        return remoteService.getRecipes(query, from, to, APP_ID, APP_KEY);
    }

}
