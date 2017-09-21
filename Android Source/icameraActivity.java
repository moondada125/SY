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

public class icameraActivity extends AppCompatActivity {
    //서버주소
    public static final String sIP = "192.168.43.111";
    //사용할 통신 포트
    public static final int sPORT = 8001;

    //데이터 보낼 클래스
    public SendTu mSendTu = null;
    public SendTd mSendTd = null;
    public SendTl mSendTl = null;
    public SendTr mSendTr = null;

    //화면 표시용 TextView
    public TextView itxtView = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.icamera);

        Button btnTu = (Button)findViewById(R.id.Up);
        Button btnTd = (Button)findViewById(R.id.Down);
        Button btnTl = (Button)findViewById(R.id.Left);
        Button btnTr = (Button)findViewById(R.id.Right);
        Button ioutbutton01=(Button)findViewById(R.id.ioutbutton01);

        itxtView = (TextView) findViewById(R.id.itextView);

        btnTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SendData 클래스 생성
                mSendTu = new SendTu();
                //보내기 시작
                mSendTu.start();
            }
        });

        btnTd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SendData 클래스 생성
                mSendTd = new SendTd();
                //보내기 시작
                mSendTd.start();
            }
        });

        btnTl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SendData 클래스 생성
                mSendTl = new SendTl();
                //보내기 시작
                mSendTl.start();
            }
        });

        btnTr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SendData 클래스 생성
                mSendTr = new SendTr();
                //보내기 시작
                mSendTr.start();
            }
        });

        ioutbutton01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //webview
        WebView webView = (WebView)findViewById(R.id.iwebview);
        webView.setWebViewClient(new WebViewClient());
        webView.setBackgroundColor(255);

        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.loadData("<html><head><style type='text/css'>body{margin:auto auto;text-align:center;} img{width:100%25;} div{overflow: hidden;} </style></head><body><div><img src='http://192.168.43.245:8090/stream/video.mjpeg'/></div></body></html>" ,"text/html",  "UTF-8");
        //webView.loadData("<img src='http://192.168.43.112:8080/stream/video.mjpeg'/>" ,"text/html",  "UTF-8");
    }

    //데이터 보내는 쓰레드 클래스
    class SendTu extends Thread{
        public void run(){
            try{
                //UDP 통신용 소켓 생성
                DatagramSocket socket = new DatagramSocket();
                //서버 주소 변수
                InetAddress serverAddr = InetAddress.getByName(sIP);

                //보낼 데이터 생성
                byte[] buf = ("Up").getBytes();

                //패킷으로 변경
                DatagramPacket packet = new DatagramPacket(buf, buf.length, serverAddr, sPORT);

                //패킷 전송!
                socket.send(packet);

                //데이터 수신 대기
                socket.receive(packet);
                //데이터 수신되었다면 문자열로 변환
                String msg = new String(packet.getData());

                //txtView에 표시
                itxtView.setText(msg);
            }catch (Exception e){

            }
        }
    }

    //데이터 보내는 쓰레드 클래스
    class SendTd extends Thread{
        public void run(){
            try{
                //UDP 통신용 소켓 생성
                DatagramSocket socket = new DatagramSocket();
                //서버 주소 변수
                InetAddress serverAddr = InetAddress.getByName(sIP);

                //보낼 데이터 생성
                byte[] buf = ("Down").getBytes();

                //패킷으로 변경
                DatagramPacket packet = new DatagramPacket(buf, buf.length, serverAddr, sPORT);

                //패킷 전송!
                socket.send(packet);

                //데이터 수신 대기
                socket.receive(packet);
                //데이터 수신되었다면 문자열로 변환
                String msg = new String(packet.getData());

                //txtView에 표시
                itxtView.setText(msg);
            }catch (Exception e){

            }
        }
    }

    //데이터 보내는 쓰레드 클래스
    class SendTl extends Thread{
        public void run(){
            try{
                //UDP 통신용 소켓 생성
                DatagramSocket socket = new DatagramSocket();
                //서버 주소 변수
                InetAddress serverAddr = InetAddress.getByName(sIP);

                //보낼 데이터 생성
                byte[] buf = ("Left").getBytes();

                //패킷으로 변경
                DatagramPacket packet = new DatagramPacket(buf, buf.length, serverAddr, sPORT);

                //패킷 전송!
                socket.send(packet);

                //데이터 수신 대기
                socket.receive(packet);
                //데이터 수신되었다면 문자열로 변환
                String msg = new String(packet.getData());

                //txtView에 표시
                itxtView.setText(msg);
            }catch (Exception e){

            }
        }
    }

    //데이터 보내는 쓰레드 클래스
    class SendTr extends Thread{
        public void run(){
            try{
                //UDP 통신용 소켓 생성
                DatagramSocket socket = new DatagramSocket();
                //서버 주소 변수
                InetAddress serverAddr = InetAddress.getByName(sIP);

                //보낼 데이터 생성
                byte[] buf = ("Right").getBytes();

                //패킷으로 변경
                DatagramPacket packet = new DatagramPacket(buf, buf.length, serverAddr, sPORT);

                //패킷 전송!
                socket.send(packet);

                //데이터 수신 대기
                socket.receive(packet);
                //데이터 수신되었다면 문자열로 변환
                String msg = new String(packet.getData());

                //txtView에 표시
                itxtView.setText(msg);
            }catch (Exception e){

            }
        }
    }
}
