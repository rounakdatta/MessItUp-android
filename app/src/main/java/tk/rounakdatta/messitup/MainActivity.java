package tk.rounakdatta.messitup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    Button btn;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btnSubmit);
        txt = findViewById(R.id.txtDisplay);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String homepageURL = "https://www.google.com";
                String homePage = "Hello Google";

                HttpGetRequest foo = new HttpGetRequest();
                try {
                    homePage = foo.execute(homepageURL).get();
                } catch (Exception e) {
                    System.out.println(e);
                }


                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);

                intent.putExtra("Text",homePage);
                startActivity(intent);
                txt.setText("Button presses!");


            }
        });
    }

}