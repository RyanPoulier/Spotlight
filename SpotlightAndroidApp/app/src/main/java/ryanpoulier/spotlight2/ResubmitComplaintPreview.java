package ryanpoulier.spotlight2;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

public class ResubmitComplaintPreview extends AppCompatActivity {

    TextView ClosePreviewComplaintID, DetailTitle, CloseFinalComments, DetailIssueType, CloseTime, DetailLocation, DetailStatus;
    ImageView CloseImage1;
    VideoView CloseVideo;
    RatingBar RatingPreview;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    DBhelper DBhelper;
    String id, title, timestamp, description, issuetype, location, status, detailimage1uri, detailvideouri= "";
    Uri URI, vidURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_resubmit_complaint_preview);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_icon);


        ClosePreviewComplaintID = (TextView) findViewById(R.id.txtClosePreviewComplaintID);
        DetailTitle = (TextView) findViewById(R.id.txtClosureTitle);
        CloseFinalComments= (TextView) findViewById(R.id.txtCloseFinalComments);
        CloseTime = (TextView) findViewById(R.id.txtClosureDate);
        DetailLocation = (TextView) findViewById(R.id.txtLocation);
        CloseImage1  = (ImageView) findViewById(R.id.imageViewClose1);
        CloseVideo = (VideoView) findViewById(R.id.videoViewClose);
        RatingPreview = (RatingBar) findViewById(R.id.ratingBarRatingPreview);

        displayID();
        displayCurrentTime();
        displayImages();
        displayVideo();
        displayRating();
        displayFinalComments();


        DBhelper = new DBhelper(getApplicationContext());
        sqLiteDatabase = DBhelper.getReadableDatabase();
        cursor = DBhelper.getDetailedData(sqLiteDatabase, ClosePreviewComplaintID.getText().toString());


        // code based on https://www.youtube.com/watch?v=rSQIIaiTGvE&index=2&list=WL
        if (cursor.moveToFirst()) {
           do {
               title = cursor.getString(1);
               issuetype= cursor.getString(3);
               description= cursor.getString(4);
               location = cursor.getString(5);
               detailimage1uri = cursor.getString(6);
               detailvideouri= cursor.getString(7);
               status= cursor.getString(8);

            }
            while (cursor.moveToNext());
        }
        DetailTitle.setText(title);

    }

    private void displayCurrentTime() {
        Date currentTime= new Date();
        String date_text= currentTime.toString();
        CloseTime.setText(date_text);
    }

    private void displayID() {
        SharedPreferences transferpref = getSharedPreferences("complaintid", MODE_WORLD_READABLE);
        String id = transferpref.getString("complaintid", "no data test");
        ClosePreviewComplaintID.setText(id);
    }

    private void displayImages() {
        SharedPreferences closepref = getSharedPreferences("photo", MODE_WORLD_READABLE);
        String photolocation= closepref.getString("photo", "no data test");
        URI = Uri.parse(photolocation);

        InputStream inputStream;
        try {
            inputStream=getContentResolver().openInputStream(URI);
            Bitmap image = BitmapFactory.decodeStream(inputStream);
            CloseImage1 .setImageBitmap(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void displayVideo() {
        SharedPreferences closepref = getSharedPreferences("video", MODE_WORLD_READABLE);
        String videolocation= closepref.getString("video", "no data test");

        vidURI = Uri.parse(videolocation);
        CloseVideo.setVideoURI(vidURI);
        CloseVideo.seekTo(1000);
    }

    private void displayRating() {
        SharedPreferences closepref = getSharedPreferences("rating", MODE_WORLD_READABLE);
        String sRating = closepref.getString("rating", "no data test");
        Float fRating = Float.parseFloat(sRating);
        RatingPreview.setRating(fRating);
    }

    private void displayFinalComments() {
        SharedPreferences closepref = getSharedPreferences("finalcomments", MODE_WORLD_READABLE);
        String comments = closepref.getString("finalcomments", "no data test");
        CloseFinalComments.setText(comments);
    }
    public void CloseComplaint(View v) {
        CloseMessages();

    }

    public void CloseMessages () {
        AlertDialog.Builder builder = new AlertDialog.Builder(ResubmitComplaintPreview.this);
        builder.setTitle("Alert");
        builder.setMessage("Are you sure you want to re-submit this complaint?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                AfterCloseMessage();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    public void AfterCloseMessage (){
        AlertDialog.Builder builder = new AlertDialog.Builder(ResubmitComplaintPreview.this);

        builder.setTitle("Success!");
        builder.setMessage("Complaint was re-submitted successfully. You will also receive a confirmation via SMS shortly");
        builder.setCancelable(false);
        builder.setNeutralButton("OK",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(ResubmitComplaintPreview.this, Home.class);
                startActivity(intent);
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
