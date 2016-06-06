package com.example.administrator.netactivity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.administrator.netactivity.NetWork.NetControl;
import com.example.administrator.netactivity.NetWork.NetRequest;
import com.example.administrator.netactivity.NetWork.NetResponse;
import com.example.administrator.netactivity.NetWork.testNetWork.MyNetRequest;

public class MainActivity extends AppCompatActivity {
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
    }


    public void requestData(View view) {
        new MyNetRequest()
                .action("app/upgrade")
                .paramKVs("whl","whl")
                .listener(new NetControl.Listener() {
            @Override
            public void onComplete(NetRequest req, NetResponse resp) throws Exception {
                Log.d("whl",resp.respJSON.toString());
            }
        }).build().start();
    }
}
