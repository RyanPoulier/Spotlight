package ryanpoulier.spotlight3;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

/**
 * Created by shamal on 8/4/16.
 */
public class Feed extends Fragment {
    ListView listview;
    ListDataAdapter lsd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feed_fragment, container, false);
        listview = (ListView) view.findViewById(R.id.listFeed);
        lsd = new ListDataAdapter(getActivity().getApplicationContext(), R.layout.latest_list_row);
        listview.setAdapter(lsd);

//        lsd.add(new DataProvider("title A", "12:00", "1"));
//        lsd.add(new DataProvider("title B", "17:00", "2"));
//        lsd.add(new DataProvider("title C", "17:00", "2"));
//        lsd.add(new DataProvider("title D", "17:00", "2"));
//        lsd.add(new DataProvider("title E", "17:00", "2"));

        Button btnNewComplaint = (Button) view.findViewById(R.id.btn_new_complaint);
        btnNewComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenNewComplaint(view);
            }
        });
        // Inflate the layout for this fragment
        new FeedList().execute();

        return view;
    }

    public void OpenNewComplaint (View view){
        Intent intent=new Intent (view.getContext(), New_complaint.class);
        startActivity(intent);
    }

    private class FeedList extends AsyncTask<Void, Void, String> {
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
            HttpGet httpGet = new HttpGet("http://ec2-52-66-125-116.ap-south-1.compute.amazonaws.com:8080/spotlight-api/issues");
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
                            lsd.add(new DataProvider(jsonObject.getString("title"), "17:00", ""));
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
