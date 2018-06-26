package deviceOwner;

import org.json.JSONException;
import typedef.Device;
import typedef.DeviceOwnerOperation;

import java.util.List;

public interface Ownership {

    List<Device> getDeviceOwnership(List<DeviceOwnerOperation> list, String uid) throws JSONException;

}
