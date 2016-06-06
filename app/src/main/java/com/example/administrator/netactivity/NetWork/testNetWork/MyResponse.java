package com.example.administrator.netactivity.NetWork.testNetWork;

import com.example.administrator.netactivity.NetWork.NetResponse;

/**
 * @author wanghuilin
 * @time 2016/6/6  15:34
 */
public class MyResponse extends NetResponse {
    @Override
    protected void build() {
        super.build();
        if (statusCode == STAUTS_OK) {
            if (respJSON == null || respJSON.optInt("code", 0) != 1) {
                statusCode = STAUTS_DATA_ERROR;
            }
        }
    }
}
