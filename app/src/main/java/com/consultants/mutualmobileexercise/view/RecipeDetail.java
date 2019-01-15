package com.consultants.mutualmobileexercise.view;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.consultants.mutualmobileexercise.R;
import com.consultants.mutualmobileexercise.model.recipe.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeDetail extends AppCompatActivity {

    private TextView tvIngredients;
    private ImageView ivRecipeImage;
    private TextView tvRecipeLabel;
    private TextView tvSource;
    private TextView tvSourceURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        final Recipe recipe = (Recipe) getIntent().getSerializableExtra("recipe");

        ivRecipeImage = findViewById(R.id.ivRecipeImage);
        tvRecipeLabel = findViewById(R.id.tvRecipeLabel);
        tvIngredients = findViewById(R.id.tvIngredients);
        Toolbar toolbar = findViewById(R.id.toolbar);
        tvSourceURL = findViewById(R.id.tvSourceURL);
        tvSource = findViewById(R.id.tvSource);

        setSupportActionBar(toolbar);

        setImage(recipe);
        setIngredients(recipe);
        setSource(recipe);

    }

    private void setSource(final Recipe recipe) {
        tvSource.setText(recipe.getSource());
        tvSourceURL.setText(recipe.getUrl());
        tvSourceURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(recipe.getUrl());
                Intent intent = new Intent (Intent.ACTION_WEB_SEARCH,uri);
                startActivity(intent);
            }
        });
    }

    private void setImage(Recipe recipe) {
        Picasso.get().load(recipe.getImage()).into(ivRecipeImage);
        tvRecipeLabel.setText(recipe.getLabel());
    }

    private void setIngredients(Recipe recipe) {
        List<String> IngredientLines = recipe.getIngredientLines();
        StringBuilder string = new StringBuilder();
        for (String s: IngredientLines){
            string.append(s + "\n");
        }
        tvIngredients.setText(string);
    }
}
