
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oauth.OAuthValidator;
import oauth.impl.OAuthValidatorMockImpl;
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

import static typedef.ActionOperation.decodeRequest;
import static typedef.PropertyOperation.decodeGetPropertyRequest;
import static typedef.PropertyOperation.decodeSetPropertyRequest;
import static typedef.StatusOperation.decodeGetStatusRequest;
import static typedef.SubscribeOperation.decodeSetSubscribeRequest;


public class HelloHandler extends AbstractHandler {

    private JSONObject context;
    private JSONArray array;
    private String User_Token;
    private String requestId;
    private String intent;
    private OAuthValidator validator;
    private Operation operation;
    private Subscribe subscribe;
    private Status deviceStatus;

    public HelloHandler() {
        operation = new OperationMockImpl();
        validator = new OAuthValidatorMockImpl();
        subscribe = new SubscribeMockImpl();
        deviceStatus = new DeviceStatusMockImpl();
    }

    @Override
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) throws IOException,
            ServletException {
        PrintWriter out = response.getWriter();
        User_Token = request.getHeader("User_Token");
        Boolean validationresult = validator.validate(User_Token);
        if (!validationresult) {
            JSONObject objreturn = new JSONObject();
            response.setStatus(401);
            try {
                objreturn.append("code", "-1");
                objreturn.append("description", "xxxx");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            out.println(objreturn);
            baseRequest.setHandled(true);
            return;
        }
        BufferedReader reader = request.getReader();
        StringBuilder buff1 = new StringBuilder();
        String st = null;
        while ((st = reader.readLine()) != null) {
            buff1.append(st);
        }
        String body = buff1.toString();
        //To JSON
        try {
            JSONObject jsonObject = new JSONObject(body);
            context = jsonObject;
            requestId = context.getString("requestId");
            intent = context.getString("intent");
        } catch (JSONException e) {
            e.printStackTrace();
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
                        obj = this.onGetProperties(context, array);
                        if (obj == null) {
                            response.setStatus(400);
                            out.println("properties is null");
                        } else {
                            response.setStatus(200);
                            out.println(obj);
                        }
                        break;
                    case 2:
                        try {
                            array = context.getJSONArray("properties");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        obj = this.onSetProperties(context, array);
                        if (obj == null) {
                            response.setStatus(400);
                            out.println("properties is null");
                        } else {
                            response.setStatus(200);
                            out.println(obj);
                        }
                        break;
                    case 3:
                        try {
                             array = context.getJSONArray("action");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        obj = this.onExecuteAction(context, array);
                        response.setStatus(200);
                        out.println(obj);
                        break;
                    default:
                        response.setStatus(400);
                        out = response.getWriter();
                        out.println("intent invalid");
                    case 4:
                        try {
                            array = context.getJSONArray("devices");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        obj = this.onSubscribe(context, array);
                        response.setStatus(200);
                        out.println(obj);
                        break;
                    case 5:
                        try {
                            array = context.getJSONArray("devices");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        obj = this.offSubscribe(context, array);
                        response.setStatus(200);
                        out.println(obj);
                        break;
                    case 6:
                        try {
                            array = context.getJSONArray("devices");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        obj = this.getDeviceStatus(context, array);
                        response.setStatus(200);
                        out.println(obj);
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
        JSONObject objreturn = new JSONObject();
        try {
            objreturn.put("intent", intent);
            objreturn.put("requestId", requestId);
            objreturn.put("devices", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return objreturn;

    }

    private JSONObject onGetProperties(JSONObject context, JSONArray properties) {
        JSONObject objreturn = new JSONObject();
        if (properties == null) {
            return objreturn;
        }
        List<PropertyOperation> list = new ArrayList<>();
        if (properties.length() > 0) {
            for (int i = 0; i < properties.length(); i++) {
                // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                JSONObject aaa = properties.optJSONObject(i);
                PropertyOperation bbb = decodeGetPropertyRequest(aaa);
                list.add(bbb);
            }
        }
        list.forEach(property -> operation.get(property));
        List<JSONObject> result = list.stream().map(PropertyOperation::encodeGetPropertyResponse).collect(Collectors.toList());
        try {
            objreturn.put("requestId", requestId);
            objreturn.put("intent", intent);
            objreturn.put("properties", new JSONArray(result));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return objreturn;
    }


    private JSONObject onSetProperties(JSONObject context, JSONArray properties) {
        JSONObject objreturn = new JSONObject();
        if (properties == null) {
            return objreturn;
        }
        List<PropertyOperation> list = new ArrayList<>();
        if (properties.length() > 0) {
            for (int i = 0; i < properties.length(); i++) {
                JSONObject aaa = properties.optJSONObject(i);
                PropertyOperation bbb = decodeSetPropertyRequest(aaa);
                list.add(bbb);
            }
        }
        list.forEach((property) -> {
            this.operation.set(property);
        });
        List<JSONObject> result = list.stream().map(PropertyOperation::encodeSetPropertyResponse).collect(Collectors.toList());
        try {
            objreturn.put("requestId", requestId);
            objreturn.put("intent", intent);
            objreturn.put("properties", new JSONArray(result));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return objreturn;
    }


    private JSONObject onExecuteAction(JSONObject context, JSONArray action) {
        JSONObject objreturn = new JSONObject();
        if (action == null) {
            return objreturn;
        }
        List<ActionOperation> list = new ArrayList<>();
        if (action.length() > 0) {
            for (int i = 0; i < action.length(); i++) {
                JSONObject aaa = action.optJSONObject(i);
                ActionOperation bbb = decodeRequest(aaa);
                list.add(bbb);
            }
        }
        list.forEach((action1) -> {
            this.operation.invoke(action1);
        });
        List<JSONObject> result = list.stream().map(ActionOperation::encodeResponse).collect(Collectors.toList());
        try {
            objreturn.put("requestId", requestId);
            objreturn.put("intent", intent);
            objreturn.put("action", new JSONArray(result));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return objreturn;
    }

    private JSONObject onSubscribe(JSONObject context, JSONArray devices) {
        JSONObject objreturn = new JSONObject();
        if (devices == null) {
            return objreturn;
        }
        List<SubscribeOperation> list = new ArrayList<>();
        if (devices.length() > 0) {
            for (int i = 0; i < devices.length(); i++) {
                JSONObject aaa = devices.optJSONObject(i);
                SubscribeOperation bbb = decodeSetSubscribeRequest(aaa);
                list.add(bbb);
            }
        }
        list.forEach((subscribe) -> {
            this.subscribe.set(subscribe);
        });
        List<JSONObject> result = list.stream().map(SubscribeOperation::encodeSetSubscribeResponse).collect(Collectors.toList());
        try {
            objreturn.put("requestId", requestId);
            objreturn.put("intent", intent);
            objreturn.put("devices", new JSONArray(result));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return objreturn;
    }

    private JSONObject offSubscribe(JSONObject context, JSONArray devices) {
        JSONObject objreturn = new JSONObject();
        if (devices == null) {
            return objreturn;
        }
        List<SubscribeOperation> list = new ArrayList<>();
        if (devices.length() > 0) {
            for (int i = 0; i < devices.length(); i++) {
                JSONObject aaa = devices.optJSONObject(i);
                SubscribeOperation bbb = decodeSetSubscribeRequest(aaa);
                list.add(bbb);
            }
        }
        list.forEach((subscribe) -> {
            this.subscribe.unset(subscribe);
        });
        List<JSONObject> result = list.stream().map(SubscribeOperation::encodeSetSubscribeResponse).collect(Collectors.toList());
        try {
            objreturn.put("devices", new JSONArray(result));
            objreturn.put("intent", intent);
            objreturn.put("requestId", requestId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return objreturn;
    }

    private JSONObject getDeviceStatus(JSONObject context, JSONArray devices) {
        JSONObject objreturn = new JSONObject();
        if (devices == null) {
            return objreturn;
        }
        List<StatusOperation> list = new ArrayList<>();
        if (devices.length() > 0) {
            for (int i = 0; i < devices.length(); i++) {
                // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                JSONObject aaa = devices.optJSONObject(i);
                StatusOperation bbb = decodeGetStatusRequest(aaa);
                list.add(bbb);
            }
        }
        list.forEach(deviceStatus -> {
            this.deviceStatus.get(deviceStatus);
        });
        List<JSONObject> result = list.stream().map(StatusOperation::encodeGetStatusResponse).collect(Collectors.toList());
        try {
            objreturn.put("requestId", requestId);
            objreturn.put("intent", intent);
            objreturn.put("devices", new JSONArray(result));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return objreturn;

    }
}
