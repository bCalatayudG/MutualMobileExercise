package com.consultants.mutualmobileexercise.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.consultants.mutualmobileexercise.R;
import com.consultants.mutualmobileexercise.model.recipe.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private List<Recipe> recipeList;
    private RecipeListItemListener listener;


    RecipeAdapter(Context context) {
        this.listener = (RecipeListItemListener) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item
                , parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Recipe recipe = recipeList.get(position);
        Picasso.get().load(recipe.getImage()).into(holder.recipeImage);

    }

    @Override
    public int getItemCount() {
        if(recipeList != null)
            return recipeList.size();
        else
            return 0;
    }

    void setImages(List<Recipe> recipes) {
        recipeList = recipes;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView recipeImage;

        ViewHolder(View itemView) {
            super(itemView);
            recipeImage = itemView.findViewById(R.id.recipeImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onItemClicked(recipeList.get(getLayoutPosition()));
        }
    }


    public interface RecipeListItemListener {
        void onItemClicked(Recipe recipe);
    }
}
