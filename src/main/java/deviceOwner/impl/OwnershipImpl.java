package deviceOwner.impl;

import deviceOwner.Ownership;
import org.json.JSONException;
import typedef.Device;
import typedef.DeviceOwnerOperation;

import java.util.ArrayList;
import java.util.List;

public class OwnershipImpl implements Ownership {

    @Override
    public List<Device> getDeviceOwnership(List<DeviceOwnerOperation> list, String uid) throws JSONException {
        List<Device> deviceList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).uid.equals(uid)) {
                deviceList.add(i, list.get(i).DEV.get(0));
            }
        }
        return deviceList;
    }


}
