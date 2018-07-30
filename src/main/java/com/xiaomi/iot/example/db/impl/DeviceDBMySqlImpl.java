package com.xiaomi.iot.example.db.impl;

import com.xiaomi.iot.example.db.DeviceDB;
import com.xiaomi.iot.example.typedef.*;

import java.util.List;

public class DeviceDBMySqlImpl implements DeviceDB {

    @Override
    public List<Device> getDevices(String uid) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public Instance getInstance(String did) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public List<Services> getServices(Instance instance) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public List<Property> getProperties(Services service) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public List<Actions> getActions(Services service) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public String getUid(String token) {
        throw new RuntimeException("not implemented");
    }
}
