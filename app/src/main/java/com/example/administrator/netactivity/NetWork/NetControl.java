package com.example.administrator.netactivity.NetWork;

import android.util.Log;


public class NetControl {

    private NetRequest netRequest;
    private NetResponse netResponse;

    protected NetControl(NetRequest netRequest, NetResponse netResponse) {
        this.netRequest = netRequest;
        this.netResponse = netResponse;
    }

    public void start() {
        Log.w("whl", netRequest.url);
        Log.w("whl", "param=" + netRequest.paramFormat);
//        ReqManager.getInstance().notifyListener(netRequest.requestTag, ReqManager.ReqStatus.loading);
        VolleyControl.getInstance().post(
                netRequest.reqMethod,
                netRequest.url,
                netRequest.requestTag,
                netRequest.httpHeader,
                netRequest.lastParam,
                new VolleyControl.Listener() {
                    @Override
                    public void onComplete(String response, Exception error) {
                        Log.w("whl", netRequest.action + " code=" + netResponse.statusCode, error);
                        if (netRequest.listener != null) {
                            netResponse.netRequest = netRequest;
                            netResponse.respOrigin = response;
                            netResponse.build();
                            try {
                                netRequest.listener.onComplete(netRequest, netResponse);
                            } catch (Exception e) {
                                netResponse.statusCode = NetResponse.STAUTS_DATA_ERROR;
                                try {
                                    netRequest.listener.onComplete(netRequest, netResponse);
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }
                                e.printStackTrace();
                            }
                        }
//                        ReqManager.getInstance().notifyListener(
//                                netRequest.requestTag,
//                                netResponse.statusCode == NetResponse.STAUTS_OK ?
//                                        ReqManager.ReqStatus.success :
//                                        ReqManager.ReqStatus.error
//                        );
                    }

                    @Override
                    public void onStatusCode(int statusCode) {
                        statusCode = statusCode == 304 ? 200 : statusCode;
                        netResponse.statusCode = statusCode;
                    }
                });
    }

    public interface Listener {
        public void onComplete(NetRequest req, NetResponse resp) throws Exception;
    }

}
