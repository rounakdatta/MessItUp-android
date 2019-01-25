package tk.rounakdatta.messitup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class NotGoing extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, "Completing this action is compulsory!", Toast.LENGTH_SHORT);
        toast.show();
    }

    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_going);

        final SharedPreferences wowCookies = getApplicationContext().getSharedPreferences("wowCookies", 0);
        final SharedPreferences.Editor cookidator = wowCookies.edit();

        uid = wowCookies.getString("uid", "null");
        Bundle extra = getIntent().getExtras();
        final String whichMealNotGoing = extra.getString("notGoingMealType");

        String[] arraySpinner = new String[] {
                "I'm eating out today", "I'll skip this meal", "I rarely eat at mess", "I do not like today's " + whichMealNotGoing
        };

        final Spinner reasonChooser = (Spinner) findViewById(R.id.notGoingReason);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reasonChooser.setAdapter(adapter);

        // confirm that not going to mess with reason
        Button notGoingButton = findViewById(R.id.notGoingConfirm);
        notGoingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String myReason = reasonChooser.getItemAtPosition(reasonChooser.getSelectedItemPosition()).toString();

                String mealResponse;

                try {
                    System.out.println(uid);
                    String mealRequest = "uid=" + uid + "&mealType=" + whichMealNotGoing + "&mealTime=&reason=" + myReason + "&going=false";
                    HttpPostRequest mealhttp = new HttpPostRequest();
                    mealResponse = mealhttp.execute("https://dearestdaringapplescript--rounak.repl.co/user/meal/opinion", mealRequest).get();
                    System.out.println(mealResponse);

                    cookidator.putString("opinionGivenForMeal", whichMealNotGoing);
                    cookidator.commit();

                    Intent data = new Intent();
                    data.putExtra("notGoingConfirm", "true");
                    setResult(RESULT_OK, data);
                    finish();

                } catch(Exception ex) {
                    System.out.println(ex);
                }

            }
        });

    }
}
