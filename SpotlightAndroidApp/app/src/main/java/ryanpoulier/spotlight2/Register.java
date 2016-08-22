package ryanpoulier.spotlight2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.GsonBuilder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shamal on 8/19/16.
 */
public class Register extends AppCompatActivity {
    Button btnSignUp;
    TextView txtFName;
    TextView txtLName;
    TextView txtEmail;
    TextView txtContactNo;
    TextView txtPassword;
    TextView txtRPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnSignUp = (Button) findViewById(R.id.btnSignup);
        txtFName = (TextView) findViewById(R.id.txtFName);
        txtLName = (TextView) findViewById(R.id.txtLName);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtContactNo = (TextView) findViewById(R.id.txtContactNo);
        txtPassword = (TextView) findViewById(R.id.txtPassword);
        txtRPassword = (TextView) findViewById(R.id.txtRPassword);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUp();
            }
        });
    }

    private void SignUp() {
        String password = txtPassword.getText().toString();
        String rpassword = txtRPassword.getText().toString();

        if (password.equals(rpassword)) {
            Map<String, String> comment = new HashMap<String, String>();
            comment.put("firstName", txtFName.getText().toString());
            comment.put("lastName", txtLName.getText().toString());
            comment.put("email", txtEmail.getText().toString());
            comment.put("contactNo", txtContactNo.getText().toString());
            comment.put("city", "Colombo");
            comment.put("password", password);
            String json = new GsonBuilder().create().toJson(comment, Map.class);
            new Signup().execute("http://ec2-52-66-125-116.ap-south-1.compute.amazonaws.com:8080/spotlight-api/users", json);
            //makeRequest("http://ec2-52-66-125-116.ap-south-1.compute.amazonaws.com:8080/spotlight-api/users", json);
            onBackPressed();
        }

    }

    private class Signup extends AsyncTask<String, String, HttpResponse> {

        @Override
        protected HttpResponse doInBackground(String... params) {
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
                return response;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
