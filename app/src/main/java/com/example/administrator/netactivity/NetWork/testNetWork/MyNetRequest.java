package com.example.administrator.netactivity.NetWork.testNetWork;

import com.example.administrator.netactivity.NetWork.NetControl;
import com.example.administrator.netactivity.NetWork.NetRequest;
import com.example.administrator.netactivity.NetWork.ParamControl;

import org.json.JSONObject;

/**
 * @author wanghuilin
 * @time 2016/6/6  15:27
 */
public class MyNetRequest extends NetRequest {
    private String Host = "http://192.168.1.25:8080/fwmApi/";

    public MyNetRequest() {
        super.reqMethod(REQ_METHOD_GET);
        super.host(Host);
//        super.netResponse(new );

    }

    @Override
    public NetControl build() {
        super.action(action + ".json");
        String bodyJSON = ParamControl.buildParam(paramType, paramKVs);

        JSONObject headJson = new JSONObject();
//        headJson.put("versionCode", ApplicationUtil.getVersionCode(MediaApplication.appContext));
//        headJson.put("userToken", PersonalUtil.getUserToken(MediaApplication.appContext));
//        headJson.put("deviceUUID", Util.getUUDI());

        paramType(ParamControl.ParamType.NULL);
        super.paramKVs("{\"header\":" + headJson.toString() + ",\"body\":" + bodyJSON + "}");

        return super.build();
    }
}
