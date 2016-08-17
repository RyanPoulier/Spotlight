package ryanpoulier.spotlight2;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.fitness.request.DataReadRequest;

import java.util.List;

import static android.support.v4.app.ActivityCompat.startActivity;

public class Latest_Complaints extends AppCompatActivity {

    ListView listview;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor1, cursor2;
    ListDataAdapter lsd, emptylsd;
    ResultsDataAdapter resultslsd;
    DBhelper DBhelper;
    String title, timestamp,id,listsearchterm;
    AutoCompleteTextView listsearch;
    Spinner smain, srefined;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest__complaints);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_icon);

        listview = (ListView) findViewById(R.id.lsvLatest);
        lsd = new ListDataAdapter(getApplicationContext(), R.layout.latest_list_row);

        smain = (Spinner) findViewById(R.id.splistsearch);
        srefined = (Spinner) findViewById(R.id.splistsearchrefine);
        srefined.setVisibility(View.INVISIBLE);
        emptylsd = new ListDataAdapter(getApplicationContext(), R.layout.empty_list_row);
        resultslsd = new ResultsDataAdapter(getApplicationContext(), R.layout.results_list_row);
        DBhelper = new DBhelper(getApplicationContext());
        sqLiteDatabase = DBhelper.getReadableDatabase();

        listsearch = (AutoCompleteTextView) findViewById(R.id.txt_list_search);


        cursor1 = DBhelper.getSummaryData(sqLiteDatabase);

        if (cursor1.moveToFirst()) {
            do {
                id = cursor1.getString(0);
                title = cursor1.getString(1);
                timestamp = cursor1.getString(2);
                DataProvider dataProvider = new DataProvider(title, timestamp, id);
                lsd.add(dataProvider);
                //listview.setAdapter(emptylsd);
                listview.setAdapter(lsd);
            }
            while (cursor1.moveToNext());
        }

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    storeIDRef();
                    Intent intent = new Intent(Latest_Complaints.this, ComplaintDetails.class);
                    startActivity(intent);
                }
            });
    }


    public void OpenNewComment (View view){
        Intent intent=new Intent (this,Comment.class);
        startActivity(intent);
    }

    public void MapSwitch (View view){
        Intent intent=new Intent (this,Nearby_issues.class);
        startActivity(intent);
    }

    public void storeIDRef () {

        SharedPreferences transferpref = getSharedPreferences("complaintid", MODE_WORLD_READABLE);

        SharedPreferences.Editor editor=transferpref.edit();
        editor.putString("complaintid", id.toString());
        editor.apply();

        Toast.makeText(this, "ID recorded", Toast.LENGTH_LONG).show();

    }

    public void ListSearch (View view){
        // SHORTCUT
        srefined.setVisibility(View.VISIBLE);

        listsearchterm = listsearch.getText().toString();
        Toast.makeText(this, listsearchterm, Toast.LENGTH_LONG).show();

        cursor2 = DBhelper.search(sqLiteDatabase, listsearchterm);

        if (cursor2.moveToFirst()) {
            do {
                id= cursor2.getString(0);
                title= cursor2.getString(1);
                timestamp=cursor2.getString(2);
            }
            while (cursor2.moveToNext());
            ResultsDataProvider rDataProvider = new ResultsDataProvider(title,timestamp, id);
            resultslsd.add(rDataProvider);
            listview.setAdapter(emptylsd);
            listview.setAdapter(resultslsd);
            Toast.makeText(this, title, Toast.LENGTH_LONG).show();
        }
    }
}


