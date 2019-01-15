package com.consultants.mutualmobileexercise.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import com.consultants.mutualmobileexercise.R;
import com.consultants.mutualmobileexercise.model.recipe.Recipe;
import com.consultants.mutualmobileexercise.viewModel.RecipeViewModel;

import java.util.List;

import static com.consultants.mutualmobileexercise.view.RecipeAdapter.*;

public class MainActivity extends AppCompatActivity implements RecipeListItemListener{

    private RecipeViewModel mRecipeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        mRecipeViewModel.getImages("");

        final RecipeAdapter adapter = getRecipeAdapter();

        mRecipeViewModel.getAllImages().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                adapter.setImages(recipes);

            }
        });

        setSearchView();


    }

    private void setSearchView() {
        // initiate a search view
        SearchView simpleSearchView = findViewById(R.id.svMain);
        simpleSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mRecipeViewModel.getImages(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    @NonNull
    private RecipeAdapter getRecipeAdapter() {
        RecyclerView recyclerView = findViewById(R.id.rv_images);
        final RecipeAdapter adapter = new RecipeAdapter(this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        return adapter;
    }

    @Override
    public void onItemClicked(Recipe recipe) {
        Intent intent = new Intent(this, RecipeDetail.class);
        intent.putExtra("recipe", recipe);
        startActivity(intent);
    }
}
