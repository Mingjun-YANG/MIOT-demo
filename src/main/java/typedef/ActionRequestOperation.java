package typedef;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ActionRequestOperation {

    public String did;

    public String siid;

    public String aiid;

    public JSONArray in;

    public static ActionRequestOperation decodeActionRequest(JSONObject object) {
        ActionRequestOperation decodeActionRequest = new ActionRequestOperation();
        try {
            decodeActionRequest.did = object.getString("did");
            decodeActionRequest.siid = object.getString("siid");
            decodeActionRequest.aiid = object.getString("aiid");
            decodeActionRequest.in = object.getJSONArray("in");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return decodeActionRequest;
    }
}
