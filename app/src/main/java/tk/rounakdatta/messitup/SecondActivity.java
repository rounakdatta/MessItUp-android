package tk.rounakdatta.messitup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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


        Button feedbackButton = findViewById(R.id.feedbackButton);
        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent feedBackIntent = new Intent(getApplicationContext(), Feedback.class);
                feedBackIntent.putExtra("feedbackMessage", "Please provide genuine feedback");
                startActivityForResult(feedBackIntent, 2000);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

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

            System.out.println("Feedback received");
        } else {
            System.out.println("Feedback not received");
        }
    }

}
