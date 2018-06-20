package typedef;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class DeviceOwnerOperation extends AbstractOperation{

    public  ArrayList<Device> DEV = new ArrayList<>();
    // 功能ID
    public String uid;

    public static DeviceOwnerOperation decodeGetDeviceOwner(JSONObject o, String uid) {
        DeviceOwnerOperation ownership = new DeviceOwnerOperation();
        try {
             JSONArray devices = o.getJSONArray("devices");
            ownership.uid = o.getString("uid");
            for(int i = 0 ; i < devices.length() ; i++)
                if (uid.equals(ownership.uid)) {
                    JSONObject object = devices.getJSONObject(i);
                    Device device = new Device();
                    device.setDid(object.getString("did"));
                    device.setType(object.getString("type"));
                    ownership.DEV.add(device);
                }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ownership;
    }
}



