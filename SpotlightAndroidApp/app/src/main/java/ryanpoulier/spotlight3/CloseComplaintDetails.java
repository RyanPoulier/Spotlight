package ryanpoulier.spotlight3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by ASUS on 25/03/2016.
 */
public class CloseComplaintDetails extends AppCompatActivity {
    ImageButton Vote;
    TextView CloseComplaintID, DetailTitle, DetailDescription, DetailIssueType, DetailTime, DetailLocation, DetailStatus;
    ImageView DetailImage1;
    VideoView DetailVideo;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    DBhelper DBhelper;
    String id, title, timestamp, description, issuetype, location, status, detailimage1uri, detailvideouri= "";


    ListView lsv;
    String [] usernames= {"Female User 1","Male User 1"};
    String [] commenttimestamps= {"21/03/2016 11:40","20/03/2016 16:06"};
    String [] comments = {"Fix immediately","Valid issue"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.close_complaint_details);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_icon);


        Vote = (ImageButton) findViewById(R.id.btn_vote);
        CloseComplaintID = (TextView) findViewById(R.id.txtCloseComplaintID);
        DetailTitle = (TextView) findViewById(R.id.txtDetailTitle);
        DetailIssueType = (TextView) findViewById(R.id.txtDetailType);
        DetailDescription = (TextView) findViewById(R.id.txtDetailDescription);
        DetailTime = (TextView) findViewById(R.id.txtDetailTime);
        DetailLocation = (TextView) findViewById(R.id.txtLocation);
        DetailStatus = (TextView) findViewById(R.id.txtDetailStatus);
        DetailImage1  = (ImageView) findViewById(R.id.imgview_Detail_Image_1);
        DetailVideo = (VideoView) findViewById(R.id.videoView_Detail_Video);

        displayID();


        DBhelper = new DBhelper(getApplicationContext());
        sqLiteDatabase = DBhelper.getReadableDatabase();
        cursor = DBhelper.getDetailedData(sqLiteDatabase, CloseComplaintID.getText().toString());


        // code based on https://www.youtube.com/watch?v=rSQIIaiTGvE&index=2&list=WL
        if (cursor.moveToFirst()) {
            do {
                title = cursor.getString(1);
                timestamp=cursor.getString(2);
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
        DetailIssueType.setText(issuetype);
        DetailDescription.setText(description);
        DetailTime.setText(timestamp);
        DetailLocation.setText(location);
        DetailStatus.setText(status);
        Uri URI = Uri.parse(detailimage1uri);
        InputStream inputStream;
        try {
            inputStream=getContentResolver().openInputStream(URI);
            Bitmap image = BitmapFactory.decodeStream(inputStream);
            DetailImage1.setImageBitmap(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Uri vidURI = Uri.parse(detailvideouri);
        DetailVideo.setVideoURI(vidURI);
        DetailVideo.seekTo(1000);

        lsv = (ListView)findViewById(R.id.listViewDetailComment);
        //Adapter object
        CommentsDataAdapter cadap= new CommentsDataAdapter (this,usernames,commenttimestamps,comments);
        lsv.setAdapter(cadap);
    }

    public void OpenCloseComplaintPhotoOption(View view) {

        Intent intent = new Intent(this, CloseComplaintPhotoOption.class);
        startActivity(intent);
    }

    private void displayID() {
        SharedPreferences transferpref = getSharedPreferences("complaintid", MODE_WORLD_READABLE);
        String id = transferpref.getString("complaintid", "no data test");
        CloseComplaintID.setText(id);
    }
}

