package ryanpoulier.spotlight2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.gson.GsonBuilder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class New_Complaint_Preview extends AppCompatActivity {
    TextView descriptionpreview, gpsCoordinatespreview, issuetypepreview, titlepreview,dateview;
    ImageView image1;
    VideoView video1;
    String title_issue_type, title_location;
    float longi, lati;
    String address;
    Context context= this;
    Button Submit_Complaint;
    DBhelper DBhelper;
    SQLiteDatabase sqLiteDatabase;
    Uri URI, vidURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //myDB = new DBhelper(this);
        setContentView(R.layout.activity_new__complaint__preview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_icon);

        descriptionpreview = (TextView) findViewById(R.id.txtDesc_Preview);
        gpsCoordinatespreview= (TextView) findViewById(R.id.txtCoordinates);
        issuetypepreview= (TextView) findViewById(R.id.txtIssueType);
        titlepreview= (TextView) findViewById(R.id.txtTitle);
        Submit_Complaint = (Button) findViewById(R.id.btn_Complaint_Submit);
        dateview= (TextView) findViewById(R.id.txtDate);
        image1= (ImageView) findViewById(R.id.imgView_NewPre_Image_1);
        video1 = (VideoView) findViewById(R.id.videoViewNewPreview1);
        //Integer idpreview = (TextView) findViewById(R.id.txtComplaintId);

        displayCurrentTime();
        displayDataDescription();
        displayDataLocation();
        displayDataIssueType();
        displayDataTitle();
        displayImages();
        displayVideo();
    }

    // code from https://www.youtube.com/watch?v=8byyh8Lb_xc&index=3&list=FLsCn-tnRZVHIyKOq7o6b36Q
    private void displayCurrentTime() {
        Date currentTime= new Date();
            String date_text= currentTime.toString();
            dateview.setText(date_text);
    }

    private void displayDataDescription() {
        SharedPreferences prefs = getSharedPreferences("desc", MODE_WORLD_READABLE);
        String desc= prefs.getString("description", "no data test");
        descriptionpreview.setText(desc);
    }

    private void displayDataLocation() {
        SharedPreferences prefs = getSharedPreferences("gpsCoordinates", MODE_WORLD_READABLE);
        String coordinates= prefs.getString("gpsCoordinates", "no data test");
        gpsCoordinatespreview.setText(coordinates);
        title_location= coordinates;
        longi = prefs.getFloat("longitude", 0);
        lati = prefs.getFloat("latitude", 0);
        address = prefs.getString("address","");
    }

    private void displayDataIssueType(){
        SharedPreferences prefs = getSharedPreferences("issuetype", MODE_WORLD_READABLE);
        String issuetype= prefs.getString("issuetype", "no data test");
        issuetypepreview.setText(issuetype);
        title_issue_type= issuetype;

    }
    private void displayDataTitle(){
        SharedPreferences prefs = getSharedPreferences("desc", MODE_WORLD_READABLE);
        String issuetype= prefs.getString("title", "");
        titlepreview.setText(issuetype);
    }

    private void displayImages() {
        SharedPreferences prefs = getSharedPreferences("photo", MODE_WORLD_READABLE);
        String photolocation= prefs.getString("photo", "no data test");
        URI = Uri.parse(photolocation);

        InputStream inputStream;
        try {
            inputStream=getContentResolver().openInputStream(URI);
            Bitmap image = BitmapFactory.decodeStream(inputStream);
            image1.setImageBitmap(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void displayVideo() {
        SharedPreferences prefs = getSharedPreferences("video", MODE_WORLD_READABLE);
        String videolocation= prefs.getString("video", "no data test");

        vidURI = Uri.parse(videolocation);
        video1.setVideoURI(vidURI);
        //String videooutput = vidURI.toString();
        //Toast.makeText(New_Complaint_Preview.this, videooutput, Toast.LENGTH_LONG).show();
        video1.start();
    }

    // based on https://www.youtube.com/watch?v=T0ClYrJukPA
    public void SaveComplaint(View view) {
        final SharedPreferences prefs = getSharedPreferences("gpsCoordinates", MODE_WORLD_READABLE);
        AlertDialog.Builder builder = new AlertDialog.Builder(New_Complaint_Preview.this);
        builder.setTitle("Alert");
        builder.setMessage("Are you sure you want to submit this complaint?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Integer Id = null;
                String complaintitle= titlepreview.getText().toString();
                String timestamp= dateview.getText().toString();
                String description= descriptionpreview.getText().toString();
                String issuetype = issuetypepreview.getText().toString();
                String location = gpsCoordinatespreview.getText().toString();
                String image1uri = URI.toString();
                String videouri = vidURI.toString();
                String status = "Notification sent to GoSL";

//                DBhelper= new DBhelper (context);
//                sqLiteDatabase= DBhelper.getWritableDatabase();
//                DBhelper.addData(Id, complaintitle, timestamp, issuetype, description, location, image1uri, videouri, status, sqLiteDatabase);
//                DBhelper.close();
                Map<String, String> comment = new HashMap<String, String>();
                comment.put("title", complaintitle);
                comment.put("description", description);
                comment.put("address", address);
                comment.put("longitude", longi + "");
                comment.put("latitude", lati + "");
                comment.put("issueType", title_issue_type);
                comment.put("userId", Login.userId);
                String json = new GsonBuilder().create().toJson(comment, Map.class);
//                makeRequest("http://ec2-52-66-125-116.ap-south-1.compute.amazonaws.com:8080/spotlight-api/issues", json);
//                AfterSubmissionMessage();
                new AddComplaintTask().execute("http://ec2-52-66-125-116.ap-south-1.compute.amazonaws.com:8080/spotlight-api/issues", json);
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    public class AddComplaintTask extends AsyncTask<String, String, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            // TODO: attempt authentication against a network service.

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

        @Override
        protected void onPostExecute(final Boolean success) {
            AfterSubmissionMessage();
        }

    }

    public void AfterSubmissionMessage (){
        AlertDialog.Builder builder = new AlertDialog.Builder(New_Complaint_Preview.this);

        builder.setTitle("Success!");
        builder.setMessage("New complaint was submitted successfully. You will also receive a confirmation via SMS shortly");
        builder.setCancelable(false);
        builder.setNeutralButton("OK",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(New_Complaint_Preview.this, Home.class);
                startActivity(intent);
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void execute() {
        Map<String, String> comment = new HashMap<String, String>();
        comment.put("subject", "Using the GSON library");
        comment.put("message", "Using libraries is convenient.");
        String json = new GsonBuilder().create().toJson(comment, Map.class);
        makeRequest("http://192.168.0.1:3000/post/77/comments", json);
    }

    public static HttpResponse makeRequest(String uri, String json) {
        try {
            HttpPost httpPost = new HttpPost(uri);
            httpPost.setEntity(new StringEntity(json));
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            return new DefaultHttpClient().execute(httpPost);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}



