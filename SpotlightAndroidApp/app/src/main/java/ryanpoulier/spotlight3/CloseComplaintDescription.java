package ryanpoulier.spotlight3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

public class CloseComplaintDescription extends AppCompatActivity {

    EditText finalcomments;
    RatingBar resrating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close_complaint_description);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_icon);

        finalcomments = (EditText) findViewById(R.id.txtFinalComments);
        resrating = (RatingBar) findViewById(R.id.ratingBarResolutionRating);
    }

    public void OpenResubmitOption(View view){
        storeDescription ();
        Intent intent=new Intent (this,Resubmit_Option.class);
        startActivity(intent);
    }

    public void storeDescription () {

        SharedPreferences closepref = getSharedPreferences("finalcomments", MODE_WORLD_READABLE);
        SharedPreferences.Editor editor=closepref.edit();
        editor.putString("finalcomments", finalcomments.getText().toString());
        editor.apply();

        float rating = resrating.getRating();
        SharedPreferences closeprefrating = getSharedPreferences("rating", MODE_WORLD_READABLE);
        SharedPreferences.Editor edit=closeprefrating.edit();
        edit.putString("rating", String.valueOf(rating));
        edit.apply();

        //Toast.makeText(this, String.valueOf(rating), Toast.LENGTH_LONG).show();

    }
}
