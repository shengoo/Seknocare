package sally.seknocare;

/**
 * Created by UC206612 on 2016/12/30.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

public class TimePickerDialog extends Dialog implements View.OnClickListener {
    private TimePicker timePicker;
    private Button btnCancel;
    private Button btnSetting;

    private int Hour;
    private int Minute;

    public TimePickerDialog(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public TimePickerDialog(Context context, boolean cancelable,
                            OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        // TODO Auto-generated constructor stub
    }

    public TimePickerDialog(Context context, int theme) {
        super(context, theme);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.timer_picker);

        setCanceledOnTouchOutside(false);

        btnCancel = (Button)findViewById(R.id.cancel);
        btnCancel.setOnClickListener(this);

        btnSetting = (Button)findViewById(R.id.setting);
        btnSetting.setOnClickListener(this);

        timePicker = (TimePicker) findViewById(R.id.tpPicker);
        timePicker.setIs24HourView(true);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch(v.getId()) {
            case R.id.setting:
                Hour = timePicker.getCurrentHour();
                Minute = timePicker.getCurrentMinute();

                ShowContent.Instance().setHour(Hour);
                ShowContent.Instance().setMinute(Minute);
                MainActivity.TimeStart = true;

                ShowContent.Instance().setTime(Hour + ":" + Minute);
                //Toast.makeText(Main, Hour + "时" + Minute + "分", Toast.LENGTH_SHORT);
                MainActivity.textViewMode.setText(ShowContent.Instance().getShowContent());
                this.cancel();
                break;
            case R.id.cancel:
                this.cancel();
                break;
        }
    }

}

