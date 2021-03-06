package com.learning.yieldssubmit200526;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.learning.gson.V_Products_Info_Recent;
import com.learning.utils.BaseActivity;
import com.learning.utils.HttpUtil;
import com.learning.utils.LogUtil;
import com.learning.utils.Utility;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChooseProductNameMainActivity extends BaseActivity {
    private RecyclerView recyclerViewOfProducts = null;
    private List<V_Products_Info_Recent> v_products_info_recentList= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_product_name_main);
        recyclerViewOfProducts = (RecyclerView)super.findViewById(R.id.recyclerViewOfProducts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewOfProducts.setLayoutManager(layoutManager);
        //加载数据。
        getDataToList();
    }

    private void getDataToList() {
        HttpUtil.sendOKHttpRequest(getString(R.string.urlOfGetAllProductInfosRecent), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.d("ChooseProductNameMainActivity",e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                v_products_info_recentList = Utility.getAllProductInfosRecentOrderByCreatedTimeDesc(responseData);
                LogUtil.d("ChooseProductNameMainActivity",v_products_info_recentList.toString());
                //更新UI
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ProductInfosRecentAdapter adapter = new ProductInfosRecentAdapter(v_products_info_recentList);
                        recyclerViewOfProducts.setAdapter(adapter);
                    }
                });
            }
        });

    }
}