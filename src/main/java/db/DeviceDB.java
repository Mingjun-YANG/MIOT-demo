package db;

import org.json.JSONException;
import typedef.*;

import java.util.List;

public interface DeviceDB {


    List<Device> getDevices(String uid) throws JSONException;

    Instance getInstance(String did) throws JSONException;

    List<Services> getServices(Instance instance) throws JSONException;

    List<Properties> getProperties(Services service) throws JSONException;

    List<Actions> getActions(Services service) throws JSONException;

    String getUid(String token);
}