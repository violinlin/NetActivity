package com.example.administrator.netactivity.NetWork;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lizeng on 16/2/17.
 */
public class NetResponse {

    /**
     * 返回状态值
     */
    public static final int STAUTS_OK = 200;
    public static final int STAUTS_DATA_ERROR = 1000;   //返回数据错误

    public NetRequest netRequest;

    public String respOrigin;   //原始返回数据
    public String respFormat;   //解密后的原始数据
    public int statusCode;
    public JSONObject respJSON;

    protected void build() {
        if (!TextUtils.isEmpty(respOrigin)) {
            try {
                if (netRequest.secretFunc != null) {
                    respFormat = netRequest.secretFunc.dencrypt(respOrigin);
                } else {
                    respFormat = respOrigin;
                }
                respJSON = str2json(respFormat);
            } catch (JSONException e) {
                statusCode = STAUTS_DATA_ERROR;
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断是否XML格式
     *
     * @param content
     * @return
     */
    public static boolean isXml(String content) {
        return content.getBytes()[0] == '<';
    }

    public static JSONObject str2json(String str) throws JSONException {

        return isXml(str) ? XML2JSON(str) : new JSONObject(str);
    }

    private static JSONObject XML2JSON(String str) throws JSONException {
//        XML TO JSONObject
        return new JSONObject(str);
    }
}
