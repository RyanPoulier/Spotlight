package ryanpoulier.spotlight2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RequestUpdate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_update);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_icon);
    }

    public void StatusRequestConfirm (View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(RequestUpdate.this);
        builder.setTitle("Alert");
        builder.setMessage("Are you sure you want to send a status request to the government department?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                SendStatusRequest();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void SendStatusRequest (){
        AlertDialog.Builder builder = new AlertDialog.Builder(RequestUpdate.this);

        builder.setTitle("Alert");
        builder.setMessage("Status request was sent to the government department. You will be redirected to the complaint view page");
        builder.setCancelable(false);
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(RequestUpdate.this, ComplaintDetails.class);
                startActivity(intent);
                dialog.cancel();

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
