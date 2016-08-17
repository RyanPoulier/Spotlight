package ryanpoulier.spotlight3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by ASUS on 02/03/2016.
 */
public class CloseComplaintPhotoOption extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close_complaint_photo_option);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_icon);
    }

    public void OpenCloseComplaintPhoto (View view){
        Intent intent=new Intent (this,CloseComplaintPhoto.class);
        startActivity(intent);
    }

    public void OpenCloseComplaintVideoOption (View view){
    Intent intent=new Intent (this,CloseComplaintVideoOption.class);
    startActivity(intent);
}


}
