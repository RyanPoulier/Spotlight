package ryanpoulier.spotlight3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class New_Complaint_Video_Option extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__complaint__video__option);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_icon);
    }
    public void OpenNewComplaintVideo(View view) {
        Intent intent = new Intent(this, New_Complaint_Video.class);
        startActivity(intent);
    }

    public void OpenNewComplaintDescription(View view) {
        Intent intent = new Intent(this, New_Complaint_Description.class);
        startActivity(intent);
    }


}
