package ryanpoulier.spotlight3;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by shamal on 8/5/16.
 */
public class MyComplaints extends Fragment {
    ListView listview;
    ListDataAdapter lsd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mycomplaints, container, false);
        listview = (ListView) view.findViewById(R.id.listViewMyComplaints);
        lsd = new ListDataAdapter(getActivity().getApplicationContext(), R.layout.latest_list_row);
        listview.setAdapter(lsd);

        lsd.add(new DataProvider("title A", "12:00", "1"));
        lsd.add(new DataProvider("title B", "17:00", "2"));
        lsd.add(new DataProvider("title C", "17:00", "2"));
        lsd.add(new DataProvider("title D", "17:00", "2"));
        lsd.add(new DataProvider("title E", "17:00", "2"));
        // Inflate the layout for this fragment
        return view;
    }
}

