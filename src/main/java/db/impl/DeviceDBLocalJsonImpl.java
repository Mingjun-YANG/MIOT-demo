package db.impl;

import db.DeviceDB;
import operater.DatabaseOperater;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import typedef.*;

import java.util.ArrayList;
import java.util.List;

public class DeviceDBLocalJsonImpl implements DeviceDB {

    private JSONObject json;

    @Override
    public String getUid(String token) {
        DatabaseOperater reader = new DatabaseOperater();
        String string = reader.databaseReader("account");
        try {
            json = new JSONObject(string);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray array = json.optJSONArray("db");
        for (int i = 0; i < array.length(); i++) {
            if (array.optJSONObject(i).optString("token").equals(token)) {
                return array.optJSONObject(i).optString("uid");
            }
        }
        return "NOTFOUND";
    }


    @Override
    public List<Device> getDevices(String uid) throws JSONException {
        DatabaseOperater reader = new DatabaseOperater();
        String string = reader.databaseReader("devices");
        JSONObject json = new JSONObject(string);
        JSONArray array = json.getJSONArray("db");
        List<Device> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            if (array.getJSONObject(i).getString("uid").equals(uid)) {
                Device device = new Device();
                device.setType(array.getJSONObject(i).getJSONArray("devices").getJSONObject(0).getString("type"));
                device.setDid(array.getJSONObject(i).getJSONArray("devices").getJSONObject(0).getString("did"));
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
    public List<Services> getServices(Instance instance) throws JSONException {
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
    public List<Properties> getProperties(Services service) throws JSONException {
        List<Properties> list = new ArrayList<>();
        for (int i = 0; i < service.getProperties().length(); i++) {
            Properties property = new Properties();
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
    public List<Actions> getActions(Services service) throws JSONException {
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
