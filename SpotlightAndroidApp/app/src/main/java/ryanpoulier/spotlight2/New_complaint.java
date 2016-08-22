package ryanpoulier.spotlight2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class New_complaint extends AppCompatActivity {
    ListView lst_Issue_Type;
    String issuetype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_complaint);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_icon);
        String[] subcategories = getIntent().getExtras().getStringArray("subcategories");

        lst_Issue_Type= (ListView)findViewById(R.id.lv_issue_type);

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < subcategories.length; ++i) {
            list.add(subcategories[i]);
        }
        final ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1
                , list);
        lst_Issue_Type.setAdapter(adapter);


        lst_Issue_Type.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        issuetype=String.valueOf(parent.getItemAtPosition(position));
                        storeIssueType();
                    }

                                    }
        );
    }

    public void OpenNewComplaintLocation (View view){
        Intent intent=new Intent (this,New_Complaint_Location.class);
        startActivity(intent);
    }

    public void storeIssueType () {

        SharedPreferences prefs = getSharedPreferences("issuetype", MODE_WORLD_READABLE);

        SharedPreferences.Editor editor=prefs.edit();
        editor.putString("issuetype", issuetype);
        editor.apply();

        //Toast.makeText (New_complaint.this, issuetype, Toast.LENGTH_LONG).show();

    }

    public void EmeOpenNewComplaintSuggest (View view){
        Intent intent=new Intent (this,New_Complaint_Suggest.class);
        startActivity(intent);
    }

}
