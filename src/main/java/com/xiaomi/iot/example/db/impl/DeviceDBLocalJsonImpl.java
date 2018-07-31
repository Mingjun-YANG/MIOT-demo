package com.xiaomi.iot.example.db.impl;

import com.xiaomi.iot.example.db.DeviceDB;
import com.xiaomi.iot.example.exeception.MyException;
import com.xiaomi.iot.example.operater.DatabaseOperater;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.xiaomi.iot.example.typedef.*;

import java.util.ArrayList;
import java.util.List;

public class DeviceDBLocalJsonImpl implements DeviceDB {

    private JSONObject json;

    @Override
    public String getUid(String token) throws Exception {
        DatabaseOperater reader = new DatabaseOperater();
        String string = reader.databaseReader("account");
        json = new JSONObject(string);

        JSONArray array = json.optJSONArray("db");
        for (int i = 0; i < array.length(); i++) {
            if (array.optJSONObject(i).optString("token").equals(token)) {
                return array.optJSONObject(i).optString("uid");
            }
        }

        throw new MyException(200, "Token invalid");
    }

    @Override
    public List<Device> getDevices(String uid) throws JSONException {
        DatabaseOperater reader = new DatabaseOperater();
        String string = reader.databaseReader("devices");
        JSONObject json = new JSONObject(string);

        JSONArray array = json.optJSONArray("db");
        List<Device> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            if (array.optJSONObject(i).optString("uid").equals(uid)) {
                Device device = new Device();
                device.setType(array.optJSONObject(i).optJSONArray("devices").optJSONObject(0).optString("type"));
                device.setDid(array.optJSONObject(i).optJSONArray("devices").optJSONObject(0).optString("did"));
                list.add(device);
            }
        }

        return list;
    }

    @Override
    public Instance getInstance(String did) throws JSONException {
        DatabaseOperater reader = new DatabaseOperater();
        String string = reader.databaseReader(did);
        JSONObject json = new JSONObject(string);
        Instance instance = new Instance();
        instance.setType(json.optString("type"));
        instance.setDescription(json.optString("description"));
        instance.setStatus(json.optString("status"));
        instance.setSubscriptionId(json.optString("subscriptionId"));
        instance.setCustomized_services(json.optJSONArray("custonized-services"));
        instance.setServices(json.optJSONArray("services"));

        return instance;
    }

    @Override
    public List<Services> getServices(Instance instance) {
        List<Services> list = new ArrayList<>();
        for (int i = 0; i < instance.getServices().length(); i++) {
            Services services = new Services();
            services.setType(instance.getServices().optJSONObject(i).optString("type"));
            services.setSiid(instance.getServices().optJSONObject(i).optInt("iid"));
            services.setDescription(instance.getServices().optJSONObject(i).optString("description"));
            services.setProperties(instance.getServices().optJSONObject(i).optJSONArray("properties"));
            services.setActions(instance.getServices().optJSONObject(i).optJSONArray("actions"));
            list.add(services);
        }

        return list;
    }

    @Override
    public List<Property> getProperties(Services service) {
        List<Property> list = new ArrayList<>();
        for (int i = 0; i < service.getProperties().length(); i++) {
            Property property = new Property();
            property.setIid(service.getProperties().optJSONObject(i).optInt("iid"));
            property.setType(service.getProperties().optJSONObject(i).optString("type"));
            property.setFormat(service.getProperties().optJSONObject(i).optString("format"));
            property.setValue(service.getProperties().optJSONObject(i).opt("value"));
            property.setAccess(service.getProperties().optJSONObject(i).optJSONArray("access"));
            property.setDescription(service.getProperties().optJSONObject(i).optString("description"));
            property.setValue_range(service.getProperties().optJSONObject(i).optJSONArray("value-range"));
            property.setUnit(service.getProperties().optJSONObject(i).optString("unit"));
            list.add(property);
        }

        return list;
    }

    @Override
    public List<Actions> getActions(Services service) {
        List<Actions> list = new ArrayList<>();
        for (int i = 0; i < service.getActions().length(); i++) {
            Actions action = new Actions();
            action.setIid(service.getActions().optJSONObject(i).optInt("iid"));
            action.setType(service.getActions().optJSONObject(i).optString("type"));
            action.setDescription(service.getActions().optJSONObject(i).optString("description"));
            action.setIn(service.getActions().optJSONObject(i).optJSONArray("in"));
            action.setOut(service.getActions().optJSONObject(i).optJSONArray("out"));
            list.add(action);
        }

        return list;
    }
}
