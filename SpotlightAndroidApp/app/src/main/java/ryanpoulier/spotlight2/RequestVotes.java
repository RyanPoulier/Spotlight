package ryanpoulier.spotlight2;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class RequestVotes extends AppCompatActivity {
ListView listView;
String [] usernames= {"Thilini Fernando","Ashan Perera","Vidura De Silva","Praveen Gunasekera","Rashmi Peiris","Parami Jayasumana"};
    String [] reputation= {"2300 points","1800 points","1800 points","1600 points","1500 points","1300 points"};
    int[] images = {R.mipmap.suggestpic1,R.mipmap.suggestpic2,R.mipmap.suggestpic3,R.mipmap.suggestpic4,R.mipmap.suggestpic5,R.mipmap.suggestpic6};

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_votes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_icon);

        listView= (ListView)findViewById(R.id.listViewSuggestedUsers);

        //Adapter object
        UserSuggestionsDataAdapter adap= new UserSuggestionsDataAdapter (this,usernames, reputation,images);
        listView.setAdapter(adap);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
                parent.getChildAt(pos).setBackgroundColor(Color.LTGRAY);
                Toast.makeText(getApplicationContext(),usernames[pos], Toast.LENGTH_LONG).show();
            }
        });
    }


    public void SubmitVoteRequest (View view){
        Toast.makeText(RequestVotes.this, "Vote request submitted to user", Toast.LENGTH_LONG).show();
        Intent intent=new Intent (this,ComplaintDetails.class);
        startActivity(intent);
    }
}
