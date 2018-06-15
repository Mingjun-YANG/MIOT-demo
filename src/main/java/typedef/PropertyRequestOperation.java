package typedef;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PropertyRequestOperation{

    public String did;

    public int siid;

    public int iid;

    public static PropertyRequestOperation decodeGetPropertyRequest(JSONObject object) {
        PropertyRequestOperation propertyRequest = new PropertyRequestOperation();
        try {
                propertyRequest.did = object.getString("did");
                propertyRequest.siid = object.getInt("siid");
                propertyRequest.iid = object.getInt("iid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return propertyRequest;
    }
}
