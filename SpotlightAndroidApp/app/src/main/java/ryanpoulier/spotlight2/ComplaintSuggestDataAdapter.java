package ryanpoulier.spotlight2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 24/02/2016.
 */
public class ComplaintSuggestDataAdapter extends ArrayAdapter {
    List list = new ArrayList();

    public ComplaintSuggestDataAdapter(Context context, int resource) {
        super(context, resource);
    }

    static class LayoutHandler {
        TextView TITLE, TIMESTAMP,ID;
        ImageButton BTNVOTE,BTNCOMMENT;
    }

    @Override
    public void add(Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {

        View row = convertView;
        final LayoutHandler layoutHandler;
        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.complaint_suggest_list_row, parent, false);
            layoutHandler = new LayoutHandler();
            // tut 32 got it wrong
            layoutHandler.TITLE = (TextView) row.findViewById(R.id.t_title);
            layoutHandler.TIMESTAMP = (TextView) row.findViewById(R.id.t_timestamp);
            layoutHandler.ID = (TextView) row.findViewById(R.id.txtID);
            row.setTag(layoutHandler);
        }

        else {
            layoutHandler = (LayoutHandler) row.getTag();

        }

        SuggestComplaintProvider dataProvider = (SuggestComplaintProvider) this.getItem(position);
        layoutHandler.TITLE.setText(dataProvider.getTitle());
        layoutHandler.TIMESTAMP.setText(dataProvider.getTimestamp());
        layoutHandler.ID.setText(dataProvider.getID());

        return row;

    }


}
