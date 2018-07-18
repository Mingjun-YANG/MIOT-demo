package typedef;

public class PropertyRequest {

    private String did;

    private int siid;

    private int piid;

    private Object value;

    public int getPiid() {
        return piid;
    }

    public void setPiid(int piid) {
        this.piid = piid;
    }

    public int getSiid() {
        return siid;
    }

    public void setSiid(int siid) {
        this.siid = siid;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
