package tk.rounakdatta.messitup;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TabFragment extends Fragment {

    int position;
    private TextView textView;


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
        return inflater.inflate(R.layout.fragment_tab, container, false);
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

        try {
            messMenu = mmhttp.execute(uri).get();
            textView.setText(messMenu);

        } catch (Exception e) {
            System.out.println(e);
            messMenu = "Error getting data";
            textView.setText("Error getting data");
        }



    }

}
