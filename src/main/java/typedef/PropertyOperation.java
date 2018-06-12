package typedef;

import org.json.JSONException;
import org.json.JSONObject;

public class PropertyOperation extends AbstractOperation {

    // 设备ID
    public String did;

    // 功能ID
    public int siid;

    // 属性ID
    public int piid;

    // 属性值
    public Object value;

    public static PropertyOperation decodeGetPropertyRequest(JSONObject o) {
        PropertyOperation property = new PropertyOperation();
        try {
            property.did = o.getString("did");
            property.siid = o.getInt("siid");
            property.piid = o.getInt("piid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return property;
    }

    public static PropertyOperation decodeSetPropertyRequest(JSONObject o) {
        PropertyOperation property = new PropertyOperation();
        try {
            property.did = o.getString("did");
            property.siid = o.getInt("siid");
            property.piid = o.getInt("piid");
            property.value = o.getInt("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return property;
    }

    public JSONObject encodeGetPropertyResponse() {
        JSONObject o = new JSONObject();
        o = toJSON(o);
        if (this.status == 0) {
            try {
                o.put("value", this.value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            try {
                o.put("description", this.description);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return o;
    }

    public JSONObject encodeSetPropertyResponse() {
        JSONObject o = new JSONObject();
        o = toJSON(o);
        if (this.status != 0) {
            try {
                o.put("description", this.description);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return o;
    }

    private JSONObject toJSON(JSONObject o) {
        try {
            o.put("did", this.did);
            o.put("siid", this.siid);
            o.put("piid", this.piid);
            o.put("status", this.status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return o;
    }
}
