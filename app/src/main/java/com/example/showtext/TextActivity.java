package com.example.showtext;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TextActivity extends AppCompatActivity {
    private ConstraintLayout containerRL;
    private String TEXT;
    private Button toastBtn;
    private Button notifyBtn;
    private Button popupBtn;
    private Button completeBtn;
    private TextView textView;
    private TextView errorView;
    private boolean check;
    private static int cnt=0;

    private boolean isCheck(String text){
        if(text.length() == 0 || text == null)
            return false;
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_text);

        containerRL = findViewById(R.id.constLayout2);
        containerRL.setBackground(getResources().getDrawable(R.drawable.blue_rasp_bg));

        View vB = findViewById(R.id.viewBottom);
        vB.setBackground(getResources().getDrawable(R.drawable.round_rectangle));
        vB.setClipToOutline(true);

        View vT = findViewById(R.id.viewTop);
        vT.setBackground(getResources().getDrawable(R.drawable.small_round_rectangle));
        vT.setClipToOutline(true);

        vT.setOnFocusChangeListener((view, e)->{
            if(cnt%2 == 0){
                vT.setBackground(getResources().getDrawable(R.drawable.small_round_rectangle_glow));
                textView.setTextColor(Color.WHITE);
            }else{
                vT.setBackground(getResources().getDrawable(R.drawable.small_round_rectangle));

            }
            cnt++;
        });//Changing Background on every focus change

        toastBtn = findViewById(R.id.toastBtn);
        notifyBtn = findViewById(R.id.notifyBtn);
        popupBtn = findViewById(R.id.popupBtn);
        completeBtn = findViewById(R.id.completeBtn);
        textView = findViewById(R.id.viewTop);
        errorView =findViewById(R.id.errorMsg);

        errorView.setText("");

        toastBtn.setOnClickListener((view)->{
            textView.clearFocus();
            errorView.setText("");
            TEXT = textView.getText().toString();
            check = isCheck(TEXT);//check for text  validation
            if (check){
                Toast.makeText(this, TEXT, Toast.LENGTH_SHORT).show();
            }else{
                errorView.setText("Text is empty!");
            }
        }); //check is yet to change

        notifyBtn.setOnClickListener((view)->{
            textView.clearFocus();
            errorView.setText("");
            TEXT = textView.getText().toString();
            check = isCheck(TEXT);//check for text  validation
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("NOTE_ID","NOTE_ID",NotificationManager.IMPORTANCE_DEFAULT);
                NotificationManager manager = getSystemService(NotificationManager.class);
                manager.createNotificationChannel(channel);
            }

            Intent snoozeIntent = new Intent(this, NotificationActivity.class);

            snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);
            snoozeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent dismissIntent = PendingIntent.getActivity(this, 0, snoozeIntent, PendingIntent.FLAG_CANCEL_CURRENT);


            if (check){
                Log.d("notify","running");
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "NOTE_ID")
                        .setSmallIcon(R.drawable.notification)
                        .setContentTitle("MSG")
                        .setContentText(TEXT)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true)
                        .addAction(R.drawable.complete, "CLEAR", dismissIntent);

                NotificationManagerCompat manager = NotificationManagerCompat.from(this);

                manager.notify(999,builder.build());


            }else{
                errorView.setText("Text is empty!");
            }
        });

        popupBtn.setOnClickListener((view)->{
            textView.clearFocus();
            errorView.setText("");
            TEXT = textView.getText().toString();
            check = isCheck(TEXT);//check for text  validation
            if (check){
                AlertDialog.Builder alert = new AlertDialog.Builder(this,R.style.AlertDialogStyle);
                alert.setTitle("Pop Up message");
                alert.setMessage(TEXT);


                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //perform nothing
                            }
                        });

                alert.show();
            }else{
                errorView.setText("Text is empty!");
            }
        });

        completeBtn.setOnClickListener((view)->{
            textView.clearFocus();
            errorView.setText("");
            TEXT = textView.getText().toString();
            check = isCheck(TEXT);//check for text  validation
            if (check){
                textView.setText("COMPLETED");
                textView.setTextColor(Color.GREEN);
            }else{
                errorView.setText("Text is empty!");
            }
        });

    }
}