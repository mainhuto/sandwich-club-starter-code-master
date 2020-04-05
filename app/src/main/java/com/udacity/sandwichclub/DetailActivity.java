package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

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
        } else {

            TextView alsKnownAsLabelTextView = (TextView) findViewById(R.id.also_known_label_tv);
            TextView alsKnownAsTextView = (TextView) findViewById(R.id.also_known_tv);
            List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
            if ( ( alsoKnownAs != null ) && ( alsoKnownAs.size() > 0 ) ) {
                alsKnownAsTextView.setText(TextUtils.join("\n", alsoKnownAs));
            } else {
                alsKnownAsTextView.setVisibility(View.GONE);
                alsKnownAsLabelTextView.setVisibility(View.GONE);
            }

            TextView ingredientsTextView = (TextView) findViewById(R.id.ingredients_tv);
            List<String> ingredients = sandwich.getIngredients();
            if ( ( ingredients != null ) && ( ingredients.size() > 0 ) ) {
                ingredientsTextView.setText(TextUtils.join("\n", ingredients));
            } else {
                ingredientsTextView.setVisibility(View.INVISIBLE);
            }

            TextView originLabelTextView = (TextView) findViewById(R.id.origin_label_tv);
            TextView originTextView = (TextView) findViewById(R.id.origin_tv);
            if ( !sandwich.getPlaceOfOrigin().isEmpty() ) {
                originTextView.setText(sandwich.getPlaceOfOrigin());
            } else {
                originLabelTextView.setVisibility(View.GONE);
                originTextView.setVisibility(View.GONE);
            }

            TextView descriptionTextView = (TextView) findViewById(R.id.description_tv);
            descriptionTextView.setText(sandwich.getDescription());

        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.drawable.user_place_holder)
                .error(R.drawable.user_place_holder)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

    }
}
