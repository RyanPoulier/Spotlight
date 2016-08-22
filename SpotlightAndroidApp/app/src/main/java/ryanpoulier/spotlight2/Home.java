package ryanpoulier.spotlight2;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    ListView listview;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    ListDataAdapter lsd;
    DBhelper DBhelper;
    String id,title, timestamp,transferID;

    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


//        listview = (ListView) findViewById(R.id.listViewHomeLatest);
//        lsd = new ListDataAdapter(getApplicationContext(), R.layout.latest_list_row);
//        listview.setAdapter(lsd);
//        DBhelper = new DBhelper(getApplicationContext());
//        sqLiteDatabase = DBhelper.getReadableDatabase();
//        cursor = DBhelper.getSummaryData(sqLiteDatabase);
//
//        lsd.add(new DataProvider("title A", "12:00", "1"));
//        lsd.add(new DataProvider("title B", "17:00", "2"));
//        lsd.add(new DataProvider("title C", "17:00", "2"));
//        lsd.add(new DataProvider("title D", "17:00", "2"));
//        lsd.add(new DataProvider("title E", "17:00", "2"));

//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                transferID = ((TextView) view.findViewById(R.id.txtID)).getText().toString();
//                storeIDRef();
//                Intent intent = new Intent(Home.this, ComplaintDetails.class);
//                startActivity(intent);
//            }
//        });

//        Button btnNewComplaint = (Button) findViewById(R.id.btn_new_complaint);
//        btnNewComplaint.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                OpenNewComplaint(view);
//            }
//        });
//
//
//        if (cursor.moveToFirst()) {
//            do {
//                id = cursor.getString(0);
//                title = cursor.getString(1);
//                timestamp = cursor.getString(2);
//                DataProvider dataProvider = new DataProvider(title, timestamp, id);
//                lsd.add(dataProvider);
//            }
//            while (cursor.moveToNext());
//        }
//        //Slider
        mNavItems.add(new NavItem("Home", "", R.mipmap.latest_complaints));
        mNavItems.add(new NavItem("Nearby Complaints", "", R.mipmap.nearby_complaints));
        mNavItems.add(new NavItem("My Complaints", "", R.mipmap.my_complaints));
        mNavItems.add(new NavItem("My Votes/Comments", "", R.mipmap.my_comments_and_votes));
        mNavItems.add(new NavItem("Priority Chart", "", R.mipmap.spotlit_issues));


        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        // Populate the Navigtion Drawer with options
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter);

        // Drawer Item click listeners
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFromDrawer(position);
                //OpenNearbyIssues(view);
            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Log.d("aa", "onDrawerClosed: " + getTitle());

                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_reorder_black_24dp);
        mDrawerToggle.syncState();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        Feed ls_fragment = new Feed();
        fragmentTransaction.replace(R.id.fragment_container, ls_fragment);
        fragmentTransaction.commit();
    }

    private void selectItemFromDrawer(int position) {
        FragmentManager fragmentManager = getFragmentManager();
        switch (position){
            case 0:
                Feed feed = new Feed();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, feed).commit();
                break;
            case 1:
                NearbyComplaints nearbyComplaints = new NearbyComplaints();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, nearbyComplaints)
                        .commit();
                break;
            case 2:
                MyComplaints myComplaints = new MyComplaints();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, myComplaints)
                        .commit();
                break;
        }
        Log.d("Position", position+"");


        mDrawerList.setItemChecked(position, true);
        setTitle(mNavItems.get(position).mTitle);

        // Close the drawer
        mDrawerLayout.closeDrawer(mDrawerPane);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle
        // If it returns true, then it has handled
        // the nav drawer indicator touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    public void OpenNewComplaint (View view){
//        Intent intent=new Intent (this,New_complaint.class);
//        startActivity(intent);
        Intent intent = new Intent(this, IssueCategory.class);
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
        Intent intent=new Intent (this, MyComplaintsOld.class);
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

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.user_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

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
