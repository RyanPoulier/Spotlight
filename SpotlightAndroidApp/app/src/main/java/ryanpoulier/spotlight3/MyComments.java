package ryanpoulier.spotlight3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TabHost;

public class MyComments extends AppCompatActivity {

    ListView voteslist;
    String [] votestitles= {"POTHOLE @ 6.876667,79.9013236","SEWAGE DUMP @ 6.8644638,79.8824543"};
    String [] votesstatuses= {"Notification sent to GoSL","Notification sent to GoSL"};
    String [] votesdates = {"21/03/2016","18/03/2016"};

    ListView commentslist;
    String [] commenttitles= {"POTHOLE @ 6.876667,79.9013236","SEWAGE DUMP @ 6.8644638,79.8824543", "POTHOLE @ 6.876657,79.9013246"};
    String [] commentstatuses= {"Notification sent to GoSL","Notification sent to GoSL","Notification sent to GoSL"};
    String [] commentdates = {"21/03/2016","18/03/2016", "21/03/2016"};
    String [] comments = {"Valid complaint. Quick action needed!","I saw this too","Fix fast. Major hassle..."};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_comments);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_icon);

        // code from https://www.youtube.com/watch?v=jWcnDx5_-mc
        TabHost tabs= (TabHost) findViewById(R.id.tabHost);
        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("Tag 1");
        spec.setContent(R.id.lnrVotes);
        spec.setIndicator("My votes");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("Tag 2");
        spec.setContent(R.id.lnrComments);
        spec.setIndicator("My comments");
        tabs.addTab(spec);

        voteslist = (ListView)findViewById(R.id.listViewMyVotes);
        //Adapter object
        VotesDataAdapter vadap= new VotesDataAdapter (this,votestitles,votesstatuses,votesdates);
        voteslist.setAdapter(vadap);

        commentslist = (ListView)findViewById(R.id.listViewMyComments);
        //Adapter object
        MyCommentsDataAdapter mcadap= new MyCommentsDataAdapter (this,commenttitles,commentstatuses,commentdates,comments);
        commentslist.setAdapter(mcadap);
    }
}

