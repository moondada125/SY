package com.example.yonghwi.python01;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MainActivity extends AppCompatActivity {
    //서버주소
    public static final String sIP = "192.168.43.111";
    //사용할 통신 포트
    public static final int sPORT = 8001;

    //데이터 보낼 클래스
    public SendLon mSendLon = null;
    public SendLoff mSendLoff = null;
    public SendCu mSendCu = null;
    public SendCd mSendCd = null;
    public SendCs mSendCs = null;

    //화면 표시용 TextView
    public TextView txtView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button btnLon = (Button)findViewById(R.id.LightOn);
        Button btnLoff = (Button)findViewById(R.id.LightOff);
        Button btnCu = (Button)findViewById(R.id.CurtainUp);
        Button btnCd = (Button)findViewById(R.id.CurtainDown);
        Button btnCs = (Button)findViewById(R.id.CurtainStop);

        txtView = (TextView) findViewById(R.id.textView);
        Button exitbutton=(Button)findViewById(R.id.exitbutton);

        exitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void changebutton01(View v) {
        Button lightbutton01=(Button)findViewById(R.id.lightbutton01);
        Button LightOn=(Button)findViewById(R.id.LightOn);
        Button LightOff=(Button)findViewById(R.id.LightOff);
        Button curtainbutton01=(Button)findViewById(R.id.curtainbutton01);
        Button CurtainUp=(Button)findViewById(R.id.CurtainUp);
        Button CurtainDown=(Button)findViewById(R.id.CurtainDown);
        Button CurtainStop=(Button)findViewById(R.id.CurtainStop);

        switch (v.getId()) {
            case R.id.lightbutton01:
                lightbutton01.setVisibility(v.INVISIBLE);
                LightOn.setVisibility(v.VISIBLE);
                LightOff.setVisibility(v.VISIBLE);
                break;

            case R.id.LightOn:
                lightbutton01.setVisibility(v.VISIBLE);
                LightOn.setVisibility(v.INVISIBLE);
                LightOff.setVisibility(v.INVISIBLE);
                lightbutton01.setBackgroundColor(Color.RED);
                lightbutton01.setTextColor(Color.WHITE);
                lightbutton01.setText("켜짐");
                mSendLon = new SendLon();
                //보내기 시작
                mSendLon.start();
                break;

            case R.id.LightOff:
                lightbutton01.setVisibility(v.VISIBLE);
                LightOn.setVisibility(v.INVISIBLE);
                LightOff.setVisibility(v.INVISIBLE);
                lightbutton01.setBackgroundColor(Color.GRAY);
                lightbutton01.setTextColor(Color.BLACK);
                lightbutton01.setText("꺼짐");
                mSendLoff = new SendLoff();
                //보내기 시작u
                mSendLoff.start();
                break;

            case R.id.curtainbutton01:
                curtainbutton01.setVisibility(v.INVISIBLE);
                CurtainUp.setVisibility(v.VISIBLE);
                CurtainDown.setVisibility(v.VISIBLE);
                CurtainStop.setVisibility(v.VISIBLE);
                break;

            case R.id.CurtainUp:
                curtainbutton01.setVisibility(v.VISIBLE);
                CurtainUp.setVisibility(v.INVISIBLE);
                CurtainDown.setVisibility(v.INVISIBLE);
                CurtainStop.setVisibility(v.INVISIBLE);
                curtainbutton01.setBackgroundColor(Color.RED);
                curtainbutton01.setTextColor(Color.WHITE);
                curtainbutton01.setText("올림");
                mSendCu = new SendCu();
                //보내기 시작
                mSendCu.start();
                break;

            case R.id.CurtainDown:
                curtainbutton01.setVisibility(v.VISIBLE);
                CurtainUp.setVisibility(v.INVISIBLE);
                CurtainDown.setVisibility(v.INVISIBLE);
                CurtainStop.setVisibility(v.INVISIBLE);
                curtainbutton01.setBackgroundColor(Color.GRAY);
                curtainbutton01.setTextColor(Color.BLACK);
                curtainbutton01.setText("내림");
                mSendCd = new SendCd();
                //보내기 시작
                mSendCd.start();
                break;

            case R.id.CurtainStop:
                curtainbutton01.setVisibility(v.VISIBLE);
                CurtainUp.setVisibility(v.INVISIBLE);
                CurtainDown.setVisibility(v.INVISIBLE);
                CurtainStop.setVisibility(v.INVISIBLE);
                curtainbutton01.setBackgroundColor(Color.YELLOW);
                curtainbutton01.setTextColor(Color.BLACK);
                curtainbutton01.setText("정지");
                mSendCs = new SendCs();
                //보내기 시작
                mSendCs.start();
                break;
        }
    }

    public void moveIcamera(View target) {
        Intent intent = new Intent(getApplicationContext(), icameraActivity.class);
        startActivity(intent);
    }

    public void moveOcamera(View target) {
        Intent intent = new Intent(getApplicationContext(), ocameraActivity.class);
        startActivity(intent);
    }

    //데이터 보내는 쓰레드 클래스
    class SendLon extends Thread{
        public void run(){
            try{
                //UDP 통신용 소켓 생성
                DatagramSocket socket = new DatagramSocket();
                //서버 주소 변수
                InetAddress serverAddr = InetAddress.getByName(sIP);

                //보낼 데이터 생성
                byte[] buf = ("Light on").getBytes();

                //패킷으로 변경
                DatagramPacket packet = new DatagramPacket(buf, buf.length, serverAddr, sPORT);

                //패킷 전송!
                socket.send(packet);

                //데이터 수신 대기
                socket.receive(packet);
                //데이터 수신되었다면 문자열로 변환
                String msg = new String(packet.getData());

                //txtView에 표시
                txtView.setText(msg);
            }catch (Exception e){

            }
        }
    }

    //데이터 보내는 쓰레드 클래스
    class SendLoff extends Thread{
        public void run(){
            try{
                //UDP 통신용 소켓 생성
                DatagramSocket socket = new DatagramSocket();
                //서버 주소 변수
                InetAddress serverAddr = InetAddress.getByName(sIP);

                //보낼 데이터 생성
                byte[] buf = ("Light off").getBytes();

                //패킷으로 변경
                DatagramPacket packet = new DatagramPacket(buf, buf.length, serverAddr, sPORT);

                //패킷 전송!
                socket.send(packet);

                //데이터 수신 대기
                socket.receive(packet);
                //데이터 수신되었다면 문자열로 변환
                String msg = new String(packet.getData());

                //txtView에 표시
                txtView.setText(msg);
            }catch (Exception e){

            }
        }
    }

    //데이터 보내는 쓰레드 클래스
    class SendCu extends Thread{
        public void run(){
            try{
                //UDP 통신용 소켓 생성
                DatagramSocket socket = new DatagramSocket();
                //서버 주소 변수
                InetAddress serverAddr = InetAddress.getByName(sIP);

                //보낼 데이터 생성
                byte[] buf = ("Curtain up").getBytes();

                //패킷으로 변경
                DatagramPacket packet = new DatagramPacket(buf, buf.length, serverAddr, sPORT);

                //패킷 전송!
                socket.send(packet);

                //데이터 수신 대기
                socket.receive(packet);
                //데이터 수신되었다면 문자열로 변환
                String msg = new String(packet.getData());

                //txtView에 표시
                txtView.setText(msg);
            }catch (Exception e){

            }
        }
    }

    //데이터 보내는 쓰레드 클래스
    class SendCd extends Thread{
        public void run(){
            try{
                //UDP 통신용 소켓 생성
                DatagramSocket socket = new DatagramSocket();
                //서버 주소 변수
                InetAddress serverAddr = InetAddress.getByName(sIP);

                //보낼 데이터 생성
                byte[] buf = ("Curtain down").getBytes();

                //패킷으로 변경
                DatagramPacket packet = new DatagramPacket(buf, buf.length, serverAddr, sPORT);

                //패킷 전송!
                socket.send(packet);

                //데이터 수신 대기
                socket.receive(packet);
                //데이터 수신되었다면 문자열로 변환
                String msg = new String(packet.getData());

                //txtView에 표시
                txtView.setText(msg);
            }catch (Exception e){

            }
        }
    }

    //데이터 보내는 쓰레드 클래스
    class SendCs extends Thread{
        public void run(){
            try{
                //UDP 통신용 소켓 생성
                DatagramSocket socket = new DatagramSocket();
                //서버 주소 변수
                InetAddress serverAddr = InetAddress.getByName(sIP);

                //보낼 데이터 생성
                byte[] buf = ("Curtain stop").getBytes();

                //패킷으로 변경
                DatagramPacket packet = new DatagramPacket(buf, buf.length, serverAddr, sPORT);

                //패킷 전송!
                socket.send(packet);

                //데이터 수신 대기
                socket.receive(packet);
                //데이터 수신되었다면 문자열로 변환
                String msg = new String(packet.getData());

                //txtView에 표시
                txtView.setText(msg);
            }catch (Exception e){

            }
        }
    }
}
