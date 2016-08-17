package ryanpoulier.spotlight3;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class About extends AppCompatActivity {

    Uri uriUrl = Uri.parse("https://www.facebook.com/Spotlight-App-1553348381649264/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_icon);


    }
    // code from http://code.tutsplus.com/tutorials/launching-the-browser-from-your-android-applications-the-easy-way--mobile-2414
    public void OpenFacebook(View view){
        Intent intent=new Intent (Intent.ACTION_VIEW, uriUrl);
        startActivity(intent);
    }

    public void OpenTwitter(View view){
        Intent intent=new Intent (this,CloseComplaintPreview.class);
        startActivity(intent);
    }
}
