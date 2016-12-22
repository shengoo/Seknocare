package sally.seknocare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {
    private final static int REQUEST_CONNECT_DEVICE = 1;

    private Button btnBluetooth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBluetooth = (Button) findViewById(R.id.bluetooth);
        btnBluetooth.setOnClickListener((View.OnClickListener) this);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
//            if (_socket == null && v.getId() != R.id.bluetooth) {
//                Toast.makeText(getApplicationContext(), R.string.connect_reminder, Toast.LENGTH_SHORT).show();
//                return;
//            }
        switch (v.getId()) {
//                case R.id.press: //0xC1
//                    //msg = Toast.makeText(getApplicationContext(), R.string.press_cn, Toast.LENGTH_SHORT);
//                    //msg.show();
//                    sendMessage(0xC1);
//                    ShowContent.Instance().setMode("PRESS");
//                    ShowContent.Instance().setStrang(0);
//                    MainActivity.textViewMode.setText(ShowContent.Instance().getShowContent());
//                    break;
//                case R.id.nip: //0xA8
//                    sendMessage(0xA8);
//                    ShowContent.Instance().setMode("NIP");
//                    ShowContent.Instance().setStrang(0);
//                    MainActivity.textViewMode.setText(ShowContent.Instance().getShowContent());
//                    break;
//                case R.id.prick: //0xA2
//                    sendMessage(0xA2);
//                    ShowContent.Instance().setMode("PRICK");
//                    ShowContent.Instance().setStrang(0);
//                    MainActivity.textViewMode.setText(ShowContent.Instance().getShowContent());
//                    break;
//                case R.id.rap: //0xA7
//                    sendMessage(0xA7);
//                    ShowContent.Instance().setMode("RAP");
//                    ShowContent.Instance().setStrang(0);
//                    MainActivity.textViewMode.setText(ShowContent.Instance().getShowContent());
//                    break;
//                case R.id.stroke: //0xA3
//                    sendMessage(0xA3);
//                    ShowContent.Instance().setMode("STROKE");
//                    ShowContent.Instance().setStrang(0);
//                    MainActivity.textViewMode.setText(ShowContent.Instance().getShowContent());
//                    break;
//                case R.id.flutter: //0xA6
//                    sendMessage(0xA6);
//                    ShowContent.Instance().setMode("FLUTTER");
//                    ShowContent.Instance().setStrang(0);
//                    MainActivity.textViewMode.setText(ShowContent.Instance().getShowContent());
//                    break;
//                case R.id.scrape: //0xA4
//                    sendMessage(0xA4);
//                    ShowContent.Instance().setMode("SCRAPE");
//                    ShowContent.Instance().setStrang(0);
//                    MainActivity.textViewMode.setText(ShowContent.Instance().getShowContent());
//                    break;
//                case R.id.pinch: //0xA5
//                    sendMessage(0xA5);
//                    ShowContent.Instance().setMode("PINCH");
//                    ShowContent.Instance().setStrang(0);
//                    MainActivity.textViewMode.setText(ShowContent.Instance().getShowContent());
//                    break;
//                case R.id.auto: //0xC0
//                    sendMessage(0xC0);
//                    ShowContent.Instance().setMode("AUTO");
//                    ShowContent.Instance().setStrang(0);
//                    MainActivity.textViewMode.setText(ShowContent.Instance().getShowContent());
//                    break;
//                case R.id.timer:
//                    TimePickerDialog timeDialog = new TimePickerDialog(this);
//                    timeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    timeDialog.show();
//                    break;
//                case R.id.increase:
//                    if (ShowContent.Instance().getTime().equals("0:0")) break;
//                    if (ShowContent.Instance().getStrang() == 10) break;
//
//                    if (ShowContent.Instance().getStrang() >= 5) {
//                        RemainderDialog remainderDialog = new RemainderDialog(this);
//                        remainderDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                        remainderDialog.show();
//                        break;
//                    }
//
//                    sendMessage(0xF2 + ShowContent.Instance().getStrang());
//
//                    ShowContent.Instance().setStrang(ShowContent.Instance().getStrang() + 1);
//                    MainActivity.textViewMode.setText(ShowContent.Instance().getShowContent());
//                    break;
//                case R.id.turnoff:
//                    ShowContent.Instance().setTime("0:0");
//                    ShowContent.Instance().setStrang(0);
//
//                    sendMessage(0xB2);
//                    MainActivity.textViewMode.setText(ShowContent.Instance().getShowContent());
//
//                    finish();
//                    break;
//                case R.id.decrease:
//                    if (ShowContent.Instance().getTime().equals("0:0")) break;
//                    if (ShowContent.Instance().getStrang() == 0) break;
//                    sendMessage(0xF1 + ShowContent.Instance().getStrang() - 1);
//
//                    ShowContent.Instance().setStrang(ShowContent.Instance().getStrang() - 1);
//                    MainActivity.textViewMode.setText(ShowContent.Instance().getShowContent());
//                    break;
            case R.id.bluetooth:
//                    if(_bluetooth.isEnabled()==false){  //如果蓝牙服务不可用则提示
//                        Toast.makeText(this, " Bluetooth Opening...", Toast.LENGTH_LONG).show();
//                        return;
//                    }
//                    if(_socket==null){
                Intent serverIntent = new Intent(this, DeviceListActivity.class); //跳转程序设置
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);  //设置返回宏定义
//                    }
//                    else{
//                        Toast.makeText(this, " Bluetooth Connected", Toast.LENGTH_LONG).show();
//                        break;
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
//                    break;


    }
}
