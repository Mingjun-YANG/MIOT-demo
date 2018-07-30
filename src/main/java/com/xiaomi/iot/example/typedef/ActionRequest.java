package com.xiaomi.iot.example.typedef;

import org.json.JSONArray;

public class ActionRequest {
    private String did;

    private int siid;

    private int aiid;

    private JSONArray in;

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public int getSiid() {
        return siid;
    }

    public void setSiid(int siid) {
        this.siid = siid;
    }

    public int getAiid() {
        return aiid;
    }

    public void setAiid(int aiid) {
        this.aiid = aiid;
    }

    public JSONArray getIn() {
        return in;
    }

    public void setIn(JSONArray in) {
        this.in = in;
    }
}
