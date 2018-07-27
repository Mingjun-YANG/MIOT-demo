package miot.request;

import org.json.JSONArray;

public class MiotGetPropertiesRequest extends MiotRequest {

    private JSONArray properties;

    public JSONArray getProperties() {
        return properties;
    }

    public void setProperties(JSONArray properties) {
        this.properties = properties;
    }
}
