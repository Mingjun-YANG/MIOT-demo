package typedef;

public class Device {
    private String did;

    private String type;

//    private String subscriptionId;
//
//    public String getSubscriptionId () {
//        return subscriptionId;
//    }
//
//    public void setSubscriptionId (String subscriptionId) {
//        this.subscriptionId = subscriptionId;
//        return;
//    }

    public String getDid() {
        return did;
    }
    public String getType() {
        return type;
    }

    public void setDid(String did) {
        this.did = did;
        return;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return did + type;
    }
}
