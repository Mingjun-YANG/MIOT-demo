package operater;

import device.Operation;
import device.impl.OperationMockImpl;
import deviceOwner.Ownership;
import deviceOwner.impl.OwnershipImpl;
import oauth.OAuthValidator;
import oauth.impl.OAuthValidatorMockImpl;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import status.Status;
import status.impl.DeviceStatusMockImpl;
import subscribe.Subscribe;
import subscribe.impl.SubscribeMockImpl;
import typedef.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;




public class IntentOperater {

    private OAuthValidator validator;
    private Operation operation;
    private Subscribe subscribe;
    private DecodeOperater decodeOperater;
    private HeaderValidator headerValidator;
    private IntentSwitcher intentSwitcher;
    private Ownership ownership;
    private Status deviceStatus;
    private String uid;

    private void ServiceHandler() {
        operation = new OperationMockImpl();
        validator = new OAuthValidatorMockImpl();
        subscribe = new SubscribeMockImpl();
        deviceStatus = new DeviceStatusMockImpl();
        headerValidator = new HeaderValidator();
        intentSwitcher = new IntentSwitcher();
    }

    public JSONObject onGetDevices(HttpServletResponse response, String requestId, String intent, String uid) throws JSONException{
        decodeOperater = new DecodeOperater();
        ownership = new OwnershipImpl();
        List<DeviceOwnerOperation> list = decodeOperater.decodeGetDevice(uid);

        JSONArray jsonArray = new JSONArray();
        list.forEach(deviceOwnerOperation -> {

            deviceOwnerOperation.DEV.forEach(device -> {

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("did",device.getDid());
                    jsonObject.put("type",device.getType());

                    jsonArray.put(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            });

        });




//        List<Device> devices = ownership.getDeviceOwnership(list ,uid);
//        List<JSONObject> result = list.stream().map(DeviceOwnerOperation::encodeGetDeviceOwner).collect(Collectors.toList());
//        System.out.println(result.get(0));




        JSONObject objReturn = ReturnObjectOperate.fillReturnObject(jsonArray, "devices", requestId, intent);
//        ownership.getDeviceOwnership(uid);
//        List<JSONObject> result = ownership.getDeviceOwnership(uid).stream().map(DeviceOwnerOperation::encodeGetDeviceOwner).collect(Collectors.toList());

//        list.forEach(a-> ownership.getDeviceOwnership(uid));
//
//        ownership.getDeviceOwnership(uid);
//        List<JSONObject> result = list.stream().map(DeviceOwnerOperation::encodeGetDeviceOwner).collect(Collectors.toList());
        return objReturn;
    }

//    public JSONObject onGetProperties(JSONObject context) {
//        JSONArray array = new JSONArray();
//        try {
//            array = context.getJSONArray("properties");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        JSONObject objReturn = new JSONObject();
//        if (array == null) {
//            return objReturn;
//        }
//
//        List<PropertyOperation> list;
//        list = DecodeOperater.decodeGetProperty(array);
//        list.forEach(property -> operation.get(property));
//        List<JSONObject> result = list.stream().map(PropertyOperation::encodeGetPropertyResponse).collect(Collectors.toList());
//        objReturn = ReturnObjectOperate.fillReturnObject(result, "properties", requestId, intent);
//
//        return objReturn;
//    }
//
//
//    public JSONObject onSetProperties(JSONObject context) {
//        JSONArray array = new JSONArray();
//        try {
//            array = context.getJSONArray("properties");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        JSONObject objReturn = new JSONObject();
//        if (array == null) {
//            return objReturn;
//        }
//
//        List<PropertyOperation> list;
//        list = DecodeOperater.decodeSetProperty(array);
//        list.forEach((property) -> {
//            this.operation.set(property);
//        });
//
//        List<JSONObject> result = list.stream().map(PropertyOperation::encodeSetPropertyResponse).collect(Collectors.toList());
//        objReturn = ReturnObjectOperate.fillReturnObject(result, "properties", requestId, intent);
//        return objReturn;
//    }
//
//    public JSONObject onExecuteAction(JSONObject context) {
//        JSONArray array = new JSONArray();
//        try {
//            array = context.getJSONArray("action");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        JSONObject objReturn = new JSONObject();
//        if (array == null) {
//            return objReturn;
//        }
//        List<ActionOperation> list;
//        list = decodeOperater.decodeAction(array);
//        list.forEach((action1) -> {
//            this.operation.invoke(action1);
//        });
//        List<JSONObject> result = list.stream().map(ActionOperation::encodeResponse).collect(Collectors.toList());
//        objReturn = ReturnObjectOperate.fillReturnObject(result, "action", requestId, intent);
//        return objReturn;
//    }
//
//    public JSONObject onSubscribe(JSONObject context) {
//        JSONArray array = new JSONArray();
//        try {
//            array = context.getJSONArray("devices");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        JSONObject objReturn = new JSONObject();
//        if (array == null) {
//            return objReturn;
//        }
//
//        List<SubscribeOperation> list;
//        list = decodeOperater.decodeSetSubscribe(array);
//        list.forEach((subscribe) -> {
//            this.subscribe.set(subscribe);
//        });
//
//        List<JSONObject> result = list.stream().map(SubscribeOperation::encodeSetSubscribeResponse).collect(Collectors.toList());
//        objReturn = ReturnObjectOperate.fillReturnObject(result, "devices", requestId, intent);
//        return objReturn;
//    }
//
//    public JSONObject offSubscribe(JSONObject context) {
//        JSONArray array = new JSONArray();
//        try {
//            array = context.getJSONArray("devices");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        JSONObject objReturn = new JSONObject();
//        if (array == null) {
//            return objReturn;
//        }
//
//        List<SubscribeOperation> list;
//        list = decodeOperater.decodeSetSubscribe(array);
//        list.forEach((subscribe) -> {
//            this.subscribe.unset(subscribe);
//        });
//
//        List<JSONObject> result = list.stream().map(SubscribeOperation::encodeSetSubscribeResponse).collect(Collectors.toList());
//
//        objReturn = ReturnObjectOperate.fillReturnObject(result, "devices", requestId, intent);
//        return objReturn;
//    }
//
//    public JSONObject getDeviceStatus(JSONObject context) {
//        JSONArray array = new JSONArray();
//        try {
//            array = context.getJSONArray("devices");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        JSONObject objReturn = new JSONObject();
//        if (array == null) {
//            return objReturn;
//        }
//
//        List<StatusOperation> list;
//        list = DecodeOperater.decodeGetStatus(array);
//        list.forEach(deviceStatus -> {
//            this.deviceStatus.get(deviceStatus);
//        });
//
//        List<JSONObject> result = list.stream().map(StatusOperation::encodeGetStatusResponse).collect(Collectors.toList());
//
//        objReturn = ReturnObjectOperate.fillReturnObject(result, "devices", requestId, intent);
//
//        return objReturn;
//    }
}
