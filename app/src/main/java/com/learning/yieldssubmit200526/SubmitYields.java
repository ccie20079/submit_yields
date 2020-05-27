package com.learning.yieldssubmit200526;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SubmitYields extends AppCompatActivity  {
    private EditText editName = null;
    private static final int REQUEST_CHOOSE_NAME_OF_THE_EMP = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_yields);
        this.editName = (EditText)super.findViewById(R.id.editName);
//        this.editName.setOnFocusChangeListener(new  editName_OnfocusChangeListenerImpl());
        this.editName.setOnClickListener(new editName_OnClickListenerImpl());
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
}
