//package com.example.dburtnja.androidticketfinder;
//
//import android.app.AlarmManager;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.Intent;
//import android.media.Ringtone;
//import android.media.RingtoneManager;
//import android.net.Uri;
//import android.os.Vibrator;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import com.example.dburtnja.androidticketfinder.TicketInfo.Ticket;
//import com.google.gson.Gson;
//
//public class Main3Activity extends AppCompatActivity {
//    private Ticket      ticket;
//    private Ringtone    ringtone;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main3);
//
//        Gson            gson;
//        EditText        cookieHolder;
//        TextView        status;
//        TextView        header;
//        Button          openBrowser;
//
//        status = (TextView) findViewById(R.id.status);
//        header = (TextView) findViewById(R.id.header);
//        openBrowser = (Button) findViewById(R.id.openBrowser);
//        cookieHolder = (EditText) findViewById(R.id.cookieHolder);
//        gson = new Gson();
//        ticket = gson.fromJson(getIntent().getStringExtra("ticket"), Ticket.class);
//        Log.d("coockie:44", ticket.getCookie());
//        cookieHolder.setText(ticket.getCookie());
//        if (ticket.haveTicket) {
//            header.setText(R.string.success);
//            status.setText("Натисніть кнопку для вибору та купівлі!");
//        } else {
//            header.setText(R.string.error);
//            status.setText(ticket.status);
//        }
//        playMusic();
//        openBrowser.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent  intent;
//
//                stopMusic();
//                intent = new Intent(Main3Activity.this, Main2Activity.class);
//                Log.d("coockie61", ticket.getCookie());
//                intent.putExtra("cookie", ticket.getCookie());
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void playMusic(){
//        Uri alarm;
//
//        alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//        ringtone = RingtoneManager.getRingtone(getApplicationContext(), alarm);
//        ringtone.play();
//    }
//
//    private void stopMusic(){
//        ringtone.stop();
//    }
//}
