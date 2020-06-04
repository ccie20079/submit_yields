package com.learning.yieldssubmit200526;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.learning.gson.V_Line_Info;
import com.learning.utils.HttpUtil;
import com.learning.utils.LogUtil;
import com.learning.utils.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ShowAllLinesInfoOrderByCreatedTimeActivity extends AppCompatActivity {
    SimpleAdapter simpleAdapter = null;
    List<V_Line_Info> line_info_list  = null;
    Button btn = null;
    private String result = null;
    private static final String url = "http://192.168.1.113/Show_Products_Cost/GetAllLinesForSubmitYieldsApp!getAllLinesOrderByCreateTimeDesc.action";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_lines_info_order_by_created_time);
        btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取数据，数据类型ArrayList<Map<String,String>>
                HttpUtil.sendOKHttpRequest(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        LogUtil.d("SubmitYields",e.toString());
                        result = e.toString();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ShowAllLinesInfoOrderByCreatedTimeActivity.this,result,Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String responseText = response.body().string();
                        line_info_list = Utility.getAllLinesInfoOrderByCreatedTime(responseText);
                        LogUtil.d("SubmitYields",line_info_list.toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ShowAllLinesInfoOrderByCreatedTimeActivity.this,line_info_list.toString(),Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });


    }
}