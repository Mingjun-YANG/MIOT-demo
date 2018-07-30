package com.xiaomi.iot.example.miot.request;

import org.json.JSONArray;

public class MiotGetStatusRequest extends MiotRequest {

    private JSONArray devices;

    public JSONArray getDevices() {
        return devices;
    }

    public void setDevices(JSONArray devices) {
        this.devices = devices;
    }
}
