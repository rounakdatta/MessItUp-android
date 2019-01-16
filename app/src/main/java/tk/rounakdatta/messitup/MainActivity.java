package tk.rounakdatta.messitup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btnHome, registerButton, loginButton;
    TextView txtHome, registerMessage, loginText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // register activity
        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mainServer = "https://dearestdaringapplescript--rounak.repl.co";
                String registerPageURL = mainServer + "/register";

                String registerPage = "Loading...";

                HttpGetRequest regMe = new HttpGetRequest();
                try {
                    registerPage = regMe.execute(registerPageURL).get();
                } catch(Exception e) {
                    System.out.println(e);
                }

                Intent registerIntent = new Intent(getApplicationContext(), Register.class);

                registerIntent.putExtra("RegisterMessage",registerPage);
                startActivity(registerIntent);

            }
            });

        // home activity
        btnHome = findViewById(R.id.btnHome);
        txtHome = findViewById(R.id.txtDisplay);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mainServer = "https://dearestdaringapplescript--rounak.repl.co";
                String homepageURL = mainServer + "/";

                String homePage = "Hello Google";

                HttpGetRequest foo = new HttpGetRequest();
                try {
                    homePage = foo.execute(homepageURL).get();
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