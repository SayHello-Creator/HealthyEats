package com.healthyeats.model.viewModels;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.healthyeats.R;
import com.healthyeats.controller.recipe.RecipeFragment;
import com.healthyeats.model.json.UserJson;
import com.healthyeats.model.recipe.Ingredient;
import com.healthyeats.model.recipe.Recipe;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class ViewHelper {

    // Get recipes from file (this will be to populate favorite recipes
    // and liked recipes
    public List<Recipe> getRecipe(Context context, String file, UserJson js) {
        Gson gson = new Gson();
        String ret = js.streamReader(context, file);
        Type listRecipeType = new TypeToken<List<Recipe>>() {}.getType();
        List<Recipe> recipes = gson.fromJson(ret, listRecipeType);
        return recipes;
    }

    // Get ingredients from file (this will be to populate grocery list
    public List<Ingredient> getIngredient(Context context, UserJson js) {
        Gson gson = new Gson();
        String ret = js.streamReader(context, "ingredient.json");
        Type listIngredientType = new TypeToken<List<Ingredient>>() {}.getType();
        List<Ingredient> ingredients = gson.fromJson(ret, listIngredientType);

        return ingredients;
    }

    public int toDP(float dp, Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) (metrics.density * dp + 0.5f);
    }

    // Create CardView template for all Cards on cookbook page
    public CardView createCard(Context context) {
        // Card View Creation - Height | Width
        CardView card = new CardView(new ContextThemeWrapper(context, R.style.recipeCardView));
        card.setLayoutParams(new CardView.LayoutParams(
                toDP(223, context), toDP(299, context)));

        // Margin
        ViewGroup.MarginLayoutParams layoutParams =
                (ViewGroup.MarginLayoutParams) card.getLayoutParams();
        layoutParams.setMargins(toDP(10, context), 0, 0, 0);
        card.requestLayout();

        return card;
    }

    //Check if a recipe is in a specific json file given the file name
    public boolean isInFile(String filename, Context context, int id) {
        boolean isIn = false;
        UserJson userJson = new UserJson(context);
        List<Recipe> recipes = getRecipe(context, filename, userJson);

        if (recipes != null) {
            for (int i = 0; i < recipes.size(); i++) {
                if (recipes.get(i).getId() == id) {
                    isIn = true;
                    break;
                }
            }
        }

        return isIn;
    }

    //Check if a recipe is in a specific json file given the file name
    public boolean ingredInFile(Context context, Ingredient ingred) {
        boolean isIn = false;
        UserJson userJson = new UserJson(context);
        List<Ingredient> ingredientList = getIngredient(context, userJson);

        if (ingredientList != null) {
            for (int i = 0; i < ingredientList.size(); i++) {
                if (ingredientList.get(i).getName().equals(ingred.getName())) {
                    isIn = true;
                    break;
                }
            }
        }

        return isIn;
    }

    // Creates the Heart Icon
    public ImageButton heart(Activity activity, Context context, int id) {
        //Image Button Creation - Height | Width
        ImageButton heart = new ImageButton(new ContextThemeWrapper(activity, R.style.recipeHeartIcon), null, 0);
        heart.setLayoutParams(new LinearLayout.LayoutParams(toDP(46, context), toDP(40, context)));

        // Margin
        ViewGroup.MarginLayoutParams layoutParams =
                (ViewGroup.MarginLayoutParams) heart.getLayoutParams();
        layoutParams.setMargins(toDP(170, context), toDP(5, context), 0, 0);
        heart.requestLayout();

        // Set Scale
        heart.setScaleType(ImageView.ScaleType.FIT_XY);
        UserJson userJson = new UserJson(context);
        if (isInFile("recipesFav.json", context, id)) {
            heart.setImageDrawable(context.getResources().getDrawable(R.drawable.heartfull_icon));
        }

        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserJson userJson = new UserJson(context);
                boolean isIn = isInFile("recipesFav.json", context, id);

                if (isIn) {
                    userJson.deleteFromFileRecipe(id, context, "recipesFav.json");
                    heart.setImageDrawable(context.getResources().getDrawable((R.drawable.heartempty_icon)));
                } else {
                    userJson.writeToFileRecipe(id, context, "recipesFav.json");
                    heart.setImageDrawable(context.getResources().getDrawable((R.drawable.heartfull_icon)));
                }

            }
        });

        return heart;
    }

    // Sets the white footer at the bottom of each card
    public ImageView setWhiteFooter(Activity activity, Context context) {
        //Image Button Creation - Height | Width
        ImageView blueBackground = new ImageView(new ContextThemeWrapper(activity, R.style.recipeBlueBackground), null, 0);
        blueBackground.setLayoutParams(new LinearLayout.LayoutParams(toDP(223, context), toDP(86, context)));

        // Margin
        ViewGroup.MarginLayoutParams layoutParams =
                (ViewGroup.MarginLayoutParams) blueBackground.getLayoutParams();
        layoutParams.setMargins(0, toDP(215, context), 0, 0);
        blueBackground.requestLayout();

        return blueBackground;
    }

    // Sets the price on the CookBook page
    public TextView setRecipePrice(float price, Activity activity, Context context) {
        // Text View Creation - Height | Width
        TextView recipePrice = new TextView(activity);
        recipePrice.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // Margin
        ViewGroup.MarginLayoutParams layoutParams =
                (ViewGroup.MarginLayoutParams) recipePrice.getLayoutParams();
        layoutParams.setMargins(toDP(185, context), toDP(220, context), toDP(5, context), 0);
        recipePrice.requestLayout();

        //Set Text and Change Size
        recipePrice.setTextSize(toDP(5, context));
        recipePrice.setText("$" + Float.toString(price));

        return recipePrice;
    }

    // Sets the dietary on theCookbook page
    public TextView setDietary(Activity activity, Context context) {
        // Text View Creation - Height | Width
        TextView dietary = new TextView(activity);
        dietary.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // Margin
        ViewGroup.MarginLayoutParams layoutParams =
                (ViewGroup.MarginLayoutParams) dietary.getLayoutParams();
        layoutParams.setMargins(toDP(145, context), toDP(260, context), 0, 0);
        dietary.requestLayout();

        //Set Text
        dietary.setText("Vegetarian");

        return dietary;
    }

    // Sets the difficulty image view on cookbook page
    public ImageView setDifficultyImg(String difficulty, Activity activity, Context context, Resources resources) {
        //Image Button Creation - Height | Width
        ImageView difficultyImg = new ImageView(new ContextThemeWrapper(activity, R.style.recipeCardDifficulty), null, 0);
        difficultyImg.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // Margin
        ViewGroup.MarginLayoutParams layoutParams =
                (ViewGroup.MarginLayoutParams) difficultyImg.getLayoutParams();
        layoutParams.setMargins(toDP(10, context), toDP(260, context), 0, 0);
        difficultyImg.requestLayout();

        //Set difficulty
        if (difficulty.equals("Beginner")) {
            difficultyImg.setImageDrawable(resources.getDrawable(R.drawable.difficultyeasy_icon));
        } else if (difficulty.equals("Intermediate")) {
            difficultyImg.setImageDrawable(resources.getDrawable(R.drawable.difficultymed_icon));
        } else if (difficulty.equals("Hard")) {
            difficultyImg.setImageDrawable(resources.getDrawable(R.drawable.difficultyhard_icon));
        }

        return difficultyImg;
    }

    // Set the Name of the Recipe
    public TextView setRecipeName(String name, Activity activity, Context context) {
        //Text View Creation - Height | Width
        TextView recipeName = new TextView(activity);
        recipeName.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // Margin
        ViewGroup.MarginLayoutParams layoutParams =
                (ViewGroup.MarginLayoutParams) recipeName.getLayoutParams();
        layoutParams.setMargins(toDP(15, context), toDP(225, context), 0, 0);
        recipeName.requestLayout();

        //Set Text and Change Size
        recipeName.setTextSize(toDP(5, context));
        recipeName.setText(name);

        return recipeName;
    }

    // Creates the Fork Icon
    public ImageButton fork(Activity activity, Context context) {
        //Image Button Creation - Height | Width
        ImageButton fork = new ImageButton(new ContextThemeWrapper(activity, R.style.recipeCardFork), null, 0);
        fork.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // Margin
        ViewGroup.MarginLayoutParams layoutParams =
                (ViewGroup.MarginLayoutParams) fork.getLayoutParams();
        layoutParams.setMargins(toDP(120, context), toDP(260, context), 0, 0);
        fork.requestLayout();

        // Set Scale
        fork.setScaleType(ImageView.ScaleType.FIT_XY);

        return fork;
    }

    // Set the Difficulty of Recipe
    public TextView setRecipeDifficulty(String level, Activity activity, Context context) {
        //Text View Creation - Height | Width
        TextView difficulty = new TextView(activity);
        difficulty.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // Margin
        ViewGroup.MarginLayoutParams layoutParams =
                (ViewGroup.MarginLayoutParams) difficulty.getLayoutParams();
        layoutParams.setMargins(toDP(35, context), toDP(260, context), 0, 0);
        difficulty.requestLayout();

        //Set Text
        difficulty.setText(level);

        return difficulty;
    }

    // Creates the Card for each recipe and calls other helpers to allocate the object
    public void createFullCard(String name, float price, String difficulty, LinearLayout parent, int id, Activity activity, Context context, Resources resources, FragmentManager frag) {
        CardView card = createCard(context);
        parent.addView(card);

        ImageButton background = new ImageButton(new ContextThemeWrapper(activity, R.style.recipeBackgroundMain), null, 0);

        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecipeFragment obj = RecipeFragment.newInstance(id);
                FragmentTransaction fr = frag.beginTransaction();
                fr.replace(R.id.nav_host_fragment, obj);
                fr.addToBackStack(null);
                fr.commit();
            }
        });

        TextView recipeDifficultyText = new TextView(activity);
        recipeDifficultyText.setTextAppearance(activity, R.style.recipeCardDifficultyText);

        card.addView(background);
        card.addView(heart(activity, context, id));
        card.addView(setWhiteFooter(activity, context));
        card.addView(setRecipePrice(price, activity, context));
        card.addView(setDietary(activity, context));
        card.addView(setDifficultyImg(difficulty, activity, context, resources));
        card.addView(setRecipeName(name, activity, context));
        card.addView(fork(activity, context));
        card.addView(setRecipeDifficulty(difficulty, activity, context));
    }
}