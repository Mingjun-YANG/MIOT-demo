import db.impl.DeviceDBLocalJsonImpl;
import exeception.MyExeception;
import miot.*;
import miot.impl.DecodeRequestImpl;
import miot.impl.EncodeResponseImpl;
import miot.request.*;
import operater.impl.RequestOperaterImpl;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import typedef.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ServiceHandler extends AbstractHandler {

    private DeviceDBLocalJsonImpl deviceDB = new DeviceDBLocalJsonImpl();
    private EncodeResponse encodeResponse = new EncodeResponseImpl();
    private DecodeRequest decodeRequest = new DecodeRequestImpl();
    private RequestOperaterImpl requestOperater = new RequestOperaterImpl();

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

    private MiotResponse onGetDevices(MiotRequest req) throws Exception{
        String uid = deviceDB.getUid(req.getToken());
        List<Device> list = deviceDB.getDevices(uid);
        JSONArray deviceArray = new JSONArray();
        list.forEach(Device -> {

            JSONObject deviceObject = new JSONObject();
            try {
                deviceObject.put("type", Device.getType());
                deviceObject.put("did", Device.getDid());
                deviceArray.put(deviceObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
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
        JSONArray array = requestOperater.getProperties(uid, list);
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
        JSONArray array = requestOperater.setProperties(uid, list);
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

    private MiotResponse onExcecuteActions(MiotRequest req) throws Exception {
        MiotActionRequest miotActionRequest = (MiotActionRequest) req;
        List<ActionRequest> list = decodeRequest.decodeActionRequest(miotActionRequest);
        String uid = deviceDB.getUid(req.getToken());
        JSONArray array = requestOperater.actionRequestOperater(uid,list);
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
        JSONArray array = requestOperater.subscribeRequestOperater(uid,list);
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
        JSONArray array = requestOperater.deviceStatusRequestOperater(uid, list);
        MiotResponse response = new MiotResponse();
        response.setCode(200);
        response.setResponseBody(encodeResponse.fillStatusResponse(array, req));
        return response;
    }

    private MiotResponse execute(MiotRequest req) throws Exception{
        int status = 200;
        MiotResponse response = new MiotResponse();
        JSONObject resultObject = new JSONObject();

        try {
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
                    return onExcecuteActions(req);

                case GET_DEVICE_STATUS:
                    return onGetStatus(req);

                default:
                    status = 404;
                    resultObject.put("Description", "Not Found");
                    response.setCode(status);
                    response.setResponseBody(resultObject);
            }
        } catch (JSONException e) {
            status = 503;
            resultObject.put("Description", "Service unavaliable");
        } catch (MyExeception e) {
//            status = e.getHttpStatus();
            // e.getCode();
            // e.getMessage();
        } finally {
            response.setCode(status);
            response.setResponseBody(resultObject);
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
        try {
            resp = execute(req);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.getWriter().println(resp.getResponseBody().toString());
        response.setStatus(resp.getCode());

        baseRequest.setHandled(true);
    }
}
