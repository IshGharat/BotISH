package com.example.botish;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class MainActivity extends AppCompatActivity  {
    SeekBar speedBar;
    Button Forward,Backward,Left,Right;
    TextView PWM;

    public int getSpeed() {
        return Speed;
    }

    public void setSpeed(int speed) {
        Speed = speed;
    }

    private int Speed=40;
    String bot_command="s,0";
    String host="192.168.4.1";
    int port=2010;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        speedBar = findViewById(R.id.speed);
        Forward = findViewById(R.id.Forward);
        Backward = findViewById(R.id.Backward);
        Left = findViewById(R.id.Left);
        Right = findViewById(R.id.Right);
        PWM = findViewById(R.id.pwm);

        speedBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setSpeed(progress);
                PWM.setText(String.valueOf(Speed));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        Forward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getActionMasked()==MotionEvent.ACTION_DOWN){
                    bot_command=String.format("f,%d",getSpeed());
                }
                if(event.getActionMasked()==MotionEvent.ACTION_UP){
                    bot_command="s,0";
                }
                Drive();
                return false;
            }
        });
        Backward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getActionMasked()==MotionEvent.ACTION_DOWN){
                    bot_command=String.format("b,%d",getSpeed());
                }
                if(event.getActionMasked()==MotionEvent.ACTION_UP){
                    bot_command="s,0";
                }
                Drive();
                return false;
            }
        });
        Left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getActionMasked()==MotionEvent.ACTION_DOWN){
                    bot_command=String.format("l,%d",getSpeed());
                }
                if(event.getActionMasked()==MotionEvent.ACTION_UP){
                    bot_command="s,0";
                }
                Drive();
                return false;
            }
        });
        Right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getActionMasked()==MotionEvent.ACTION_DOWN){
                    bot_command=String.format("r,%d",getSpeed());
                }
                if(event.getActionMasked()==MotionEvent.ACTION_UP){
                    bot_command="s,0";
                }
                Drive();
                return false;
            }
        });
    }
    private void Drive(){
        final Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {


            @Override
            public void run() {

                DatagramSocket ds = null;
                try {
                    ds = new DatagramSocket();
                    // IP Address below is the IP address of that Device where server socket is opened.
                    InetAddress serverAddr = InetAddress.getByName("192.168.4.1");
                    DatagramPacket dp;
                    dp = new DatagramPacket(bot_command.getBytes(), bot_command.length(), serverAddr, 2010);
                    ds.send(dp);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (ds != null) {
                        ds.close();
                    }
                }
            }
        });
        thread.start();


    }




}