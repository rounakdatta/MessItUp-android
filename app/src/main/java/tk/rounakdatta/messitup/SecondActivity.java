package tk.rounakdatta.messitup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    TextView tv;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        final SharedPreferences wowCookies = getApplicationContext().getSharedPreferences("wowCookies", 0);
        final SharedPreferences.Editor cookidator = wowCookies.edit();

        uid = wowCookies.getString("uid", "null");

        // this is the dashboard activity

        Bundle extra = getIntent().getExtras();
        String s = extra.getString("WelcomeHome");
        tv = findViewById(R.id.textView);
        tv.setText(s);


        String[] arraySpinner = new String[] {
                "Breakfast", "Lunch", "Snacks", "Dinner"
        };
        Spinner mealChooser = (Spinner) findViewById(R.id.mealTime);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mealChooser.setAdapter(adapter);

        // feedback screen
        Button feedbackButton = findViewById(R.id.feedbackButton);
        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent feedBackIntent = new Intent(getApplicationContext(), Feedback.class);
                feedBackIntent.putExtra("feedbackMessage", "Please provide genuine feedback");
                startActivityForResult(feedBackIntent, 2000);
            }
        });

        // going activity
        Button goingConfirm = findViewById(R.id.goingButton);
        goingConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goingIntent = new Intent(getApplicationContext(), Going.class);
                goingIntent.putExtra("goingMessage", "Please confirm your mess going timings");
                startActivityForResult(goingIntent, 3000);
            }
        });

        // not going activity
        Button notGoingConfirm = findViewById(R.id.notGoingButton);
        notGoingConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent notGoingIntent = new Intent(getApplicationContext(), NotGoing.class);
                notGoingIntent.putExtra("notGoingMessage", "Please confirm why you wont be going");

                Spinner mealTypeChooser = (Spinner) findViewById(R.id.mealTime);
                String myMeal = mealTypeChooser.getItemAtPosition(mealTypeChooser.getSelectedItemPosition()).toString();
                notGoingIntent.putExtra("notGoingMealType", myMeal);
                startActivityForResult(notGoingIntent, 4000);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {
            if (data.getStringExtra("feedbackSuccess").equals("true")) {

                String[] foodParams = data.getStringArrayExtra("foodParams");


                String fbResponse;
                try {
                    String feedbackRequest = "uid=" + uid + "&foodQuality=" + foodParams[0] + "&foodAvailability=" + foodParams[1] + "&foodTaste=" + foodParams[2];
                    System.out.println(feedbackRequest);
                    HttpPostRequest fbhttp = new HttpPostRequest();
                    fbResponse = fbhttp.execute("https://dearestdaringapplescript--rounak.repl.co/user/feedback/mess/food", feedbackRequest).get();
                    System.out.println(fbResponse);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

        } catch(Exception e) {

            try {
                if (data.getStringExtra("messSuccess").equals("true"))  {

                    System.out.println("Came back!");
                    System.out.println(data.getStringExtra("timeOfMess"));

                    Spinner mealChooser = (Spinner) findViewById(R.id.mealTime);
                    String myMeal = mealChooser.getItemAtPosition(mealChooser.getSelectedItemPosition()).toString();

                    String mealResponse;

                    try {
                        String mealRequest = "uid=" + uid + "&mealType=" + myMeal + "&mealTime=" + data.getStringExtra("timeOfMess") + "&reason=&going=true";
                        HttpPostRequest mealhttp = new HttpPostRequest();
                        mealResponse = mealhttp.execute("https://dearestdaringapplescript--rounak.repl.co/user/meal/opinion", mealRequest).get();
                        System.out.println(mealResponse);

                    } catch(Exception ex) {
                        System.out.println(ex);
                    }


                }
            } catch (Exception e2) {
                if (data.getStringExtra("notGoingConfirm").equals("true"))  {
                    System.out.println("Yaaay");
                }
            }

        }

    }

}
