package ryanpoulier.spotlight2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Resubmit_Option extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resubmit__option);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_icon);
    }

    public void OpenResubmit(View view){
        Intent intent=new Intent (this,new_resubmission.class);
        startActivity(intent);
    }

    public void ReturntoCloseComplaint(View view){
        Intent intent=new Intent (this,CloseComplaintPreview.class);
        startActivity(intent);
    }
}
