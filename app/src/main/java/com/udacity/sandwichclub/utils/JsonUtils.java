package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String JSON_NAME_KEY = "name";
    private static final String JSON_MAIN_NAME_KEY = "mainName";
    private static final String JSON_ALSO_KNOWN_AS_KEY = "alsoKnownAs";
    private static final String JSON_ORIGIN_KEY = "placeOfOrigin";
    private static final String JSON_DESCRIPTION_KEY = "description";
    private static final String JSON_IMAGE_KEY = "image";
    private static final String JSON_INGREDIENTS_KEY = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = null;
        try {
            JSONObject sandwichJSON = new JSONObject(json);
            JSONObject nameJSON = sandwichJSON.optJSONObject(JSON_NAME_KEY);
            String mainName = "";
            List<String> alsoKnownAs = new ArrayList<>();
            if ( nameJSON != null ) {
                mainName = nameJSON.optString(JSON_MAIN_NAME_KEY);
                JSONArray alsoKnownAsList = nameJSON.optJSONArray(JSON_ALSO_KNOWN_AS_KEY);
                if ( alsoKnownAsList != null ) {
                    for (int index = 0; index < alsoKnownAsList.length(); index++) {
                        alsoKnownAs.add( alsoKnownAsList.optString(index));
                    }
                }
            }
            String placeOfOrigin = sandwichJSON.optString(JSON_ORIGIN_KEY);
            String description = sandwichJSON.optString(JSON_DESCRIPTION_KEY);
            String image = sandwichJSON.optString(JSON_IMAGE_KEY);
            List<String> ingredients = new ArrayList<>();
            JSONArray ingredientsList = sandwichJSON.optJSONArray(JSON_INGREDIENTS_KEY);
            if ( ingredientsList != null ) {
                for (int index = 0; index < ingredientsList.length(); index++) {
                    ingredients.add( ingredientsList.optString(index));
                }
            }
            sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }
}
