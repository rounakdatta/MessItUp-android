package tk.rounakdatta.messitup;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

public class Login extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final SharedPreferences wowCookies = getApplicationContext().getSharedPreferences("wowCookies", 0);
        final SharedPreferences.Editor cookidator = wowCookies.edit();

        // login old user and goto dashboard
        View loginSubmit = findViewById(R.id.loginSubmit);

        loginSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get the cred details
                TextView userEmailTextView = (TextView) findViewById(R.id.loginEmail);
                String userEmail = userEmailTextView.getText().toString();

                TextView userPasswordTextView = (TextView) findViewById(R.id.loginPassword);
                String userPassword = userPasswordTextView.getText().toString();

                String loginRequest = "email=" + userEmail + "&pwd=" + userPassword;

                String bar;
                HttpPostRequest foo = new HttpPostRequest();
                try {
                    bar = foo.execute("https://dearestdaringapplescript--rounak.repl.co/login", loginRequest).get();

                    JSONObject loginResults = new JSONObject(bar);
                    String userId = loginResults.getString("uid");

                    cookidator.putString("uid", userId);
                    cookidator.putString("email", loginResults.getString("email"));
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
        String s=extra.getString("LoginMessage");
        tv = findViewById(R.id.loginDisplay);
        tv.setText(s);

    }
}
