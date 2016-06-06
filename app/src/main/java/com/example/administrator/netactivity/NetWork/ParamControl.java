package com.example.administrator.netactivity.NetWork;

import org.json.JSONObject;

import java.net.URLEncoder;

/**
 * Created by lizeng on 16/4/8.
 */
public class ParamControl {

    public enum ParamType {
        JSON, XML, MAP, NULL
    }

    public static String buildParam(ParamType paramType, Object[] paramKVs) {
        String param = null;
        switch (paramType) {
            case JSON:
                param = buildJSONParam(paramKVs).toString();
                break;
            case XML:
                param = buildXMLParam(paramKVs);
                break;
            case MAP:
                param = buildMAPParam(paramKVs);
                break;
            case NULL:
                param = String.valueOf(paramKVs[0]);
                break;
        }
        return param;
    }

    public static String buildMAPParam(Object... kvs) {
        String prestr = "";
        if (kvs != null) {
            try {
                for (int i = 0; i < kvs.length - 1; i += 2) {
                    String key = URLEncoder.encode((String) kvs[i], "UTF-8");
                    String value = URLEncoder.encode((String) kvs[i + 1], "UTF-8");

                    if (i == 0) {
                        prestr += (key + "=" + value);
                    } else {
                        prestr += ("&" + key + "=" + value);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return prestr;
    }

    public static JSONObject buildJSONParam(Object... kvs) {
        JSONObject paramJson = new JSONObject();
        if (kvs != null) {
            for (int i = 0; i < kvs.length - 1; i += 2) {
                try {
                    paramJson.put(kvs[i].toString(), kvs[i + 1]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return paramJson;
    }

    public static String buildXMLParam(Object... kvs) {
        StringBuffer xmlParam = new StringBuffer();
        if (kvs != null) {
            xmlParam.append("<request>");

            String[] kv;
            String element, elementAttr, elementText;
            for (int i = 0; i < kvs.length - 1; i += 2) {
                kv = kvs[i].toString().split(" ", 2);

                element = kv[0];
                elementAttr = kv.length >= 2 ? " " + kv[1] : "";
                elementText = kvs[i + 1].toString();

                xmlParam.append("<").append(element).append(elementAttr).append(">");
                xmlParam.append(elementText);
                xmlParam.append("</").append(element).append(">");

            }

            xmlParam.append("</request>");
        }
        return xmlParam.toString();
    }
}
