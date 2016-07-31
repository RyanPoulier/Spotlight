package ryanpoulier.spotlight2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class New_Complaint_Photo_Option extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__complaint__photo__option);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_icon);
    }

    public void OpenNewComplaintPhoto(View view) {
        Intent intent = new Intent(this, New_Complaint_Photo.class);
        startActivity(intent);
    }

    public void OpenNewComplaintVideoOption(View view) {
        Intent intent = new Intent(this, SameComplaintVideoOption.class);
        startActivity(intent);
    }
}
