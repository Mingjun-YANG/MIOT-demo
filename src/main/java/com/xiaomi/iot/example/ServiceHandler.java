package com.xiaomi.iot.example;

import com.xiaomi.iot.example.db.impl.DeviceDBLocalJsonImpl;
//import com.xiaomi.iot.example.exeception.MyException;
import com.xiaomi.iot.example.exeception.MyException;
import com.xiaomi.iot.example.miot.*;
import com.xiaomi.iot.example.miot.impl.DecodeRequestImpl;
import com.xiaomi.iot.example.miot.impl.EncodeResponseImpl;
import com.xiaomi.iot.example.miot.request.*;
import com.xiaomi.iot.example.operater.impl.RequestOperaterImpl;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.json.JSONArray;
import org.json.JSONObject;
import com.xiaomi.iot.example.typedef.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ServiceHandler extends AbstractHandler {

    private DeviceDBLocalJsonImpl deviceDB = new DeviceDBLocalJsonImpl();
    private EncodeResponse encodeResponse = new EncodeResponseImpl();
    private DecodeRequest decodeRequest = new DecodeRequestImpl();
    private RequestOperaterImpl requestOperator = new RequestOperaterImpl();

    private void getUserId(MiotRequest req){
        try {
            req.setUid(deviceDB.getUid(req.getToken()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * {
     *     "requestId": "xxxx",
     *     "intent": "get-devices",
     * }
     */

    private MiotResponse onGetDevices(MiotRequest req) throws Exception {
        String uid = deviceDB.getUid(req.getToken());
        List<Device> list = deviceDB.getDevices(uid);
        JSONArray deviceArray = new JSONArray();

        for (Device device : list) {
            JSONObject deviceObject = new JSONObject();
            deviceObject.put("type", device.getType());
            deviceObject.put("did", device.getDid());
            deviceArray.put(deviceObject);
        }

        MiotResponse response = new MiotResponse();
        response.setCode(200);
        response.setResponseBody(encodeResponse.fillStatusResponse(deviceArray, req));
        return response;
    }

    /**
     * {
     *     "requestId": "xxxx",
     *     "intent": "get-properties",
     *     "properties": [
     *        {
     *            "did": "xxx",
     *            "siid": "xxxx",
     *            "piid": "xxxx",
     *        }
     *     ]
     * }
     */

    private MiotResponse onGetProperties(MiotRequest req) throws Exception {
        MiotGetPropertiesRequest miotGetPropertiesRequest = (MiotGetPropertiesRequest) req;
        List<PropertyRequest> list = decodeRequest.decodeGetPropertyRequest(miotGetPropertiesRequest);
        String uid = deviceDB.getUid(req.getToken());
        JSONArray array = requestOperator.getProperties(uid, list);
        MiotResponse response = new MiotResponse();
        response.setCode(200);
        response.setResponseBody(encodeResponse.fillPropertyResponse(array, req));
        return response;
    }

    /**
     * {
     *     "requestId": "xxxx",
     *     "intent": "set-properties",
     *     "properties": [
     *        {
     *            "did": "xxx",
     *            "siid": "xxxx",
     *            "piid": "xxxx",
     *            "value": xx
     *        }
     *     ]
     * }
     */

    private MiotResponse onSetProperties(MiotRequest req) throws Exception {
        MiotSetPropertiesRequest miotSetPropertiesRequest = (MiotSetPropertiesRequest) req;
        List<PropertyRequest> list = decodeRequest.decodeSetPropertyRequest(miotSetPropertiesRequest);
        String uid = deviceDB.getUid(req.getToken());
        JSONArray array = requestOperator.setProperties(uid, list);
        MiotResponse response = new MiotResponse();
        response.setCode(200);
        response.setResponseBody(encodeResponse.fillPropertyResponse(array, req));
        return response;

    }

    /**
     * {
     *     "requestId": "xxxx",
     *     "intent": "invoke-action",
     *     "properties": [
     *        {
     *            "did": "xxx",
     *            "siid": "xxxx",
     *            "aiid": "xxxx",
     *            "in":[xx,xx]
     *        }
     *     ]
     * }
     */
    private MiotResponse onExecuteAction(MiotRequest req) throws Exception {
        MiotActionRequest miotActionRequest = (MiotActionRequest) req;
        List<ActionRequest> list = decodeRequest.decodeActionRequest(miotActionRequest);
        String uid = deviceDB.getUid(req.getToken());
        JSONArray array = requestOperator.actionRequestOperater(uid,list);
        MiotResponse response = new MiotResponse();
        response.setCode(200);
        response.setResponseBody(encodeResponse.fillActionResponse(array, req));
        return response;
    }

    /**
     * {
     *     "requestId": "xxxx",
     *     "intent": "subscribe",
     *     "devices": [
     *        {
     *            "did": "xxx",
     *            "subscriptionId": "xxxx"
     *        }
     *     ]
     * }
     */
    private MiotResponse onSubscribe(MiotRequest req) throws Exception{
        MiotSubscribeRequest miotSubscribeRequest = (MiotSubscribeRequest) req;
        List<SubscribeRequest> list = decodeRequest.decodeSubscribeRequest(miotSubscribeRequest);
        String uid = deviceDB.getUid(req.getToken());
        JSONArray array = requestOperator.subscribeRequestOperater(uid,list);
        MiotResponse response = new MiotResponse();
        response.setCode(200);
        response.setResponseBody(encodeResponse.fillSubscribeResponse(array, req));
        return response;
    }

    /**
     * {
     *     "requestId": "xxxx",
     *     "intent": "get-device-status",
     *     "devices": ["xxxx", "xxxx"]
     * }
     */
    private MiotResponse onGetStatus(MiotRequest req) throws Exception {
        MiotGetStatusRequest miotGetStatusRequest = (MiotGetStatusRequest) req;
        List<StatusRequest> list = decodeRequest.decodeStatusRequest(miotGetStatusRequest);
        String uid = deviceDB.getUid(req.getToken());
        JSONArray array = requestOperator.deviceStatusRequestOperater(uid, list);
        MiotResponse response = new MiotResponse();
        response.setCode(200);
        response.setResponseBody(encodeResponse.fillStatusResponse(array, req));
        return response;
    }

    private MiotResponse execute(MiotRequest req) throws Exception {
        int status;
        MiotResponse response = new MiotResponse();
        JSONObject resultObject = new JSONObject();

        getUserId(req);

        switch (req.getIntent()) {
            case GET_DEVICES:
                return onGetDevices(req);

            case GET_PROPERTIES:
                return onGetProperties(req);

            case SET_PROPERTIES:
                return onSetProperties(req);

            case SUBSCRIBE:
            case UNSUBSCRIBE:
                return onSubscribe(req);

            case INVOKE_ACTION:
                return onExecuteAction(req);

            case GET_DEVICE_STATUS:
                return onGetStatus(req);

            default:
                status = 404;
                resultObject.put("Description", "Not Found");
                response.setCode(status);
                response.setResponseBody(resultObject);
                break;
        }

        return response;
    }

    @Override
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        JSONObject context = MiotRequestCodec.bodyReader(request);
        MiotRequest req = MiotRequestCodec.decode(request, context);
        MiotResponse resp = null;

        int status = 404;
        String body = new String();

        try {
            resp = execute(req);

            status = resp.getCode();
            body = resp.getResponseBody().toString();
        } catch (Exception e) {
            if (e instanceof MyException) {
                MyException ee = (MyException) e;

                status = ee.getHttpStatus();
                body = ee.getMessage();

            }
            if (e instanceof NullPointerException) {
                 status = 500;
                 body = e.getMessage();
            }
        } finally {
            response.getWriter().println(body);
            response.setStatus(status);
        }
        baseRequest.setHandled(true);
    }
}
