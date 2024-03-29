package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        TextView mainNameTextView = findViewById(R.id.mainName);
        TextView placeOfOriginTextView = findViewById(R.id.placeOfOrigin);
        TextView descriptionTextView = findViewById(R.id.description);
        TextView alsoKnownAsTextView = findViewById(R.id.alsoKnownAs);
        TextView ingredientsTextView = findViewById(R.id.ingredients);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }


        assert intent != null;
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        mainNameTextView.setText(sandwich.getMainName());
        placeOfOriginTextView.setText(sandwich.getPlaceOfOrigin());
        descriptionTextView.setText(sandwich.getDescription());

        for (int i = 0; i <sandwich.getAlsoKnownAs().length(); i++){

            try {
                String space = ", ";
                if (i == sandwich.getAlsoKnownAs().length()-1){
                    space = ""; // to avoid comma at the end
                }
                alsoKnownAsTextView.append(sandwich.getAlsoKnownAs().get(i).toString()+space);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        for (int i = 0; i <sandwich.getIngredients().length(); i++){

            try {
                String space = ", ";
                if (i == sandwich.getIngredients().length()-1){
                    space = ""; // to avoid comma at the end
                }
                ingredientsTextView.append(sandwich.getIngredients().get(i).toString()+space);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }



        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.drawable.loading)
                .into(ingredientsIv);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

}
