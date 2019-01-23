package tk.rounakdatta.messitup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Register extends AppCompatActivity {


    @Override
    public void onBackPressed() {
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, "Completing this action is compulsory!", Toast.LENGTH_SHORT);
        toast.show();
    }

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final SharedPreferences wowCookies = getApplicationContext().getSharedPreferences("wowCookies", 0);
        final SharedPreferences.Editor cookidator = wowCookies.edit();

        // show appropriate hostel on selection
        RadioGroup hostelGroup = findViewById(R.id.hostelGroup);
        hostelGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checkedRadioButton = radioGroup.findViewById(i);

                boolean isChecked = checkedRadioButton.isChecked();
                if (isChecked) {
                    String maleOrFemale = checkedRadioButton.getText().toString();
                    System.out.println(maleOrFemale);
                    if (maleOrFemale.equals("Male") || maleOrFemale.equals("Female")) {

                        String mainServer = "https://dearestdaringapplescript--rounak.repl.co";

                        String uri;
                        if (maleOrFemale.equals("Male")) {
                            uri = mainServer + "/get/hostel/boys";
                        } else {
                            uri = mainServer + "/get/hostel/girls";
                        }

                        HttpGetRequest hdhttp = new HttpGetRequest();
                        try {
                            String hostelData = hdhttp.execute(uri).get();
                            JSONArray hostelArray = new JSONArray(hostelData);

                            final List<String> hostelList = new ArrayList<String>();
                            final List<String> messList = new ArrayList<String>();

                            for (int j = 0; j < hostelArray.length(); j++) {
                                hostelList.add(hostelArray.getJSONObject(j).getString("hostelName"));
                                messList.add(hostelArray.getJSONObject(j).getString("messName"));
                            }


                            // populate the hostel spinner
                            Context context = getApplicationContext();

                            Spinner hostelSpinner = (Spinner) findViewById(R.id.hostelSelect);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, hostelList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            hostelSpinner.setAdapter(adapter);

                            hostelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    TextView messName = findViewById(R.id.messName);
                                    messName.setText(messList.get(i));
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });


                        } catch (Exception e) {
                            System.out.println(e);

                            Context context = getApplicationContext();
                            Toast toast = Toast.makeText(context, "Error fetching hostel data!", Toast.LENGTH_SHORT);
                            toast.show();
                        }

                    }
                }
            }
        });

        // display the study years
        String[] studyYears = new String[] {"I", "II", "III", "IV", "Other"};
        Spinner studySpinner = (Spinner) findViewById(R.id.studyYear);
        ArrayAdapter<String> studyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, studyYears);
        studyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        studySpinner.setAdapter(studyAdapter);

        // register new user and goto dashboard
        View registerSubmit = findViewById(R.id.registerSubmit);

        registerSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get the cred details
                TextView userEmailTextView = (TextView) findViewById(R.id.registerEmail);
                String userEmail = userEmailTextView.getText().toString();

                TextView userPasswordTextView = (TextView) findViewById(R.id.registerPassword);
                String userPassword = userPasswordTextView.getText().toString();

                TextView studentNameTextView = findViewById(R.id.studentName);
                String studentName = studentNameTextView.getText().toString();

                Spinner hostelNameSpinner = findViewById(R.id.hostelSelect);
                String hostelName = hostelNameSpinner.getSelectedItem().toString();

                TextView messNameTextView = findViewById(R.id.messName);
                String messName = messNameTextView.getText().toString();

                Spinner studyYearSpinner = findViewById(R.id.studyYear);
                String studyYear = studyYearSpinner.getSelectedItem().toString();

                String registerRequest = "email=" + userEmail + "&pwd=" + userPassword + "&studentName=" + studentName + "&hostelName=" + hostelName + "&messName=" + messName + "&studyYear=" + studyYear;

                String bar;
                HttpPostRequest foo = new HttpPostRequest();
                try {
                    bar = foo.execute("https://dearestdaringapplescript--rounak.repl.co/register", registerRequest).get();

                    JSONObject registerResults = new JSONObject(bar);
                    String userId = registerResults.getString("uid");

                    cookidator.putString("uid", userId);
                    cookidator.putString("email", registerResults.getString("email"));
                    cookidator.putBoolean("loggedIn", true);
                    cookidator.commit();

                    System.out.println(bar);

                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, "Registration success!", Toast.LENGTH_SHORT);
                    toast.show();

                    toast = Toast.makeText(context, "Logging you in!", Toast.LENGTH_SHORT);
                    toast.show();

                    Intent data = new Intent();
                    data.putExtra("registerSuccess", "true");
                    setResult(RESULT_OK, data);
                    finish();

                } catch(Exception e) {
                    System.out.println(e);

                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, "An error occurred!", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });
        

        Bundle extra = getIntent().getExtras();
        String s = extra.getString("RegisterMessage");
        tv=findViewById(R.id.registerDisplay);
        tv.setText(s);
    }
}
