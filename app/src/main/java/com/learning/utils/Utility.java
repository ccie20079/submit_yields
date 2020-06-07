package com.learning.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.learning.gson.V_Emp_Name;
import com.learning.gson.V_Line_Info;
import com.learning.gson.V_Team_Info;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.GenericSignatureFormatError;
import java.util.List;

public class Utility {
//    /**
//     * 解析和处理服务器返回的省级数据
//     * @param response
//     * @return
//     */
//    public static boolean handleProvinceResponse(String response){
//        if(!TextUtils.isEmpty(response)){
//            try {
//                JSONArray allProvinces = new JSONArray(response);
//                for(int i = 0;i<allProvinces.length();i++){
//                    JSONObject provinceObject = allProvinces.getJSONObject(i);
//                    Province province = new Province();
//                    province.setProvinceName(provinceObject.getString("name"));
//                    province.setProvinceCode(provinceObject.getInt("id"));
//                    province.save();        //保存于本地
//                }
//                return true;
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        return false;
//    }
//
//    /**
//     *
//     * @param response
//     * @param provinceCode
//     * @return
//     */
//    public static boolean handleCityResponse(String response,int provinceCode){
//        if(!TextUtils.isEmpty(response)){
//            try {
//                JSONArray allCities = new JSONArray(response);
//                for(int i = 0;i<allCities.length();i++){
//                    JSONObject cityObject = allCities.getJSONObject(i);
//                    City city = new City();
//                    city.setCityCode(cityObject.getInt("id"));
//                    city.setCityName(cityObject.getString("name"));
//                    city.setProvinceCode(provinceCode);
//                    city.save();
//
//                }
//                return true;
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        return false;
//    }
//    public static boolean handleCountyResponse(String response,int cityCode){
//        if(TextUtils.isEmpty(response)) return false;
//        try{
//            JSONArray allCounties = new JSONArray(response);
//            for(int i = 0;i<allCounties.length();i++){
//                JSONObject countyObject = allCounties.getJSONObject(i);
//                County county = new County();
//                county.setCityCode(cityCode);
//                county.setCountyName(countyObject.getString("name"));
//                county.setWeatherId(countyObject.getString("weather_id"));
//                county.save();
//            }
//            return true;
//        }catch (JSONException e){
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    /**
//     * 将返回的JSON数据解析成Weather实体类
//     * @param response
//     * @return
//     */
//    public static Weather handleWeatherResponse(String response){
//        try{
//            JSONObject jsonObject = new JSONObject(response);
//            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
//            String weatherContent = jsonArray.getJSONObject(0).toString();
//            return new Gson().fromJson(weatherContent,Weather.class);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
    public static List<V_Line_Info> getAllLinesInfoOrderByCreatedTime(String jsonData){
        Gson gson = new Gson();
        List<V_Line_Info> v_line_infoList =gson.fromJson(jsonData,new TypeToken<List<V_Line_Info>>(){}.getType());
        return v_line_infoList;
    }
    public static List<V_Team_Info> getAllTeamsInfoOrderByCreatedTime(String jsonData){
        Gson gson = new Gson();
        //有可能会返回空值
        List<V_Team_Info> v_team_infoList =gson.fromJson(jsonData,new TypeToken<List<V_Team_Info>>(){}.getType());
        return v_team_infoList;
    }

    public static List<V_Emp_Name> getAllEmpInfosOfYesterdayOrderByCreatedTimeDesc(String jsonData) {
       Gson gson = new Gson();
       List<V_Emp_Name> v_emp_nameList = gson.fromJson(jsonData,new TypeToken<List<V_Emp_Name>>(){}.getType());
       return v_emp_nameList;
    }
}
