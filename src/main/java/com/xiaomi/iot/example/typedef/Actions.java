package com.xiaomi.iot.example.typedef;

import org.json.JSONArray;

public class Actions {

    private int iid;

    private String type;

    private String description;

    private JSONArray in;

    private JSONArray out;

    public int getIid() {
        return iid;
    }

    public void setIid(int iid) {
        this.iid = iid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JSONArray getIn() {
        return in;
    }

    public void setIn(JSONArray in) {
        this.in = in;
    }

    public JSONArray getOut() {
        return out;
    }

    public void setOut(JSONArray out) {
        this.out = out;
    }
}
