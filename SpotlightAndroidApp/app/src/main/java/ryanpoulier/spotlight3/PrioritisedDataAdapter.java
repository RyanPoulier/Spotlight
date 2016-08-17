package ryanpoulier.spotlight3;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 21/03/2016.
 */
public class PrioritisedDataAdapter extends ArrayAdapter {
    List list = new ArrayList();
    int check = 1;

    public PrioritisedDataAdapter(Context context, int resource) {
        super(context, resource);
    }

    static class LayoutHandler {
        TextView TITLE, TIMESTAMP,ID,NUMBER;
        ImageButton BTNVOTE,BTNCOMMENT,BTNSHARE;
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
    public View getView(int position, final View convertView, final ViewGroup parent) {

        View row = convertView;
        final LayoutHandler layoutHandler;
        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.prioritisedlistrow, parent, false);
            layoutHandler = new LayoutHandler();
            // tut 32 got it wrong
            layoutHandler.TITLE = (TextView) row.findViewById(R.id.t_title);
            layoutHandler.TIMESTAMP = (TextView) row.findViewById(R.id.t_timestamp);
            layoutHandler.ID = (TextView) row.findViewById(R.id.txtID);
            layoutHandler.NUMBER = (TextView) row.findViewById(R.id.txt_priority_number);

            layoutHandler.BTNCOMMENT= (ImageButton) row.findViewById(R.id.btn_summarycomment);
            layoutHandler.BTNCOMMENT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // based on content from http://stackoverflow.com/questions/4197135/how-to-start-activity-in-adapter
                    Intent i = new Intent(getContext(), ComplaintDetails.class);

                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    v.getContext().startActivity(i);
                }
            });

            layoutHandler.BTNVOTE = (ImageButton) row.findViewById(R.id.btn_summaryvote);
            layoutHandler.BTNVOTE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //code from http://stackoverflow.com/questions/30427450/select-and-deselect-in-button-onclick-in-android
                    if (check == 1) {
                        layoutHandler.BTNVOTE.setBackgroundColor(Color.parseColor("#FF6FA9CF"));
                        Toast.makeText(getContext(), "Vote submitted", Toast.LENGTH_LONG).show();
                        check = 0;
                    } else {
                        layoutHandler.BTNVOTE.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                        Toast.makeText(getContext(), "Vote cancelled", Toast.LENGTH_LONG).show();
                        check = 1;
                    }
                }
            });

            layoutHandler.BTNSHARE = (ImageButton) row.findViewById(R.id.btn_summaryshare);
            layoutHandler.BTNSHARE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //code from http://code.tutsplus.com/tutorials/android-sdk-implement-a-share-intent--mobile-8433
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBody = "Use the Spotlight app to view/vote for/comment on the complaint titled '"+ layoutHandler.TITLE.getText().toString().toUpperCase() + "'.";
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    sharingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    parent.getContext().startActivity(Intent.createChooser(sharingIntent, "Share via"));
                }
            });

            row.setTag(layoutHandler);
        }

        else {
            layoutHandler = (LayoutHandler) row.getTag();

        }

        DataProvider dataProvider = (DataProvider) this.getItem(position);
        layoutHandler.TITLE.setText(dataProvider.getTitle());
        layoutHandler.TIMESTAMP.setText(dataProvider.getTimestamp());
        layoutHandler.ID.setText(dataProvider.getID());
        layoutHandler.NUMBER.setText(String.valueOf(position+1));

        return row;

    }


}