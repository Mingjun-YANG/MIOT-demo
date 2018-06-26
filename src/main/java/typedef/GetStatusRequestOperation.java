package typedef;

import org.json.JSONException;
import org.json.JSONObject;

public class GetStatusRequestOperation {

    private String did;

    private String status;

    public static GetStatusRequestOperation decodeGetStatusRequest(JSONObject object) {
        GetStatusRequestOperation decodeGetStatusRequest = new GetStatusRequestOperation();
        try {
            decodeGetStatusRequest.did = object.getString("did");
            decodeGetStatusRequest.status = object.getString("status");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return decodeGetStatusRequest;
    }
}
