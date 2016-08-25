package ryanpoulier.spotlight2;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AbsListView;
import android.widget.AdapterView;
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
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by shamal on 8/4/16.
 */
public class Feed extends Fragment {
    ListView listview;
    ListDataAdapter lsd;
    Button btnNewComplaint;
    boolean isButtonHidden = false;
    static int initialHeight;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feed_fragment, container, false);
        btnNewComplaint = (Button) view.findViewById(R.id.btn_new_complaint);
        listview = (ListView) view.findViewById(R.id.listFeed);

        lsd = new ListDataAdapter(getActivity().getApplicationContext(), R.layout.latest_list_row);
        listview.setAdapter(lsd);

//        lsd.add(new DataProvider("title A", "12:00", "1"));
//        lsd.add(new DataProvider("title B", "17:00", "2"));
//        lsd.add(new DataProvider("title C", "17:00", "2"));
//        lsd.add(new DataProvider("title D", "17:00", "2"));
//        lsd.add(new DataProvider("title E", "17:00", "2"));
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), ComplaintDetails.class);
                startActivity(intent);
            }
        });

        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int mLastFirstVisibleItem;
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                if(mLastFirstVisibleItem<i && !isButtonHidden)
                {
                    collapse(btnNewComplaint);
                    isButtonHidden = true;
                }
                if(mLastFirstVisibleItem>i && isButtonHidden)
                {
                    expand(btnNewComplaint);
                    isButtonHidden = false;
                }
                mLastFirstVisibleItem=i;
            }
        });

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
//        Intent intent=new Intent (view.getContext(), New_complaint.class);
        Intent intent = new Intent(view.getContext(), IssueCategory.class);
        startActivity(intent);
    }

    public static void expand(final View v) {
        //v.measure(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        final int targetHeight = initialHeight;
//        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        //v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
               v.getLayoutParams().height = targetHeight;
//                v.getLayoutParams().height = interpolatedTime == 1
//                        ? v.setVisibility(View.GONE)
//                        : targetHeight;
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
//        final int initialHeight = v.getMeasuredHeight();
        initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
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
