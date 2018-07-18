package miot.impl;

import miot.Request;
import org.json.JSONObject;
import typedef.ActionRequest;
import typedef.PropertyRequest;
import typedef.SubscribeRequest;

import java.util.ArrayList;
import java.util.List;

public class RequestImpl implements Request {

    public List<PropertyRequest> getPropertyRequest(JSONObject context) {
        List<PropertyRequest> list = new ArrayList<>();
        for (int i = 0; i < context.optJSONArray("properties").length(); i++) {
            PropertyRequest propertyRequest = new PropertyRequest();
            propertyRequest.setDid(context.optJSONArray("properties").optJSONObject(i).optString("did"));
            propertyRequest.setSiid(context.optJSONArray("properties").optJSONObject(i).optInt("siid"));
            propertyRequest.setPiid(context.optJSONArray("properties").optJSONObject(i).optInt("piid"));
            list.add(propertyRequest);
        }
        return list;
    }

    public List<PropertyRequest> setPropertyRequest(JSONObject context) {
        List<PropertyRequest> list = new ArrayList<>();
        for (int i = 0; i < context.optJSONArray("properties").length(); i++) {
            PropertyRequest propertyRequest = new PropertyRequest();
            propertyRequest.setDid(context.optJSONArray("properties").optJSONObject(i).optString("did"));
            propertyRequest.setSiid(context.optJSONArray("properties").optJSONObject(i).optInt("siid"));
            propertyRequest.setPiid(context.optJSONArray("properties").optJSONObject(i).optInt("piid"));
            propertyRequest.setValue(context.optJSONArray("properties").optJSONObject(i).opt("value"));
            list.add(propertyRequest);
        }
        return list;
    }

    public List<ActionRequest> excecuteActionRequest(JSONObject context) {
        List<ActionRequest> list = new ArrayList<>();
        for (int i = 0; i < context.optJSONArray("action").length(); i++) {
            ActionRequest actionRequest = new ActionRequest();
            actionRequest.setDid(context.optJSONArray("action").optJSONObject(i).optString("did"));
            actionRequest.setSiid(context.optJSONArray("action").optJSONObject(i).optInt("siid"));
            actionRequest.setAiid(context.optJSONArray("action").optJSONObject(i).optInt("aiid"));
            actionRequest.setIn(context.optJSONArray("action").optJSONObject(i).optJSONArray("in"));
            list.add(actionRequest);
        }
        return list;
    }


    public List<SubscribeRequest> subscribeRequest(JSONObject context) {
        List<SubscribeRequest> list = new ArrayList<>();
        for (int i = 0; i < context.optJSONArray("devices").length(); i++) {
            SubscribeRequest subscribeRequest = new SubscribeRequest();
            subscribeRequest.setDid(context.optJSONArray("devices").optJSONObject(i).optString("did"));
            subscribeRequest.setSubscriptionId(context.optJSONArray("devices").optJSONObject(i).optString("subscriptionId"));
            list.add(subscribeRequest);
        }
        return list;
    }


}
