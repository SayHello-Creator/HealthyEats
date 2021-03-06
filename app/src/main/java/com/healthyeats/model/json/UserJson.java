package com.healthyeats.model.json;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.healthyeats.controller.searchAndFilter.SearchAndFilter;
import com.healthyeats.model.account.Account;
import com.healthyeats.model.recipe.Ingredient;
import com.healthyeats.model.recipe.Recipe;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserJson {

    /**
     *  constructor for UserJson
     * @param context the context from the method
     */
    public UserJson(Context context){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("recipesFav.json", Context.MODE_APPEND| Context.MODE_PRIVATE));
            outputStreamWriter.close();
            OutputStreamWriter outputStreamWriter2 = new OutputStreamWriter(context.openFileOutput("ingredient.json", Context.MODE_APPEND| Context.MODE_PRIVATE));
            outputStreamWriter2.close();
            OutputStreamWriter outputStreamWriter3 = new OutputStreamWriter(context.openFileOutput("account.json", Context.MODE_APPEND| Context.MODE_PRIVATE));
            outputStreamWriter3.close();
            OutputStreamWriter outputStreamWriter4 = new OutputStreamWriter(context.openFileOutput("weeklyMeals.json", Context.MODE_APPEND| Context.MODE_PRIVATE));
            outputStreamWriter4.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createJson(Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("recipesFav.json", Context.MODE_APPEND| Context.MODE_PRIVATE));
            outputStreamWriter.close();
            OutputStreamWriter outputStreamWriter2 = new OutputStreamWriter(context.openFileOutput("ingredient.json", Context.MODE_APPEND| Context.MODE_PRIVATE));
            outputStreamWriter2.close();
            OutputStreamWriter outputStreamWriter3 = new OutputStreamWriter(context.openFileOutput("account.json", Context.MODE_APPEND| Context.MODE_PRIVATE));
            outputStreamWriter3.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  deleteFromFile will delete a recipe from the file
     * @param recipeID ID of the recipe you want to delete
     * @param context the context of a method
     * @param filename the name of the file
     */
    public void deleteFromFileRecipe(int recipeID, Context context, String filename) {
        Gson gson = new Gson();
        String ret = streamReader(context, filename);
        Type listRecipeType = new TypeToken<List<Recipe>>() {}.getType();
        List<Recipe> recipes = gson.fromJson(ret, listRecipeType);

        for (int i = 0; i < recipes.size(); i++){
            if (recipes.get(i).getId() == recipeID ){
                recipes.remove(i);
            }
        }
        streamWriter(recipes, context, filename, gson);
    }

    /**
     * writeToFile recipe that you want to write to file
     * @param recipeID the recipe id to write
     * @param context the method context
     * @param filename the name of the file
     */
    public void writeToFileRecipe(int recipeID, Context context, String filename) {
        Gson gson = new Gson();
        SearchAndFilter sF = new SearchAndFilter();
        String ret = streamReader(context, filename);
        Type listRecipeType = new TypeToken<List<Recipe>>() {}.getType();
        List<Recipe> recipes = gson.fromJson(ret, listRecipeType);

        if (recipes == null){
            recipes = new ArrayList<Recipe>();
        }

        for (int i = 0; i < recipes.size(); i++) {
            if (recipes.get(i).getId() == recipeID) {
                return;
            }
        }
        recipes.add(sF.searchById(recipeID));
        streamWriter(recipes, context, filename, gson);
    }

    /**
     *  deleteFromFile will delete a recipe from the file
     * @param ingredient the ingredient you want to delete
     * @param context the context of a method
     */
    public void deleteFromFileIngredients(Ingredient ingredient, Context context) {
        Gson gson = new Gson();
        String ret = streamReader(context, "ingredient.json");
        Type listRecipeType = new TypeToken<List<Ingredient>>() {}.getType();
        List<Ingredient> ingredients = gson.fromJson(ret, listRecipeType);
        for (int i = 0; i < ingredients.size(); i++){
            if (ingredients.get(i).getName().equals(ingredient.getName())) {
                ingredients.remove(i);
            }
        }
        streamWriter(ingredients, context, "ingredient.json", gson);
    }

    /**
     * writeToFile recipe that you want to write to file
     * it could add the items quantities together. :)
     * @param inputIngred the ingredient to write
     * @param context the method context
     */
    public void writeToFileIngredient(Ingredient inputIngred, Context context) {
        Gson gson = new Gson();
        String ret = streamReader(context, "ingredient.json");
        Type listIngredientType = new TypeToken<List<Ingredient>>() {}.getType();
        List<Ingredient> ingredient = gson.fromJson(ret, listIngredientType);

        if (ingredient == null) {
            ingredient = new ArrayList<Ingredient>();
        }

        for (int i = 0; i < ingredient.size(); i++) {
            if (ingredient.get(i).getName().equals(inputIngred.getName())) {
                return;
            }
        }
        ingredient.add(inputIngred);
        streamWriter(ingredient, context, "ingredient.json", gson);
    }

    /**
     * writeToFile account that you want to write to file, Since account
     * is singleton, when called this method will destroy the stored data
     * and put in the data given.
     * @param account the account to write
     * @param context the method context
     */
    public void writeToFileAccount(Account account, Context context) {
        Gson gson = new Gson();
        streamWriter(account, context, "account.json", gson);
    }

    /**
     * returns the ingredient saved in the users files
     * @param context of the methods
     * @return a list of the ingredients
     */
    public List<Ingredient> getIngredientsList(Context context){
        Gson gson = new Gson();
        String ret = streamReader(context, "ingredient.json");
        Type listRecipeType = new TypeToken<List<Ingredient>>() {}.getType();
        List<Ingredient> ingredients = gson.fromJson(ret, listRecipeType);
        return ingredients;
    }

    /**
     * returns the Recipe saved in the users files
     * @param context of the methods
     * @return a list of the Recipes
     */
    public List<Recipe> getRecipesList(Context context){
        Gson gson = new Gson();
        String ret = streamReader(context, "recipesFav.json");
        Type listRecipeType = new TypeToken<List<Recipe>>() {}.getType();
        List<Recipe> recipes = gson.fromJson(ret, listRecipeType);
        return recipes;
    }

    /**
     * returns the account saved in the users files
     * @param context of the methods
     * @return the current account
     */
    public Account getAccount(Context context){
        Gson gson = new Gson();
        String ret = streamReader(context, "account.json");
        Type listAccountType = new TypeToken<Account>() {}.getType();
        Account account= gson.fromJson(ret, listAccountType);
        return account;
    }
    /**
     * streamWriter will write the object to a json. Given a file name
     * @param object object a user wants to store in a Json
     * @param context context of the method
     * @param fileName the name of the file to store
     * @param gson transforms an object to a json
     */
    private void streamWriter(Object object, Context context, String fileName, Gson gson){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStreamWriter.write(gson.toJson(object));
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * streamReader will read what is in a json file
     * @param context the context of the method
     * @param fileName the file to read
     * @return returns a string of data
     */
    public String streamReader(Context context, String fileName) {
        String ret = "";
        try {
            InputStream inputStream = context.openFileInput(fileName);
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append("\n").append(receiveString);
                }
                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return ret;
    }
    //For testing only
    public void deleteEverythingFromFile(Context context, String fileName){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStreamWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}