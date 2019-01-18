package tk.rounakdatta.messitup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    Button btnHome, registerButton, logoutButton, registerSubmit, loginButton;
    TextView txtHome, registerMessage, loginText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SharedPreferences wowCookies = getApplicationContext().getSharedPreferences("wowCookies", 0);
        final SharedPreferences.Editor cookidator = wowCookies.edit();

        // register activity
        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                // goto register activity
                Intent registerIntent = new Intent(getApplicationContext(), Register.class);
                registerIntent.putExtra("RegisterMessage", "Please register");
                startActivity(registerIntent);


                /*
                // register GET API
                String mainServer = "https://dearestdaringapplescript--rounak.repl.co";
                String registerPageURL = mainServer + "/register";

                String registerPage = "Loading...";

                HttpGetRequest regMe = new HttpGetRequest();
                try {
                    registerPage = regMe.execute(registerPageURL).get();
                } catch(Exception e) {
                    System.out.println(e);
                }

                // register POST API
                String bar;
                HttpPostRequest foo = new HttpPostRequest();
                try {
                    bar = foo.execute("https://dearestdaringapplescript--rounak.repl.co/register", "/register").get();
                    System.out.println("-------");
                    System.out.println(bar);
                } catch(Exception e) {
                    System.out.println(e);
                }

                Intent registerIntent = new Intent(getApplicationContext(), Register.class);

                registerIntent.putExtra("RegisterMessage",registerPage);
                startActivity(registerIntent);

                */

            }
            });


        // login activity
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                // goto login activity
                Intent loginIntent = new Intent(getApplicationContext(), Login.class);
                loginIntent.putExtra("LoginMessage", "Please login");
                startActivity(loginIntent);

            }
        });

        // logout activity
        logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {

                String mainServer = "https://dearestdaringapplescript--rounak.repl.co";
                String logoutURL = mainServer + "/logout";

                HttpGetRequest regMe = new HttpGetRequest();
                try {
                    regMe.execute(logoutURL).get();
                    cookidator.remove("uid");
                    cookidator.remove("email");
                    cookidator.putBoolean("loggedIn", false);

                    cookidator.commit();

                } catch(Exception e) {
                    System.out.println(e);
                }

            }
        });

        // userdashboard activity
        btnHome = findViewById(R.id.btnHome);
        txtHome = findViewById(R.id.txtDisplay);
        btnHome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                String mainServer = "https://dearestdaringapplescript--rounak.repl.co";
                String homepageURL = mainServer + "/userdashboard";

                String homePage = "Loading...";

                HttpPostRequest foo = new HttpPostRequest();
                try {

                    String userId = wowCookies.getString("uid", "null");

                    homePage = foo.execute(homepageURL, "uid=" + userId).get();
                } catch (Exception e) {
                    System.out.println(e);
                }


                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);

                intent.putExtra("WelcomeHome",homePage);
                startActivity(intent);
                txtHome.setText("Button presses!");


            }
        });

    }

}