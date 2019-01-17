package tk.rounakdatta.messitup;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

public class Register extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final SharedPreferences wowCookies = getApplicationContext().getSharedPreferences("wowCookies", 0);
        final SharedPreferences.Editor cookidator = wowCookies.edit();

        // register new user and goto dashboard
        View registerSubmit = findViewById(R.id.registerSubmit);

        registerSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get the cred details
                TextView userEmailTextView = (TextView) findViewById(R.id.registerEmail);
                String userEmail = userEmailTextView.getText().toString();

                TextView userPasswordTextView = (TextView) findViewById(R.id.registerPassword);
                String userPassword = userPasswordTextView.getText().toString();

                String registerRequest = "email=" + userEmail + "&pwd=" + userPassword;

                String bar;
                HttpPostRequest foo = new HttpPostRequest();
                try {
                    bar = foo.execute("https://dearestdaringapplescript--rounak.repl.co/register", registerRequest).get();

                    JSONObject registerResults = new JSONObject(bar);
                    String userId = registerResults.getString("uid");

                    cookidator.putString("uid", userId);
                    cookidator.putString("email", registerResults.getString("email"));
                    cookidator.putBoolean("loggedIn", true);
                    cookidator.commit();

                    System.out.println("-------");
                    System.out.println(bar);
                } catch(Exception e) {
                    System.out.println(e);
                }

            }
        });
        

        Bundle extra= getIntent().getExtras();
        String s=extra.getString("RegisterMessage");
        tv=findViewById(R.id.registerDisplay);
        tv.setText(s);
    }
}
