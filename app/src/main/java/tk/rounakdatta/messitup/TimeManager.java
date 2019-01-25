package tk.rounakdatta.messitup;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TimeManager extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_manager);

        final SharedPreferences wowCookies = getApplicationContext().getSharedPreferences("wowCookies", 0);
        final SharedPreferences.Editor cookidator = wowCookies.edit();

        Bundle extra = getIntent().getExtras();
        String timesNow = extra.getString("timesNow");
        System.out.println(timesNow);

        //cookidator.putString("opinionTime", opinionMeal);
        //cookidator.putString("feedBackTime", feedbackMeal);
    }
}
