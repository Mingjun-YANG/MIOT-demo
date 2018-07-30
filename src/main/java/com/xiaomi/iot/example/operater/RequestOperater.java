package com.xiaomi.iot.example.operater;

import org.json.JSONArray;
import com.xiaomi.iot.example.typedef.ActionRequest;
import com.xiaomi.iot.example.typedef.PropertyRequest;
import com.xiaomi.iot.example.typedef.StatusRequest;
import com.xiaomi.iot.example.typedef.SubscribeRequest;

import java.util.List;

public interface RequestOperater {

    JSONArray actionRequestOperater(String uid, List<ActionRequest> list);

    JSONArray subscribeRequestOperater(String uid, List<SubscribeRequest> list);

    JSONArray deviceStatusRequestOperater(String uid, List<StatusRequest> list);

    JSONArray setProperties(String uid, List<PropertyRequest> list) throws Exception;

    JSONArray getProperties(String uid, List<PropertyRequest> list) throws Exception;



}
