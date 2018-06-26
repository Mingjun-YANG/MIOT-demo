package operater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


class ResponseOperater {


    static JSONObject fillResponse(int i, int j, JSONArray idArray) throws JSONException {
        JSONObject objectReturn = new JSONObject();
        switch (i) {
            case 0:
                objectReturn.put("did", idArray.getJSONObject(j).getInt("did"));
                objectReturn.put("piid", idArray.getJSONObject(j).getInt("iid"));
                objectReturn.put("siid", idArray.getJSONObject(j).getInt("siid"));
                objectReturn.put("status", 0);
                return objectReturn;
            case -1:
                objectReturn.put("did", idArray.getJSONObject(j).getInt("did"));
                objectReturn.put("piid", idArray.getJSONObject(j).getInt("iid"));
                objectReturn.put("siid", idArray.getJSONObject(j).getInt("siid"));
                objectReturn.put("status", -1);
                objectReturn.put("description", "Device not found");
                return objectReturn;
            case -2:
                objectReturn.put("did", idArray.getJSONObject(j).getInt("did"));
                objectReturn.put("piid", idArray.getJSONObject(j).getInt("iid"));
                objectReturn.put("siid", idArray.getJSONObject(j).getInt("siid"));
                objectReturn.put("status", -2);
                objectReturn.put("description", "Service not found");
                return objectReturn;
            case -3:
                objectReturn.put("did", idArray.getJSONObject(j).getInt("did"));
                objectReturn.put("piid", idArray.getJSONObject(j).getInt("iid"));
                objectReturn.put("siid", idArray.getJSONObject(j).getInt("siid"));
                objectReturn.put("status", -3);
                objectReturn.put("description", "Property not found");
                return objectReturn;
            case -5:
                objectReturn.put("did", idArray.getJSONObject(j).getInt("did"));
                objectReturn.put("aiid", idArray.getJSONObject(j).getInt("iid"));
                objectReturn.put("siid", idArray.getJSONObject(j).getInt("siid"));
                objectReturn.put("status", -5);
                objectReturn.put("description", "Action not found");
                return objectReturn;
            case -10:
                objectReturn.put("did", idArray.getJSONObject(j).getInt("did"));
                objectReturn.put("piid", idArray.getJSONObject(j).getInt("iid"));
                objectReturn.put("siid", idArray.getJSONObject(j).getInt("siid"));
                objectReturn.put("status", -10);
                objectReturn.put("description", "Property out of range");
                return objectReturn;
            case -8:
                objectReturn.put("did", idArray.getJSONObject(j).getInt("did"));
                objectReturn.put("piid", idArray.getJSONObject(j).getInt("iid"));
                objectReturn.put("siid", idArray.getJSONObject(j).getInt("siid"));
                objectReturn.put("status", -8);
                objectReturn.put("description", "Property is not writeable");
                return objectReturn;
        }
        return objectReturn;
    }
    static JSONObject fillPropertyResponse(int i, int j, JSONObject property,JSONArray idArray) throws JSONException {
        JSONObject objectReturn = new JSONObject();
        switch (i) {
            case 0:
                objectReturn.put("value", property.get("value"));
                objectReturn.put("did", idArray.getJSONObject(j).getInt("did"));
                objectReturn.put("piid", idArray.getJSONObject(j).getInt("iid"));
                objectReturn.put("siid", idArray.getJSONObject(j).getInt("siid"));
                objectReturn.put("status", 0);
                return objectReturn;
        }
        return objectReturn;
    }

    static JSONObject fillResponse(int i, int j, JSONArray idArray, String subscribeId) throws JSONException {
        JSONObject objectReturn = new JSONObject();
        switch (i) {
            case 0:
                objectReturn.put("did", idArray.getJSONObject(j).getInt("did"));
                objectReturn.put("status", 0);
                objectReturn.put("subscribeId", subscribeId);
                return objectReturn;
            case -16:
                objectReturn.put("did", idArray.getJSONObject(j).getInt("did"));
                objectReturn.put("status", -16);
                objectReturn.put("description", "Invalid subscriptionId");
                return objectReturn;
        }
        return objectReturn;
    }

    static JSONObject fillStatusResponse(int i, int j, JSONObject status, JSONArray idArray) throws JSONException {

        JSONObject objectReturn = new JSONObject();
        switch (i) {
            case 0:
                objectReturn.put("did", idArray.getJSONObject(j).getInt("did"));
                objectReturn.put("online", status.getString("status"));
                objectReturn.put("name", status.getString("type"));
                objectReturn.put("status", 0);
                return objectReturn;
            case -1:
                objectReturn.put("did", idArray.getJSONObject(j).getInt("did"));
                objectReturn.put("description", "invalid device id");
                objectReturn.put("status", -1);
                return objectReturn;
        }
        return objectReturn;
    }

    static JSONObject fillActionResponse(int i, int j, JSONObject action, JSONArray idArray) throws JSONException {

        JSONObject objectReturn = new JSONObject();
        switch (i) {
            case 0:
                objectReturn.put("did", idArray.getJSONObject(j).getInt("did"));
                objectReturn.put("siid", idArray.getJSONObject(j).getInt("siid"));
                objectReturn.put("aiid", idArray.getJSONObject(j).getInt("aiid"));
                objectReturn.put("out", action.getJSONArray("out"));
                objectReturn.put("status", 0);
                return objectReturn;
            case -2:
                objectReturn.put("did", idArray.getJSONObject(j).getInt("did"));
                objectReturn.put("aiid", idArray.getJSONObject(j).getInt("aiid"));
                objectReturn.put("siid", idArray.getJSONObject(j).getInt("siid"));
                objectReturn.put("status", -2);
                objectReturn.put("description", "Service not found");
                return objectReturn;
            case -5:
                objectReturn.put("did", idArray.getJSONObject(j).getInt("did"));
                objectReturn.put("aiid", idArray.getJSONObject(j).getInt("aiid"));
                objectReturn.put("siid", idArray.getJSONObject(j).getInt("siid"));
                objectReturn.put("status", -5);
                objectReturn.put("description", "Action not found");
                return objectReturn;
        }
        return objectReturn;
    }
}
