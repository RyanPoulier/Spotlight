package ryanpoulier.spotlight2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by shamal on 8/21/16.
 */
public class IssueCategory extends AppCompatActivity {
    ListView lst_Issue_Type;
    String issuetype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_icon);


        lst_Issue_Type = (ListView) findViewById(R.id.lstIssueCategories);
        String[] values = new String[]{"Drainage & Water", "Graffiti & Posters", "Natural Environment", "Parks", "Public Restrooms", "Road/Highway", "Waste Dumps & Litter"};
        final String[][] subcategories = new String[][]{{"Blocked drain", "Sunken drain cover", "Water leak"}, {"Illegal graffiti", "Posters"}, {"Dead tree/shrub", "Fallen tree/shrub", "Overgrown tree/shrub"}, {"Broken park equipment", "Damaged footpath/running path"}, {"Damaged fittings in restroom", "Uncleaned restroom"}, {"Broken sidewalk", "Damaged manhole cover", "Damaged road sign", "Debris (from vehicular accident)", "Faded road markings", "Misplaced road sign", "Pothole", "Proposal for new road sign", "Stagnant water", "Street light", "Traffic light"}, {"Household waste dump", "Industrial waste dump", "Litter/Rubbish", "Overflowing garbage bin", "Sewage dump"}};
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        final ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1
                , list);
        lst_Issue_Type.setAdapter(adapter);

        lst_Issue_Type.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(view.getContext(), New_complaint.class);
                        intent.putExtra("subcategories", subcategories[position]);
                        startActivity(intent);
                    }

                }
        );
    }

}
