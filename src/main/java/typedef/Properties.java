package typedef;


import org.json.JSONArray;

public class Properties {

    private int iid;

    private String type;

    private String description;

    private Object value;

    private String format;

    private JSONArray access;

    private JSONArray value_range;

    private String unit;

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setIid(int iid) {
        this.iid = iid;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getFormat() {
        return format;
    }

    public int getIid() {
        return iid;
    }

    public Object getValue() {
        return value;
    }

    public JSONArray getAccess() {
        return access;
    }

    public void setAccess(JSONArray access) {
        this.access = access;
    }

    public JSONArray getValue_range() {
        return value_range;
    }

    public void setValue_range(JSONArray value_range) {
        this.value_range = value_range;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
