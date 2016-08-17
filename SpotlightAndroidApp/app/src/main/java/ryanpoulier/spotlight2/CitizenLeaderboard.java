package ryanpoulier.spotlight2;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class CitizenLeaderboard extends AppCompatActivity {
    ListView listView;
    String [] usernames= {"Thilini Fernando","Ashan Perera","Vidura De Silva","Praveen Gunasekera","Rashmi Peiris","Parami Jayasumana"};
    String [] reputation= {"2300 points","1800 points","1800 points","1600 points","1500 points","1300 points"};
    int[] images = {R.mipmap.suggestpic1,R.mipmap.suggestpic2,R.mipmap.suggestpic3,R.mipmap.suggestpic4,R.mipmap.suggestpic5,R.mipmap.suggestpic6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen_leaderboard);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_icon);

        listView= (ListView)findViewById(R.id.lsv_leaderboard);

        //Adapter object
        UserSuggestionsDataAdapter adap= new UserSuggestionsDataAdapter (this,usernames,reputation,images);
        listView.setAdapter(adap);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {

                Toast.makeText(getApplicationContext(), usernames[pos], Toast.LENGTH_LONG).show();
            }
        });
    }
}




