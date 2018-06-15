package typedef;

import org.json.JSONArray;

public class Services {

    private String siid;

    private String type;

    private String description;

    private JSONArray properties;

    public String getSiid() {
        return siid;
    }

    public void setSiid(String siid) {
        this.siid = siid;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProperties(JSONArray properties) {
        this.properties = properties;
    }

    public JSONArray getProperties() {
        return properties;
    }
}
