package tk.rounakdatta.messitup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Register extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Bundle extra= getIntent().getExtras();
        String s=extra.getString("RegisterMessage");
        tv=findViewById(R.id.registerDisplay);
        tv.setText(s);
    }
}
