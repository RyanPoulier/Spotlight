package ryanpoulier.spotlight2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

        SharedPreferences prefs = getSharedPreferences("issuetype", MODE_WORLD_READABLE);
        String issuetype= prefs.getString("issuetype", "no data test");
        new SuggestionList().execute(issuetype);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SubmissionMessagesSuggest();
                transferID = ((TextView) view.findViewById(R.id.txtID)).getText().toString();
                storeIDRefSuggest();
            }
        });
    }

    public void OpenNewComplaintPhotoOption(final View view) {
        Intent intent = new Intent(this, New_Complaint_Description.class);
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
                Map<String, String> comment = new HashMap<String, String>();
                comment.put("issueId", transferID);
                comment.put("userId", Login.userId);
                String json = new GsonBuilder().create().toJson(comment, Map.class);
                new AddComplaint().execute("http://ec2-52-66-125-116.ap-south-1.compute.amazonaws.com:8080/spotlight-api/complaints", json);
                Intent intent = new Intent(New_Complaint_Suggest.this, Home.class);
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

    private class SuggestionList extends AsyncTask<String, String, String> {
        protected String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
            InputStream in = entity.getContent();


            StringBuffer out = new StringBuffer();
            int n = 1;
            while (n > 0) {
                byte[] b = new byte[4096];
                n = in.read(b);


                if (n > 0) out.append(new String(b, 0, n));
            }


            return out.toString();
        }


        @Override
        protected String doInBackground(String... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpGet httpGet = null;
            httpGet = new HttpGet("http://ec2-52-66-125-116.ap-south-1.compute.amazonaws.com:8080/spotlight-api/issues/type/"+ params[0].replace(" ", "%20"));
            String text = null;
            try {
                HttpResponse response = httpClient.execute(httpGet, localContext);


                HttpEntity entity = response.getEntity();


                text = getASCIIContentFromEntity(entity);


            } catch (Exception e) {
                return e.getLocalizedMessage();
            }


            return text;
        }

        protected void onPostExecute(String results) {
            if (results!=null) {
                try {
                    JSONArray issues = new JSONArray(results);
                    for(int i = 0, count = issues.length(); i< count; i++)
                    {
                        try {
                            JSONObject jsonObject = issues.getJSONObject(i);
                            Date date = new Date(jsonObject.getLong("createdTime")*1000);
                            DateFormat dateFormat = DateFormat.getDateInstance();
                            JSONObject id = jsonObject.getJSONObject("_id");
                            String datetime = dateFormat.format(date);

                            csda.add(new SuggestComplaintProvider(jsonObject.getString("title"), datetime, id.getString("$oid"), jsonObject.getString("issueType")));
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private class AddComplaint extends AsyncTask<String, String, Boolean> {
        protected String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
            InputStream in = entity.getContent();
            StringBuffer out = new StringBuffer();
            int n = 1;
            while (n > 0) {
                byte[] b = new byte[4096];
                n = in.read(b);


                if (n > 0) out.append(new String(b, 0, n));
            }


            return out.toString();
        }


        @Override
        protected Boolean doInBackground(String... params) {
            HttpPost httpPost = new HttpPost(params[0]);
            try {
                httpPost.setEntity(new StringEntity(params[1]));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            try {
                HttpResponse response = new DefaultHttpClient().execute(httpPost);
                if(response.getStatusLine().getStatusCode() != 202){
                    return false;
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }

            // TODO: register the new account here.
            return false;
        }

        protected void onPostExecute(final boolean results) {
            if (results) {
//                Intent intent = new Intent(New_Complaint_Preview.this, Home.class);
//                startActivity(intent);

            }
        }
    }
}







