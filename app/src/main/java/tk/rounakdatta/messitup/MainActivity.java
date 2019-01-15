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
        btn=findViewById(R.id.btnSubmit);
        txt=findViewById(R.id.txtDisplay);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),SecondActivity.class);


                    new Thread(new Runnable(){

                        OkHttpClient client = new OkHttpClient();

                        String runn(String url) throws IOException {
                            Request request = new Request.Builder()
                                    .url(url)
                                    .build();

                            try (Response response = client.newCall(request).execute()) {
                                return response.body().string();
                            } catch(Exception e) {
                                System.out.println(e);
                                return "no";
                            }
                        }



                        @Override
                        public void run() {
                            // Do network action in this function

                            try {
                                String foo = runn("https://www.google.com");
                                System.out.println(foo.toString());
                            } catch(Exception e) {
                                System.out.println("noooooooooooooo");
                            }

                        }
                    }).start();


                intent.putExtra("Text","helllllllllllllo0o");
                startActivity(intent);
                txt.setText("Button presses!");

            }
        });

//        URL url = new URL("http://www.android.com/");
//        HttpURLConnection connection=new HttpURLConnection("http://google.com") {
//            @Override
//            public void disconnect() {
//
//            }
//
//            @Override
//            public boolean usingProxy() {
//                return false;
//            }
//
//            @Override
//            public void connect() throws IOException {
//
//            }
//        };
    }



//    public String doGet(String urlString) {
////        try {
////            URL url = new URL(urlString);
////
////            HttpURLConnection foo = (HttpURLConnection)url.openConnection();
////
////            BufferedReader bro = new BufferedReader(new InputStreamReader(foo.getInputStream()));
////            System.out.println("RoD"+String.valueOf(bro));
////            return "helloworld";
////
////        } catch(Exception e) {
////            System.out.println(e);
////            return "error!";
////        }
//
//
//
//
//    }

}
