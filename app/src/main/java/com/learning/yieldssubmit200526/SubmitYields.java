package com.learning.yieldssubmit200526;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.learning.utils.BaseActivity;
import com.learning.utils.DateHelper;
import com.learning.utils.MyApplication;

import java.util.Calendar;

public class SubmitYields extends BaseActivity {
    private EditText editName = null;
    private EditText editTextOfDatePicker = null;
    private Button btnSubmitYields = null;      //提交产量按钮。
    private TextView textViewToShowSubmittedInfo = null;
    private Toolbar toolbar = null;
    private static final int REQUEST_CHOOSE_NAME_OF_THE_EMP = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(Build.VERSION.SDK_INT>=21){
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
        setContentView(R.layout.activity_submit_yields);
        this.editName = (EditText)super.findViewById(R.id.editName);
        //请求焦点
        this.editName.requestFocus();
        btnSubmitYields = (Button)super.findViewById(R.id.btnSubmitYields);
        textViewToShowSubmittedInfo = (TextView)super.findViewById(R.id.textViewToShowSubmittedInfo);
//        this.editName.setOnFocusChangeListener(new  editName_OnfocusChangeListenerImpl());
        this.editName.setOnClickListener(new editName_OnClickListenerImpl());
        this.editTextOfDatePicker = (EditText)findViewById(R.id.editTextOfDatePicker);
        this.editTextOfDatePicker.setOnTouchListener(new editTextOfDatePicker_onTouchListenerImpl());
        this.editTextOfDatePicker.setOnFocusChangeListener(new editTextOfDatePicker_onFocusChangeListenerImpl());
        this.editTextOfDatePicker.setText(DateHelper.getDateOfToday());
        this.btnSubmitYields.setOnClickListener(new submitYields_onClickListenerImpl());
        textViewToShowSubmittedInfo.setOnLongClickListener(new toShowSubmittedInfo_onLongClickListenerImpl());
        toolbar = (Toolbar)super.findViewById(R.id.toolbar);
//        int titleId = Resources.getSystem().getIdentifier("toolbar_title","id","android");
//        TextView titleOfToolBar =(TextView)super.findViewById(titleId);
//        titleOfToolBar.setGravity(Gravity.CENTER);
        this.setSupportActionBar(toolbar);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }
    /**
     *  当点击到文本框时触发。
     */
    private class editName_OnfocusChangeListenerImpl implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(hasFocus){
                Toast.makeText(SubmitYields.this,"EditName: I has focus",Toast.LENGTH_SHORT).show();
            }
        }
    }
    /**
     * 当点击编辑框时，跳转到选择姓名页面
     */
    private class editName_OnClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intentToChooseNameOfEmps = new Intent(SubmitYields.this,ChooseNameOfEmpsActivity.class);
            startActivityForResult(intentToChooseNameOfEmps,REQUEST_CHOOSE_NAME_OF_THE_EMP);
        }
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
            case REQUEST_CHOOSE_NAME_OF_THE_EMP:
                if(resultCode==RESULT_OK){
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

    private class editTextOfDatePicker_onFocusChangeListenerImpl implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(hasFocus){
                showDatePickerDialog();
            }
        }
    }

    /**
     * 提交产量监听器
     */
    private class submitYields_onClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View v) {
//            Toast.makeText(MyApplication.getContext(),"产量已经提交！",Toast.LENGTH_SHORT).show();
            Snackbar.make(v,"已经提交",Snackbar.LENGTH_SHORT).setAction("Undo", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(SubmitYields.this,"Data restored",Toast.LENGTH_SHORT).show();
                }
            }).show();
        }
    }

    private class toShowSubmittedInfo_onLongClickListenerImpl implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View v) {
            Toast.makeText(MyApplication.getContext(),"将转向今日提交详情",Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
