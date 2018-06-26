package typedef;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ServiceOperation {

    public JSONArray properties = new JSONArray();

    public JSONArray action = new JSONArray();

    public String siid;

    public String type;

    public String description;


    public static List<ServiceOperation> decodeGetService(JSONObject o) {
        List<ServiceOperation> list = new ArrayList<>();
        try {
            JSONArray services = o.getJSONArray("services");
            for (int i = 0; i < services.length(); i++) {
                JSONObject object = services.getJSONObject(i);
                ServiceOperation Service = new ServiceOperation();
                Service.siid = object.getString("iid");
                Service.type = object.getString("type");
                Service.description = object.getString("description");
                Service.properties = object.optJSONArray("properties");
                Service.action = object.optJSONArray("actions");
                list.add(Service);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
