package typedef;


public class Properties {

    private String iid;

    private String type;

    private String description;

    private String value;

    private String format;

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    public void setValue(String value) {
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

    public String getIid() {
        return iid;
    }

    public String getValue() {
        return value;
    }

}
