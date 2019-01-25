package tk.rounakdatta.messitup;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toolbar;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;

public class MessTable extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_table);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        //String weekday = new DateFormatSymbols().getShortWeekdays()[dayOfWeek];
        //System.out.println(weekday);

        dayOfWeek = (dayOfWeek + 5) % 7;

        TabLayout.Tab tab = tabLayout.getTabAt(dayOfWeek);
        tab.select();
    }
}
