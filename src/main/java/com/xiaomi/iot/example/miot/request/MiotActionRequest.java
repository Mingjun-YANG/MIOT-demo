package com.xiaomi.iot.example.miot.request;

import org.json.JSONArray;

public class MiotActionRequest extends MiotRequest {

    private JSONArray action;


    public JSONArray getAction() {
        return action;
    }

    public void setAction(JSONArray action) {
        this.action = action;
    }
}
