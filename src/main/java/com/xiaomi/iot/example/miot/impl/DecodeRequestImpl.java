package com.xiaomi.iot.example.miot.impl;

import com.xiaomi.iot.example.miot.DecodeRequest;
import com.xiaomi.iot.example.miot.request.*;
import com.xiaomi.iot.example.typedef.ActionRequest;
import com.xiaomi.iot.example.typedef.PropertyRequest;
import com.xiaomi.iot.example.typedef.StatusRequest;
import com.xiaomi.iot.example.typedef.SubscribeRequest;

import java.util.ArrayList;
import java.util.List;

public class DecodeRequestImpl implements DecodeRequest {

    public List<PropertyRequest> decodeGetPropertyRequest(MiotGetPropertiesRequest req) {
        List<PropertyRequest> list = new ArrayList<>();
        for (int i = 0; i < req.getProperties().length(); i++) {
            PropertyRequest propertyRequest = new PropertyRequest();
            propertyRequest.setDid(req.getProperties().optJSONObject(i).optString("did"));
            propertyRequest.setSiid(req.getProperties().optJSONObject(i).optInt("siid"));
            propertyRequest.setPiid(req.getProperties().optJSONObject(i).optInt("piid"));
            list.add(propertyRequest);
        }
        return list;
    }

    public List<PropertyRequest> decodeSetPropertyRequest(MiotSetPropertiesRequest req) {
        List<PropertyRequest> list = new ArrayList<>();
        for (int i = 0; i < req.getProperties().length(); i++) {
            PropertyRequest propertyRequest = new PropertyRequest();
            propertyRequest.setDid(req.getProperties().optJSONObject(i).optString("did"));
            propertyRequest.setSiid(req.getProperties().optJSONObject(i).optInt("siid"));
            propertyRequest.setPiid(req.getProperties().optJSONObject(i).optInt("piid"));
            propertyRequest.setValue(req.getProperties().optJSONObject(i).opt("value"));
            list.add(propertyRequest);
        }
        return list;
    }

    public List<ActionRequest> decodeActionRequest(MiotActionRequest req) {
        List<ActionRequest> list = new ArrayList<>();
        for (int i = 0; i < req.getAction().length(); i++) {
            ActionRequest actionRequest = new ActionRequest();
            actionRequest.setDid(req.getAction().optJSONObject(i).optString("did"));
            actionRequest.setSiid(req.getAction().optJSONObject(i).optInt("siid"));
            actionRequest.setAiid(req.getAction().optJSONObject(i).optInt("aiid"));
            actionRequest.setIn(req.getAction().optJSONObject(i).optJSONArray("in"));
            list.add(actionRequest);
        }
        return list;
    }


    public List<SubscribeRequest> decodeSubscribeRequest(MiotSubscribeRequest req) {
        List<SubscribeRequest> list = new ArrayList<>();
        for (int i = 0; i < req.getDevices().length(); i++) {
            SubscribeRequest subscribeRequest = new SubscribeRequest();
            subscribeRequest.setDid(req.getDevices().optJSONObject(i).optString("did"));
            subscribeRequest.setSubscriptionId(req.getDevices().optJSONObject(i).optString("subscriptionId"));
            list.add(subscribeRequest);
        }
        return list;
    }

    public List<StatusRequest> decodeStatusRequest(MiotGetStatusRequest req) {
        List<StatusRequest> list = new ArrayList<>();
        for (int i = 0; i < req.getDevices().length(); i++) {
            StatusRequest statusRequest = new StatusRequest();
            statusRequest.setDevice(req.getDevices().optString(i));
            list.add(statusRequest);
        }
        return list;
    }

}
