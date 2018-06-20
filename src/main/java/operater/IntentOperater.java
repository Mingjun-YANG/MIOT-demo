package operater;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import typedef.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;


public class IntentOperater {

    private DecodeOperater decodeOperater;
    private DatabaseOperater deviceOwnerOperation;

    public JSONObject onGetDevices(HttpServletResponse response, String requestId, String intent, String uid) throws JSONException {
        decodeOperater = new DecodeOperater();
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
//                    jsonObject.put("subscriptionId", device.getSubscriptionId());
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
                idObject.put("did", PropertyRequestOperation.did);
                idObject.put("siid", PropertyRequestOperation.siid);
                idObject.put("iid", PropertyRequestOperation.iid);
                idArray.put(idObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        List<JSONObject> listReturn = new ArrayList<>();

        for (int j = 0; j < idArray.length(); j++) {
            for (int i = 0; i < jsonArray.length(); i++) {
                if (jsonArray.getJSONObject(i).getString("did").equals(idArray.getJSONObject(j).getString("did"))) {
                    DatabaseOperater databaseOperater = new DatabaseOperater();
                    String st = databaseOperater.databaseReader(jsonArray.getJSONObject(i).getString("did"));
                    JSONObject json = new JSONObject(st);
                    List<ServiceOperation> s = ServiceOperation.decodeGetService(json);
                    if (s.size() < idArray.getJSONObject(j).getInt("siid") || idArray.getJSONObject(j).getInt("siid") < 0) {
                        listReturn.add(ResponseOperater.fillResponse(-2, j, idArray));
                    } else if (idArray.getJSONObject(j).getInt("iid") > s.get(idArray.getJSONObject(j).getInt("siid") - 1).properties.length() || idArray.getJSONObject(j).getInt("iid") < 0) {
                        listReturn.add(ResponseOperater.fillResponse(-3, j, idArray));
                    } else {
                        ServiceOperation a = s.get(idArray.getJSONObject(j).getInt("siid") - 1);
                        JSONObject object = a.properties.getJSONObject(idArray.getJSONObject(j).getInt("iid") - 1);
                        JSONObject objectReturn = new JSONObject();
                        objectReturn.put("value", object.getString("value"));
                        objectReturn.put("did", idArray.getJSONObject(j).getInt("did"));
                        objectReturn.put("piid", idArray.getJSONObject(j).getInt("iid"));
                        objectReturn.put("siid", idArray.getJSONObject(j).getInt("siid"));
                        objectReturn.put("status", 0);
                        listReturn.add(objectReturn);
                    }


                }
            }
        }
        System.out.println(listReturn);
        response.setStatus(200);

        return null;
    }

    public List<JSONObject> onGetStatus(HttpServletResponse response, String requestId, String intent, String uid, JSONObject context) throws JSONException {
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

        JSONArray idArray = new JSONArray();
        try {
            idArray = context.getJSONArray("devices");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        List<JSONObject> listReturn = new ArrayList<>();

        for (int j = 0; j < idArray.length(); j++) {
            for (int i = 0; i < jsonArray.length(); i++) {

                if (jsonArray.getJSONObject(i).getString("did").equals(idArray.getString(j))) {
                    DatabaseOperater databaseOperater = new DatabaseOperater();
                    String st = databaseOperater.databaseReader(jsonArray.getJSONObject(i).getString("did"));
                    JSONObject json = new JSONObject(st);

                    listReturn.add(ResponseOperater.fillStatusResponse(0, j, json, idArray));
                    break;

                } else {
                    listReturn.add(ResponseOperater.fillStatusResponse(-1, j, jsonArray.getJSONObject(i) , idArray));
                    break;
                }
            }
        }
        response.setStatus(200);
        return listReturn;

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

        for (int j = 0; j < idArray.length(); j++) {
            for (int i = 0; i < jsonArray.length(); i++) {
                if (jsonArray.getJSONObject(i).getString("did").equals(idArray.getJSONObject(j).getString("did"))) {
                    DatabaseOperater databaseOperater = new DatabaseOperater();
                    String st = databaseOperater.databaseReader(jsonArray.getJSONObject(i).getString("did"));
                    JSONObject json = new JSONObject(st);


                    System.out.println(json.getString("subscriptionId"));


                    List<ServiceOperation> s = ServiceOperation.decodeGetService(json);
                    for (int k = 1; k < idArray.length(); k++) {
                        if (s.size() < idArray.getJSONObject(j).getInt("siid") || idArray.getJSONObject(j).getInt("siid") < 0) {
                            listReturn.add(ResponseOperater.fillResponse(-2, j, idArray));
                            break;
                        } else if (idArray.getJSONObject(j).getInt("iid") > s.get(idArray.getJSONObject(j).getInt("siid") - 1).properties.length() || idArray.getJSONObject(j).getInt("iid") < 0) {
                            listReturn.add(ResponseOperater.fillResponse(-3, j, idArray));
                            break;
                        } else {
                            ServiceOperation a = s.get(idArray.getJSONObject(j).getInt("siid") - 1);
                            JSONObject object = a.properties.getJSONObject(idArray.getJSONObject(j).getInt("iid") - 1);
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
                                        listReturn.add(ResponseOperater.fillResponse(-8, j, idArray));
                                        break;
                                    } else if (flag) {
                                        if (object.optJSONArray("value-range") != null) {
                                            int min = ValueFormat.toInteger(object.getJSONArray("value-range").get(0));
                                            int max = ValueFormat.toInteger(object.getJSONArray("value-range").get(1));
                                            if (ValueFormat.toInteger(idArray.getJSONObject(j).getInt("value")) < min || ValueFormat.toInteger(idArray.getJSONObject(j).getInt("value")) > max) {
                                                listReturn.add(ResponseOperater.fillResponse(-10, j, idArray));
                                                break;
                                            }
                                        } else if (object.optJSONArray("value-list") != null) {
                                            if (ValueFormat.toFloat(idArray.getJSONObject(j).getInt("value")) < 0 || ValueFormat.toFloat(idArray.getJSONObject(j).getInt("value")) > object.getJSONArray("value-list").length()) {
                                                listReturn.add(ResponseOperater.fillResponse(-10, j, idArray));
                                                break;
                                            }
                                        }
                                        listReturn.add(ResponseOperater.fillResponse(0, j, idArray));
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
                                        listReturn.add(ResponseOperater.fillResponse(-8, j, idArray));
                                        break;
                                    } else if (flag) {
                                        if (object.optJSONArray("value-range") != null) {
                                            float min = ValueFormat.toFloat(object.optJSONArray("value-range").get(0));
                                            float max = ValueFormat.toFloat(object.optJSONArray("value-range").get(1));
                                            if (ValueFormat.toFloat(idArray.getJSONObject(j).getInt("value")) < min || ValueFormat.toFloat(idArray.getJSONObject(j).getInt("value")) > max) {
                                                listReturn.add(ResponseOperater.fillResponse(-10, j, idArray));
                                            }
                                        } else if (object.optJSONArray("value-list") != null) {
                                            if (ValueFormat.toFloat(idArray.getJSONObject(j).getInt("value")) < 0 || ValueFormat.toFloat(idArray.getJSONObject(j).getInt("value")) > object.getJSONArray("value-list").length()) {
                                                listReturn.add(ResponseOperater.fillResponse(-10, j, idArray));
                                                break;
                                            }
                                        }
                                        listReturn.add(ResponseOperater.fillResponse(0, j, idArray));
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
                                        listReturn.add(ResponseOperater.fillResponse(-8, j, idArray));
                                        break;
                                    }
                                    listReturn.add(ResponseOperater.fillResponse(0, j, idArray));
                                    break;
                                }
                            } else {
                                listReturn.add(ResponseOperater.fillResponse(-10, j, idArray));
                                break;
                            }
                        }
                    }
                } else if (i == jsonArray.length() - 1 && j != idArray.length() - 1) {
                    listReturn.add(ResponseOperater.fillResponse(-1, j, idArray));
                    break;
                }
            }
        }
        response.setStatus(200);
        return listReturn;
    }

    public List<JSONObject> onSubscribe(HttpServletResponse response, String requestId, String intent, String uid, JSONObject context) throws JSONException {
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
            array = context.getJSONArray("devices");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        List<SubscribeRequestOperation> list1;
        list1 = DecodeOperater.decodeSubscribe(array);


        JSONArray idArray = new JSONArray();
        list1.forEach(SubscribeRequestOperation -> {

            JSONObject idObject = new JSONObject();
            try {
                idObject.put("did", SubscribeRequestOperation.did);
                idObject.put("subscriptionId", SubscribeRequestOperation.subscriptionId);
                idArray.put(idObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        List<JSONObject> listReturn = new ArrayList<>();

        for (int j = 0; j < idArray.length(); j++) {
            for (int i = 0; i < jsonArray.length(); i++) {

                if (jsonArray.getJSONObject(i).getString("did").equals(idArray.getJSONObject(j).getString("did"))) {
                    DatabaseOperater databaseOperater = new DatabaseOperater();
                    String st = databaseOperater.databaseReader(jsonArray.getJSONObject(i).getString("did"));
                    JSONObject json = new JSONObject(st);

                    if (json.getString("subscriptionId").equals(idArray.getJSONObject(i).getString("subscriptionId"))) {
                        listReturn.add(ResponseOperater.fillResponse(0, j, idArray, json.getString("subscriptionId")));
                        break;
                    } else {
                        listReturn.add(ResponseOperater.fillResponse(-16, j, idArray, "invalid subscriptionId"));
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
