package ryanpoulier.spotlight3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by ASUS on 07/03/2016.
 */
public class SameComplaintPhotoOption extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_same_complaint_photo_option);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_icon);
    }

    public void OpenSameComplaintPhoto (View view){
        Intent intent=new Intent (this,SameComplaintPhoto.class);
        startActivity(intent);
    }

    public void  OpenSameComplaintVideoOption (View view){
        Intent intent=new Intent (this,SameComplaintVideoOption.class);
        startActivity(intent);
    }

}
