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
import android.widget.TextView;
import android.widget.Toast;

public class MyComplaintsOld extends AppCompatActivity {

    ListView listview;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    MyComplaintDataAdapter listd;
    DBhelper DBhelper;
    String mytransferID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_complaints);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_icon);

        listview = (ListView) findViewById(R.id.listViewMyComplaints);
        listd = new MyComplaintDataAdapter(getApplicationContext(), R.layout.my_complaints_list_row);
        listview.setAdapter(listd);
        DBhelper = new DBhelper(getApplicationContext());
        sqLiteDatabase = DBhelper.getReadableDatabase();
        cursor = DBhelper.getSummaryData(sqLiteDatabase);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mytransferID = ((TextView) view.findViewById(R.id.txtMyID)).getText().toString();
                storeIDRef();
                Intent intent = new Intent(MyComplaintsOld.this, ComplaintDetails.class);
                startActivity(intent);
            }
        });

        if (cursor.moveToFirst()) {
            do {
                String id,title, timestamp;
                id= cursor.getString(0);
                title= cursor.getString(1);
                timestamp=cursor.getString(2);
//                DataProvider dataProvider = new DataProvider(title,timestamp, id);
//                listd.add(dataProvider);
            }
            while (cursor.moveToNext());
        }
    }

    public void storeIDRef () {

        SharedPreferences transferpref = getSharedPreferences("complaintid", MODE_WORLD_READABLE);

        SharedPreferences.Editor editor=transferpref.edit();
        editor.putString("complaintid", mytransferID.toString());
        editor.apply();

        Toast.makeText(this, "ID recorded", Toast.LENGTH_LONG).show();

    }
}

