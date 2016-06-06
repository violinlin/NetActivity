package com.example.administrator.netactivity.NetWork;

import android.text.TextUtils;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.netactivity.MainActivity;

import java.util.Map;



public class VolleyControl {

    private static VolleyControl volleyControl;
    private RequestQueue requestQueue;

    private VolleyControl() {
        requestQueue = Volley.newRequestQueue(MainActivity.context);
    }

    protected synchronized static VolleyControl getInstance() {
        if (volleyControl == null) {
            volleyControl = new VolleyControl();
        }
        return volleyControl;
    }

    protected void post(int reqMethod, String url, String reqTag, final Map<String, String> httpHeader, final byte[] param, final Listener listener) {
        if (!TextUtils.isEmpty(reqTag)) {
            requestQueue.cancelAll(reqTag);
        }
        Response.Listener<String> respListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onComplete(response, null);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse == null) {
                    listener.onStatusCode(0);
                } else {
                    listener.onStatusCode(error.networkResponse.statusCode);
                }
                listener.onComplete(null, error);
            }
        };

        StringRequest request = new StringRequest(reqMethod, url, respListener, errorListener) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                return param;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return httpHeader == null ? super.getHeaders() : httpHeader;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                listener.onStatusCode(response.statusCode);
                return super.parseNetworkResponse(response);
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                return super.parseNetworkError(volleyError);
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 2, 1));

        if (!TextUtils.isEmpty(reqTag)) {
            request.setTag(reqTag);
        }
        requestQueue.add(request);
    }

    interface Listener {
        public void onComplete(String response, Exception error);

        public void onStatusCode(int statusCode);
    }
}
