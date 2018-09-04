package com.example.nesic.receivetcpmessage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

public class MainActivity extends AppCompatActivity {

    private SimpleTcpServer simpleTcpServer;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)this.findViewById(R.id.textView);

        simpleTcpServer = new SimpleTcpServer(8080);
        simpleTcpServer.hello();
        simpleTcpServer.start();

//        try {
//            ServerSocket serverSocket = new ServerSocket();
//            serverSocket.bind(new InetSocketAddress(3030));
//            serverSocket.accept();
//        } catch (IOException e){
//            e.printStackTrace();
//        }
    }
}
