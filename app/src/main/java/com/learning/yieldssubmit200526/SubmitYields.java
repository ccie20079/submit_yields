package com.learning.yieldssubmit200526;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import com.learning.utils.BaseActivity;
import com.learning.utils.DateHelper;
import com.learning.utils.LogUtil;
import com.learning.utils.MyApplication;
import java.util.Calendar;
public class SubmitYields extends BaseActivity {
    private static final String TAG = "SubmitYields";
    private static final int REQUEST_TO_GET_LINE_NAME = 1;
    private static final int REQUEST_TO_GET_TEAM_NAME = 2;
    private static final int REQUEST_TO_GET_EMP_NAME = 3;
    private EditText editTextOfDatePicker = null;
    private EditText editTextOfLineName = null;
    private EditText editTextOfTeamName = null;
    private EditText editTextOfEmpName = null;
    private Button btnSubmit = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(Build.VERSION.SDK_INT>=21){
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
        setContentView(R.layout.activity_submit_yields);
        //设定日期选择事件
        this.editTextOfLineName = (EditText)super.findViewById(R.id.editTextOfLineName);
        this.editTextOfDatePicker = (EditText)findViewById(R.id.editTextOfDatePicker);
        this.editTextOfDatePicker.setOnTouchListener(new editTextOfDatePicker_onTouchListenerImpl());
        this.editTextOfDatePicker.setOnFocusChangeListener(new editTextOfDatePicker_onFocusChangeListenerImpl());
        this.editTextOfDatePicker.setText(DateHelper.getDateOfToday());
        this.editTextOfLineName.setOnClickListener(new editTextOfLineName_OnClickListenerImpl());
        this.editTextOfTeamName = (EditText)super.findViewById(R.id.editTextOfTeamName);
        editTextOfTeamName.setOnClickListener(new EditTextOfTeamName_onClickListenerImpl());
        this.editTextOfEmpName = (EditText)super.findViewById(R.id.editTextOfEmpName);
        editTextOfEmpName.setOnClickListener(new EditTextOfEmpName_OnClickListenerImpl());
        this.btnSubmit = (Button)super.findViewById(R.id.btnSubmitYields);
        lostFocus();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }
    /**
     * 返回选择的姓名
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
                //获取线体
            case REQUEST_TO_GET_LINE_NAME:
                if(resultCode==RESULT_OK){
                    String returnedLineName = data.getStringExtra("line_name");
                    LogUtil.d(TAG,returnedLineName);
                    this.editTextOfLineName.setText(returnedLineName);
                    break;
                }
            case REQUEST_TO_GET_TEAM_NAME:
                if(resultCode==RESULT_OK){
                    String returnedTeamName = data.getStringExtra("team_name");
                    LogUtil.d(TAG,returnedTeamName);
                    this.editTextOfTeamName.setText(returnedTeamName);
                    break;
                }
                //处理员工姓名Activity返回的结果
            case REQUEST_TO_GET_EMP_NAME:
                if(resultCode==RESULT_OK){
                    String returnedEmpName = data.getStringExtra("emp_name");
                    LogUtil.d(TAG,returnedEmpName);
                    this.editTextOfEmpName.setText(returnedEmpName);
                    break;
                }
            default:
                break;
        }
    }

    private class editTextOfDatePicker_onTouchListenerImpl implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction()==MotionEvent.ACTION_DOWN){
                showDatePickerDialog();
                return true;
            }
            return false;
        }
    }

    /**
     * 显示日期选择框
     */
    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(SubmitYields.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                SubmitYields.this.editTextOfDatePicker.setText(year + "-" + (month +1)+"-" +dayOfMonth);
            }
        },calendar.get(Calendar.YEAR),calendar.get(calendar.MONTH),calendar.get(calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    /**
     * 日期选择监听器
     */
    private class editTextOfDatePicker_onFocusChangeListenerImpl implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(hasFocus){
                showDatePickerDialog();
            }
        }
    }

    /**
     * 线体选择监听器
     */
    private class editTextOfLineName_OnClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MyApplication.getContext(),ShowAllLinesInfoOrderByCreatedTimeActivity.class);
            startActivityForResult(intent,REQUEST_TO_GET_LINE_NAME);
        }
    }
    private void lostFocus(){
        this.btnSubmit.requestFocus();
    }

    /**
     * 姓名选择监听器
     */
    private class EditTextOfTeamName_onClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MyApplication.getContext(),ShowAllTeamNameOrderByCreatedTimeActivity.class);
            startActivityForResult(intent,REQUEST_TO_GET_TEAM_NAME);
        }
    }

    /**
     * 转向员工姓名选择页面
     */
    private class EditTextOfEmpName_OnClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MyApplication.getContext(),ShowAllEmpNameOrderByCreatedTimeActivity.class);
            startActivityForResult(intent,REQUEST_TO_GET_EMP_NAME);
        }
    }
}
