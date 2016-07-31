package ryanpoulier.spotlight2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

/**
 * Created by ASUS on 05/03/2016.
 */
public class CloseComplaintVideo extends AppCompatActivity {
    public static final int VIDEO_GALLERY_REQUEST = 20;
    private VideoView vidview_Video_1;
    private String selectedPath;
    String pictureDirectoryPath;
    VideoView view_Video_1;
    Uri videoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close_complaint_video);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_icon);

        view_Video_1 = (VideoView) findViewById(R.id.vidview_Video_1);
    }

    //code from https://www.youtube.com/watch?v=wBuWqqBWziU&list=FLsCn-tnRZVHIyKOq7o6b36Q&index=1
    public void  OpenVideoGallery (View v) {
        //using an implicit intent
        Intent videoPickerIntent= new Intent (Intent.ACTION_PICK);

        File pictureDirectory= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        pictureDirectoryPath= pictureDirectory.getPath();

        Uri data= Uri.parse (pictureDirectoryPath);

        videoPickerIntent.setType("video/*");

        startActivityForResult(videoPickerIntent, VIDEO_GALLERY_REQUEST); //refactored
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK) {
            if (requestCode== VIDEO_GALLERY_REQUEST) {
                videoUri = data.getData();
                view_Video_1.setVideoURI(videoUri);
                view_Video_1.start();

            }
        }
    }

    public void OpenCloseComplaintDescription (View view){
        storeCloseVideoURI ();
        Intent intent=new Intent (this,CloseComplaintDescription.class);
        startActivity(intent);
    }

    public void storeCloseVideoURI () {

        SharedPreferences closepref = getSharedPreferences("video", MODE_WORLD_READABLE);

        SharedPreferences.Editor editor=closepref.edit();
        editor.putString("video", videoUri.toString());
        editor.apply();

        //Toast.makeText(this, "Video URI Saved", Toast.LENGTH_LONG).show();

    }
}