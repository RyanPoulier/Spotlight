package ryanpoulier.spotlight3;

import android.content.Context;
import android.content.DialogInterface;
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
public class MyCommentsDataAdapter extends ArrayAdapter<String> {

    String[] complaintitle = {};
    String[] status = {};
    String[] commenttimestamp = {};
    String[] comment = {};
    Context c;
    LayoutInflater inflater;

    public MyCommentsDataAdapter(Context context, String[] complaintitle, String[] status,String[]commenttimestamp, String[]comment) {
        super(context, R.layout.comments_list_row, complaintitle);

        this.c = context;
        this.complaintitle = complaintitle;
        this.status  = status;
        this.commenttimestamp = commenttimestamp;
        this.comment = comment;
    }


    static class LayoutHandler {
        TextView COMPLAINTTITLE, COMPLAINTSTATUS, COMMENTTIMESTAMP,COMMENT;
        ImageButton BTNCOMMENTDELETE;    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.my_comments_list_row, null);
        }

        //Viewholder object
        final LayoutHandler lHandler = new LayoutHandler();

        //initializing the view
        lHandler.COMPLAINTTITLE= (TextView) convertView.findViewById(R.id.txt_my_comment_complaint_title);
        lHandler.COMPLAINTSTATUS = (TextView) convertView.findViewById(R.id.txt_my_comment_status);
        lHandler.COMMENTTIMESTAMP = (TextView) convertView.findViewById(R.id.txt_my_comment_timestamp);
        lHandler.COMMENT = (TextView) convertView.findViewById(R.id.txt_my_comment);
        lHandler.BTNCOMMENTDELETE = (ImageButton) convertView.findViewById(R.id.btn_my_comment_delete);
        lHandler.BTNCOMMENTDELETE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Alert");
                builder.setMessage("Are you sure you want to delete this comment?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AfterCommentDeleteMessage();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        // assigning data to views

        lHandler.COMPLAINTTITLE.setText(complaintitle[position]);
        lHandler.COMPLAINTSTATUS.setText(status[position]);
        lHandler.COMMENTTIMESTAMP.setText(commenttimestamp[position]);
        lHandler.COMMENT.setText(comment[position]);
        return convertView;
    }

    public void AfterCommentDeleteMessage (){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setMessage("Comment deleted");
        builder.setCancelable(false);
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }




}



