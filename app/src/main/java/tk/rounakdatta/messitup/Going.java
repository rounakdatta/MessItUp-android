package tk.rounakdatta.messitup;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Going extends AppCompatActivity {


    private TimePicker timePicker1;
    private TextView time;
    private Calendar calendar;
    private String format = "";

    @Override
    public void onBackPressed() {
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, "Completing this action is compulsory!", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_going);

        timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
        time = (TextView) findViewById(R.id.textView1);
        calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        //showTime(hour, min);


        Bundle extra = getIntent().getExtras();
        String s = extra.getString("goingMessage");
        TextView tv = findViewById(R.id.goingText);
        tv.setText(s);

    }

    public void setTime(View view) {
        int hour = timePicker1.getCurrentHour();
        int min = timePicker1.getCurrentMinute();
        showTime(hour, min);
    }

    public void showTime(int hour, int min) {

        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        String hourString = String.format("%02d", hour);
        String minString = String.format("%02d", min);

        System.out.println(hourString);
        System.out.println(minString);
        System.out.println(format);

        time.setText(new StringBuilder().append(hour).append(" : ").append(min)
                .append(" ").append(format));

        Intent data = new Intent();
        data.putExtra("messSuccess", "true");
        data.putExtra("timeOfMess", hourString + ":" + minString + ":" + format);
        setResult(RESULT_OK, data);
        finish();

    }


}
