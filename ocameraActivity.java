package com.example.yonghwi.python01;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by YongHwi on 2017-08-12.
 */

public class ocameraActivity extends AppCompatActivity {
    //서버주소
    public static final String sIP = "192.168.43.111";
    //사용할 통신 포트
    public static final int sPORT = 8001;

    //데이터 보낼 클래스
    public SendDo mSendDo = null;
    public SendDc mSendDc = null;

    //화면 표시용 TextView
    public TextView otxtView = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ocamera);

        Button btnDo = (Button)findViewById(R.id.DoorOpen);
        Button btnDc = (Button)findViewById(R.id.DoorClose);
        Button ooutbutton01 = (Button)findViewById(R.id.ooutbutton01);

        otxtView = (TextView) findViewById(R.id.otextView);

        btnDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SendData 클래스 생성
                mSendDo = new SendDo();
                //보내기 시작
                mSendDo.start();
            }
        });

        btnDc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SendData 클래스 생성
                mSendDc = new SendDc();
                //보내기 시작
                mSendDc.start();
            }
        });

        ooutbutton01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //webview
        WebView webView = (WebView)findViewById(R.id.owebview);
        webView.setWebViewClient(new WebViewClient());
        webView.setBackgroundColor(255);

        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.loadData("<html><head><style type='text/css'>body{margin:auto auto;text-align:center;} img{width:100%25;} div{overflow: hidden;} </style></head><body><div><img src='http://192.168.43.245:8080/stream/video.mjpeg'/></div></body></html>" ,"text/html",  "UTF-8");
        //webView.loadData("<img src='http://192.168.43.112:8090/stream/video.mjpeg'/>" ,"text/html",  "UTF-8");
        //webView.loadData("<img src='http://192.168.43.112:8090/stream/video.mjpeg'/>" ,"text/html",  "UTF-8");
        //webView.loadUrl("http://192.168.137.2:8090");
    }

    //데이터 보내는 쓰레드 클래스
    class SendDo extends Thread{
        public void run(){
            try{
                //UDP 통신용 소켓 생성
                DatagramSocket socket = new DatagramSocket();
                //서버 주소 변수
                InetAddress serverAddr = InetAddress.getByName(sIP);

                //보낼 데이터 생성
                byte[] buf = ("Door open").getBytes();

                //패킷으로 변경
                DatagramPacket packet = new DatagramPacket(buf, buf.length, serverAddr, sPORT);

                //패킷 전송!
                socket.send(packet);

                //데이터 수신 대기
                socket.receive(packet);
                //데이터 수신되었다면 문자열로 변환
                String msg = new String(packet.getData());

                //txtView에 표시
                otxtView.setText(msg);
            }catch (Exception e){

            }
        }
    }

    //데이터 보내는 쓰레드 클래스
    class SendDc extends Thread{
        public void run(){
            try{
                //UDP 통신용 소켓 생성
                DatagramSocket socket = new DatagramSocket();
                //서버 주소 변수
                InetAddress serverAddr = InetAddress.getByName(sIP);

                //보낼 데이터 생성
                byte[] buf = ("Door close").getBytes();

                //패킷으로 변경
                DatagramPacket packet = new DatagramPacket(buf, buf.length, serverAddr, sPORT);

                //패킷 전송!
                socket.send(packet);

                //데이터 수신 대기
                socket.receive(packet);
                //데이터 수신되었다면 문자열로 변환
                String msg = new String(packet.getData());

                //txtView에 표시
                otxtView.setText(msg);
            }catch (Exception e){

            }
        }
    }
}
