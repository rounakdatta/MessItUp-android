package tk.rounakdatta.messitup;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;

public class SecondActivity extends AppCompatActivity {
    TextView tv;
    String uid;

    SharedPreferences wowCookies;
    SharedPreferences.Editor cookidator;

    int breakfast_start_time = 730;
    int lunch_start_time = 1130;
    int snacks_start_time = 1630;
    int dinner_start_time = 1930;

    int breakfast_end_time = 900;
    int lunch_end_time = 1400;
    int snacks_end_time = 1730;
    int dinner_end_time = 2130;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        wowCookies = getApplicationContext().getSharedPreferences("wowCookies", 0);
        cookidator = wowCookies.edit();

        Date d = new Date();
        CharSequence nowTime  = DateFormat.format("HHmm", d.getTime());
        int nowTimeValue = Integer.parseInt(nowTime.toString());

        // feedback time calculation
        String meal = "NULL";
        if (nowTimeValue >= breakfast_start_time && nowTimeValue < lunch_start_time) {
            meal = "Breakfast";
        } else if (nowTimeValue >= lunch_start_time && nowTimeValue <= snacks_start_time) {
            meal = "Lunch";
        } else if (nowTimeValue >= snacks_start_time && nowTimeValue <= dinner_start_time) {
            meal = "Snacks";
        } else {
            meal = "Dinner";
        }

        TextView feedbackTime = findViewById(R.id.feedbackTime);
        feedbackTime.setText(meal);



        // should we enable / disable the feedback button
        String whatIsMyLastFeedback = wowCookies.getString("feedbackGivenForMeal", "null");
        Button theFeedbackButton = findViewById(R.id.feedbackButton);
        if (whatIsMyLastFeedback.equals(meal)) {
            theFeedbackButton.setEnabled(false);
        } else {
            theFeedbackButton.setEnabled(true);
        }


        // opinion time calculation
        String opinionMeal = "NULL";
        if (nowTimeValue >= breakfast_start_time && nowTimeValue < lunch_start_time) {
            opinionMeal = "Lunch";
        } else if (nowTimeValue >= lunch_start_time && nowTimeValue <= snacks_start_time) {
            opinionMeal = "Snacks";
        } else if (nowTimeValue >= snacks_start_time && nowTimeValue <= dinner_start_time) {
            opinionMeal = "Dinner";
        } else {
            opinionMeal = "Breakfast";
        }

        TextView opinionTime = findViewById(R.id.opinionTime);
        opinionTime.setText(opinionMeal);

        // should we enable / disable the going / not going button
        String whatIsMyLastOpinion = wowCookies.getString("opinionGivenForMeal", "null");
        Button goingButton = findViewById(R.id.goingButton);
        Button notGoingButton = findViewById(R.id.notGoingButton);
        if (whatIsMyLastOpinion.equals(opinionTime.getText())) {
            goingButton.setEnabled(false);
            notGoingButton.setEnabled(false);
        } else {
            goingButton.setEnabled(true);
            notGoingButton.setEnabled(true);
        }


        uid = wowCookies.getString("uid", "null");

        // this is the dashboard activity
        Bundle extra = getIntent().getExtras();
        String s = extra.getString("WelcomeHome");
        tv = findViewById(R.id.textView);
        tv.setText(s);

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

                TextView opinionText = findViewById(R.id.opinionTime);
                String myMeal = opinionText.getText().toString();
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

                TextView feedbackText = findViewById(R.id.feedbackTime);
                String myMeal = feedbackText.getText().toString();
                System.out.println(myMeal);

                String fbResponse;
                try {
                    String feedbackRequest = "uid=" + uid + "&foodQuality=" + foodParams[0] + "&foodAvailability=" + foodParams[1] + "&foodTaste=" + foodParams[2] + "&mealType=" + myMeal;
                    System.out.println(feedbackRequest);
                    HttpPostRequest fbhttp = new HttpPostRequest();
                    fbResponse = fbhttp.execute("https://dearestdaringapplescript--rounak.repl.co/user/feedback/mess/food", feedbackRequest).get();
                    System.out.println(fbResponse);

                    cookidator.putString("feedbackGivenForMeal", myMeal);
                    cookidator.commit();

                } catch (Exception e) {
                    System.out.println(e);
                }
            }

        } catch(Exception e) {

            try {
                if (data.getStringExtra("messSuccess").equals("true"))  {

                    System.out.println("Came back!");
                    System.out.println(data.getStringExtra("timeOfMess"));

                    TextView opinionText = findViewById(R.id.opinionTime);
                    String myMeal = opinionText.getText().toString();

                    String mealResponse;

                    try {
                        String mealRequest = "uid=" + uid + "&mealType=" + myMeal + "&mealTime=" + data.getStringExtra("timeOfMess") + "&reason=&going=true";
                        HttpPostRequest mealhttp = new HttpPostRequest();
                        mealResponse = mealhttp.execute("https://dearestdaringapplescript--rounak.repl.co/user/meal/opinion", mealRequest).get();
                        System.out.println(mealResponse);

                    } catch(Exception ex) {
                        System.out.println(ex);
                    }

                    TextView opinionTime = findViewById(R.id.opinionTime);
                    cookidator.putString("opinionGivenForMeal", (String) opinionTime.getText());
                    cookidator.commit();

                    // should we enable / disable the going / not going button
                    String whatIsMyLastOpinion = wowCookies.getString("opinionGivenForMeal", "null");
                    Button goingButton = findViewById(R.id.goingButton);
                    Button notGoingButton = findViewById(R.id.notGoingButton);
                    if (whatIsMyLastOpinion.equals(opinionTime.getText())) {
                        goingButton.setEnabled(false);
                        notGoingButton.setEnabled(false);
                    } else {
                        goingButton.setEnabled(true);
                        notGoingButton.setEnabled(true);
                    }


                }
            } catch (Exception e2) {
                if (data.getStringExtra("notGoingConfirm").equals("true"))  {
                    System.out.println("Yaaay");

                    // should we enable / disable the going / not going button
                    String whatIsMyLastOpinion = wowCookies.getString("opinionGivenForMeal", "null");
                    Button goingButton = findViewById(R.id.goingButton);
                    Button notGoingButton = findViewById(R.id.notGoingButton);
                    TextView opinionTime = findViewById(R.id.opinionTime);
                    if (whatIsMyLastOpinion.equals(opinionTime.getText())) {
                        goingButton.setEnabled(false);
                        notGoingButton.setEnabled(false);
                    } else {
                        goingButton.setEnabled(true);
                        notGoingButton.setEnabled(true);
                    }

                }
            }

        }

        // should we enable / disable the feedback button
        TextView feedbackTime = findViewById(R.id.feedbackTime);
        String meal = (String) feedbackTime.getText();
        String whatIsMyLastFeedback = wowCookies.getString("feedbackGivenForMeal", "null");
        Button theFeedbackButton = findViewById(R.id.feedbackButton);
        if (whatIsMyLastFeedback.equals(meal)) {
            theFeedbackButton.setEnabled(false);
        } else {
            theFeedbackButton.setEnabled(true);
        }

    }

}
