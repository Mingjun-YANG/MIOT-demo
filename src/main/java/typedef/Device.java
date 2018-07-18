package typedef;

public class Device {
    private String did;

    private String type;

    public String getDid() {
        return did;
    }

    public String getType() {
        return type;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return did + type;
    }
}
