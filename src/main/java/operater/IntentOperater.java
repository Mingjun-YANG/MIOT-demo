package operater;

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




public class IntentOperater {

    private OAuthValidator validator;
    private Subscribe subscribe;
    private DecodeOperater decodeOperater;
    private HeaderValidator headerValidator;
    private IntentSwitcher intentSwitcher;
    private Ownership ownership;
    private Status deviceStatus;
    private String uid;

    private void ServiceHandler() {
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

        JSONObject objReturn = ReturnObjectOperate.fillReturnObject(jsonArray, "devices", requestId, intent);
//        ownership.getDeviceOwnership(uid);
//        List<JSONObject> result = ownership.getDeviceOwnership(uid).stream().map(DeviceOwnerOperation::encodeGetDeviceOwner).collect(Collectors.toList());

//        list.forEach(a-> ownership.getDeviceOwnership(uid));
//
//        ownership.getDeviceOwnership(uid);
//        List<JSONObject> result = list.stream().map(DeviceOwnerOperation::encodeGetDeviceOwner).collect(Collectors.toList());
        return objReturn;
    }

    public JSONObject onGetProperties (HttpServletResponse response, String requestId, String intent, String uid, JSONObject context) throws JSONException{
        List<DeviceOwnerOperation> list = decodeOperater.decodeGetDevice(uid);

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        list.forEach(deviceOwnerOperation -> {
            deviceOwnerOperation.DEV.forEach(device -> {
                try {
                    jsonObject.put("did",device.getDid());
                    jsonObject.put("type",device.getType());
                    jsonArray.put(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            });

        });

        JSONArray array = new JSONArray();
        try {
            array = context.getJSONArray("properties");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        List<PropertyRequestOperation> list1;
        list1 = DecodeOperater.decodeGetProperty(array);



        JSONArray idArray = new JSONArray();
        list1.forEach(PropertyRequestOperation -> {

            JSONObject idObject = new JSONObject();
            try {
                idObject.put("did",PropertyRequestOperation.did);
                idObject.put("siid",PropertyRequestOperation.siid);
                idObject.put("iid",PropertyRequestOperation.iid);
                idArray.put(idObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        for (int i = 0; i < jsonArray.length(); i++) {
            for (int j = 0; j < idArray.length(); j++) {
                if (jsonArray.getJSONObject(i).getString("did").equals(idArray.getJSONObject(j).getString("did"))) {
                    DatabaseOperater databaseOperater = new DatabaseOperater();
                    String st = databaseOperater.databaseReader(jsonArray.getJSONObject(i).getString("did"));
                    JSONObject json = new JSONObject(st);
                    List<ServiceOperation> s = ServiceOperation.decodeGetService(json);
                    List<JSONObject> listReturn = new ArrayList<>();
                    for (int k = 0; k < idArray.length(); k++) {
                        if (s.size() < idArray.getJSONObject(k).getInt("siid") || idArray.getJSONObject(k).getInt("siid") < 0) {
                            JSONObject objectReturn = new JSONObject();
                            objectReturn.put("did", idArray.getJSONObject(k).getInt("did"));
                            objectReturn.put("piid", idArray.getJSONObject(k).getInt("iid"));
                            objectReturn.put("siid", idArray.getJSONObject(k).getInt("siid"));
                            objectReturn.put("status", -2);
                            objectReturn.put("description", "Service not found");
                            listReturn.add(objectReturn);
                        }
                        else if (idArray.getJSONObject(k).getInt("iid") > s.get(idArray.getJSONObject(k).getInt("siid") - 1).properties.length() || idArray.getJSONObject(k).getInt("iid") < 0) {
                            JSONObject objectReturn = new JSONObject();
                            objectReturn.put("did", idArray.getJSONObject(k).getInt("did"));
                            objectReturn.put("piid", idArray.getJSONObject(k).getInt("iid"));
                            objectReturn.put("siid", idArray.getJSONObject(k).getInt("siid"));
                            objectReturn.put("status", -3);
                            objectReturn.put("description", "Property not found");
                            listReturn.add(objectReturn);
                        } else {
                            ServiceOperation a = s.get(idArray.getJSONObject(k).getInt("siid") - 1);
                            JSONObject object = a.properties.getJSONObject(idArray.getJSONObject(k).getInt("iid") - 1);
                            JSONObject objectReturn = new JSONObject();
                            objectReturn.put("value", object.getString("value"));
                            objectReturn.put("did", idArray.getJSONObject(k).getInt("did"));
                            objectReturn.put("piid", idArray.getJSONObject(k).getInt("iid"));
                            objectReturn.put("siid", idArray.getJSONObject(k).getInt("siid"));
                            objectReturn.put("status", 0);
                            listReturn.add(objectReturn);
                        }
                    }
                    System.out.println(listReturn);
                    response.setStatus(400);
                }
            }
        }












//        list1.forEach(property -> operation.get(property, jsonArray));
        return null;
    }
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
