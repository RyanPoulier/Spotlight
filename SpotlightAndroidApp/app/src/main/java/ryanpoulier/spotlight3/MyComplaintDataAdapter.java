package ryanpoulier.spotlight3;

import android.content.Context;
import android.content.Intent;
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
public class MyComplaintDataAdapter extends ArrayAdapter {
    List mylist = new ArrayList();


    public MyComplaintDataAdapter(Context cnt, int resource) {
        super(cnt, resource);
    }

    static class LayoutHandler {
        TextView TITLE, TIMESTAMP, ID;
        ImageButton BTNCLOSE,BTNDELETE, BTNSHARE;
    }

    @Override
    public void add(Object object) {
        super.add(object);
        mylist.add(object);
    }

    @Override
    public int getCount() {
        return mylist.size();
    }

    @Override
    public Object getItem(int position) {
        return mylist.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        LayoutHandler lytHandler;
        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.my_complaints_list_row, parent, false);
            lytHandler = new LayoutHandler();
            // tut 32 got it wrong
            lytHandler.TITLE = (TextView) row.findViewById(R.id.txt_my_title);
            lytHandler.TIMESTAMP = (TextView) row.findViewById(R.id.t_timestamp);
            lytHandler.ID = (TextView) row.findViewById (R.id.txtMyID);
            lytHandler.BTNCLOSE= (ImageButton) row.findViewById(R.id.btn_close);
            lytHandler.BTNCLOSE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // based on content from http://stackoverflow.com/questions/4197135/how-to-start-activity-in-adapter

                    Intent i = new Intent(getContext(), CloseComplaintDetails.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    v.getContext().startActivity(i);
                }
            });
            lytHandler.BTNDELETE = (ImageButton) row.findViewById(R.id.btn_my_complaint_delete);
            lytHandler.BTNDELETE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // based on content from http://stackoverflow.com/questions/4197135/how-to-start-activity-in-adapter
                    Intent intnew = new Intent(getContext(), DeleteComplaint.class);
                    intnew.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    v.getContext().startActivity(intnew);
                }
            });

            row.setTag(lytHandler);
        } else {
            lytHandler = (LayoutHandler) row.getTag();

        }

        DataProvider dataProvider = (DataProvider) this.getItem(position);
        lytHandler.TITLE.setText(dataProvider.getTitle());
        lytHandler.TIMESTAMP.setText(dataProvider.getTimestamp());
        lytHandler.ID.setText(dataProvider.getID());

        return row;

    }

}
