package ryanpoulier.spotlight3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class New_Complaint_Description extends AppCompatActivity {

    EditText editDescription;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_new__complaint__description);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_icon);

            editDescription = (EditText) findViewById(R.id.txtDescription);
        }

        public void OpenNewComplaintPreview(View v) {
            storeDescription();
            Intent intent = new Intent(this, New_Complaint_Preview.class);
            startActivity(intent);
        }

    //code from https://www.youtube.com/watch?v=8byyh8Lb_xc&index=3&list=FLsCn-tnRZVHIyKOq7o6b36Q and https://www.youtube.com/watch?v=xv_JJbjDQ3M&list=FLsCn-tnRZVHIyKOq7o6b36Q&index=2

        public void storeDescription () {

            SharedPreferences prefs = getSharedPreferences("desc", MODE_WORLD_READABLE);

            SharedPreferences.Editor editor=prefs.edit();
            editor.putString("description", editDescription.getText().toString());
            editor.apply();

        }

}
