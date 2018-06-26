package operater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReturnObjectOperate {

    public static JSONObject fillReturnObject(JSONArray list, String name, String requestId, String intent) {
        JSONObject objReturn = new JSONObject();

        try {
            objReturn.put("requestId", requestId);
            objReturn.put("intent", intent);
            objReturn.put(name, list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return objReturn;
    }
}
