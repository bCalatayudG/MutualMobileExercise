package com.consultants.mutualmobileexercise.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.consultants.mutualmobileexercise.model.data.remote.RemoteDataSource;
import com.consultants.mutualmobileexercise.model.recipe.Hit;
import com.consultants.mutualmobileexercise.model.recipe.Recipe;
import com.consultants.mutualmobileexercise.model.recipe.RecipeResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RecipeViewModel extends AndroidViewModel {

    private static final String TAG = RecipeViewModel.class.getSimpleName() + "_TAG";
    private List<Recipe> recipeList = new ArrayList<>();
    private RemoteDataSource remoteDataSource;
    private MutableLiveData<List<Recipe>> listLiveData;



    public RecipeViewModel(@NonNull Application application) {
        super(application);
        remoteDataSource = new RemoteDataSource("https://api.edamam.com/",application);
        listLiveData = new MutableLiveData<>();
    }

    public void getImages(String query){
        Observer<Recipe> observer = new Observer<Recipe>() {
            @Override
            public void onSubscribe(Disposable d) {
                recipeList.clear();
            }

            @Override
            public void onNext(Recipe recipe) {
                recipeList.add(recipe);
                listLiveData.setValue(recipeList);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: "+e.getMessage());
            }

            @Override
            public void onComplete() {
            }
        };
        if(query.equals(""))
            query = "fish";
        String from = "0";
        String to = "100";
        remoteDataSource.getResponse(query, from, to)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(getFlatMapFunction())
                .subscribe(observer);
    }

    private Function<RecipeResponse, Observable<Recipe>> getFlatMapFunction() {
        return new Function<RecipeResponse, Observable<Recipe>>() {
            @Override
            public Observable<Recipe> apply(final RecipeResponse response) throws Exception {

                return Observable.create(new ObservableOnSubscribe<Recipe>() {
                    @Override
                    public void subscribe(ObservableEmitter<Recipe> e) throws Exception {

                        for (Hit hitsItem : response.getHits()) {
                            e.onNext(hitsItem.getRecipe());
                        }
                        e.onComplete();
                    }
                });
            }
        };
    }

    public MutableLiveData<List<Recipe>> getAllImages(){
        return listLiveData;
    }

}
