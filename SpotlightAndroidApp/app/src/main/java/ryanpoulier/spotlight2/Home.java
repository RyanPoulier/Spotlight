package ryanpoulier.spotlight2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends AppCompatActivity {
    ListView listview;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    ListDataAdapter lsd;
    DBhelper DBhelper;
    String id,title, timestamp,transferID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        listview = (ListView) findViewById(R.id.listViewHomeLatest);
        lsd = new ListDataAdapter(getApplicationContext(), R.layout.latest_list_row);
        listview.setAdapter(lsd);
        DBhelper = new DBhelper(getApplicationContext());
        sqLiteDatabase = DBhelper.getReadableDatabase();
        cursor = DBhelper.getSummaryData(sqLiteDatabase);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                transferID = ((TextView) view.findViewById(R.id.txtID)).getText().toString();
                storeIDRef();
                Intent intent = new Intent(Home.this, ComplaintDetails.class);
                startActivity(intent);
            }
        });


        if (cursor.moveToFirst()) {
            do {
                id= cursor.getString(0);
                title= cursor.getString(1);
                timestamp=cursor.getString(2);
                DataProvider dataProvider = new DataProvider(title,timestamp, id);
                lsd.add(dataProvider);
            }
            while (cursor.moveToNext());
        }
    }

    public void OpenNewComplaint (View view){
        Intent intent=new Intent (this,New_complaint.class);
        startActivity(intent);
    }

    public void OpenLatestComplaint (View view){
        Intent intent=new Intent (this,Latest_Complaints.class);
        startActivity(intent);
    }

    public void OpenNearbyIssues (View view){
        Intent intent=new Intent (this,Nearby_issues.class);
        startActivity (intent);
    }

    public void OpenPriorityComplaints (View view){
        Intent intent=new Intent (this,Chart.class);
        startActivity(intent);
    }

    public void OpenMyComplaints (View view){
        Intent intent=new Intent (this, MyComplaints.class);
        startActivity(intent);
    }

    public void OpenUserSettings(MenuItem item) {
        Intent intent=new Intent (this,MyProfile.class);
        startActivity(intent);
    }

    public void OpenMyComments (View view){
        Intent intent=new Intent (this,MyComments.class);
        startActivity(intent);
    }

    public void OpenAbout(MenuItem item) {
        Intent intent=new Intent (this,About.class);
        startActivity(intent);
    }

   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.user_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void OpenCitizenLeaderboard(MenuItem item) {
        Intent intent=new Intent (this,CitizenLeaderboard.class);
        startActivity(intent);
    }

    public void OpenSpotlightTest(MenuItem item) {
        Intent intent=new Intent (this,SpotlightTest.class);
        startActivity(intent);
    }

    public void storeIDRef () {

        SharedPreferences transferpref = getSharedPreferences("complaintid", MODE_WORLD_READABLE);

        SharedPreferences.Editor editor=transferpref.edit();
        editor.putString("complaintid", transferID.toString());
        editor.apply();

        //Toast.makeText(this, "ID recorded", Toast.LENGTH_LONG).show();

    }

    public void storeHomeID () {

        SharedPreferences commentidpref = getSharedPreferences("complaintid", MODE_WORLD_READABLE);
        SharedPreferences commenttitlepref = getSharedPreferences("complainttitle", MODE_WORLD_READABLE);

        SharedPreferences.Editor editorid=commentidpref.edit();
        editorid.putString("complaintid", transferID.toString());
        editorid.apply();
        Toast.makeText(this, "ID recorded", Toast.LENGTH_LONG).show();

        SharedPreferences.Editor editortitle=commenttitlepref.edit();
        editortitle.putString("complainttitle", title.toString());
        editortitle.apply();
        Toast.makeText(this, "Title recorded", Toast.LENGTH_LONG).show();


    }
}
