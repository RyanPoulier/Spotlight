package ryanpoulier.spotlight2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by ASUS on 05/03/2016.
 */
public class CloseComplaintVideoOption extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close__complaint__video__option);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_icon);
    }

    public void OpenCloseComplaintVideo (View view){
        Intent intent=new Intent (this,CloseComplaintVideo.class);
        startActivity(intent);
    }

    public void OpenCloseComplaintDescription (View view){
        Intent intent=new Intent (this,CloseComplaintDescription.class);
        startActivity(intent);
    }
}
