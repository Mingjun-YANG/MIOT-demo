package deviceOwner;

import org.json.JSONException;
import org.json.JSONObject;
import typedef.Device;
import typedef.DeviceOwnerOperation;

import java.util.List;

public interface Ownership {

    List<Device> getDeviceOwnership(List<DeviceOwnerOperation> list, String uid) throws JSONException;

}
