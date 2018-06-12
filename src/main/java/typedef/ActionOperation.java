package typedef;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActionOperation extends AbstractOperation {

    public String did;
    public int siid;
    public int piid;
    public List<Object> in = new ArrayList<Object>();
    public List<Object> out = new ArrayList<Object>();
    public static JSONArray argumentIn = new JSONArray();

    public static ActionOperation decodeRequest(JSONObject o) {
        ActionOperation action = new ActionOperation();
        try {
            action.did = o.getString("did");
            action.siid = o.getInt("siid");
            action.piid = o.getInt("piid");
            argumentIn = o.getJSONArray("in");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (argumentIn != null) {
        }

        return action;
    }

    public JSONObject encodeResponse() {
        JSONObject o = new JSONObject();
        try {
            o.put("did", this.did);
            o.put("siid", this.siid);
            o.put("piid", this.piid);
            o.put("status", this.status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (this.status == 0) {
            if (out.size() > 0) {
                try {
                    o.put("out", new JSONArray(out));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

}
