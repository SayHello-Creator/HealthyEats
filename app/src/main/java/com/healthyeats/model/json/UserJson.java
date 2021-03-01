package com.healthyeats.model.json;


import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("recipes.json", Context.MODE_PRIVATE));
            outputStreamWriter.close();
            OutputStreamWriter outputStreamWriter2 = new OutputStreamWriter(context.openFileOutput("ingredient.json", Context.MODE_PRIVATE));
            outputStreamWriter2.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  deleteFromFile will delete a recipe from the file
     * @param recipe the recipe you want to delete
     * @param context the context of a method
     */
    public void deleteFromFile(Recipe recipe, Context context) {
        Gson gson = new Gson();
        String ret = streamReader(context, "recipes.json");
        Type listRecipeType = new TypeToken<List<Recipe>>() {}.getType();
        List<Recipe> recipes = gson.fromJson(ret, listRecipeType);
        for(int i = 0; i < recipes.size(); i++){
            if( recipes.get(i).getName().equals(recipe.getName())){
                recipes.remove(i);
            }
        }
        streamWriter(recipes, context, "recipes.json", gson);
    }

    /**
     * writeToFile recipe that you want to write to file
     * @param recipe the recipe to write
     * @param context the method context
     */
    public void writeToFile(Recipe recipe, Context context) {
        Gson gson = new Gson();
        String ret = streamReader(context, "recipes.json");
        Type listRecipeType = new TypeToken<List<Recipe>>() {}.getType();
        List<Recipe> recipes = gson.fromJson(ret, listRecipeType);
        if(recipes == null){
            recipes = new ArrayList<Recipe>();
        }
        recipes.add(recipe);
        streamWriter(recipes, context, "recipes.json", gson);
    }

    /**
     *  deleteFromFile will delete a recipe from the file
     * @param ingredient the ingredient you want to delete
     * @param context the context of a method
     */
    public void deleteFromFile(Ingredient ingredient, Context context) {
        Gson gson = new Gson();
        String ret = streamReader(context, "ingredient.json");
        Type listRecipeType = new TypeToken<List<Ingredient>>() {}.getType();
        List<Ingredient> ingredients = gson.fromJson(ret, listRecipeType);
        for(int i = 0; i < ingredients.size(); i++){
            if( ingredients.get(i).getName().equals(ingredient.getName())){
                ingredients.remove(i);
            }
        }
        streamWriter(ingredients, context, "recipes.json", gson);
    }


    /**
     * writeToFile recipe that you want to write to file
     * @param ingredient the ingredient to write
     * @param context the method context
     */
    public void writeToFile(Ingredient ingredient, Context context) {
        Gson gson = new Gson();
        String ret = streamReader(context, "ingredient.json");
        Type listRecipeType = new TypeToken<List<Ingredient>>() {}.getType();
        List<Ingredient> ingredients = gson.fromJson(ret, listRecipeType);
        if(ingredients == null){
            ingredients = new ArrayList<Ingredient>();
        }
        ingredients.add(ingredient);
        streamWriter(ingredient, context, "recipes.json", gson);
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
    private String streamReader(Context context, String fileName){
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
}
