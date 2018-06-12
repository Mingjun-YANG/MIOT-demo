package operater;

import org.json.JSONArray;
import org.json.JSONObject;
import typedef.ActionOperation;
import typedef.PropertyOperation;
import typedef.StatusOperation;
import typedef.SubscribeOperation;

import java.util.ArrayList;
import java.util.List;

import static typedef.PropertyOperation.decodeGetPropertyRequest;
import static typedef.PropertyOperation.decodeSetPropertyRequest;
import static typedef.ActionOperation.decodeRequest;
import static typedef.SubscribeOperation.decodeSetSubscribeRequest;
import static typedef.StatusOperation.decodeGetStatusRequest;

public class DecodeOperater {

    public static List<PropertyOperation> decodeGetProperty(JSONArray o) {
        List<PropertyOperation> list = new ArrayList<>();
        if (o.length() > 0) {
            for (int i = 0; i < o.length(); i++) {
                JSONObject aaa = o.optJSONObject(i);
                PropertyOperation bbb = decodeGetPropertyRequest(aaa);
                list.add(bbb);
            }
        }
        return list;
    }

    public static List<PropertyOperation> decodeSetProperty(JSONArray o) {
        List<PropertyOperation> list = new ArrayList<>();
        if (o.length() > 0) {
            for (int i = 0; i < o.length(); i++) {
                JSONObject aaa = o.optJSONObject(i);
                PropertyOperation bbb = decodeSetPropertyRequest(aaa);
                list.add(bbb);
            }
        }
        return list;
    }

    public List<ActionOperation> decodeAction(JSONArray o) {

        List<ActionOperation> list = new ArrayList<>();
        if (o.length() > 0) {
            for (int i = 0; i < o.length(); i++) {
                JSONObject aaa = o.optJSONObject(i);
                ActionOperation bbb = decodeRequest(aaa);
                list.add(bbb);
            }
        }
        return list;
    }

    public List<SubscribeOperation> decodeSetSubscribe(JSONArray o) {

        List<SubscribeOperation> list = new ArrayList<>();
        if (o.length() > 0) {
            for (int i = 0; i < o.length(); i++) {
                JSONObject aaa = o.optJSONObject(i);
                SubscribeOperation bbb = decodeSetSubscribeRequest(aaa);
                list.add(bbb);
            }
        }
        return list;
    }

    public static List<StatusOperation> decodeGetStatus(JSONArray o) {

        List<StatusOperation> list = new ArrayList<>();
        if (o.length() > 0) {
            for (int i = 0; i < o.length(); i++) {
                JSONObject aaa = o.optJSONObject(i);
                StatusOperation bbb = decodeGetStatusRequest(aaa);
                list.add(bbb);
            }
        }
        return list;
    }
}
