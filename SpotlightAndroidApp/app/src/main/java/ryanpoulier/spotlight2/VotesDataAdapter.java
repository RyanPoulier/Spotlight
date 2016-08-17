package ryanpoulier.spotlight2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by ASUS on 21/03/2016.
 */
public class VotesDataAdapter extends ArrayAdapter<String> {

        String[] complaintitle = {};
        String[] complaintstatus = {};
        String[] votedate = {};
        Context c;
        LayoutInflater inflater;

        public VotesDataAdapter(Context context, String[] title, String[] status, String[] date) {
            super(context, R.layout.comments_list_row, title);

            this.c = context;
            this.complaintitle = title;
            this.complaintstatus = status;
            this.votedate = date;
        }

    static class LayoutHandler {
        TextView COMPLAINTTITLE, VOTETIMESTAMP,COMPLAINTSTATUS;
        ImageButton BTNVOTEDELETE;    }



        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.my_votes_list_row, null);
            }


            final LayoutHandler layHandler = new LayoutHandler();

            //initializing
            layHandler.COMPLAINTTITLE = (TextView) convertView.findViewById(R.id.txt_Vote_Complaint_Title);
            layHandler.COMPLAINTSTATUS = (TextView) convertView.findViewById(R.id.txt_Vote_Status);
            layHandler.VOTETIMESTAMP = (TextView) convertView.findViewById(R.id.txt_Vote_Date);
            layHandler.BTNVOTEDELETE = (ImageButton) convertView.findViewById(R.id.brn_vote_delete);
            layHandler.BTNVOTEDELETE.setOnClickListener(new View.OnClickListener() {
                        @Override
                                public void onClick(View v) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                           builder.setTitle("Alert");
                                           builder.setMessage("Are you sure you want to delete this vote?");

                                              builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                              public void onClick(DialogInterface dialog, int id) {
                                                AfterDeleteMessage(); }
                                                                });

                                                                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                                                    public void onClick(DialogInterface dialog, int id) {
                                                                    }
                                                                });

                                                                AlertDialog alert = builder.create();
                                                                alert.show();
                                                            }
                                                        });


                    // assigning data

                    layHandler.COMPLAINTTITLE.setText(complaintitle[position]);
                    layHandler.COMPLAINTSTATUS.setText(complaintstatus[position]);
                    layHandler.VOTETIMESTAMP.setText(votedate[position]);

                    return convertView;

            }

                    public void AfterDeleteMessage (){
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                        builder.setMessage("Vote deleted");
                        builder.setCancelable(false);
                        builder.setNeutralButton("OK",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {}
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
}






