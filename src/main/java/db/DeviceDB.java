package db;

import db.impl.DeviceDBLocalJsonImpl;
import org.json.JSONException;
import org.json.JSONObject;
import typedef.*;

import java.util.List;

public interface DeviceDB {

//    static DeviceDB create(JSONObject config) {
//        if (config.getXXXX() == XXX) {
//            return new DeviceDBLocalJsonImpl();
//        }
//
//        return new DeviceDBMySqlImpl();
//    }

    List<Device> getDevices(String uid) throws JSONException;

    Instance getInstance(String did) throws JSONException;

    List<Services> getServices(Instance instance) throws JSONException;

    List<Properties> getProperties(Services service) throws JSONException;

    List<Actions> getActions(Services service) throws JSONException;

    String getUid(String token);
}