package ryanpoulier.spotlight3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class new_resubmission extends AppCompatActivity {

    ListView listView;
    String [] officialsname= {"xxxxxxx xxxxxxxxxxxxx","xxxxxxxxx xxxxxxxxxx","xxxxxxx xxxxxxxxxxxxx","xxxxxx xxxxxxxxx","xxxxxxxx xxxxx"};
    String [] officialsposition= {"Councillor","Councillor","Councillor","Councillor","Councillor"};
    //String [] officialsdepartment= {"Engineering - Water supply and drainage","Legal","Administrative","Planning and development","Engineering - Lands and environmental development","Engineering - Traffic design and road safety"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_resubmission);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_icon);

        listView= (ListView)findViewById(R.id.listViewOfficials);

        //Adapter object
        OfficialsDataAdapter adap= new OfficialsDataAdapter (this,officialsname,officialsposition);
        listView.setAdapter(adap);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {

                //Toast.makeText(getApplicationContext(), officialsname[pos], Toast.LENGTH_LONG).show();
            }
        });

    }

    public void OpenResubmitComplaintPreview(View view){
        Intent intent=new Intent (this,ResubmitComplaintPreview.class);
        startActivity(intent);
    }



}
