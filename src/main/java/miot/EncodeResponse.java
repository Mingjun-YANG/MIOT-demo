package miot;

import miot.request.MiotRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import typedef.*;

public interface EncodeResponse {

    JSONObject deviceNotFoundResponse(PropertyRequest propertyObject) throws JSONException;

    JSONObject deviceNotFoundResponse(ActionRequest actionRequest) throws JSONException;

    JSONObject deviceNotFoundResponse(SubscribeRequest subscribeRequest) throws JSONException;

    JSONObject deviceNotFoundResponse(String did) throws JSONException;

    JSONObject serviceNotFoundResponse(PropertyRequest propertyRequest) throws JSONException;

    JSONObject serviceNotFoundResponse(ActionRequest actionRequest) throws JSONException;

    JSONObject propertyNotFoundResponse(PropertyRequest propertyRequest) throws JSONException;

    JSONObject actionNotFoundResponse(ActionRequest actionRequest) throws JSONException;

    JSONObject propertyGetResponse(PropertyRequest propertyRequest, Property properties) throws JSONException;

    JSONObject propertySetResponse(PropertyRequest propertyRequest) throws JSONException;

    JSONObject propertyValueOutRangeResponse(PropertyRequest propertyRequest) throws JSONException;

    JSONObject propertyInvalidFormatResponse(PropertyRequest propertyRequest) throws JSONException;

    JSONObject propertyNotReadableResponse(PropertyRequest propertyRequest) throws JSONException;

    JSONObject propertyNotWriteableResponse(PropertyRequest propertyRequest) throws JSONException;

    JSONObject actionExcecutedResponse(ActionRequest actionRequest, Actions actions) throws JSONException;

    JSONObject subscribeIdInvalidResponse(SubscribeRequest subscribeRequest) throws JSONException;

    JSONObject subscribeExcecutedResponse(SubscribeRequest subscribeRequest) throws JSONException;

    JSONObject getStatusResponse(String did, String status, String name) throws JSONException;

    JSONObject fillPropertyResponse(JSONArray propertyArray, MiotRequest req) throws JSONException;

    JSONObject fillActionResponse(JSONArray actionArray, MiotRequest req) throws JSONException;

    JSONObject fillSubscribeResponse(JSONArray subscribeArray, MiotRequest req) throws JSONException;

    JSONObject fillStatusResponse(JSONArray deviceStatusArray, MiotRequest req) throws JSONException;

    JSONObject tokenInvalidResponse(MiotRequest req) throws JSONException;

}
