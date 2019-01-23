package tk.rounakdatta.messitup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    Button btnHome, registerButton, logoutButton, registerSubmit, loginButton;
    TextView txtHome, registerMessage, loginText;

    String uid;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data.getStringExtra("registerSuccess").equals("true")) {
            findViewById(R.id.registerButton).setVisibility(View.GONE);
            findViewById(R.id.loginButton).setVisibility(View.GONE);
            findViewById(R.id.logoutButton).setVisibility(View.VISIBLE);
            findViewById(R.id.btnHome).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.registerButton).setVisibility(View.VISIBLE);
            findViewById(R.id.loginButton).setVisibility(View.VISIBLE);
            findViewById(R.id.logoutButton).setVisibility(View.GONE);
            findViewById(R.id.btnHome).setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SharedPreferences wowCookies = getApplicationContext().getSharedPreferences("wowCookies", 0);
        final SharedPreferences.Editor cookidator = wowCookies.edit();

        uid = wowCookies.getString("uid", "null");
        if (uid.length() == 28) {
            findViewById(R.id.registerButton).setVisibility(View.GONE);
            findViewById(R.id.loginButton).setVisibility(View.GONE);
            findViewById(R.id.logoutButton).setVisibility(View.VISIBLE);
            findViewById(R.id.btnHome).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.registerButton).setVisibility(View.VISIBLE);
            findViewById(R.id.loginButton).setVisibility(View.VISIBLE);
            findViewById(R.id.logoutButton).setVisibility(View.GONE);
            findViewById(R.id.btnHome).setVisibility(View.GONE);
        }

        // register activity
        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                // goto register activity
                Intent registerIntent = new Intent(getApplicationContext(), Register.class);
                registerIntent.putExtra("RegisterMessage", "Please register");
                startActivityForResult(registerIntent, 1000);

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
                startActivityForResult(loginIntent, 2000);

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

                    findViewById(R.id.registerButton).setVisibility(View.VISIBLE);
                    findViewById(R.id.loginButton).setVisibility(View.VISIBLE);
                    findViewById(R.id.logoutButton).setVisibility(View.GONE);
                    findViewById(R.id.btnHome).setVisibility(View.GONE);

                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, "Logged you out!", Toast.LENGTH_SHORT);
                    toast.show();

                } catch(Exception e) {
                    System.out.println(e);

                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, "Error logging out!!", Toast.LENGTH_SHORT);
                    toast.show();

                }


            }
        });

        // dish menu activity
        Button todayMenu = findViewById(R.id.todayMenu);
        todayMenu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent menuIntent = new Intent(getApplicationContext(), MessTable.class);
                startActivity(menuIntent);

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