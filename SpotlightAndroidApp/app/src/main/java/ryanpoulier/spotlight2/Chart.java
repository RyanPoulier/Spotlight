package ryanpoulier.spotlight2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class Chart extends AppCompatActivity {
    ListView listview;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    PrioritisedDataAdapter pda;
    DBhelper DBhelper;
    String title, timestamp,id, number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_icon);

        listview = (ListView) findViewById(R.id.listViewChart);
        pda = new PrioritisedDataAdapter(getApplicationContext(), R.layout.prioritisedlistrow);
        listview.setAdapter(pda);
        DBhelper = new DBhelper(getApplicationContext());
        sqLiteDatabase = DBhelper.getReadableDatabase();
        cursor = DBhelper.getSummaryData(sqLiteDatabase);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                storeIDRef();
                Intent intent=new Intent (Chart.this,ComplaintDetails.class);
                startActivity(intent);
            }
        });

        if (cursor.moveToFirst()) {
            do {

                id= cursor.getString(0);
                title= cursor.getString(1);
                timestamp=cursor.getString(2);
//                DataProvider dataProvider = new DataProvider(title,timestamp, id);
//                pda.add(dataProvider);
            }
            while (cursor.moveToNext());
        }
    }

    public void OpenNewComment (View view){
        Intent intent=new Intent (this,Comment.class);
        startActivity(intent);
    }

    public void storeIDRef () {

        SharedPreferences transferpref = getSharedPreferences("complaintid", MODE_WORLD_READABLE);

        SharedPreferences.Editor editor=transferpref.edit();
        editor.putString("complaintid", id.toString());
        editor.apply();

        Toast.makeText(this, "ID recorded", Toast.LENGTH_LONG).show();

    }
}
