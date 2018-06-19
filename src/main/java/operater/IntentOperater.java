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

    public JSONObject onGetDevices(HttpServletResponse response, String requestId, String intent, String uid) throws JSONException {
        decodeOperater = new DecodeOperater();
        ownership = new OwnershipImpl();
        List<DeviceOwnerOperation> list = decodeOperater.decodeGetDevice(uid);

        JSONArray jsonArray = new JSONArray();
        list.forEach(deviceOwnerOperation -> {

            deviceOwnerOperation.DEV.forEach(device -> {

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("did", device.getDid());
                    jsonObject.put("type", device.getType());

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

    public List<JSONObject> onGetProperties(HttpServletResponse response, String requestId, String intent, String uid, JSONObject context) throws JSONException {
        List<DeviceOwnerOperation> list = decodeOperater.decodeGetDevice(uid);

        JSONArray jsonArray = new JSONArray();
        list.forEach(deviceOwnerOperation -> {
            deviceOwnerOperation.DEV.forEach(device -> {
                try {
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.put("did", device.getDid());
                    jsonObject.put("type", device.getType());
                    jsonArray.put(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            });

        });
        JSONObject jsonObject = new JSONObject();
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
                idObject.put("did", PropertyRequestOperation.did);
                idObject.put("siid", PropertyRequestOperation.siid);
                idObject.put("iid", PropertyRequestOperation.iid);
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
                        } else if (idArray.getJSONObject(k).getInt("iid") > s.get(idArray.getJSONObject(k).getInt("siid") - 1).properties.length() || idArray.getJSONObject(k).getInt("iid") < 0) {
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
                    response.setStatus(200);
                }
            }
        }


//        list1.forEach(property -> operation.get(property, jsonArray));
        return null;
    }

    //
//
    public List<JSONObject> onSetProperties(HttpServletResponse response, String requestId, String intent, String uid, JSONObject context) throws JSONException {
        List<DeviceOwnerOperation> list = decodeOperater.decodeGetDevice(uid);

        JSONArray jsonArray = new JSONArray();
        list.forEach(deviceOwnerOperation -> {
            deviceOwnerOperation.DEV.forEach(device -> {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("did", device.getDid());
                    jsonObject.put("type", device.getType());
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
        list1 = DecodeOperater.decodeSetProperty(array);


        JSONArray idArray = new JSONArray();
        list1.forEach(PropertyRequestOperation -> {

            JSONObject idObject = new JSONObject();
            try {
                idObject.put("did", PropertyRequestOperation.did);
                idObject.put("siid", PropertyRequestOperation.siid);
                idObject.put("iid", PropertyRequestOperation.iid);
                idObject.put("value", PropertyRequestOperation.value);
                idArray.put(idObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        List<JSONObject> listReturn = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            for (int j = 0; j < idArray.length(); j++) {
                if (jsonArray.getJSONObject(i).getString("did").equals(idArray.getJSONObject(j).getString("did"))) {
                    DatabaseOperater databaseOperater = new DatabaseOperater();
                    String st = databaseOperater.databaseReader(jsonArray.getJSONObject(i).getString("did"));
                    JSONObject json = new JSONObject(st);
                    List<ServiceOperation> s = ServiceOperation.decodeGetService(json);
                    for (int k = 0; k < idArray.length(); k++) {
                        if (s.size() < idArray.getJSONObject(k).getInt("siid") || idArray.getJSONObject(k).getInt("siid") < 0) {
                            System.out.println(idArray.getJSONObject(k).getInt("siid"));
                            System.out.println(s.size());
                            JSONObject objectReturn = new JSONObject();
                            objectReturn.put("did", idArray.getJSONObject(k).getInt("did"));
                            objectReturn.put("piid", idArray.getJSONObject(k).getInt("iid"));
                            objectReturn.put("siid", idArray.getJSONObject(k).getInt("siid"));
                            objectReturn.put("status", -2);
                            objectReturn.put("description", "Service not found");
                            listReturn.add(objectReturn);
                        } else if (idArray.getJSONObject(k).getInt("iid") > s.get(idArray.getJSONObject(k).getInt("siid") - 1).properties.length() || idArray.getJSONObject(k).getInt("iid") < 0) {
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
                            System.out.println(ValueFormat.converter(object.getString("format")).getClass());
                            System.out.println(idArray.getJSONObject(j).get("value").getClass());
                            if (ValueFormat.converter(object.getString("format")).getClass() == idArray.getJSONObject(j).get("value").getClass()) {
                                if (ValueFormat.converter(object.getString("format")).getClass() == Integer.class) {
                                    boolean flag = false;
                                    System.out.println(object.getJSONArray("access").getString(1));
                                    for (int m = 0; m < object.getJSONArray("access").length(); m++) {
                                        if (object.getJSONArray("access").getString(m).equals("write")) {
                                            flag = true;
                                            break;
                                        }
                                    }
                                    if (!flag) {
                                        JSONObject objectReturn = new JSONObject();
                                        objectReturn.put("did", idArray.getJSONObject(k).getInt("did"));
                                        objectReturn.put("piid", idArray.getJSONObject(k).getInt("iid"));
                                        objectReturn.put("siid", idArray.getJSONObject(k).getInt("siid"));
                                        objectReturn.put("status", -8);
                                        objectReturn.put("description", "Property is not writeable");
                                        listReturn.add(objectReturn);
                                    } else if (flag) {
                                        if (object.optJSONArray("value-range") != null) {
                                            int min = ValueFormat.toInteger(object.getJSONArray("value-range").get(0));
                                            int max = ValueFormat.toInteger(object.getJSONArray("value-range").get(1));
                                            if (ValueFormat.toInteger(idArray.getJSONObject(k).getInt("value")) < min || ValueFormat.toInteger(idArray.getJSONObject(k).getInt("value")) > max) {
                                                JSONObject objectReturn = new JSONObject();
                                                objectReturn.put("did", idArray.getJSONObject(k).getInt("did"));
                                                objectReturn.put("piid", idArray.getJSONObject(k).getInt("iid"));
                                                objectReturn.put("siid", idArray.getJSONObject(k).getInt("siid"));
                                                objectReturn.put("status", -10);
                                                objectReturn.put("description", "Property out of range");
                                                listReturn.add(objectReturn);
                                            }
                                        } else if (object.optJSONArray("value-list") != null) {
                                            if (ValueFormat.toFloat(idArray.getJSONObject(k).getInt("value")) < 0 || ValueFormat.toFloat(idArray.getJSONObject(k).getInt("value")) > object.getJSONArray("value-list").length()) {
                                                JSONObject objectReturn = new JSONObject();
                                                objectReturn.put("did", idArray.getJSONObject(k).getInt("did"));
                                                objectReturn.put("piid", idArray.getJSONObject(k).getInt("iid"));
                                                objectReturn.put("siid", idArray.getJSONObject(k).getInt("siid"));
                                                objectReturn.put("status", -10);
                                                objectReturn.put("description", "Property out of range");
                                                listReturn.add(objectReturn);
                                            }
                                        }
                                        JSONObject objectReturn = new JSONObject();
                                        objectReturn.put("did", idArray.getJSONObject(k).getInt("did"));
                                        objectReturn.put("piid", idArray.getJSONObject(k).getInt("iid"));
                                        objectReturn.put("siid", idArray.getJSONObject(k).getInt("siid"));
                                        objectReturn.put("status", 0);
                                        listReturn.add(objectReturn);
                                        break;
                                    }
                                } else if (ValueFormat.converter(object.getString("format")).getClass() == Float.class) {
                                    boolean flag = false;
                                    for (int m = 0; m < object.getJSONArray("access").length(); m++) {
                                        if (object.getJSONArray("access").get(m) == "write") {
                                            flag = true;
                                            break;
                                        }
                                    }
                                    if (!flag) {
                                        JSONObject objectReturn = new JSONObject();
                                        objectReturn.put("did", idArray.getJSONObject(k).getInt("did"));
                                        objectReturn.put("piid", idArray.getJSONObject(k).getInt("iid"));
                                        objectReturn.put("siid", idArray.getJSONObject(k).getInt("siid"));
                                        objectReturn.put("status", -8);
                                        objectReturn.put("description", "Property is not writeable");
                                        listReturn.add(objectReturn);
                                    } else if (flag) {
                                        if (object.optJSONArray("value-range") != null) {
                                            float min = ValueFormat.toFloat(object.optJSONArray("value-range").get(0));
                                            float max = ValueFormat.toFloat(object.optJSONArray("value-range").get(1));
                                            if (ValueFormat.toFloat(idArray.getJSONObject(k).getInt("value")) < min || ValueFormat.toFloat(idArray.getJSONObject(k).getInt("value")) > max) {
                                                JSONObject objectReturn = new JSONObject();
                                                objectReturn.put("did", idArray.getJSONObject(k).getInt("did"));
                                                objectReturn.put("piid", idArray.getJSONObject(k).getInt("iid"));
                                                objectReturn.put("siid", idArray.getJSONObject(k).getInt("siid"));
                                                objectReturn.put("status", -10);
                                                objectReturn.put("description", "Property out of range");
                                                listReturn.add(objectReturn);
                                                break;
                                            }
                                        } else if (object.optJSONArray("value-list") != null) {
                                            if (ValueFormat.toFloat(idArray.getJSONObject(k).getInt("value")) < 0 || ValueFormat.toFloat(idArray.getJSONObject(k).getInt("value")) > object.getJSONArray("value-list").length()) {
                                                JSONObject objectReturn = new JSONObject();
                                                objectReturn.put("did", idArray.getJSONObject(k).getInt("did"));
                                                objectReturn.put("piid", idArray.getJSONObject(k).getInt("iid"));
                                                objectReturn.put("siid", idArray.getJSONObject(k).getInt("siid"));
                                                objectReturn.put("status", -10);
                                                objectReturn.put("description", "Property out of range");
                                                listReturn.add(objectReturn);
                                                break;
                                            }
                                        }
                                        JSONObject objectReturn = new JSONObject();
                                        objectReturn.put("did", idArray.getJSONObject(k).getInt("did"));
                                        objectReturn.put("piid", idArray.getJSONObject(k).getInt("iid"));
                                        objectReturn.put("siid", idArray.getJSONObject(k).getInt("siid"));
                                        objectReturn.put("status", 0);
                                        listReturn.add(objectReturn);
                                        break;
                                    }
                                } else if (ValueFormat.converter(object.getString("format")).getClass() == Boolean.class) {
                                    boolean flag = false;
                                    for (int m = 0; m < object.getJSONArray("access").length(); m++) {
                                        if (object.getJSONArray("access").getString(m).equals("write")) {
                                            flag = true;
                                            break;
                                        }
                                    }
                                    if (!flag) {
                                        JSONObject objectReturn = new JSONObject();
                                        objectReturn.put("did", idArray.getJSONObject(k).getInt("did"));
                                        objectReturn.put("piid", idArray.getJSONObject(k).getInt("iid"));
                                        objectReturn.put("siid", idArray.getJSONObject(k).getInt("siid"));
                                        objectReturn.put("status", -8);
                                        objectReturn.put("description", "Property is not writeable");
                                        listReturn.add(objectReturn);
                                        break;
                                    }
                                    JSONObject objectReturn = new JSONObject();
                                    objectReturn.put("did", idArray.getJSONObject(k).getInt("did"));
                                    objectReturn.put("piid", idArray.getJSONObject(k).getInt("iid"));
                                    objectReturn.put("siid", idArray.getJSONObject(k).getInt("siid"));
                                    objectReturn.put("status", 0);
                                    listReturn.add(objectReturn);
                                    break;
                                }
                            }
                            else {
                                JSONObject objectReturn = new JSONObject();
                                objectReturn.put("did", idArray.getJSONObject(k).getInt("did"));
                                objectReturn.put("piid", idArray.getJSONObject(k).getInt("iid"));
                                objectReturn.put("siid", idArray.getJSONObject(k).getInt("siid"));
                                objectReturn.put("status", -10);
                                objectReturn.put("description", "Property format is wrong");
                                listReturn.add(objectReturn);
                            }
                        }
                    }
                }
            }
        }
        response.setStatus(200);
        return listReturn;
    }
}

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
