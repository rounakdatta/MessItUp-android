package tk.rounakdatta.messitup;

import android.content.Intent;
import android.media.Rating;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class Feedback extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        final String[] foodParams = new String[3];


        RatingBar foodQuality = findViewById(R.id.foodQuality);
        foodQuality.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                foodParams[0] = String.valueOf(v);

            }
        });

        RatingBar foodAvailability = findViewById(R.id.foodAvailability);
        foodAvailability.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                foodParams[1] = String.valueOf(v);

            }
        });

        RatingBar foodTaste = findViewById(R.id.foodTaste);
        foodTaste.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                foodParams[2] = String.valueOf(v);

            }
        });

        Button submitFeedback = findViewById(R.id.submitFeedback);
        submitFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent data = new Intent();
                data.putExtra("feedbackSuccess", "true");
                data.putExtra("foodParams", foodParams);
                setResult(RESULT_OK, data);
                finish();

            }
        });


        Bundle extra = getIntent().getExtras();
        String s = extra.getString("feedbackMessage");
        TextView tv = findViewById(R.id.feedbackMessage);
        tv.setText(s);
    }
}
