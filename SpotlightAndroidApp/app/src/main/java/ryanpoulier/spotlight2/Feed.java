package ryanpoulier.spotlight2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by shamal on 8/4/16.
 */
public class Feed extends Fragment{
    ListView listview;
    ListDataAdapter lsd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feed_fragment, container, false);
        listview = (ListView) view.findViewById(R.id.listFeed);
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
