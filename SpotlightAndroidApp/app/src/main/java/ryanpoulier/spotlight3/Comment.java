package ryanpoulier.spotlight3;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Comment extends AppCompatActivity {

    TextView CommentComplaintID, CommentComplaintTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_icon);

        CommentComplaintID = (TextView) findViewById(R.id.txt_comment_complaint_id);
        CommentComplaintTitle = (TextView) findViewById(R.id.txt_comment_complaint_title);

        //displayIDTitle();
    }

    private void displayIDTitle() {
        SharedPreferences commentidpref = getSharedPreferences("complaintid", MODE_WORLD_READABLE);
        String cid = commentidpref.getString("complaintid", "no data test");
        CommentComplaintID.setText(cid);

        SharedPreferences commenttitlepref = getSharedPreferences("complainttitle", MODE_WORLD_READABLE);
        String ctitle = commenttitlepref .getString("complainttitle", "no data test");
        CommentComplaintTitle.setText(ctitle);
    }

    public void CommentSubmissionMessages (View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Comment.this);
        builder.setTitle("Alert");
        builder.setMessage("Are you sure you want to submit this comment?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                AfterSubmissionMessage();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    public void AfterSubmissionMessage (){
        AlertDialog.Builder builder = new AlertDialog.Builder(Comment.this);

        builder.setTitle("Alert");
        builder.setMessage("Comment was submitted. You will now be redirected to the complaint view page");
        builder.setCancelable(false);
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(Comment.this, ComplaintDetails.class);
                startActivity(intent);
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
