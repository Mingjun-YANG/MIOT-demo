package typedef;

import org.json.JSONException;
import org.json.JSONObject;

public class SubscribeOperation extends AbstractOperation {

    // 设备ID
    public String did;

    // 订阅ID
    public String subscriptionId;


    public static SubscribeOperation decodeSetSubscribeRequest(JSONObject o) {
        SubscribeOperation subscribe = new SubscribeOperation();
        try {
            subscribe.did = o.getString("did");
            subscribe.subscriptionId = o.getString("subscriptionId");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return subscribe;
    }

    public JSONObject encodeSetSubscribeResponse() {
        JSONObject o = new JSONObject();
        try {
            o.put("did", this.did);
            o.put("subscriptionId", this.subscriptionId);
            o.put("status", this.status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (this.status != 0) {
            try {
                o.put("description", this.description);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return o;
    }

}
