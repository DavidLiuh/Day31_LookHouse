package com.qf.day31_lookhouse.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qf.day31_lookhouse.model.CitiyEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 解析JSON工具类
 */
public class JsonUtil {

    private static String[] labels = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    /**
     * 解析城市列表JSON，得到城市实体对象集合
     *
     * CitiyEntity：cityname = A, type = 0
     * CitiyEntity：cityname = 鞍山， type = 1
     * CitiyEntity：cityname = 安阳， type = 1
     * CitiyEntity：cityname = B, type = 0
     * CitiyEntity：cityname = 北京， type = 1
     * CitiyEntity：cityname = 北海， type = 1
     * ...
     *
     * @param json
     * @return
     */
    public static List<CitiyEntity> getCitysByJSON(String json){
        List<CitiyEntity> datas = null;
        if(json != null){
            datas = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(json);
                int code = jsonObject.getInt("retcode");//得到结果返回码
                if(code == 0){//表示获取结果成功
                    jsonObject = jsonObject.getJSONObject("cities");
                    for(int i = 0; i < labels.length; i++){
                        CitiyEntity citiyEntity = new CitiyEntity(labels[i], 0);//手动创建一个标签对象，加入集合中
                        datas.add(citiyEntity);


                        JSONArray jsonArray = jsonObject.optJSONArray(labels[i]);//解析字母标签
                        if(jsonArray != null){
                            TypeToken<List<CitiyEntity>> typeToken = new TypeToken<List<CitiyEntity>>(){};
                            List<CitiyEntity> cityEntitys = new Gson().fromJson(jsonArray.toString(), typeToken.getType());

                            datas.addAll(cityEntitys);//把该标签下的城市列表加入总的城市列表中
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return datas;
    }
}
