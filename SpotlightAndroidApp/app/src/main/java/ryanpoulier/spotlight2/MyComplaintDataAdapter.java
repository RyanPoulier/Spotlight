package ryanpoulier.spotlight2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.GsonBuilder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ASUS on 24/02/2016.
 */
public class MyComplaintDataAdapter extends ArrayAdapter {
    List mylist = new ArrayList();
    int rating = 0;
    String issueId;


    public MyComplaintDataAdapter(Context cnt, int resource) {
        super(cnt, resource);
    }

    static class LayoutHandler {
        TextView TITLE, TIMESTAMP, ID;
        ImageButton BTNCLOSE,BTNDELETE, BTNSHARE;
    }

    @Override
    public void add(Object object) {
        super.add(object);
        mylist.add(object);
    }

    @Override
    public int getCount() {
        return mylist.size();
    }

    @Override
    public Object getItem(int position) {
        return mylist.get(position);
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        View row = convertView;
        LayoutHandler lytHandler;
        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.my_complaints_list_row, parent, false);
            lytHandler = new LayoutHandler();
            // tut 32 got it wrong
            lytHandler.TITLE = (TextView) row.findViewById(R.id.txt_my_title);
            lytHandler.TIMESTAMP = (TextView) row.findViewById(R.id.t_timestamp);
            lytHandler.ID = (TextView) row.findViewById (R.id.txtMyID);
            lytHandler.BTNCLOSE= (ImageButton) row.findViewById(R.id.btn_close);
            lytHandler.BTNCLOSE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(parent);
                }
            });
            lytHandler.BTNDELETE = (ImageButton) row.findViewById(R.id.btn_my_complaint_delete);
            lytHandler.BTNDELETE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // based on content from http://stackoverflow.com/questions/4197135/how-to-start-activity-in-adapter
                    Intent intnew = new Intent(getContext(), DeleteComplaint.class);
                    intnew.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    v.getContext().startActivity(intnew);
                }
            });

            row.setTag(lytHandler);
        } else {
            lytHandler = (LayoutHandler) row.getTag();

        }

        DataProvider dataProvider = (DataProvider) this.getItem(position);
        lytHandler.TITLE.setText(dataProvider.getTitle());
        lytHandler.TIMESTAMP.setText(dataProvider.getTimestamp());
        lytHandler.ID.setText(dataProvider.getID()+"");
        issueId = dataProvider.getID()+"";

        return row;

    }

    private void showDialog(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Rate this issue (1-5)");

// Set up the input
        final EditText input1 = new EditText(v.getContext());
        //input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input1.setHint("3");
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input1.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input1);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                rating = Integer.parseInt(input1.getText().toString());
                if(0 < rating && rating < 6){
                    Map<String, String> comment = new HashMap<String, String>();
                    comment.put("userId", Login.userId);
                    comment.put("issueId", issueId);
                    comment.put("closureRating", rating+"");
                    String json = new GsonBuilder().create().toJson(comment, Map.class);

                    new CloseComplaint().execute("http://ec2-52-66-125-116.ap-south-1.compute.amazonaws.com:8080/spotlight-api/issues/closure", json);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private class CloseComplaint extends AsyncTask<String, String, Boolean> {
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
