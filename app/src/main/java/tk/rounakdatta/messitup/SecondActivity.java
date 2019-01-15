package tk.rounakdatta.messitup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Bundle extra= getIntent().getExtras();
        String s=extra.getString("Text");
        tv=findViewById(R.id.textView);
        tv.setText(s);
    }
}
