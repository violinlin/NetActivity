package com.example.administrator.netactivity.NetWork;


import com.android.volley.Request;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import framework.org.apache.commons.codec.binary.Hex;
import framework.org.apache.commons.codec.digest.DigestUtils;


/**
 * Created by lizeng on 16/2/15.
 */
public class NetRequest {

    public static final int REQ_METHOD_GET = Request.Method.GET;
    public static final int REQ_METHOD_POST = Request.Method.POST;

    /**
     * 参数类别
     */
    public enum ParamType {
        JSON, XML, MAP, NULL
    }

    /**
     * 最后发给服务器的信息
     */
    public String url;  //访问地址
    public Map<String, String> httpHeader;    //http header数据
    public byte[] lastParam; //最终发给服务端的数据

    public String paramFormat;    //未加密，格式化后的参数

    /**
     * 可配置信息
     */
    public int reqMethod = REQ_METHOD_POST;
    public String host;   //主机地址
    public String action; //接口action
    public ParamControl.ParamType paramType = ParamControl.ParamType.JSON;   //参数格式
    public Object[] paramKVs;   //以键值对格式配置参数
    public String requestTag;   //本次请求标签，用于防止频繁反复访问，默认md5(url+encryptParam)
    public Object userTag;  //调用者携带的参数，网络请求中没有用，方便回调时访问
    public SecretFunc secretFunc;   //加解密方法
    public NetResponse netResponse;   //返回值解析对象
    public NetControl.Listener listener;    //回调监听

    public NetRequest reqMethod(int reqMethod) {
        this.reqMethod = reqMethod;
        return this;
    }

    public NetRequest host(String host) {
        this.host = host;
        return this;
    }

    public NetRequest action(String action) {
        this.action = action;
        return this;
    }

    public NetRequest httpHeader(Map<String, String> httpHeader) {
        this.httpHeader = httpHeader;
        return this;
    }

    public NetRequest paramType(ParamControl.ParamType paramType) {
        this.paramType = paramType;
        return this;
    }

    public NetRequest paramKVs(Object... paramKVs) {
        this.paramKVs = paramKVs;
        return this;
    }

    public NetRequest requestTag(String requestTag) {
        this.requestTag = requestTag;
        return this;
    }

    public NetRequest userTag(Object userTag) {
        this.userTag = userTag;
        return this;
    }

    public NetRequest secretFunc(SecretFunc secretFunc) {
        this.secretFunc = secretFunc;
        return this;
    }

    public NetRequest netResponse(NetResponse netResponse) {
        this.netResponse = netResponse;
        return this;
    }

    public NetRequest listener(NetControl.Listener listener) {
        this.listener = listener;
        return this;
    }

    public NetControl build() {
        url = host + action;

        if (paramKVs != null) {
            paramFormat = ParamControl.buildParam(paramType, paramKVs);
        }
        if (secretFunc != null) {
            lastParam = secretFunc.encrypt(paramFormat);
        } else {
            lastParam = paramFormat.getBytes();
        }

        if (requestTag == null) {
            try {
                if (lastParam != null) {
                    requestTag = new String(Hex.encodeHex(DigestUtils.md5(url + new String(lastParam, "UTF-8"))));
                } else {
                    requestTag = new String(Hex.encodeHex(DigestUtils.md5(url)));
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return new NetControl(this, netResponse == null ? new NetResponse() : netResponse);
    }

    public interface SecretFunc {
        public byte[] encrypt(String param);

        public String dencrypt(String param);
    }
}
