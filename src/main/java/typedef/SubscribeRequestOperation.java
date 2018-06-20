package typedef;

import org.json.JSONException;
import org.json.JSONObject;

public class SubscribeRequestOperation {

    public String did;

    public String subscriptionId;

    public static SubscribeRequestOperation decodeGetSubscribeRequest(JSONObject object) {
        SubscribeRequestOperation subscribeRequest = new SubscribeRequestOperation();
        try {
            subscribeRequest.did = object.getString("did");
            subscribeRequest.subscriptionId = object.getString("subscriptionId");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return subscribeRequest;
    }
}
