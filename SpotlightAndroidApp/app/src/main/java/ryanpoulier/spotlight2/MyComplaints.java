package ryanpoulier.spotlight2;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by shamal on 8/5/16.
 */
public class MyComplaints extends Fragment {
    ListView listview;
    MyComplaintDataAdapter lsd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mycomplaints, container, false);
        listview = (ListView) view.findViewById(R.id.listViewMyComplaints);
        lsd = new MyComplaintDataAdapter(getActivity().getApplicationContext(), R.layout.my_complaints_list_row);
        listview.setAdapter(lsd);

        new MyComplaintsFeedTask().execute();
        return view;
    }

    private class MyComplaintsFeedTask extends AsyncTask<Void, Void, String> {
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
        protected String doInBackground(Void... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpGet httpGet = new HttpGet("http://ec2-52-66-125-116.ap-south-1.compute.amazonaws.com:8080/spotlight-api/issues/complained/" + Login.userId);
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

                            lsd.add(new DataProvider(jsonObject.getString("title"), datetime, id.getString("$oid"), jsonObject.getString("issueType")));
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
}

