package ryanpoulier.spotlight2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DeleteComplaint extends AppCompatActivity {
    Button DELETECOMPLAINT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_complaint);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_icon);

        DELETECOMPLAINT = (Button) findViewById(R.id.btn_my_complaint_delete);
    }

    public void DeleteMyComplaint(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(DeleteComplaint.this);
        builder.setTitle("Alert");
        builder.setMessage("Are you sure you want to delete this complaint?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // delete statement
                AfterComplaintDeleteMessage();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void AfterComplaintDeleteMessage () {
        AlertDialog.Builder builder = new AlertDialog.Builder(DeleteComplaint.this);

        builder.setTitle("Success!");
        builder.setMessage("Complaint was deleted successfully. You will also receive a confirmation via SMS shortly");
        builder.setCancelable(false);
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(DeleteComplaint.this, Home.class);
                startActivity(intent);
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
