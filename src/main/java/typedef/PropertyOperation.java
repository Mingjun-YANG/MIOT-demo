package typedef;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PropertyOperation extends AbstractOperation {

    public int iid;

    public String type;

    public String description;

    public String format;

    public Object value;

    public static List<PropertyOperation> decodeGetProperty(JSONArray array) {
        List<PropertyOperation> list = new ArrayList<>();
        try {
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                PropertyOperation property = new PropertyOperation();
                property.iid = object.getInt("iid");
                property.type = object.getString("type");
                property.description = object.getString("description");
                property.format = object.getString("format");
                property.value = object.get("value");
                list.add(property);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<JSONObject> decodeFillProperty(JSONObject object) {
        List<JSONObject> list = new ArrayList<>();
        for (int i = 0; i < object.length(); i++) {
            list.add(object);
        }

        return list;

//    public static PropertyOperation decodeSetPropertyRequest(JSONObject o) {
//        PropertyOperation property = new PropertyOperation();
//        try {
//            property.did = o.getString("did");
//            property.siid = o.getInt("siid");
//            property.piid = o.getInt("piid");
//            property.value = o.getInt("value");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return property;
//    }
//
//    public JSONObject encodeGetPropertyResponse() {
//        JSONObject o = new JSONObject();
//        o = toJSON(o);
//        if (this.status == 0) {
//            try {
//                o.put("value", this.value);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        } else {
//            try {
//                o.put("description", this.description);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return o;
//    }
//
//    public JSONObject encodeSetPropertyResponse() {
//        JSONObject o = new JSONObject();
//        o = toJSON(o);
//        if (this.status != 0) {
//            try {
//                o.put("description", this.description);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return o;
//    }
//
//    private JSONObject toJSON(JSONObject o) {
//        try {
//            o.put("did", this.did);
//            o.put("siid", this.siid);
//            o.put("piid", this.piid);
//            o.put("status", this.status);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return o;
//    }
    }
}

