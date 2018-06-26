package operater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import typedef.*;

import java.util.ArrayList;
import java.util.List;


public class DecodeOperater {

    static List<DeviceOwnerOperation> decodeGetDevice(String uid) throws JSONException {

        DatabaseOperater reader = new DatabaseOperater();
        String string = reader.databaseReader("devices");
        JSONObject json = new JSONObject(string);
        JSONArray array = json.getJSONArray("db");

        List<DeviceOwnerOperation> list = new ArrayList<>();
        if (array.length() > 0) {
            for (int i = 0; i < array.length(); i++) {
                JSONObject aaa = array.optJSONObject(i);
                if (DeviceOwnerOperation.decodeGetDeviceOwner(aaa, uid).DEV.size() != 0) {
                    list.add(DeviceOwnerOperation.decodeGetDeviceOwner(aaa, uid));
                }
            }
        }
        return list;
    }

    static List<PropertyRequestOperation> decodeGetProperty(JSONArray o) {
        List<PropertyRequestOperation> list = new ArrayList<>();
        if (o.length() > 0) {
            for (int i = 0; i < o.length(); i++) {
                JSONObject aaa = o.optJSONObject(i);
                PropertyRequestOperation bbb = PropertyRequestOperation.decodeGetPropertyRequest(aaa);
                list.add(bbb);
            }
        }
        return list;
    }

    static List<PropertyRequestOperation> decodeSetProperty(JSONArray o) {
        List<PropertyRequestOperation> list = new ArrayList<>();
        if (o.length() > 0) {
            for (int i = 0; i < o.length(); i++) {
                JSONObject aaa = o.optJSONObject(i);
                PropertyRequestOperation bbb = PropertyRequestOperation.decodeSetPropertyRequest(aaa);
                list.add(bbb);
            }
        }
        return list;
    }

    static List<SubscribeRequestOperation> decodeSubscribe(JSONArray o) {
        List<SubscribeRequestOperation> list = new ArrayList<>();
        if (o.length() > 0) {
            for (int i = 0; i < o.length(); i++) {
                JSONObject aaa = o.optJSONObject(i);
                SubscribeRequestOperation bbb = SubscribeRequestOperation.decodeGetSubscribeRequest(aaa);
                list.add(bbb);
            }
        }
        return list;
    }

    static List<GetStatusRequestOperation> decodeGetStatus(JSONArray o) {
        List<GetStatusRequestOperation> list = new ArrayList<>();
        if (o.length() > 0) {
            for (int i = 0; i < o.length(); i++) {
                JSONObject aaa = o.optJSONObject(i);
                GetStatusRequestOperation bbb = GetStatusRequestOperation.decodeGetStatusRequest(aaa);
                list.add(bbb);
            }
        }
        return list;
    }

    static List<ActionRequestOperation> decodeAction(JSONArray o) {

        List<ActionRequestOperation> list = new ArrayList<>();
        if (o.length() > 0) {
            for (int i = 0; i < o.length(); i++) {
                JSONObject aaa = o.optJSONObject(i);
                ActionRequestOperation bbb = ActionRequestOperation.decodeActionRequest(aaa);
                list.add(bbb);
            }
        }
        return list;
    }
}
