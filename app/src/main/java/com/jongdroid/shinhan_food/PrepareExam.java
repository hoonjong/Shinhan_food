package com.jongdroid.shinhan_food;


import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class PrepareExam extends AppCompatActivity {

    Button b1;
    Button b2;

    TextView receiveData;
    Boolean connecting = false;

    ImageView img;

    static final int REQUEST_ENABLE_BT = 10;
    BluetoothAdapter mBluetoothAdapter;
    int mPairedDeviceCount = 0;
    Set<BluetoothDevice> pairedDevices;
    BluetoothDevice mRemoteDevice;
    BluetoothSocket mSocket = null;
    OutputStream mOutputStream = null;
    InputStream mInputStream = null;

    Thread mWorkerThread = null;

    byte[] readBuffer;
    int bufferPosition;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == RESULT_OK) {

                    selectPairedDevice();    // 페어링된 기기를 찾는 메소드를 호출
                } else if (resultCode == RESULT_CANCELED) {

                    finish();                // Activity 종료
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    void activateBluetooth() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "블루투스를 지원하지 않습니다!", Toast.LENGTH_SHORT);
            finish();
        } else {       // 장치가 블루투스를 지원하면

            if (!mBluetoothAdapter.isEnabled()) {  // 블루투스를 지원하지만 블루투스 장치가 비활성화 상태라면
                Intent enableBtIntent =
                        new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);  // 블루투스 활성화 사용자 동의를 얻는 창을 띄운다.
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            } else {                        // 블루투스를 자원하고 장치가 활성 상태인 경우
                selectPairedDevice();       // 블루투스 페어링된 목록을 보고 연결할 블루투스를 선택한다.
            }
        }
    }

    void selectPairedDevice() {
        pairedDevices = mBluetoothAdapter.getBondedDevices();
        mPairedDeviceCount = pairedDevices.size();  // 페어링된 장치의 수를 구해서 mPairedDeviceCount에 저장


        if (mPairedDeviceCount == 0) {      // 페어링된 장치가 없으면

            Toast.makeText(getApplicationContext(), "페어링된 장치가 없습니다!", Toast.LENGTH_SHORT);
            finish();                       // 액티비티 종료
        }


        final List<String> listDevices = new ArrayList<String>();
        for (BluetoothDevice device : pairedDevices) {      // 현재 사용자가 페어링한 블루투스 장치들의 이름을
            listDevices.add(device.getName());              // ArrayList인 listDevices에 장치이름을 저장한다.
        }

        // 블루투스 기기들의 이름을 ArrayAdapter 객체인 mAdapter에 담는다.  그리고 리스트뷰에 setAdapter 메소드로 어댑터를 설정한다.
        ArrayAdapter mAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listDevices);
        final ListView listView = findViewById(R.id.listview);
        listView.setAdapter(mAdapter);

        final String[] items = listDevices.toArray(new String[listDevices.size()]);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {   // 리스트뷰에 리스너 설정
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (connecting == false)
                    connectToBluetoothDevice(items[position]);  // 터치한 항목

                connecting = true;   // 화면에 있는 항목을 한 번만 선택이 가능하도록 connecting 값을 true로 설정
                findViewById(R.id.selectBT).setVisibility(View.INVISIBLE);
                findViewById(R.id.listview).setVisibility(View.INVISIBLE);
            }
        });

    }

    void receiveData() {           // 블루투스로 부터 데이터 수신하기
        final Handler handler = new Handler();

        readBuffer = new byte[1024];    // 수신 버퍼
        bufferPosition = 0;             // 버퍼 내 수신 문자 저장 위치

        mWorkerThread = new Thread(new Runnable() {
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {

                    try {
                        int bytesAvailable = mInputStream.available();  // 수신한 데이터 크기를 저장

                        if (bytesAvailable > 0) {    // 수신한 데이터가 있으면
                            byte[] packetBytes = new byte[bytesAvailable];
                            mInputStream.read(packetBytes);  // 스트림을 사용하여 수신한 데이터를 packetBytes에 넣기

                            int i = 0;
                            while (i < bytesAvailable) {

                                if (packetBytes[i] == '\n') {
                                    final String data = new String(readBuffer, "US-ASCII");
                                    bufferPosition = 0;

                                    handler.post(new Runnable() {
                                        public void run() {
                                            // 수신한 문자열을 화면에 보여줌
                                            receiveData.setText(data);
                                        }

                                    });
                                } else {
                                    readBuffer[bufferPosition++] = packetBytes[i];
                                }

                                i += 1;
                            }   //end of for
                        }
                    } catch (IOException ex) {
                        // 데이터 수신 중 오류 발생
                        finish();
                    }
                }
            }
        });

        mWorkerThread.start();
    }

    void transmitData(String msg) {    // 블루투스 모듈에 데이터 보내기
        msg += "\n";

        try {

            mOutputStream.write(msg.getBytes());        // 스트림을 이용하여 문자열 전송
        } catch (Exception e) {
            // 오류가 발생한 경우
            finish();        // 액티비티 종료
        }
    }

    BluetoothDevice getDeviceFromBondedList(String name) {
        BluetoothDevice selectedDevice = null;

        for (BluetoothDevice device : pairedDevices) {
            if (name.equals(device.getName())) {
                selectedDevice = device;
                break;
            }
        }

        return selectedDevice;
    }

    @Override
    protected void onDestroy() {
        try {
            mWorkerThread.interrupt();
            mInputStream.close();
            mOutputStream.close();
            mSocket.close();
        } catch (Exception e) {
        }

        super.onDestroy();
    }

    // 페어링된 블루투스 장치와 연결하기
    void connectToBluetoothDevice(String selectedDeviceName) {    // 사용자가 선택한 블루투스 장치의 이름을 전달 받는다.
        mRemoteDevice = getDeviceFromBondedList(selectedDeviceName);
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");  // 블루투스 장치와 통신하기 위해서 소켓생성시 UUID(범용 고유 식별자)가 필요

        try {
            mSocket = mRemoteDevice.createRfcommSocketToServiceRecord(uuid);  // UUID와 일치하는 장치를 찾아 소켓을 생성

            mSocket.connect();   // 소켓을 이용하여 블루투스 장치와 접속

            mOutputStream = mSocket.getOutputStream();   // 블루투스 통신은 Socket을 이용하여 InputStream, OutputStream 객체를 사용하여 데이터를 송수신한다.
            mInputStream = mSocket.getInputStream();     // 데이터 송수신을 위한 스트림 객체 얻기

            receiveData();    // 데이터를 블루투스로 부터 수신
        } catch (Exception e) {     // 블루투스 연결중 오류 발생시
            Toast.makeText(getApplicationContext(), "connect error", Toast.LENGTH_SHORT).show();
            finish();        // 앱 종료
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        receiveData = findViewById(R.id.receiveData);


        img = findViewById(R.id.led);

        b1.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                transmitData("1");
                img.setImageResource(R.drawable.led1on);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                transmitData("0");
                img.setImageResource(R.drawable.led1off);
            }
        });

        activateBluetooth();
    }

}
