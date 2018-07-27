package miot;

import miot.request.*;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

public class MiotRequestCodec {


    public static MiotRequest decode(HttpServletRequest request, JSONObject context) {
        MiotRequest miotRequest = new MiotRequest();
        MiotIntent intent = (MiotIntent.from(context.optString("intent")));
        miotRequest.setIntent(intent);
        miotRequest.setRequestId(context.optString("requestId"));
        miotRequest.setToken(request.getHeader("User_Token"));
        switch (intent) {
            case GET_PROPERTIES:
                MiotGetPropertiesRequest getPropertiesRequest = new MiotGetPropertiesRequest();
                getPropertiesRequest.setIntent(intent);
                getPropertiesRequest.setRequestId(context.optString("requestId"));
                getPropertiesRequest.setToken(request.getHeader("User_Token"));
                getPropertiesRequest.setProperties(context.optJSONArray("properties"));
                return getPropertiesRequest;
            case SET_PROPERTIES:
                MiotSetPropertiesRequest setPropertiesRequest = new MiotSetPropertiesRequest();
                setPropertiesRequest.setIntent(intent);
                setPropertiesRequest.setRequestId(context.optString("requestId"));
                setPropertiesRequest.setToken(request.getHeader("User_Token"));
                setPropertiesRequest.setProperties(context.optJSONArray("properties"));
                return setPropertiesRequest;
            case SUBSCRIBE:
            case UNSUBSCRIBE:
                MiotSubscribeRequest subscribeRequest = new MiotSubscribeRequest();
                subscribeRequest.setIntent(intent);
                subscribeRequest.setRequestId(context.optString("requestId"));
                subscribeRequest.setToken(request.getHeader("User_Token"));
                subscribeRequest.setDevices(context.optJSONArray("devices"));
                return subscribeRequest;
            case INVOKE_ACTION:
                MiotActionRequest actionRequest = new MiotActionRequest();
                actionRequest.setIntent(intent);
                actionRequest.setRequestId(context.optString("requestId"));
                actionRequest.setToken(request.getHeader("User_Token"));
                actionRequest.setAction(context.optJSONArray("action"));
                return actionRequest;
            case GET_DEVICE_STATUS:
                MiotGetStatusRequest getStatusRequest = new MiotGetStatusRequest();
                getStatusRequest.setIntent(intent);
                getStatusRequest.setRequestId(context.optString("requestId"));
                getStatusRequest.setToken(request.getHeader("User_Token"));
                getStatusRequest.setDevices(context.optJSONArray("devices"));
                return getStatusRequest;
            case GET_DEVICES:
                MiotGetDevicesRequest getDevicesRequest = new MiotGetDevicesRequest();
                getDevicesRequest.setIntent(intent);
                getDevicesRequest.setRequestId(context.optString("requestId"));
                getDevicesRequest.setToken(request.getHeader("User_Token"));
                return getDevicesRequest;
        }
        return miotRequest;
    }

    public static JSONObject bodyReader(HttpServletRequest request) throws IOException {
        JSONObject context = new JSONObject();
        BufferedReader reader = request.getReader();
        StringBuilder buff1 = new StringBuilder();
        String st;

        while ((st = reader.readLine()) != null) {
            buff1.append(st);
        }

        String body = buff1.toString();

        try {
            context = new JSONObject(body);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return context;
    }
}
