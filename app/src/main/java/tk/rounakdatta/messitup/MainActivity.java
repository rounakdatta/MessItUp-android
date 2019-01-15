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

//                // run the network connection activity in a new thread
//                new Thread(new Runnable() {
//
//                    @Override
//                    public void run() {
//
//                        try {
//                            outputOfGETRequest = getFromURL("https://www.google.com");
//                            System.out.println(outputOfGETRequest.toString());
//                        } catch (Exception e) {
//                            System.out.println("noooooooooooooo");
//                        }
//
//                    }
//
//                }).start();

                String homepageURL = "https://www.google.com";
                String homePage = "Hello Google";

                HttpGetRequest foo = new HttpGetRequest();
                foo.execute(homepageURL);

                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);

//                // run the network connection activity in a new thread
//                new Thread(new Runnable() {
//
//                    // initiate the okHttpClient
//                    OkHttpClient client = new OkHttpClient();
//
//                    // function to get data from URL
//                    String getFromURL(String url) throws IOException {
//                        Request request = new Request.Builder()
//                                .url(url)
//                                .build();
//
//                        try (Response response = client.newCall(request).execute()) {
//                            return response.body().string();
//                        } catch (Exception e) {
//                            System.out.println(e);
//                            return "Error connecting to MessItUp network";
//                        }
//                    }
//
//                    String outputOfGETRequest;
//
//                    @Override
//                    public void run() {
//
//                        try {
//                            outputOfGETRequest = getFromURL("https://www.google.com");
//                            System.out.println(outputOfGETRequest.toString());
//                        } catch (Exception e) {
//                            System.out.println("noooooooooooooo");
//                        }
//
//                    }
//
//                }).start();



            }
        });
    }

}