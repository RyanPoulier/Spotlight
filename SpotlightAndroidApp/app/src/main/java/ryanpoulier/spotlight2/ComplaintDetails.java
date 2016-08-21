package ryanpoulier.spotlight2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class ComplaintDetails extends AppCompatActivity {
    ImageButton Vote;
    TextView DetailComplaintID, DetailTitle, DetailDescription, DetailIssueType, DetailTime, DetailLocation, DetailStatus;
    ImageView DetailImage1;
    VideoView DetailVideo;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    DBhelper DBhelper;
    String id, title, timestamp, description, issuetype, location, status, detailimage1uri, detailvideouri = "";
    int check = 1;

    ListView lsv;
    String[] usernames = {"Female User 1", "Male User 1"};
    String[] commenttimestamps = {"21/03/2016 11:40", "20/03/2016 16:06"};
    String[] comments = {"Fix immediately", "Valid issue"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_complaint_details);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_icon);


        Vote = (ImageButton) findViewById(R.id.btn_vote);
        DetailComplaintID = (TextView) findViewById(R.id.txtDetailComplaintID);
        DetailTitle = (TextView) findViewById(R.id.txtDetailTitle);
        DetailIssueType = (TextView) findViewById(R.id.txtDetailType);
        DetailDescription = (TextView) findViewById(R.id.txtDetailDescription);
        DetailTime = (TextView) findViewById(R.id.txtDetailTime);
        DetailLocation = (TextView) findViewById(R.id.txtLocation);
        DetailStatus = (TextView) findViewById(R.id.txtDetailStatus);
        DetailImage1 = (ImageView) findViewById(R.id.imgview_Detail_Image_1);
        DetailVideo = (VideoView) findViewById(R.id.videoView_Detail_Video);

        displayID();
        Toast.makeText(this, "Do you have a similar complaint to that which is displayed on this page? If so, click the 'I HAVE THE SAME COMPLAINT' button.", Toast.LENGTH_LONG).show();


//        DBhelper = new DBhelper(getApplicationContext());
//        sqLiteDatabase = DBhelper.getReadableDatabase();
//        cursor = DBhelper.getDetailedData(sqLiteDatabase, DetailComplaintID.getText().toString());
//
//
//        // code based on https://www.youtube.com/watch?v=rSQIIaiTGvE&index=2&list=WL
//        if (cursor.moveToFirst()) {
//           do {
//               title = cursor.getString(1);
//               timestamp=cursor.getString(2);
//               issuetype= cursor.getString(3);
//               description= cursor.getString(4);
//               location = cursor.getString(5);
//               detailimage1uri = cursor.getString(6);
//               detailvideouri= cursor.getString(7);
//               status= cursor.getString(8);
//
//            }
//            while (cursor.moveToNext());
//        }
//        DetailTitle.setText(title);
//        DetailIssueType.setText(issuetype);
//        DetailDescription.setText(description);
//        DetailTime.setText(timestamp);
//        DetailLocation.setText(location);
//        DetailStatus.setText(status);
//        Uri URI = Uri.parse(detailimage1uri);
//        InputStream inputStream;
//        try {
//            inputStream=getContentResolver().openInputStream(URI);
//            Bitmap image = BitmapFactory.decodeStream(inputStream);
//            DetailImage1.setImageBitmap(image);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        Uri vidURI = Uri.parse(detailvideouri);
//        DetailVideo.setVideoURI(vidURI);
//        DetailVideo.start();
//
//        lsv = (ListView)findViewById(R.id.listViewDetailComment);
//        //Adapter object
//        CommentsDataAdapter cadap= new CommentsDataAdapter (this,usernames,commenttimestamps,comments);
//        lsv.setAdapter(cadap);
        View grid12 = findViewById(R.id.gridLayout12);
        collapse(grid12);
    }

    public void OpenRequestVotes(View view) {
        Intent intent = new Intent(this, RequestVotes.class);
        startActivity(intent);
    }

    public void OpenRequestUpdate(View view) {
        Intent intent = new Intent(this, RequestUpdate.class);
        startActivity(intent);
    }

    public void OpenNewComment(View view) {
        storeID();
        Intent intent = new Intent(this, Comment.class);
        startActivity(intent);
    }

    public void OpenSameComplaintPhotoOption(View view) {
        Intent intent = new Intent(this, SameComplaintPhotoOption.class);
        startActivity(intent);
    }

    public void OpenReportAbuse(View view) {
        Intent intent = new Intent(this, ReportAbuse.class);
        startActivity(intent);
    }

    public void VoteClicked(View view) {
        if (check == 1) {
            Vote.setBackgroundColor(Color.parseColor("#FF6FA9CF"));
            Toast.makeText(ComplaintDetails.this, "Vote submitted", Toast.LENGTH_LONG).show();
            check = 0;
        } else {
            Vote.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
            Toast.makeText(ComplaintDetails.this, "Vote cancelled", Toast.LENGTH_LONG).show();
            check = 1;
        }
    }

    private void displayID() {
        SharedPreferences transferpref = getSharedPreferences("complaintid", MODE_WORLD_READABLE);
        String id = transferpref.getString("complaintid", "no data test");
        DetailComplaintID.setText(id);
    }

    //code from http://code.tutsplus.com/tutorials/android-sdk-implement-a-share-intent--mobile-8433
    public void DetailedShare(View view) {

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Use the Spotlight app to view/vote for/comment on the complaint titled " + DetailTitle.getText().toString().toUpperCase() + " with complaint ID " + DetailComplaintID.getText().toString() + ".";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));

    }

    public void storeID() {

        SharedPreferences commentidpref = getSharedPreferences("complaintid", MODE_WORLD_READABLE);
        SharedPreferences commenttitlepref = getSharedPreferences("complainttitle", MODE_WORLD_READABLE);

        SharedPreferences.Editor editorid = commentidpref.edit();
        editorid.putString("complaintid", DetailComplaintID.toString());
        editorid.apply();
        Toast.makeText(this, "ID recorded", Toast.LENGTH_LONG).show();

        SharedPreferences.Editor editortitle = commenttitlepref.edit();
        editortitle.putString("complainttitle", DetailTitle.toString());
        editortitle.apply();
        Toast.makeText(this, "Title recorded", Toast.LENGTH_LONG).show();


    }

    public static void expand(final View v) {
        v.measure(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ActionBar.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }
}
