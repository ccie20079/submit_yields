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
import androidx.annotation.Nullable;

import com.learning.gson.V_Emp_Name;
import com.learning.utils.BaseActivity;
import com.learning.utils.DateHelper;
import com.learning.utils.HttpUtil;
import com.learning.utils.LogUtil;
import com.learning.utils.MyApplication;
import com.learning.utils.Utility;

import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SubmitYieldsActivity extends BaseActivity {
    private static final String TAG = "SubmitYieldsActivity";
    private static final int REQUEST_TO_GET_LINE_NAME = 1;
    private static final int REQUEST_TO_GET_TEAM_NAME = 2;
    private static final int REQUEST_TO_GET_EMP_NAME_WITH_PN_LINE = 3;
    private static final int REQUEST_TO_GET_EMP_NAME_WITH_PN = 4;
    private static final int REQUEST_TO_CHOOSE_PN = 5;
    private static final int REQUEST_GET_ALL_EMPS_L3M = 6;

    private EditText editTextOfDatePicker = null;
    private EditText editTextOfLineName = null;
    private EditText editTextOfTeamName = null;
    private EditText editTextOfEmpName = null;
    //工序名
    private EditText editTextOfSpecificProcess = null;

    private Button btnSubmit = null;
    private Button btnToChoosePN = null;
    //工单标题
    private TextView textViewOfProductsName = null;
    List<V_Emp_Name> v_emp_name_of_pn_line_list = null;
    List<V_Emp_Name> v_emp_name_of_pn_list = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_yields);
        //设定日期选择事件
        this.editTextOfDatePicker = (EditText)findViewById(R.id.editTextOfDatePicker);
        this.editTextOfLineName = (EditText)super.findViewById(R.id.editTextOfLineName);
        this.editTextOfTeamName = (EditText)super.findViewById(R.id.editTextOfTeamName);
        this.editTextOfEmpName = (EditText)super.findViewById(R.id.editTextOfEmpName);
        this.editTextOfSpecificProcess = (EditText)super.findViewById(R.id.editTextOfSpecificProcess);
        this.btnSubmit = (Button)super.findViewById(R.id.btnSubmitYields);
        this.btnToChoosePN = (Button) super.findViewById(R.id.btnToChoosePNInfo);




        //注册监听器
        editTextOfTeamName.setOnClickListener(new EditTextOfTeamName_onClickListenerImpl());
        editTextOfEmpName.setOnClickListener(new EditTextOfEmpName_OnClickListenerImpl());
        this.editTextOfLineName.setOnClickListener(new editTextOfLineName_OnClickListenerImpl());
        this.editTextOfDatePicker.setOnTouchListener(new editTextOfDatePicker_onTouchListenerImpl());
        this.editTextOfDatePicker.setOnFocusChangeListener(new editTextOfDatePicker_onFocusChangeListenerImpl());
        this.btnToChoosePN.setOnClickListener(new BtnToChoosePN_OnClickViewListenerImpl());

        //设置工单
        textViewOfProductsName.setText(getIntent().getStringExtra("products_name"));
        //设置日期
        this.editTextOfDatePicker.setText(DateHelper.getDateOfToday());
        /**
         * 获取该产品下的所有员工，当线体下没有员工日产量记录时，以作备用。
         */
        getAllEmpInfosWithPN(textViewOfProductsName.getText().toString());
        lostFocus();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }
    /**
     * 处理返回的信息。
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
                    /**
                     * 准备该线体，该产品的最近的日报表，以提供员工 产量上传之座次。
                     */
                    getAllEmpInfosWithPN_LineName(textViewOfProductsName.getText().toString(),
                            editTextOfLineName.getText().toString());
                    //焦点移动至班组框
                    this.editTextOfTeamName.requestFocus();
                }
                break;
                //获取班组
            case REQUEST_TO_GET_TEAM_NAME:
                if(resultCode==RESULT_OK){
                    String returnedTeamName = data.getStringExtra("team_name");
                    LogUtil.d(TAG,returnedTeamName);
                    this.editTextOfTeamName.setText(returnedTeamName);
                    //焦点移动
                    this.editTextOfEmpName.requestFocus();
                }
                break;
                /**
                 * 获取线体下的员工
                 */
            case REQUEST_TO_GET_EMP_NAME_WITH_PN_LINE:
                if(resultCode==RESULT_OK){
                    String returnedEmpName = data.getStringExtra("emp_name");
                    LogUtil.d(TAG,returnedEmpName);
                    this.editTextOfEmpName.setText(returnedEmpName);
                }
                break;
            case REQUEST_TO_GET_EMP_NAME_WITH_PN:
                if(resultCode==RESULT_OK){
                    String returnedEmpName = data.getStringExtra("emp_name");
                    LogUtil.d(TAG,returnedEmpName);
                    this.editTextOfEmpName.setText(returnedEmpName);
                }
                break;
            case  REQUEST_TO_CHOOSE_PN:
                if(resultCode==RESULT_OK){
                    String returnedPN = data.getStringExtra("products_name");
                    LogUtil.d(TAG,returnedPN);
                    this.textViewOfProductsName.setText(returnedPN);
                }
                break;
            case REQUEST_GET_ALL_EMPS_L3M:
                if(resultCode==RESULT_OK){
                    String returnedEmpName = data.getStringExtra("emp_name");
                    LogUtil.d(TAG,returnedEmpName);
                    this.editTextOfEmpName.setText(returnedEmpName);
                }
                break;
            default:
                break;
        }
    }

    private class editTextOfDatePicker_onTouchListenerImpl implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction()==MotionEvent.ACTION_DOWN){
                showDatePickerDialog();
                //焦点跳转至线体选择框
                SubmitYieldsActivity.this.editTextOfLineName.requestFocus();
                return true;
            }
            return false;
        }
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
     * 显示日期选择框
     */
    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(SubmitYieldsActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                SubmitYieldsActivity.this.editTextOfDatePicker.setText(year + "-" + (month +1)+"-" +dayOfMonth);
            }
        },calendar.get(Calendar.YEAR),calendar.get(calendar.MONTH),calendar.get(calendar.DAY_OF_MONTH));
        datePickerDialog.show();
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
        this.editTextOfLineName.requestFocus();
        this.editTextOfLineName.setShowSoftInputOnFocus(false);
        this.editTextOfEmpName.setShowSoftInputOnFocus(false);
        this.editTextOfTeamName.setShowSoftInputOnFocus(false);
        this.editTextOfDatePicker.setShowSoftInputOnFocus(false);
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
        public void onClick(View view) {
            //先判断 依据线体是否已经获得了员工信息
            if(v_emp_name_of_pn_line_list!=null && v_emp_name_of_pn_line_list.size()!=0){
                Intent intent = new Intent(MyApplication.getContext(),ShowEmpNameOfPNAndLineActivity.class);
                intent.putExtra("emp_name_of_pn_line_serializable",(Serializable)v_emp_name_of_pn_line_list);
                startActivityForResult(intent,REQUEST_TO_GET_EMP_NAME_WITH_PN_LINE);
                return;
            }
            if(v_emp_name_of_pn_list!=null && v_emp_name_of_pn_list.size()!=0){
                Intent intent = new Intent(MyApplication.getContext(),ShowEmpNameOfPNActivity.class);
                intent.putExtra("emp_name_of_pn_serializable",(Serializable)v_emp_name_of_pn_list);
                startActivityForResult(intent,REQUEST_TO_GET_EMP_NAME_WITH_PN);
                return;
            }
            //进入全部员工信息获取页面
            Intent intent = new Intent(SubmitYieldsActivity.this,ShowAllEmpNamesActivity.class);
            startActivityForResult(intent,REQUEST_GET_ALL_EMPS_L3M);
        }
    }

    /**
     * 获取该工单，该线体下 最近一天日报表中的员工名单
     * @param products_name
     * @param line_name
     */
    private void getAllEmpInfosWithPN_LineName(final String products_name, final String line_name) {

        Map<String,String> map = new HashMap<String,String>();
        map.put("products_name",products_name);
        map.put("line_name",line_name);
        HttpUtil.sendOKHttpRequestWithPostMethod(getString(R.string.urlOfGetAllEmpInfosWithPN_LineName), map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.d("SubmitYieldsActivity: ",e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                v_emp_name_of_pn_line_list = Utility.getAllEmpInfosRecentByCreatedTimeDesc(responseText);
            }
        });
    }

    /**
     * 获取该工单下  所有最近一天的日报表员工 记录提交记录。
     * @param products_name
     */
    private void getAllEmpInfosWithPN(final String products_name) {

        Map<String,String> map = new HashMap<String,String>();
        map.put("products_name",products_name);
        HttpUtil.sendOKHttpRequestWithPostMethod(getString(R.string.urlOfGetAllEmpInfosWithPN), map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.d("SubmitYieldsActivity: ",e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                v_emp_name_of_pn_list = Utility.getAllEmpInfosRecentByCreatedTimeDesc(responseText);
            }
        });
    }

    /**
     * 回到选择工单Activity
     */
    private class BtnToChoosePN_OnClickViewListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(SubmitYieldsActivity.this,ChooseProductNameMainActivity.class);
            startActivityForResult(intent,REQUEST_TO_CHOOSE_PN);
        }
    }
}
