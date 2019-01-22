package tk.rounakdatta.messitup;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.HashMap;
import java.util.Map;

class ViewPagerAdapter extends FragmentPagerAdapter {

    private String title[] = {"1", "2", "3", "4", "5", "6", "7"};
    Map<String, String> number2week = new HashMap<String, String>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);

        number2week.put("1", "Monday");
        number2week.put("2", "Tuesday");
        number2week.put("3", "Wednesday");
        number2week.put("4", "Thursday");
        number2week.put("5", "Friday");
        number2week.put("6", "Saturday");
        number2week.put("7", "Sunday");
    }

    @Override
    public Fragment getItem(int i) {
        return TabFragment.getInstance(i);
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (number2week.get(String.valueOf(position + 1))).substring(0, 3);
        //return title[position];
    }
}
