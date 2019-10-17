package com.udacity.sandwichclub.utils;


import com.udacity.sandwichclub.model.Sandwich;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {


    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = null;

        try {
            JSONObject obj = new JSONObject(json);
            JSONObject nameObject = new JSONObject(String.valueOf(obj.getJSONObject("name")));
            JSONArray alsoKnownAs = nameObject.getJSONArray("alsoKnownAs");

            JSONArray ingredients = obj.getJSONArray("ingredients");
            String mainName = nameObject.getString("mainName");
            String placeOfOrigin = obj.getString("placeOfOrigin");
            String description = obj.getString("description");
            String image = obj.getString("image");

            sandwich = new Sandwich(mainName, alsoKnownAs, ingredients, placeOfOrigin, description, image);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }
}
