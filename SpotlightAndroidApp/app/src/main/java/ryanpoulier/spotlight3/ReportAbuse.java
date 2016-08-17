package ryanpoulier.spotlight3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ReportAbuse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_abuse);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_icon);
    }

    public void SubmitAbuseRequest (View view){
        Toast.makeText(ReportAbuse.this, "Administrator has been notified of complaint", Toast.LENGTH_LONG).show();
        Intent intent=new Intent (this,ComplaintDetails.class);
        startActivity(intent);
    }
}
