package db;

import typedef.*;

import java.util.List;

public interface DeviceDB {

    List<Device> getDevices(String uid) throws Exception;

    Instance getInstance(String did) throws Exception;

    List<Services> getServices(Instance instance);

    List<Property> getProperties(Services service);

    List<Actions> getActions(Services service);

    String getUid(String token) throws Exception;
}