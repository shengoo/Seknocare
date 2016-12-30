package sally.seknocare;

/**
 * Created by UC206612 on 2016/12/30.
 */

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RemainderDialog  extends Dialog implements View.OnClickListener {

    private Button btnYes;
    private Button btnNo;

    public RemainderDialog(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.reminder);
        setCanceledOnTouchOutside(false);

        btnYes = (Button)findViewById(R.id.yes);
        btnYes.setOnClickListener(this);

        btnNo = (Button)findViewById(R.id.no);
        btnNo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.yes:
                if (ShowContent.Instance().getStrang() == 10) break;
                MainActivity.sendMessage(0xF2 + ShowContent.Instance().getStrang());

                ShowContent.Instance().setStrang(ShowContent.Instance().getStrang() + 1);
                MainActivity.textViewMode.setText(ShowContent.Instance().getShowContent());

                break;
            case R.id.no:
                break;
        }
        this.cancel();
    }

}
