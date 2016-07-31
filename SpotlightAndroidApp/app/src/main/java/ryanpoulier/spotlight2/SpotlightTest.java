package ryanpoulier.spotlight2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SpotlightTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spotlight_test);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_icon);
    }

    // code from https://www.youtube.com/watch?v=_1U2hDOcdAI
    public void closeNotification (View v){
        NotificationCompat.Builder builder=new  NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.spotlightlogo);
        builder.setTicker("Complaint closed by government");
        builder.setContentTitle("Complaint closed by government");
        builder.setAutoCancel(true);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText("Complaint C0001 has been marked as closed. Provide your feedback."));
        Intent closeintent = new Intent(this, CloseComplaintDetails.class);
        closeintent.setAction(Intent.ACTION_MAIN);
        closeintent.addCategory(Intent.CATEGORY_LAUNCHER);
        PendingIntent pcloseintent = PendingIntent.getActivity(this,0,closeintent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.addAction(R.drawable.spotlightlogo, "Close now", pcloseintent);
        builder.addAction(R.drawable.spotlightlogo, "Remind me later", pcloseintent);
        builder.setContentIntent(pcloseintent);
        NotificationManager notifmgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notifmgr.notify(0,builder.build());


    }


}
