package typedef;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PropertyOperation extends AbstractOperation {

    private int iid;

    public String type;

    private String description;

    private String format;

    private Object value;

    public static List<PropertyOperation> decodeGetProperty(JSONArray array) {
        List<PropertyOperation> list = new ArrayList<>();
        try {
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                PropertyOperation property = new PropertyOperation();
                property.iid = object.getInt("iid");
                property.type = object.getString("type");
                property.description = object.getString("description");
                property.format = object.getString("format");
                property.value = object.get("value");
                list.add(property);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }


}

