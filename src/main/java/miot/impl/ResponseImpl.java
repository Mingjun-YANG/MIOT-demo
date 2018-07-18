package miot.impl;

import miot.Response;
import org.json.JSONException;
import org.json.JSONObject;
import typedef.*;

public class ResponseImpl implements Response {

    public JSONObject deviceNotFoundResponse(PropertyRequest propertyRequest) throws JSONException {
        JSONObject propertyObject = new JSONObject();
        propertyObject.put("status", -1);
        propertyObject.put("piid", propertyRequest.getPiid());
        propertyObject.put("siid", propertyRequest.getSiid());
        propertyObject.put("did", propertyRequest.getDid());
        propertyObject.put("description", "device not found");
        return propertyObject;
    }

    @Override
    public JSONObject deviceNotFoundResponse(ActionRequest actionRequest) throws JSONException {
        JSONObject actionObject = new JSONObject();
        actionObject.put("status", -1);
        actionObject.put("aiid", actionRequest.getAiid());
        actionObject.put("siid", actionRequest.getSiid());
        actionObject.put("did", actionRequest.getDid());
        actionObject.put("description", "device not found");
        return actionObject;
    }

    public JSONObject deviceNotFoundResponse(SubscribeRequest subscribeRequest) throws JSONException {
        JSONObject subscribeObject = new JSONObject();
        subscribeObject.put("status", -1);
        subscribeObject.put("did", subscribeRequest.getDid());
        subscribeObject.put("subscriptionId", subscribeRequest.getSubscriptionId());
        subscribeObject.put("description", "device not found");
        return subscribeObject;
    }

    public JSONObject deviceNotFoundResponse(String did) throws JSONException {
        JSONObject propertyObject = new JSONObject();
        propertyObject.put("status", -1);
        propertyObject.put("did", did);
        propertyObject.put("description", "device not found");
        return propertyObject;
    }


    public JSONObject serviceNotFoundResponse(PropertyRequest propertyRequest) throws JSONException {
        JSONObject propertyObject = new JSONObject();
        propertyObject.put("status", -2);
        propertyObject.put("piid", propertyRequest.getPiid());
        propertyObject.put("siid", propertyRequest.getSiid());
        propertyObject.put("did", propertyRequest.getDid());
        propertyObject.put("description", "service not found");
        return propertyObject;
    }

    @Override
    public JSONObject serviceNotFoundResponse(ActionRequest actionRequest) throws JSONException {
        JSONObject actionObject = new JSONObject();
        actionObject.put("status", -2);
        actionObject.put("piid", actionRequest.getAiid());
        actionObject.put("siid", actionRequest.getSiid());
        actionObject.put("did", actionRequest.getDid());
        actionObject.put("description", "service not found");
        return actionObject;
    }

    public JSONObject propertyNotFoundResponse(PropertyRequest propertyRequest) throws JSONException {
        JSONObject propertyObject = new JSONObject();
        propertyObject.put("status", -3);
        propertyObject.put("piid", propertyRequest.getPiid());
        propertyObject.put("siid", propertyRequest.getSiid());
        propertyObject.put("did", propertyRequest.getDid());
        propertyObject.put("description", "property not found");
        return propertyObject;
    }

    public JSONObject actionNotFoundResponse(ActionRequest actionRequest) throws JSONException {
        JSONObject actionObject = new JSONObject();
        actionObject.put("status", -4);
        actionObject.put("piid", actionRequest.getAiid());
        actionObject.put("siid", actionRequest.getSiid());
        actionObject.put("did", actionRequest.getDid());
        actionObject.put("description", "property not found");
        return actionObject;
    }

    public JSONObject propertyGetResponse(PropertyRequest propertyRequest, Properties properties) throws JSONException {
        JSONObject propertyObject = new JSONObject();
        propertyObject.put("status", 0);
        propertyObject.put("piid", propertyRequest.getPiid());
        propertyObject.put("siid", propertyRequest.getSiid());
        propertyObject.put("did", propertyRequest.getDid());
        propertyObject.put("value", properties.getValue().toString());
        return propertyObject;
    }

    public JSONObject propertySetResponse(PropertyRequest propertyRequest) throws JSONException {
        JSONObject propertyObject = new JSONObject();
        propertyObject.put("status", 0);
        propertyObject.put("piid", propertyRequest.getPiid());
        propertyObject.put("siid", propertyRequest.getSiid());
        propertyObject.put("did", propertyRequest.getDid());
        return propertyObject;
    }

    public JSONObject propertyValueOutRangeResponse(PropertyRequest propertyRequest) throws JSONException {
        JSONObject propertyObject = new JSONObject();
        propertyObject.put("status", -10);
        propertyObject.put("piid", propertyRequest.getPiid());
        propertyObject.put("siid", propertyRequest.getSiid());
        propertyObject.put("did", propertyRequest.getDid());
        propertyObject.put("description", "value out of range");

        return propertyObject;
    }

    public JSONObject propertyInvalidFormatResponse(PropertyRequest propertyRequest) throws JSONException {
        JSONObject propertyObject = new JSONObject();
        propertyObject.put("status", -10);
        propertyObject.put("piid", propertyRequest.getPiid());
        propertyObject.put("siid", propertyRequest.getSiid());
        propertyObject.put("did", propertyRequest.getDid());
        propertyObject.put("description", "invalid value format");

        return propertyObject;
    }

    public JSONObject propertyNotReadableResponse(PropertyRequest propertyRequest) throws JSONException {
        JSONObject propertyObject = new JSONObject();
        propertyObject.put("status", -10);
        propertyObject.put("piid", propertyRequest.getPiid());
        propertyObject.put("siid", propertyRequest.getSiid());
        propertyObject.put("did", propertyRequest.getDid());
        propertyObject.put("description", "property not readable");

        return propertyObject;
    }

    public JSONObject propertyNotWriteableResponse(PropertyRequest propertyRequest) throws JSONException {
        JSONObject propertyObject = new JSONObject();
        propertyObject.put("status", -10);
        propertyObject.put("piid", propertyRequest.getPiid());
        propertyObject.put("siid", propertyRequest.getSiid());
        propertyObject.put("did", propertyRequest.getDid());
        propertyObject.put("description", "property not writeable");

        return propertyObject;
    }

    public JSONObject actionExcecutedResponse(ActionRequest actionRequest, Actions actions) throws JSONException {
        JSONObject actionObject = new JSONObject();
        actionObject.put("piid", actionRequest.getAiid());
        actionObject.put("siid", actionRequest.getSiid());
        actionObject.put("did", actionRequest.getDid());
        actionObject.put("status", 0);
        actionObject.put("out", actions.getOut());

        return actionObject;

    }

    public JSONObject subscribeIdInvalidResponse(SubscribeRequest subscribeRequest) throws JSONException {
        JSONObject subscribeObject = new JSONObject();
        subscribeObject.put("status", -16);
        subscribeObject.put("did", subscribeRequest.getDid());
        subscribeObject.put("subscriptionId", subscribeRequest.getSubscriptionId());
        subscribeObject.put("description", "invalid subscriptionId");
        return subscribeObject;
    }

    public JSONObject subscribeExcecutedResponse(SubscribeRequest subscribeRequest) throws JSONException {
        JSONObject subscribeObject = new JSONObject();
        subscribeObject.put("status", 0);
        subscribeObject.put("did", subscribeRequest.getDid());
        subscribeObject.put("subscriptionId", subscribeRequest.getSubscriptionId());
        return subscribeObject;
    }

    public JSONObject getStatusResponse(String did, String status, String name) throws JSONException {
        JSONObject statusObject = new JSONObject();
        statusObject.put("status", 0);
        statusObject.put("did", did);
        statusObject.put("online", status);
        statusObject.put("name", name);
        return statusObject;
    }

}
