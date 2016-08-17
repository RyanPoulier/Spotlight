package ryanpoulier.spotlight3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by ASUS on 07/03/2016.
 */
public class SameComplaintVideoOption extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_same_complaint_video_option);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_icon);
    }

    public void OpenSameComplaintVideo (View view){
        Intent intent=new Intent (this,SameComplaintVideo.class);
        startActivity(intent);
    }

    public void  OpenSameComplaintDescription (View v){
        Intent i=new Intent (this,SameComplaintDescription.class);
        startActivity(i);
    }
}
