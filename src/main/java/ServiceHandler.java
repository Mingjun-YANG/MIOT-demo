
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oauth.OAuthValidator;
import oauth.impl.OAuthValidatorMockImpl;
import operater.DecodeOperater;
import operater.HeaderValidator;
import operater.ResponseOperater;
import operater.ReturnObjectOperate;
import status.Status;
import status.impl.DeviceStatusMockImpl;
import subscribe.Subscribe;
import subscribe.impl.SubscribeMockImpl;
import typedef.ActionOperation;
import typedef.PropertyOperation;
import typedef.StatusOperation;
import typedef.SubscribeOperation;
import device.Operation;
import device.impl.OperationMockImpl;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ServiceHandler extends AbstractHandler {

    private JSONObject context;
    private JSONArray array;
    private String requestId;
    private String intent;
    private OAuthValidator validator;
    private Operation operation;
    private Subscribe subscribe;
    private DecodeOperater decodeOperater;
    private HeaderValidator headerValidator;
    private Status deviceStatus;
    private String uid;

    public ServiceHandler() {
        operation = new OperationMockImpl();
        validator = new OAuthValidatorMockImpl();
        subscribe = new SubscribeMockImpl();
        deviceStatus = new DeviceStatusMockImpl();
        decodeOperater = new DecodeOperater();
        headerValidator = new HeaderValidator();
    }

    @Override
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        String token = request.getHeader("User_Token");
        try {
            uid = validator.validate(token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        BufferedReader reader = request.getReader();
        StringBuilder buff1 = new StringBuilder();
        String st;
        while ((st = reader.readLine()) != null) {
            buff1.append(st);
        }
        String body = buff1.toString();
        //To JSON
        try {
            context = new JSONObject(body);
            requestId = context.optString("requestId");
            intent = context.optString("intent");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (!headerValidator.headerValidator(response, requestId, intent)) {
            return;
        }
        if (requestId == null) {
            response.setStatus(400);
            out = response.getWriter();
            out.println("requestId is null");
            return;
        } else {
            if (intent == null) {
                response.setStatus(400);
                out = response.getWriter();
                out.println("intent is null");
                return;
            } else {
                byte var6 = -1;
                switch (intent.hashCode()) {
                    case -1687278293:
                        if (intent.equals("invoke-action")) {
                            var6 = 3;
                        }
                        break;
                    case 30608254:
                        if (intent.equals("set-properties")) {
                            var6 = 2;
                        }
                        break;
                    case 395143526:
                        if (intent.equals("get-devices")) {
                            var6 = 0;
                        }
                        break;
                    case 1802344458:
                        if (intent.equals("get-properties")) {
                            var6 = 1;
                        }
                        break;
                    case 514841930:
                        if (intent.equals("subscribe")) {
                            var6 = 4;
                        }
                        break;
                    case 583281361:
                        if (intent.equals("unsubscribe")) {
                            var6 = 5;
                        }
                        break;
                    case 1336926482:
                        if (intent.equals("get-device-status")) {
                            var6 = 6;
                        }
                        break;
                    default:
                        break;
                }

                switch (var6) {

                    case 0:
                        JSONObject obj = this.onGetDevices(context);
                        response.setStatus(200);
                        out.println(obj);
                        break;

                    case 1:
                        try {
                            array = context.getJSONArray("properties");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        obj = this.onGetProperties(array);
                        ResponseOperater.outPrinter(obj, intent, response);
                        break;

                    case 2:
                        try {
                            array = context.getJSONArray("properties");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        obj = this.onSetProperties(array);
                        ResponseOperater.outPrinter(obj, intent, response);
                        break;

                    case 3:
                        try {
                            array = context.getJSONArray("action");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        obj = this.onExecuteAction(array);
                        ResponseOperater.outPrinter(obj, intent, response);
                        break;

                    case 4:
                        try {
                            array = context.getJSONArray("devices");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        obj = this.onSubscribe(array);
                        ResponseOperater.outPrinter(obj, intent, response);
                        break;

                    case 5:
                        try {
                            array = context.getJSONArray("devices");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        obj = this.offSubscribe(array);
                        ResponseOperater.outPrinter(obj, intent, response);
                        break;

                    case 6:
                        try {
                            array = context.getJSONArray("devices");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        obj = this.getDeviceStatus(array);
                        ResponseOperater.outPrinter(obj, intent, response);
                        break;

                    default:
                        break;
                }
            }
        }
        baseRequest.setHandled(true);
    }


    private JSONObject onGetDevices(JSONObject context) {
        JSONArray array = new JSONArray();
        JSONObject obj1 = new JSONObject();
        JSONObject obj2 = new JSONObject();

        try {

            obj1.put("did", "1001");
            obj1.put("name", "whitee");
            obj1.put("type", "urn:miot-spec-v2:device:light:0000A001:opple-desk:1");
            array.put(obj1);

            obj2.put("did", "1002");
            obj2.put("name", "blackee");
            obj2.put("type", "urn:miot-spec-v2:device:light:0000A001:opple-desk:1");
            array.put(obj2);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject objReturn = new JSONObject();

        try {
            objReturn.put("intent", intent);
            objReturn.put("requestId", requestId);
            objReturn.put("devices", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return objReturn;
    }

    private JSONObject onGetProperties(JSONArray properties) {
        JSONObject objReturn = new JSONObject();
        if (properties == null) {
            return objReturn;
        }

        List<PropertyOperation> list;
        list = DecodeOperater.decodeGetProperty(properties);
        list.forEach(property -> operation.get(property));
        List<JSONObject> result = list.stream().map(PropertyOperation::encodeGetPropertyResponse).collect(Collectors.toList());
        objReturn = ReturnObjectOperate.fillReturnObject(result, "properties", requestId, intent);

        return objReturn;
    }


    private JSONObject onSetProperties(JSONArray properties) {
        JSONObject objReturn = new JSONObject();
        if (properties == null) {
            return objReturn;
        }

        List<PropertyOperation> list;
        list = DecodeOperater.decodeSetProperty(properties);
        list.forEach((property) -> {
            this.operation.set(property);
        });

        List<JSONObject> result = list.stream().map(PropertyOperation::encodeSetPropertyResponse).collect(Collectors.toList());
        objReturn = ReturnObjectOperate.fillReturnObject(result, "properties", requestId, intent);
        return objReturn;
    }

    private JSONObject onExecuteAction(JSONArray action) {
        JSONObject objReturn = new JSONObject();
        if (action == null) {
            return objReturn;
        }
        List<ActionOperation> list;
        list = decodeOperater.decodeAction(action);
        list.forEach((action1) -> {
            this.operation.invoke(action1);
        });
        List<JSONObject> result = list.stream().map(ActionOperation::encodeResponse).collect(Collectors.toList());
        objReturn = ReturnObjectOperate.fillReturnObject(result, "action", requestId, intent);
        return objReturn;
    }

    private JSONObject onSubscribe(JSONArray devices) {
        JSONObject objReturn = new JSONObject();
        if (devices == null) {
            return objReturn;
        }

        List<SubscribeOperation> list;
        list = decodeOperater.decodeSetSubscribe(devices);
        list.forEach((subscribe) -> {
            this.subscribe.set(subscribe);
        });

        List<JSONObject> result = list.stream().map(SubscribeOperation::encodeSetSubscribeResponse).collect(Collectors.toList());
        objReturn = ReturnObjectOperate.fillReturnObject(result, "devices", requestId, intent);
        return objReturn;
    }

    private JSONObject offSubscribe(JSONArray devices) {
        JSONObject objReturn = new JSONObject();
        if (devices == null) {
            return objReturn;
        }

        List<SubscribeOperation> list;
        list = decodeOperater.decodeSetSubscribe(devices);
        list.forEach((subscribe) -> {
            this.subscribe.unset(subscribe);
        });

        List<JSONObject> result = list.stream().map(SubscribeOperation::encodeSetSubscribeResponse).collect(Collectors.toList());

        objReturn = ReturnObjectOperate.fillReturnObject(result, "devices", requestId, intent);
        return objReturn;
    }

    private JSONObject getDeviceStatus(JSONArray devices) {
        JSONObject objReturn = new JSONObject();
        if (devices == null) {
            return objReturn;
        }

        List<StatusOperation> list;
        list = DecodeOperater.decodeGetStatus(devices);
        list.forEach(deviceStatus -> {
            this.deviceStatus.get(deviceStatus);
        });

        List<JSONObject> result = list.stream().map(StatusOperation::encodeGetStatusResponse).collect(Collectors.toList());

        objReturn = ReturnObjectOperate.fillReturnObject(result, "devices", requestId, intent);

        return objReturn;
    }
}
