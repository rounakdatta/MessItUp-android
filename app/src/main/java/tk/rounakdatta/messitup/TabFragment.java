package tk.rounakdatta.messitup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TabFragment extends Fragment {

    int position;
    private TextView textView;

    private ExpandableListView expandableListView;

    private ExpandableListViewAdapter expandableListViewAdapter;

    private List<String> listDataGroup;

    private HashMap<String, List<String>> listDataChild;

    View rootView;



    // remember that each of the instance is being rendered when you move there
    public static Fragment getInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        TabFragment tabFragment = new TabFragment();
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("pos");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_tab, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = (TextView) view.findViewById(R.id.textView);
        textView.setMovementMethod(new ScrollingMovementMethod());

        String mainServer = "https://dearestdaringapplescript--rounak.repl.co";
        String uri = mainServer + "/get/mess/menu/" + String.valueOf(position + 1);
        HttpGetRequest mmhttp = new HttpGetRequest();

        String messMenu;
        JSONArray messMenuArray;
        JSONArray breakfastItems = new JSONArray();
        JSONArray lunchItems = new JSONArray();
        JSONArray snacksItems = new JSONArray();
        JSONArray dinnerItems = new JSONArray();

        try {
            messMenu = mmhttp.execute(uri).get();
            //textView.setText(messMenu);

            messMenuArray = new JSONArray(messMenu);

            for (int j = 0; j < messMenuArray.length(); j++) {

                if (messMenuArray.getJSONObject(j).getString("mealType").equals("Breakfast")) {
                    breakfastItems = messMenuArray.getJSONObject(j).getJSONArray("menu");
                }
                else if (messMenuArray.getJSONObject(j).getString("mealType").equals("Lunch")) {
                    lunchItems = messMenuArray.getJSONObject(j).getJSONArray("menu");
                }
                else if (messMenuArray.getJSONObject(j).getString("mealType").equals("Snacks")) {
                    snacksItems = messMenuArray.getJSONObject(j).getJSONArray("menu");
                }
                else if (messMenuArray.getJSONObject(j).getString("mealType").equals("Dinner")) {
                    dinnerItems = messMenuArray.getJSONObject(j).getJSONArray("menu");
                }
            }


        } catch (Exception e) {
            System.out.println(e);
            messMenuArray = new JSONArray();
            messMenu = "Error getting data";
            textView.setText("Error getting data");
        }


        List<String> breakfast = new ArrayList<String>();
        List<String> lunch = new ArrayList<String>();
        List<String> snacks = new ArrayList<String>();
        List<String> dinner = new ArrayList<String>();

        try {

            // breakfast items
            for (int foo = 0; foo < breakfastItems.length(); foo++) {
                breakfast.add(breakfastItems.getJSONObject(foo).getString("dishName"));
            }

            // lunch items
            for (int foo = 0; foo < lunchItems.length(); foo++) {
                lunch.add(lunchItems.getJSONObject(foo).getString("dishName"));
            }

            // snacks items
            for (int foo = 0; foo < snacksItems.length(); foo++) {
                snacks.add(snacksItems.getJSONObject(foo).getString("dishName"));
            }

            // dinner items
            for (int foo = 0; foo < dinnerItems.length(); foo++) {
                dinner.add(dinnerItems.getJSONObject(foo).getString("dishName"));
            }

        } catch (Exception e) {
            System.out.println(e);
        }


        // initializing the views
        initViews();

        // initializing the listeners
        initListeners();

        // initializing the objects
        initObjects();

        // preparing list data
        initListData(breakfast, lunch, snacks, dinner);



    }

    /**
     * method to initialize the views
     */
    private void initViews() {

        expandableListView = rootView.findViewById(R.id.expandableListView);

    }

    /**
     * method to initialize the listeners
     */
    private void initListeners() {

//        // ExpandableListView on child click listener
//        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v,
//                                        int groupPosition, int childPosition, long id) {
//                Toast.makeText(
//                        getApplicationContext(),
//                        listDataGroup.get(groupPosition)
//                                + " : "
//                                + listDataChild.get(
//                                listDataGroup.get(groupPosition)).get(
//                                childPosition), Toast.LENGTH_SHORT)
//                        .show();
//                return false;
//            }
//        });
//
//        // ExpandableListView Group expanded listener
//        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//
//            @Override
//            public void onGroupExpand(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        listDataGroup.get(groupPosition) + " " + getString(R.string.text_collapsed),
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        // ExpandableListView Group collapsed listener
//        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
//
//            @Override
//            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        listDataGroup.get(groupPosition) + " " + getString(R.string.text_collapsed),
//                        Toast.LENGTH_SHORT).show();
//
//            }
//        });

    }

    /**
     * method to initialize the objects
     */
    private void initObjects() {

        // initializing the list of groups
        listDataGroup = new ArrayList<>();

        // initializing the list of child
        listDataChild = new HashMap<>();

        // initializing the adapter object
        expandableListViewAdapter = new ExpandableListViewAdapter(getActivity(), listDataGroup, listDataChild);

        // setting list adapter
        expandableListView.setAdapter(expandableListViewAdapter);

    }

    /*
     * Preparing the list data
     *
     * Dummy Items
     */
    private void initListData(List<String> breakfast, List<String> lunch, List<String> snacks, List<String> dinner) {


        // Adding group data
        listDataGroup.add(getString(R.string.breakfast));
        listDataGroup.add(getString(R.string.lunch));
        listDataGroup.add(getString(R.string.snacks));
        listDataGroup.add(getString(R.string.dinner));

        ArrayList<String> breakfastList= new ArrayList<String>();
        ArrayList<String> lunchList= new ArrayList<String>();
        ArrayList<String> snacksList= new ArrayList<String>();
        ArrayList<String> dinnerList= new ArrayList<String>();

        // list of breakfast
        for (String item : breakfast) {
            breakfastList.add(item);
        }

        // list of lunch
        for (String item : lunch) {
            lunchList.add(item);
        }

        // list of snacks
        for (String item : snacks) {
            snacksList.add(item);
        }

        // list of dinner
        for (String item : dinner) {
            dinnerList.add(item);
        }


        // Adding child data
        listDataChild.put(listDataGroup.get(0), breakfastList);
        listDataChild.put(listDataGroup.get(1), lunchList);
        listDataChild.put(listDataGroup.get(2), snacksList);
        listDataChild.put(listDataGroup.get(3), dinnerList);

        // notify the adapter
        expandableListViewAdapter.notifyDataSetChanged();
    }

}
