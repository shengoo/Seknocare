package sally.seknocare;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class MainActivity extends Activity implements View.OnClickListener {

    private final static int REQUEST_CONNECT_DEVICE = 1;
    private final static String MY_UUID = "00001101-0000-1000-8000-00805F9B34FB";
    private InputStream is;

    boolean bRun = true;
    boolean bThread = false;

    private Button btnPress;
    private Button btnNip;
    private Button btnPrick;
    private Button btnRap;

    private Button btnStroke;
    private Button btnFlutter;
    private Button btnScrape;
    private Button btnPinch;
    private Button btnAuto;

    public static TextView textViewMode;

    private Button btnTimer;
    private Button btnIncrease;
    private Button btnTurnoff;
    private Button btnDecrease;
    private Button btnBluetooth;

    private BluetoothAdapter _bluetooth = BluetoothAdapter.getDefaultAdapter();
    private BluetoothDevice _device = null;     //蓝牙设备
    private static BluetoothSocket _socket = null;      //蓝牙通信socket

    private String smsg = "";    //显示用数据缓存
    private String fmsg = "";    //保存用数据缓存
    private int power = 0;

    public static boolean TimeStart = false;
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();


        btnPress = (Button)findViewById(R.id.press);
        btnPress.setOnClickListener(this);

        btnNip = (Button)findViewById(R.id.nip);
        btnNip.setOnClickListener(this);

        btnPrick = (Button)findViewById(R.id.prick);
        btnPrick.setOnClickListener(this);

        btnRap = (Button)findViewById(R.id.rap);
        btnRap.setOnClickListener(this);

        btnStroke = (Button)findViewById(R.id.stroke);
        btnStroke.setOnClickListener(this);

        btnFlutter = (Button)findViewById(R.id.flutter);
        btnFlutter.setOnClickListener(this);

        btnScrape = (Button)findViewById(R.id.scrape);
        btnScrape.setOnClickListener(this);

        btnPinch = (Button)findViewById(R.id.pinch);
        btnPinch.setOnClickListener(this);

        btnAuto = (Button)findViewById(R.id.auto);
        btnAuto.setOnClickListener(this);

        btnTimer = (Button)findViewById(R.id.timer);
        btnTimer.setOnClickListener(this);

        btnIncrease = (Button)findViewById(R.id.increase);
        btnIncrease.setOnClickListener(this);

        btnTurnoff = (Button)findViewById(R.id.turnoff);
        btnTurnoff.setOnClickListener(this);

        btnDecrease = (Button)findViewById(R.id.decrease);
        btnDecrease.setOnClickListener(this);

        btnBluetooth = (Button)findViewById(R.id.bluetooth);
        btnBluetooth.setOnClickListener(this);

        textViewMode = (TextView)findViewById(R.id.mode_time_level);

        //如果打开本地蓝牙设备不成功，提示信息，结束程序
        if (_bluetooth == null){
            Toast.makeText(this, R.string.bluetooth_error, Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // 设置设备可以被搜索
        new Thread(){
            public void run(){
                if(_bluetooth.isEnabled()==false){
                    _bluetooth.enable();
                }
            }
        }.start();

        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                int Hour = ShowContent.Instance().getHour();
                int Minute = ShowContent.Instance().getMinute();

                if (!TimeStart) return;

                ShowContent.Instance().setTime(Hour + ":" + Minute);
                Message msg = new Message();
                msg.what = 0;

                handler.sendMessage(msg);

                if (Minute-- == 0) {
                    Hour--;
                    Minute = 59;
                }
                if (Hour == -1) {
                    TimeStart = false;
                }
                ShowContent.Instance().setHour(Hour);
                ShowContent.Instance().setMinute(Minute);
            }

        }, 1000*60, 1000*60);

    }

    @TargetApi(23)
    private void checkPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, Constants.PermissionRequestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == Constants.PermissionRequestCode){
            boolean permissionGranted = true;
            for (int result : grantResults){
                if(result != PackageManager.PERMISSION_GRANTED){
//                    Toast.makeText(this, R.string.permission_denied,Toast.LENGTH_LONG).show();
                    permissionGranted = false;
                }
            }
            if(!permissionGranted){
                AlertDialog.Builder build = new AlertDialog.Builder(this);
                build.setMessage(R.string.permission_denied);
                build.setTitle(R.string.permission_required);
                build.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        checkPermission();
                    }
                });
                build.create().show();
            }
        }
    }

    //接收活动结果，响应startActivityForResult()
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case REQUEST_CONNECT_DEVICE:     //连接结果，由DeviceListActivity设置返回
                // 响应返回结果
                if (resultCode == Activity.RESULT_OK) {   //连接成功，由DeviceListActivity设置返回
                    // MAC地址，由DeviceListActivity设置返回
                    String address = data.getExtras()
                            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    // 得到蓝牙设备句柄
                    _device = _bluetooth.getRemoteDevice(address);

                    // 用服务号得到socket
                    try{
                        _socket = _device.createRfcommSocketToServiceRecord(UUID.fromString(MY_UUID));
                    }catch(IOException e){
                        Toast.makeText(this, "连接失败！", Toast.LENGTH_SHORT).show();
                    }
                    //连接socket
                    try{
                        _socket.connect();
                        Toast.makeText(this, "连接"+_device.getName()+"成功！", Toast.LENGTH_SHORT).show();
                        //btn.setText("断开");
                        ShowContent.Instance().setBluetoothState(true);
                        MainActivity.textViewMode.setText(ShowContent.Instance().getShowContent());

                        sendMessage(0xE0); //
                    }catch(IOException e){
                        try{
                            Toast.makeText(this, "连接失败！", Toast.LENGTH_SHORT).show();
                            _socket.close();
                            _socket = null;
                        }catch(IOException ee){
                            Toast.makeText(this, "连接失败！", Toast.LENGTH_SHORT).show();
                        }

                        return;
                    }

                    //打开接收线程
                    try{
                        is = _socket.getInputStream();   //得到蓝牙数据输入流
                    }catch(IOException e){
                        Toast.makeText(this, "接收数据失败！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(bThread==false){
                        ReadThread.start();
                        bThread=true;
                    }else{
                        bRun = true;
                    }
                }
                break;
            default:break;
        }
    }

    //接收数据线程
    Thread ReadThread=new Thread(){

        public void run(){
            int num = 0;
            byte[] buffer = new byte[1024];
            byte[] buffer_new = new byte[1024];
            int i = 0;
            int n = 0;
            bRun = true;
            //接收线程
            while(true){
                try{
                    while(is.available()==0){
                        while(bRun == false){}
                    }
                    while(true){
                        num = is.read(buffer);         //读入数据
                        n=0;

                        String s0 = new String(buffer,0,num);
                        fmsg+=s0;    //保存收到数据
                        for(i=0;i<num;i++){
                            if((buffer[i] == 0x0d)&&(buffer[i+1]==0x0a)){
                                buffer_new[n] = 0x0a;
                                i++;
                            }else{
                                buffer_new[n] = buffer[i];
                            }
                            n++;
                        }
                        String s = new String(buffer_new,0,n);
                        smsg = s;   //写入接收缓存
                        power = buffer_new[n];
                        if(is.available()==0)break;  //短时间没有数据才跳出进行显示
                    }
                    //发送显示消息，进行显示刷新
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                }catch(IOException e){
                }
            }
        }
    };

    //消息处理队列
    Handler handler= new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            //dis.setText(smsg);   //显示数据
            //Toast.makeText(getApplicationContext(), R.string.connect_reminder, Toast.LENGTH_SHORT).show();
            //ShowContent.Instance().setTime(smsg);
            if (msg.what == 1) {
                int Power = (int)smsg.toCharArray()[0];
                if (Power > 100) Power = 100;

                ShowContent.Instance().setPower(Power);
                MainActivity.textViewMode.setText(ShowContent.Instance().getShowContent());
            } else if (msg.what == 0){
                MainActivity.textViewMode.setText(ShowContent.Instance().getShowContent());
            }
        }
    };

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (_socket == null && v.getId() != R.id.bluetooth) {
            Toast.makeText(getApplicationContext(), R.string.connect_reminder, Toast.LENGTH_SHORT).show();
            return;
        }
        switch (v.getId()) {
            case R.id.press: //0xC1
                //msg = Toast.makeText(getApplicationContext(), R.string.press_cn, Toast.LENGTH_SHORT);
                //msg.show();
                sendMessage(0xC1);
                ShowContent.Instance().setMode("PRESS");
                ShowContent.Instance().setStrang(0);
                MainActivity.textViewMode.setText(ShowContent.Instance().getShowContent());
                break;
            case R.id.nip: //0xA8
                sendMessage(0xA8);
                ShowContent.Instance().setMode("NIP");
                ShowContent.Instance().setStrang(0);
                MainActivity.textViewMode.setText(ShowContent.Instance().getShowContent());
                break;
            case R.id.prick: //0xA2
                sendMessage(0xA2);
                ShowContent.Instance().setMode("PRICK");
                ShowContent.Instance().setStrang(0);
                MainActivity.textViewMode.setText(ShowContent.Instance().getShowContent());
                break;
            case R.id.rap: //0xA7
                sendMessage(0xA7);
                ShowContent.Instance().setMode("RAP");
                ShowContent.Instance().setStrang(0);
                MainActivity.textViewMode.setText(ShowContent.Instance().getShowContent());
                break;
            case R.id.stroke: //0xA3
                sendMessage(0xA3);
                ShowContent.Instance().setMode("STROKE");
                ShowContent.Instance().setStrang(0);
                MainActivity.textViewMode.setText(ShowContent.Instance().getShowContent());
                break;
            case R.id.flutter: //0xA6
                sendMessage(0xA6);
                ShowContent.Instance().setMode("FLUTTER");
                ShowContent.Instance().setStrang(0);
                MainActivity.textViewMode.setText(ShowContent.Instance().getShowContent());
                break;
            case R.id.scrape: //0xA4
                sendMessage(0xA4);
                ShowContent.Instance().setMode("SCRAPE");
                ShowContent.Instance().setStrang(0);
                MainActivity.textViewMode.setText(ShowContent.Instance().getShowContent());
                break;
            case R.id.pinch: //0xA5
                sendMessage(0xA5);
                ShowContent.Instance().setMode("PINCH");
                ShowContent.Instance().setStrang(0);
                MainActivity.textViewMode.setText(ShowContent.Instance().getShowContent());
                break;
            case R.id.auto: //0xC0
                sendMessage(0xC0);
                ShowContent.Instance().setMode("AUTO");
                ShowContent.Instance().setStrang(0);
                MainActivity.textViewMode.setText(ShowContent.Instance().getShowContent());
                break;
            case R.id.timer:
                TimePickerDialog timeDialog = new TimePickerDialog(this);
                timeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                timeDialog.show();
                break;
            case R.id.increase:
                if (ShowContent.Instance().getTime().equals("0:0")) break;
                if (ShowContent.Instance().getStrang() == 10) break;

                if (ShowContent.Instance().getStrang() >= 5) {
                    RemainderDialog remainderDialog = new RemainderDialog(this);
                    remainderDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    remainderDialog.show();
                    break;
                }

                sendMessage(0xF2 + ShowContent.Instance().getStrang());

                ShowContent.Instance().setStrang(ShowContent.Instance().getStrang() + 1);
                MainActivity.textViewMode.setText(ShowContent.Instance().getShowContent());
                break;
            case R.id.turnoff:
                ShowContent.Instance().setTime("0:0");
                ShowContent.Instance().setStrang(0);

                sendMessage(0xB2);
                MainActivity.textViewMode.setText(ShowContent.Instance().getShowContent());

                finish();
                break;
            case R.id.decrease:
                if (ShowContent.Instance().getTime().equals("0:0")) break;
                if (ShowContent.Instance().getStrang() == 0) break;
                sendMessage(0xF1 + ShowContent.Instance().getStrang() - 1);

                ShowContent.Instance().setStrang(ShowContent.Instance().getStrang() - 1);
                MainActivity.textViewMode.setText(ShowContent.Instance().getShowContent());
                break;
            case R.id.bluetooth:
                if(_bluetooth.isEnabled()==false){  //如果蓝牙服务不可用则提示
                    Toast.makeText(this, " Bluetooth Opening...", Toast.LENGTH_LONG).show();
                    return;
                }
                if(_socket==null){
                    Intent serverIntent = new Intent(this, DeviceListActivity.class); //跳转程序设置
                    startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);  //设置返回宏定义
                }
                else{
                    Toast.makeText(this, " Bluetooth Connected", Toast.LENGTH_LONG).show();
                    break;
                    //关闭连接socket
	    		/*
	    	    try{

	    	    	is.close();
	    	    	_socket.close();
	    	    	_socket = null;
	    	    	bRun = false;

	    	    	ShowContent.Instance().setBluetoothState(false);
	    	    	MainActivity.textViewMode.setText(ShowContent.Instance().getShowContent());
	    	    	//btn.setText("连接");
	    	    }catch(IOException e){}
	    	    */
                }
                break;
        }



    }

    public void onDestroy(){
        super.onDestroy();
        if(_socket!=null)  //关闭连接socket
            try{
                is.close();
                _socket.close();
                _socket = null;
                bRun = false;
            }catch(IOException e){}
        //	_bluetooth.disable();  //关闭蓝牙服务
    }

    public static void sendMessage(int msg) {
        try {
            OutputStream os = _socket.getOutputStream();
            os.write(msg);
        } catch (IOException e){
        }
    }
}
