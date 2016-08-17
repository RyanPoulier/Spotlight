package ryanpoulier.spotlight3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by ASUS on 07/03/2016.
 */
public class SameComplaintDescription extends AppCompatActivity {
    EditText editDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_same_complaint_description);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_icon);

        editDescription = (EditText) findViewById(R.id.txtSameDescription);
    }

    public void  OpenSameComplaintPreview (View view){
        storeDescription();
        Intent intent=new Intent (this,SameComplaintPreview.class);
        startActivity(intent);
    }

    public void storeDescription () {

        SharedPreferences samepref = getSharedPreferences("desc", MODE_WORLD_READABLE);

        SharedPreferences.Editor editor=samepref.edit();
        editor.putString("description", editDescription.getText().toString());
        editor.apply();

        //Toast.makeText(this, "Description Saved", Toast.LENGTH_LONG).show();

    }
}
