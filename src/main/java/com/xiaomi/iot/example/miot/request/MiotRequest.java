package com.xiaomi.iot.example.miot.request;


import com.xiaomi.iot.example.miot.MiotIntent;

public class MiotRequest {

    private String token;

    private String requestId;

    private String uid;

    private MiotIntent intent;

    public String getToken() {
        return token;
    }

    public String getRequestId() {
        return requestId;
    }

    public MiotIntent getIntent() {
        return intent;
    }

    public void setIntent(MiotIntent intent) {
        this.intent = intent;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
