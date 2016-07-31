package ryanpoulier.spotlight2;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class New_Complaint_Suggest extends AppCompatActivity {
    ListView listview;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    ComplaintSuggestDataAdapter csda;
    DBhelper DBhelper;
    String transferID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__complaint__suggest);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_icon);

        listview = (ListView) findViewById(R.id.listViewSuggestion);
        csda = new ComplaintSuggestDataAdapter(getApplicationContext(), R.layout.complaint_suggest_list_row);
        listview.setAdapter(csda);
        DBhelper = new DBhelper(getApplicationContext());
        sqLiteDatabase = DBhelper.getReadableDatabase();
        cursor = DBhelper.getSummaryData(sqLiteDatabase);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SubmissionMessagesSuggest();
                transferID = ((TextView) view.findViewById(R.id.txtID)).getText().toString();
                storeIDRefSuggest();

            }
        });

        if (cursor.moveToFirst()) {
            do {
                String id, title, timestamp;
                id= cursor.getString(0);
                title= cursor.getString(1);
                timestamp=cursor.getString(2);
                SuggestComplaintProvider suggestDataProvider = new SuggestComplaintProvider(title,timestamp, id);
                csda.add(suggestDataProvider);
            }
            while (cursor.moveToNext());
        }
    }

    public void OpenNewComplaintPhotoOption(final View view) {

        Intent intent = new Intent(this, New_Complaint_Photo_Option.class);
        startActivity(intent);
    }

    public void storeIDRefSuggest () {

        SharedPreferences transferpref = getSharedPreferences("complaintidsuggest", MODE_WORLD_READABLE);

        SharedPreferences.Editor editor=transferpref.edit();
        editor.putString("complaintidsuggest", transferID.toString());
        editor.apply();

        Toast.makeText(this, "ID recorded", Toast.LENGTH_LONG).show();

    }

    public void SubmissionMessagesSuggest () {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(New_Complaint_Suggest.this);
        builder.setTitle("Alert");
        builder.setMessage("Complaint details entered/selected on the previous pages will be deleted and you will be redirected to the page where you can merge your complaint with an existing complaint. Are you sure you wish to do this?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(New_Complaint_Suggest.this, ComplaintDetails.class);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        android.support.v7.app.AlertDialog alert = builder.create();
        alert.show();

    }
}







