package com.example.nesic.receivetcpmessage;

import android.util.Log;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class SimpleTcpServer {
    private String tag = "SimpleTcpServer.java";
    private int port = 50000;
    private ServerSocket serverSocket;
    private Socket socket;

    private TextView textView;

    // コンストラクタ
    public SimpleTcpServer(int port) {
        this.port = port;

//        textView = (TextView)findViewByID()

        // サーバソケットの作成
        try{
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(port));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    // クラスのテスト用
    public void hello() {
        Log.d(tag, "listening on " + port);
    }

    public void start() {
        if (serverSocket == null) {
            return;
        }

        // Runnableオブジェクトのrun()関数内にサブスレッドで実行したいことを記述する
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(tag, "別スレッド");
                try {
                    // アクセスの待ち受け
                    Socket socket = serverSocket.accept();

                    // 受信データバッファ
//                   byte[] data = new byte[1024];

                    // 受信ストリームの取得
                    InputStream inputStream = socket.getInputStream();
                    BufferedInputStream bis = new BufferedInputStream(inputStream);
                    DataInputStream dis = new DataInputStream(bis);

                    // ログ日時データ長
                    ByteBuffer bb = ByteBuffer.allocate(4);
                    bb.putInt(dis.readInt());
                    bb.order(ByteOrder.LITTLE_ENDIAN);
                    int dateLength = bb.getInt(0);
                    Log.d(tag, "Date Length = " + dateLength);

                    // ログ日時
                    byte[] logBuff = new byte[dateLength];
                    dis.readFully(logBuff, 0, dateLength);
                    String logDate = new String(logBuff, "SJIS");
                    Log.d(tag, "Log Date = " + logDate);

                    // カメラ名データ長
                    bb = ByteBuffer.allocate(7);
                    bb.putInt(dis.readInt());
                    bb.order(ByteOrder.LITTLE_ENDIAN);
                    int cameraNameLength = bb.getInt(0);
                    Log.d(tag, "Camera Name Length = " + cameraNameLength);

                    // カメラ名
                    byte[] cameraNameBuff = new byte[cameraNameLength];
                    dis.readFully(cameraNameBuff, 0, cameraNameLength);
                    String cameraName = new String(cameraNameBuff, "SJIS");
                    Log.d(tag, "Camera Name = " + cameraName);

                    // ログ内容長
                    bb = ByteBuffer.allocate(4);
                    bb.putInt(dis.readInt());
                    bb.order(ByteOrder.LITTLE_ENDIAN);
                    int logContentsLength = bb.getInt(0);
                    Log.d(tag, "Log Contents Length = " + logContentsLength);

                    // ログ内容
                    byte[] logContentsBuff = new byte[logContentsLength];
                    dis.readFully(logContentsBuff, 0, logContentsLength);
                    String logContents = new String(logContentsBuff, "SJIS");
                    Log.d(tag, "Log Contents = " + logContents);




                    //受信ストリームの終了
                    serverSocket.close();
                    inputStream.close();

                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
