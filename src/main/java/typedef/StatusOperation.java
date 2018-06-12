package typedef;

import org.json.JSONException;
import org.json.JSONObject;

public class StatusOperation extends AbstractOperation {

    // 设备ID
    public String did;

    //设备名字
    public String name;

    //在线与否
    public String online;

    public static StatusOperation decodeGetStatusRequest(JSONObject o) {
        StatusOperation deviceStatus = new StatusOperation();
        try {
            deviceStatus.did = o.getString("did");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return deviceStatus;
    }

    public JSONObject encodeGetStatusResponse() {
        JSONObject o = new JSONObject();
        try {
            o.put("did", this.did);
            o.put("status", this.status);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        if (this.status == 0) {
            try {
                o.put("online", this.online);
                o.put("name", this.name);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                o.put("description", this.description);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return o;
    }
}
