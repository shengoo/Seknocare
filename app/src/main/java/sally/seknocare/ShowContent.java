package sally.seknocare;

/**
 * Created by UC206612 on 2016/12/30.
 */
public class ShowContent {

    private String Mode;
    private int Power;
    private int Strang;
    private String Time;
    private int Hour;
    private int Minute;
    private boolean BluetoothState;

    private static ShowContent _Instance = null;

    private ShowContent() {
        Time = "0:0";
        Power = 0;
        Mode = "PRESS";
        Strang = 0;
        Hour = 0;
        Minute = 0;
        BluetoothState = false;
    }

    public int getHour() {
        return Hour;
    }

    public void setHour(int hour) {
        Hour = hour;
    }

    public int getMinute() {
        return Minute;
    }

    public void setMinute(int minute) {
        Minute = minute;
    }

    public static ShowContent Instance() {
        if (_Instance == null) {
            _Instance = new ShowContent();
        }
        return _Instance;
    }

    public String getShowContent() {
        return Time + "   " + Mode + ":" + Strang + "   Power:" + Power + "%   " + (BluetoothState ? "Connect" : "Disconnect");
    }


    public boolean isBluetoothState() {
        return BluetoothState;
    }

    public void setBluetoothState(boolean bluetoothState) {
        BluetoothState = bluetoothState;
    }

    public String getMode() {
        return Mode;
    }
    public void setMode(String mode) {
        Mode = mode;
    }
    public int getPower() {
        return Power;
    }
    public void setPower(int power) {
        Power = power;
    }
    public int getStrang() {
        return Strang;
    }
    public void setStrang(int strang) {
        Strang = strang;
    }
    public String getTime() {
        return Time;
    }
    public void setTime(String time) {
        Time = time;
    }
}
