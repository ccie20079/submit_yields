package com.learning.yieldssubmit200526;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.learning.gson.V_Emp_Name;
import com.learning.gson.V_Team_Info;
import com.learning.utils.BaseActivity;
import com.learning.utils.HttpUtil;
import com.learning.utils.LogUtil;
import com.learning.utils.Utility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ShowAllEmpNameOrderByCreatedTimeActivity extends BaseActivity {
    private ListView listViewOfEmpNameInfo = null;
    private List<V_Emp_Name> v_emp_nameList = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_emp_name_order_by_created_time);
        this.listViewOfEmpNameInfo = (ListView)super.findViewById(R.id.listViewOfEmpNameInfo);
        //加载昨天日报表中的员工信息
        getAllEmpInfosOfYesterdayByCreatedTimeDesc();
        //选择员工信息。
        this.listViewOfEmpNameInfo.setOnItemClickListener(new listViewOfEmpNameInfos_OnClickListenerImpl());
    }

    private void getAllEmpInfosOfYesterdayByCreatedTimeDesc()  {
        //获取数据，数据类型ArrayList<Map<String,String>>
        HttpUtil.sendOKHttpRequest(getString(R.string.urlOfGetAllEmpInfosOfYesterday), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.d("SubmitYields",e.toString());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                v_emp_nameList = Utility.getAllEmpInfosOfYesterdayOrderByCreatedTimeDesc(responseText);
                LogUtil.d("SubmitYields",v_emp_nameList.toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                        //跟新listView
                        ShowAllEmpNameOrderByCreatedTimeActivity.EmpNameAdapter empNameAdapter =
                                new ShowAllEmpNameOrderByCreatedTimeActivity.EmpNameAdapter(ShowAllEmpNameOrderByCreatedTimeActivity.this,R.layout.emp_name_item,v_emp_nameList);
                        listViewOfEmpNameInfo.setAdapter(empNameAdapter);
                    }
                });
            }
        });
    }


    private class EmpNameAdapter extends ArrayAdapter<V_Emp_Name> {
        private int resourceId; //listView子项布局的ID
        public EmpNameAdapter(@NonNull Context context, int xmlItemResourceId, @NonNull List<V_Emp_Name> objects) {
            super(context,xmlItemResourceId,objects);
            resourceId = xmlItemResourceId;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //获取当前项的V_Line_Info实例
            V_Emp_Name v_emp_name = getItem(position);
            View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            TextView textViewOfReportTeamNameAndMonitor = (TextView)view.findViewById(R.id.report_team_name_and_monitor);
            TextView textViewOfEmpName = (TextView)view.findViewById(R.id.emp_name);
            textViewOfReportTeamNameAndMonitor.setText(v_emp_name.getReportTeamNameAndMonitor());
            textViewOfEmpName.setText(v_emp_name.getEmp_name());
            return view;
        }
    }
    /**
     * 选择员工信息后返回给主界面。
     */
    private class listViewOfEmpNameInfos_OnClickListenerImpl implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            V_Emp_Name v_emp_name = v_emp_nameList==null?null:v_emp_nameList.get(position);
            Intent intent = new Intent();
            intent.putExtra("emp_name",v_emp_name==null?"":v_emp_name.getEmp_name());
            setResult(RESULT_OK,intent);
            finish();
        }
    }
}
