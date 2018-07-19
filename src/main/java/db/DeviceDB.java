package db;

import typedef.*;

import java.util.List;

public interface DeviceDB {


    List<Device> getDevices(String uid);

    Instance getInstance(String did);

    List<Services> getServices(Instance instance);

    List<Properties> getProperties(Services service);

    List<Actions> getActions(Services service);

    String getUid(String token);
}